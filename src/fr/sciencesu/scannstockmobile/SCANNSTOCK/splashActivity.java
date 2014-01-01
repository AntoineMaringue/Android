package fr.sciencesu.scannstockmobile.SCANNSTOCK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

public class splashActivity extends Activity
{
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 1000;

   
    private final transient Handler splashHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == STOPSPLASH)
            {
                final Intent intent = new Intent(splashActivity.this, ConnexionActivity.class);
                startActivity(intent);
                finish();
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
}