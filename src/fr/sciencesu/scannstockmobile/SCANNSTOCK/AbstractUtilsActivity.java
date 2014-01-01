/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.MainActivity;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import static fr.sciencesu.scannstockmobile.SCANNSTOCK.ConnexionActivity.c;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoi_000
 */
public abstract class AbstractUtilsActivity extends Activity {

    protected Context context;
    protected ArrayList<Produit> produits;
    protected int num_col;
    protected static Client c;
    protected AlertDialog dialog;
    protected String nameAssociation;
    protected Boolean validation_connexion = false;
    protected GridView grille_produit_form_server;
    protected String isbn;
    protected Handler m_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //final String infosServeur = msg.getData().getString("msg");
            // On peut faire toute action qui met à jour l'IHM
            if (msg.obj.toString().equalsIgnoreCase("associations")) {

                generateLstAssociations(c.getAssociations());
            } else if (msg.obj.toString().equalsIgnoreCase("produits")) {
                try {
                    Thread.sleep(8000);
                    generateLstProducts(c.getProducts());
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnexionActivity.class.getName()).log(Level.SEVERE, null, ex);
                }



            } else if (msg.obj.toString().equalsIgnoreCase("newproduits")) 
            {
                 try {
                    Thread.sleep(8000);
                    c.setEvent(true);
                    c.sendEvent();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnexionActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void generateLstProducts(ArrayList<String> products) {

            if (products != null) {

                produits = new ArrayList<Produit>();
                num_col = 1;//products.size() % 5;
                int x = 0;
                int y = 0;
                while (x < num_col) {
                    Produit p = new Produit();
                    p.setID(products.get(y + 0));
                    p.setName(products.get(y + 1));
                    p.setPrix(products.get(y + 2));
                    p.setContenance(products.get(y + 3));
                    p.setUnite(products.get(y + 4));
                    p.setProduits_stock(products.get(y + 5));
                    p.setDescription(products.get(y + 6));
                    //p.setDlu(products.get(y + 7));
                    //p.setDdp(products.get(y + 8));
                    p.setImgSrc(products.get(y + 8));
                    y += 8;
                    x++;
                    produits.add(p);
                }
                showDialog(1);
            }
        }
    };

    protected static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * Téléchargement des images ...
     */
    protected class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                //e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder;
                Context mContext = this;
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        switch (id) {
            case 1:
                
                View layout = inflater.inflate(R.layout.grid_btnnew, null);
                grille_produit_form_server = (GridView) layout.findViewById(R.id.gridproducts);
                grille_produit_form_server.setNumColumns(num_col);
                ProductAdapter adapter = new ProductAdapter(mContext, produits);
                grille_produit_form_server.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                grille_produit_form_server.invalidateViews();
                grille_produit_form_server.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                        if(dialog!=null)
                            dialog.dismiss();
                    }
                });
                Button btnNewProduct = (Button) findViewById(R.id.btnNewProduct);
                btnNewProduct.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProductActivity.class);
                        startActivity(intent);
                    }
                });
                builder = new AlertDialog.Builder(mContext);
                builder.setView(layout);
                dialog = builder.create();



                break;
                 case 2:
                    View layout2 = inflater.inflate(R.layout.lstassociations, null);
                    ListView list = (ListView)layout2.findViewById(R.id.mylstassociation);
                    list.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                    //list.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,listassociations));
                    ListAdapter adapter2 = new ListAdapter(mContext, listassociations);
                    list.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    list.invalidateViews();
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            

                    public void onItemClick(AdapterView<?> av, View view, int i, long l) 
                    {
                        Toast.makeText(context, "Position "+i, Toast.LENGTH_SHORT).show();
                        nameAssociation = listassociations.get(i);
                        c.setId(i + "");
                        if(dialog!=null)
                            dialog.dismiss();

                    }
                });
                builder = new AlertDialog.Builder(mContext);
                builder.setView(layout2);
                dialog = builder.create();
                     break;
            default:
                dialog = null;
        }
        return dialog;
    }

    protected class GetTaskBackground extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog;
        private String title, subject;

        public GetTaskBackground(String title, String subject) {
            this.title = title;
            this.subject = subject;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(
                    context,
                    title,
                    subject,
                    true);
        }

        @Override
        protected String doInBackground(Void... params) {
            if (c == null) {
                c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                Thread t = new Thread(c);
                t.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }

            if (validation_connexion = c.isIsConnected()) {
                if (this.subject.contains("associations")) {
                    generateAssociation();
                } else if (this.subject.contains("produits")) {
                    generateProducts();
                }else if (this.subject.contains("nouveau produit")) {
                    generateNewProducts();
                }  
                else if (this.subject.contains("Liaison")) {
                    openMenuAccueil();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog != null) {
                dialog.dismiss();
                dialog.cancel();
            }
        }

        
        private void generateAssociation() {

            c.data = "2";
            c.setEvent(true);
            m_handler.obtainMessage();
            Message msg = new Message();
            msg.obj = "associations";
            long time = 10000;
            m_handler.sendMessageDelayed(msg, time);
            //m_handler.sendMessage(msg);
            try {
                Thread.sleep(12000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void openMenuAccueil() {
            c.data = "1";
            c.setEvent(true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                System.err.println(ex.toString());
            }
            String datasServer = c.getResponseLine();

            //Enregistrements du site et du stock lié au site
            ScanNStock.__SITE = nameAssociation;
            //Insertion du stock de l'association
            ScanNStock.__STOCK = Integer.parseInt(c.getId()) + "";

            //Ouverture de la page principale
            Intent intent = new Intent(context, ListViewCustomActivity.class);
            startActivity(intent);
        }
    }

    private void generateProducts() {

        c.setISBN(isbn);
        c.setIdStock(ScanNStock.__STOCK);
        c.data = "4";
        c.setEvent(true);
        m_handler.obtainMessage();
        Message msg = new Message();
        msg.obj = "produits";
        long time = 7000;
        m_handler.sendMessageDelayed(msg, time);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void generateNewProducts() {

        c.setISBN(isbn);
        c.setIdStock(ScanNStock.__STOCK);
        c.data = "5";
        c.setEvent(true);
        m_handler.obtainMessage();
        Message msg = new Message();
        msg.obj = "newproduits";
        long time = 7000;
        m_handler.sendMessageDelayed(msg, time);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    Intent intentGetAssociations;
    protected ArrayList<String> listassociations;

    protected void generateLstAssociations(Collection<String> data) {

        listassociations = new ArrayList<String>();
        for (String string : data) {
            listassociations.add(string);
        }
        showDialog(2);
        //intentGetAssociations = new Intent(context, ListAssociationActivity.class);
        //intentGetAssociations.putStringArrayListExtra("lstAssociations", listassociations);
        //startActivity(intentGetAssociations);


    }

    
}
