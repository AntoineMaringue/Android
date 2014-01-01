package fr.sciencesu.scannstockmobile.SCANNEUR;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener  {

	
	private Button boutton_photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		boutton_photo = (Button)findViewById(R.id.boutton_photo);
		boutton_photo.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
public void onClick(View v) 
{
		if(v==boutton_photo)
		{
			Intent i = new Intent(MainActivity.this,CaptureActivity.class);
	        startActivity(i);
		}
		
		/*Sortir du jeu*/
		//else if(v==boutton_quitter){
		//	finish();
		//}
		
	}

}
