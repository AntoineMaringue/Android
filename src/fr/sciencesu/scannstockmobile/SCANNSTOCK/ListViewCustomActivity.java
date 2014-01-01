package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import fr.sciencesu.scannstockmobile.SCANNEUR.CaptureActivity;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import static fr.sciencesu.scannstockmobile.SCANNSTOCK.AbstractUtilsActivity.c;

public class ListViewCustomActivity extends AbstractUtilsActivity {

    private ListView maListViewPerso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);
        context = ListViewCustomActivity.this;
        init();

    }

    private HashMap<String, String> createMap(String string, String string2, String valueOf) {
        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
        HashMap<String, String> map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier affichageitem.xml
        map.put("titre", string);
        //on ins�re un �l�ment description que l'on r�cup�rera dans le textView description cr�� dans le fichier affichageitem.xml
        map.put("description", string2);
        //on ins�re la r�f�rence � l'image (convertit en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", valueOf);
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = ListViewCustomActivity.this;
    }

    private void init() {
    //R�cup�ration de la listview cr��e dans le fichier main.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);

        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        listItem.add(createMap("Scan", getResources().getString(R.string.Scan), String.valueOf(R.drawable.ic_scan)));

        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre ListView
        listItem.add(createMap("Paramètres", getResources().getString(R.string.Parameters), String.valueOf(R.drawable.ic_parametres)));
        listItem.add(createMap("Aide", getResources().getString(R.string.Aide), String.valueOf(R.drawable.ic_create)));
        listItem.add(createMap("Contact", getResources().getString(R.string.Contacts), String.valueOf(R.drawable.ic_contact)));
        listItem.add(createMap("Sortir", getResources().getString(R.string.Sortir), String.valueOf(R.drawable.ic_exit)));

        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"img", "titre", "description"}, new int[]{R.id.img, R.id.titre, R.id.description});

        //On attribut � notre listView l'adapter que l'on vient de cr�er
        maListViewPerso.setAdapter(mSchedule);

        //Enfin on met un �couteur d'�v�nement sur notre listView
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);

                if (map.get("titre").equalsIgnoreCase("scan")) {
                    Intent intent = new Intent(ListViewCustomActivity.this, CaptureActivity.class);
                    startActivity(intent);
                } else if (map.get("titre").equalsIgnoreCase("Paramètres")) {
                    //Intent intent = new Intent(ListViewCustomActivity.this, ParametresActivity.class);
                    //startActivity(intent);
                    //Huile --> 8002470000674
                    //Nutella --> 3017620429484
                    //Vittel --> 3048431001545
                    isbn = "3048431001545";
                    //Toast.makeText(getApplicationContext(), "Envoi " + isbn + " to " + ScanNStock.__IP
                    //      + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();

                    //Création du client pour envoyer les données au serveur de création de produit
                    if (c == null) {
                        c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                        Thread t = new Thread(c);
                        t.start();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            System.err.println(ex.getMessage());
                        }
                        //String datasServer = c.getResponseLine();
                        // Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();
                    }
                    
                    //startActivity(new Intent(ListViewCustomActivity.this,AudioPlayerActivity.class));
                    new GetTaskBackground("Chargement", "Recherche produits ....").execute();
                } else if (map.get("titre").equalsIgnoreCase("Contact")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ListViewCustomActivity.this);
                    //on attribut un titre � notre boite de dialogue
                    adb.setTitle("Contact");
                    //on ins�re un message � notre boite de dialogue, et ici on affiche le titre de l'item cliqu�
                    adb.setMessage("CONTACT DE L'APPLICATION : ");
                    //on indique que l'on veut le bouton ok � notre boite de dialogue
                    adb.setPositiveButton("Ok", null);
                    //on affiche la boite de dialogue
                    adb.show();
                } else if (map.get("titre").equalsIgnoreCase("Sortir")) {
                    System.exit(1);
                } else if (map.get("titre").equalsIgnoreCase("Aide")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(ListViewCustomActivity.this);
                    //on attribut un titre � notre boite de dialogue
                    adb.setTitle("Aide");
                    //on ins�re un message � notre boite de dialogue, et ici on affiche le titre de l'item cliqu�
                    adb.setMessage("AIDE DE L'APPLICATION : ");
                    //on indique que l'on veut le bouton ok � notre boite de dialogue
                    adb.setPositiveButton("Ok", null);
                    //on affiche la boite de dialogue
                    adb.show();
                }
            }
        });    
    
    }
}