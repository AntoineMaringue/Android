package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import static fr.sciencesu.scannstockmobile.SCANNSTOCK.AbstractUtilsActivity.c;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnexionActivity extends AbstractUtilsActivity {

    private int id_association = -1;
    private GridView gvConnex;

    void setLayoutParams(GridView view) {
        //view.setLayoutParams(new LayoutParams((int)getResources().getDimension(R.dimen.gv_width), 
        //		(int)getResources().getDimension(R.dimen.gv_height)));
        //view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        view.setNumColumns(2);
        view.setColumnWidth((int) getResources().getDimension(R.dimen.gv_column_width));
        view.setVerticalSpacing((int) getResources().getDimension(R.dimen.gv_ver_spacing));
        view.setHorizontalSpacing((int) getResources().getDimension(R.dimen.gv_hor_spacing));
        view.setGravity(Gravity.CENTER);
        view.setStretchMode(GridView.NO_STRETCH);
        view.setPadding((int) getResources().getDimension(R.dimen.gv_padding_left),
                (int) getResources().getDimension(R.dimen.gv_padding_top),
                (int) getResources().getDimension(R.dimen.gv_padding_right),
                (int) getResources().getDimension(R.dimen.gv_padding_bottom));


    }

    private void initializeMenu() {

        //R�cup�ration de la listview cr��e dans le fichier main.xml
        gvConnex = (GridView) findViewById(R.id.gridviewperso);

        setLayoutParams(gvConnex);
        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        listItem.add(createMap("Configuration", String.valueOf(R.drawable.ic_preferences_logo)));
        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre gridview
        listItem.add(createMap("Connexion", String.valueOf(R.drawable.ic_connexion)));
        listItem.add(createMap("Choix association", String.valueOf(R.drawable.ic_choix)));
        listItem.add(createMap("Validation", String.valueOf(R.drawable.ic_validation)));

        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.item_product,
                new String[]{"img", "titre"}, new int[]{R.id.product_img, R.id.product_infos});

        //On attribut � notre listView l'adapter que l'on vient de cr�er
        gvConnex.setAdapter(mSchedule);


        //Enfin on met un �couteur d'�v�nement sur notre listView
        gvConnex.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) gvConnex.getItemAtPosition(position);

                if (map.get("titre").equalsIgnoreCase("Configuration")) {

                    startActivity(new Intent(ConnexionActivity.this, ParametresActivity.class));
                } else if (map.get("titre").equalsIgnoreCase("Connexion")) {
                    new GetTaskBackground("Connexion serveur",getResources().getString(R.string.menu_connexion)).execute();

                } else if (map.get("titre").equalsIgnoreCase("Choix association")) {
                    if (!validation_connexion) {
                        Toast.makeText(context, "Connexion impossible vérifier que le serveur est lancé ou que vous avez une connexion internet", Toast.LENGTH_LONG).show();
                    } else {
                        new GetTaskBackground("Génération des associations", getResources().getString(R.string.menu_choix_association)).execute();
 }
                     
                } else if (map.get("titre").equalsIgnoreCase("Validation")) {
                    if (!validation_connexion) {
                        Toast.makeText(ConnexionActivity.this, "Connexion impossible vérifier que le serveur est lancé ou que vous avez une connexion internet", Toast.LENGTH_LONG).show();
                    } else {
                        context = ConnexionActivity.this;
                       // nameAssociation = listassociations.get(id_association).toString();                            
                        new GetTaskBackground("Liaison ID du stock ", getResources().getString(R.string.menu_liaison)).execute();  
                    }
                }
            }
        });

    }

    private HashMap<String, String> createMap(String string, String valueOf) {
        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
        HashMap<String, String> map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier affichageitem.xml
        map.put("titre", string);
        //on ins�re la r�f�rence � l'image (convertit en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", valueOf);
        return map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations);
        context = ConnexionActivity.this;
        
        
        //startActivity(new Intent(ConnexionActivity.this, Browsable.class));
        initializeMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //context = ConnexionActivity.this;
        id_association = getIntent().getIntExtra("id_association", -1);
        if (listassociations == null || listassociations.isEmpty()) {
            listassociations = getIntent().getStringArrayListExtra("lstAssociations");
        }
        if (id_association != -1) {
            validation_connexion = true;
        }
    }
}