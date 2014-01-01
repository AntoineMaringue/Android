package fr.sciencesu.scannstockmobile.SCANNEUR;

import android.content.Intent;
import android.content.pm.PackageManager;
import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;

import fr.sciencesu.scannstockmobile.SCANNSTOCK.ScanNStock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.Client;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.ListViewCustomActivity;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaptureActivity extends DecoderActivity {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet.of(ResultMetadataType.ISSUE_NUMBER, ResultMetadataType.SUGGESTED_PRICE,
            ResultMetadataType.ERROR_CORRECTION_LEVEL, ResultMetadataType.POSSIBLE_COUNTRY);
    private TextView statusView = null;
    private View resultView = null;
    private boolean inScanMode = false;
    private Button btn;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.capture);
        context = CaptureActivity.this;
        //Log.v(TAG, "onCreate()");

        resultView = findViewById(R.id.result_view);
        statusView = (TextView) findViewById(R.id.status_view);

        inScanMode = false;

        btn = (Button) findViewById(R.id.btn_search);
        btn.setEnabled(false);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(CaptureActivity.this,ListViewCustomActivity.class));

                //Toast.makeText(getApplicationContext(), "Envoi " + isbn + " to " + ScanNStock.__IP
                  //      + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();


                //Les données envoyés au serveur
                // ISBN le code scanné
                // Choice Département pour trouvé le bon stock dans la base de données
                //String data = isbn + ";" + (LocalisationActivity.choiceDepartement) != null ? LocalisationActivity.choiceDepartement : "69";

                //Création du client pour envoyer les données au serveur de création de produit


                /*if (c == null) {
                    c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                    Thread t = new Thread(c);
                    t.start();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String datasServer = c.getResponseLine();
                    Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();
                    
                    btn.setVisibility(View.GONE);
                } 
                * */
                /*c.setISBN(isbn);
                 c.setIdStock(ScanNStock.__STOCK);
                 c.data = "3";
                 c.setEvent(true);
                 try {
                 Thread.sleep(15000);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 Toast.makeText(getApplicationContext(), c.getResponseLine(), Toast.LENGTH_LONG).show();*/
                

                //IntentIntegrator integrator = new IntentIntegrator(ZXingTestActivity.this);
                //integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });
        
        


    }
 
    public static Camera cam = null;
    public void flashLightOn(View view) {

    try {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
            cam = Camera.open();
            Parameters p = cam.getParameters();
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
        }
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                Toast.LENGTH_SHORT).show();
    }
}

public void flashLightOff(View view) {
    try {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(getBaseContext(), "Exception flashLightOff",
                Toast.LENGTH_SHORT).show();
    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
        context = CaptureActivity.this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inScanMode) {
                finish();
            } else {
                onResume();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode) {
        drawResultPoints(barcode, rawResult);

        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
        handleDecodeInternally(rawResult, resultHandler, barcode);
    }

    protected void showScanner() {
        inScanMode = true;
        resultView.setVisibility(View.GONE);
        statusView.setText(R.string.msg_default_status);
        statusView.setVisibility(View.VISIBLE);
        viewfinderView.setVisibility(View.VISIBLE);
    }

    protected void showResults() {
        inScanMode = false;
        statusView.setVisibility(View.GONE);
        viewfinderView.setVisibility(View.GONE);
        resultView.setVisibility(View.VISIBLE);
    }

    // Put up our own UI for how to handle the decodBarcodeFormated contents.
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        onPause();
        showResults();

        btn.setEnabled(true);
        btn.setVisibility(View.VISIBLE);
        isbn = resultHandler.getDisplayContents().toString().trim();
        btn.setText("Recherche " + isbn);

        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
        if (barcode == null) {
            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        } else {
            barcodeImageView.setImageBitmap(barcode);
        }

        TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
        formatTextView.setText(rawResult.getBarcodeFormat().toString());

        TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
        typeTextView.setText(resultHandler.getType().toString());

        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
        TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
        timeTextView.setText(formattedTime);

        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
        View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
        metaTextView.setVisibility(View.GONE);
        metaTextViewLabel.setVisibility(View.GONE);
        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
        String myNotification = "";
        if (metadata != null) {
            StringBuilder metadataText = new StringBuilder(20);
            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
                
            ResultMetadataType cle = entry.getKey();
            Object valeur = entry.getValue();
            myNotification += "KEY = " + cle + "VALUE = " + valeur;
            
            if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
                    metadataText.append(entry.getValue()).append('\n');
                }
            }
            if (metadataText.length() > 0) {
                metadataText.setLength(metadataText.length() - 1);
                metaTextView.setText(metadataText);
                metaTextView.setVisibility(View.VISIBLE);
                metaTextViewLabel.setVisibility(View.VISIBLE);
            }
        }
        //Toast.makeText(context, myNotification, Toast.LENGTH_LONG).show();
        TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
        CharSequence displayContents = resultHandler.getDisplayContents();
        contentsTextView.setText(displayContents);
        // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);

        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
    }
}
