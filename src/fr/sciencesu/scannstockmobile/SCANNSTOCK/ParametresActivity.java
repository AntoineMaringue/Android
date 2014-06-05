package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

public class ParametresActivity extends Activity {
	ImageView savedParams;
	EditText port, ip;
	
	private SharedPreferences saves;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.params);
		saves = PreferenceManager.getDefaultSharedPreferences(ParametresActivity.this);
		

		ip = (EditText) findViewById(R.id.edtIp);
		port = (EditText) findViewById(R.id.edtPort);

		
		ip.setText(saves.getString("IP", "192.168.1.1"));
		
		port.setText(saves.getString("PORT", "5000"));

		savedParams = (ImageView) findViewById(R.id.btnSavedParam);

		savedParams.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ScanNStock.__IP = ip.getText().toString();
				ScanNStock.__PORT = port.getText().toString();
				saves = PreferenceManager.getDefaultSharedPreferences(ParametresActivity.this);

				Editor e = saves.edit();
				e.putString("IP", ip.getText().toString());
				e.putString("PORT", port.getText().toString());
				e.commit();
				
				Toast.makeText(
						getApplicationContext(),
						"Changements valides IP : " + ScanNStock.__IP
								+ " Port : " + ScanNStock.__PORT,
						Toast.LENGTH_LONG).show();

				// retour à l'activité de configuration
				ParametresActivity.this.finish();
				/*
				 * ClientBis c = new
				 * ClientBis(ScanNStock.__IP,ScanNStock.__PORT);
				 * 
				 * String data = "123456789"+";"+"69";
				 * 
				 * boolean reply = c.sendToServer(data); if(reply) {
				 * Toast.makeText(getApplicationContext(),
				 * "Produit trouv� et enregistr� dans la base ! " +
				 * ScanNStock.__PORT, Toast.LENGTH_LONG).show();
				 * 
				 * } else { Toast.makeText(getApplicationContext(),
				 * "Le produit  est introuvable !" + ScanNStock.__PORT,
				 * Toast.LENGTH_LONG).show();
				 * 
				 * }
				 */
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
