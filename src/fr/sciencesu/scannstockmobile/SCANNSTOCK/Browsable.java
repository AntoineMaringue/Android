/**
 * 
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;


import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import fr.sciencesu.scannstockmobile.SCANNEUR.R;

/**
 * @author Antoine
 * 
 *         Activity lanc�e lorsque l'utilisateur choisit un site pr�d�fini dans
 *         la cat�gorie Web Mode plein ecran pour avoir une s�curit� au niveau
 *         du site web (Pas de navigation changement d'url)
 */
public class Browsable extends AbstractBrowsable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.browser);
		browser = (WebView) findViewById(R.id.browser);
                
		url = "https://news.google.fr/nwshp?hl=fr&tab=wn&ei=neRSUraBGsel0wX6lIDQBg&ved=0CAsQqS4oBQ";

		// Mode configuration de la webview
		configurationSettings();			

		// Lancement de la webview avec l'url ad�quate
		launch();
	}	

}
