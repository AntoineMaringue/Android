package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
public class ParametresActivity extends Activity
{
	Button savedParams;
	EditText port,ip;
	
        @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.params);

		ip = (EditText)findViewById(R.id.edtIp);
		port = (EditText)findViewById(R.id.edtPort);
                
                if(ScanNStock.__IP != null)
                    ip.setText(ScanNStock.__IP);
                if(ScanNStock.__PORT != null)
                    port.setText(ScanNStock.__PORT);
		
		savedParams = (Button) findViewById(R.id.btnSavedParams);

		savedParams.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				ScanNStock.__IP = ip.getText().toString();		    	
				ScanNStock.__PORT = port.getText().toString();
				Toast.makeText(getApplicationContext(), "Changements valides IP : " + ScanNStock.__IP + " Port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
			
                                //retour à l'activité de configuration 
                                ParametresActivity.this.finish();
				/*ClientBis c = new ClientBis(ScanNStock.__IP,ScanNStock.__PORT);
				
				String data = "123456789"+";"+"69";
				
				boolean reply = c.sendToServer(data);
				if(reply)
				{
					Toast.makeText(getApplicationContext(), "Produit trouv� et enregistr� dans la base ! " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Le produit  est introuvable !" + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
					
				}*/
			}
		});
	}
        
        @Override
    protected void onResume() {
        super.onResume(); 
    }

}
