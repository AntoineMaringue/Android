/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.os.Bundle;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

/**
 *
 * @author antoi_000
 */
public class InformationProductActivity extends AbstractUtilsActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        //isbn = getIntent().getExtras().getString("ISBN");
        setContentView(R.layout.main);
        new GetTaskBackground("Chargement", "Recherche produits ....").execute();
    }
    
    
    
}
