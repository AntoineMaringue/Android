package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Initialisation d'une webview abstraite pr�param�tr� pour r�pondre au mieux aux exigences de la TV
 * 
 * @author Antoine
 *
 */
public abstract class AbstractBrowsable extends Activity{
	
		/**
		 * Largeur de l'�cran par d�faut
		 */
		protected static final int defaut_largeur = 1280;
		/**
		 * Hauteur de l'�cran par d�faut
		 */
		protected static final int defaut_hauteur =  720;
		/**
		 * Notre WebBrowser principale
		 */
		protected WebView browser;
		/**
		 * L'URL inclus dans la webview
		 */
		protected String url;		
		/**
		 * Le context de l'activity
		 */
		protected final Activity context=this;


		/**
		 * Configuration de la webview pour avoir un affichage ad�quate et n'avoir
		 * que les fonctionnalit�s indispensable
		 * 
		 * @author Antoine
		 */
		protected void configurationSettings()
		{
	
			if(browser != null)
			{
				// Activation du javascript
				browser.getSettings().setJavaScriptEnabled(true);
	
				String DESKTOP_USERAGENT = browser.getSettings().getUserAgentString();
				
				// Initialisation du user agent
				DESKTOP_USERAGENT = DESKTOP_USERAGENT.replace("Mobile ", "");
				browser.getSettings().setUserAgentString(DESKTOP_USERAGENT);
	
				// Mise en place de la taille du texte
				browser.getSettings().setTextZoom(90);
	
				// Autres param�tres
				browser.getSettings().setLoadWithOverviewMode(true);
				browser.getSettings().setUseWideViewPort(true);
				browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
				browser.getSettings().setLoadWithOverviewMode(true);
				browser.getSettings().setDefaultZoom(ZoomDensity.FAR);
				browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
				browser.setScrollbarFadingEnabled(false);
				browser.getSettings().setSupportZoom(false);
				browser.getSettings().setBuiltInZoomControls(false);
				browser.getSettings().setLightTouchEnabled(false);
				
				//Activation de l'acc�l�ration mat�riel
				browser.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
				
				//browser.setLayerType(View.LAYER_TYPE_HARDWARE, null);
				//browser.getGlobalVisibleRect(false);
                                browser.requestFocus(View.FOCUS_DOWN);
                                browser.setOnTouchListener(new View.OnTouchListener() { 

                                    public boolean onTouch(View view, MotionEvent me) {
                                          switch (me.getAction()) { 
               case MotionEvent.ACTION_DOWN: 
               case MotionEvent.ACTION_UP: 
                   if (!view.hasFocus()) { 
                       view.requestFocus(); 
                   } 
                   break; 
           } 
           return false; }
                                });
				
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				
				// On r�cup�re la taille de l'�cran
				Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
						.getDefaultDisplay();
				Point size = new Point();
				int width=defaut_largeur;
				int height=defaut_hauteur;
				try 
				{ 
					width = display.getWidth(); height = display.getHeight(); 
					//R�cup�ration des dimensions r�elle de la TV
					//display.getSize(size); 
					//width = size.x; 
					//height = size.y; 
				} 
				catch (NoSuchMethodError e) 
				{ 
					//width = display.getWidth(); height = display.getHeight(); 
				} 
				
				 //browser.setFocusable(false);
		         //browser.setFocusableInTouchMode(false);
		        /* browser.setHovered(false);
		         browser.setSelected(false);
		         browser.setOnTouchListener(new View.OnTouchListener() { 
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {           
		                switch (event.getAction()) { 
		                   //case MotionEvent.ACTION_DOWN: 
		                   case MotionEvent.ACTION_UP: 
		                       if (!v.hasFocus()) { 
		                           v.requestFocus(); 
		                       } 
		                       break; 
		               } 
		               return false; 
		            }
		            
		    });
		    
		    *browser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	
	            public void onFocusChange(View v, boolean hasFocus) {
	                v.setFocusable(false);
	                v.setFocusableInTouchMode(false);
	            }
	            
	            
	        });*/
				// On calcul la taille ad�quate de la page
				double globalScale = Math.ceil((width / defaut_largeur) * 90);
	
				// On enl�ve les possibilit�s de zoom
				this.browser.getSettings().setBuiltInZoomControls(false);
				this.browser.getSettings().setSupportZoom(false);
	
				
				// On charge la page � la bonne �chelle
				this.browser.setInitialScale((int) globalScale);
				
					//Activation barre de chargement 
					browser.setWebChromeClient(new WebChromeClient() {
						public void onProgressChanged(WebView view, int progress) {
							
								context.setTitle("Chargement...");
								context.setProgress(progress * 100);
			
								if (progress == 100)
									context.setTitle(url!=null?url:"");
							
						}
					});
				
				
				//R�cup�ration page ou erreur
				browser.setWebViewClient(new WebViewClient() {
					@Override
					public void onReceivedError(WebView view, int errorCode,
							String description, String failingUrl) {
						// Handle the error
					}
	
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						view.loadUrl(url);
						return true;
					}
				});
			}
		}	
		
		/**
		 * Lancement de la webview
		 */
		protected void launch()
		{
			if(browser != null)
				browser.loadUrl(url);
		}	
		
		

	


}
