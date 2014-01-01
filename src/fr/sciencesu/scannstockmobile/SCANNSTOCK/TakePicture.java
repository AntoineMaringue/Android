package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

public class TakePicture extends Activity implements SurfaceHolder.Callback
{
	private Camera camera;
    private boolean isPreviewRunning = false;
    private SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    private int REQUEST_CODE_IMAGE_GALLERY = 1;
    
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Handler mAutoFocusHandler;
    private int mAutoFocusMessage;
    
    private Uri taken;
    private String name;
    private OutputStream filoutputStream;

    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        Log.e(getClass().getSimpleName(), "onCreate");
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.camera);
        surfaceView = (SurfaceView)findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        ImageButton close = (ImageButton) findViewById(R.id.takepicture);
        close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				takeThePicture();
			}
        });
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }

    Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera c) {
            Log.e(getClass().getSimpleName(), "PICTURE CALLBACK RAW: " + data);
            camera.startPreview();
        }
    };
    
    Camera.PictureCallback mPictureCallbackJpeg = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera c) {
        	try {
    			Log.v(getClass().getSimpleName(), "onPictureTaken=" + data + " length = " + data.length);
    			filoutputStream.write(data);
    			filoutputStream.flush();
    			filoutputStream.close();
    			
    		} catch(Exception ex) {
    		}
        }
    };
    
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
    	public void onShutter() {
    		Log.e(getClass().getSimpleName(), "SHUTTER CALLBACK");
    	}
    };
    
    private void takeThePicture ()
    {
    	try {
    		String filename = "takePicture" + timeStampFormat.format(new Date());
    		String fl = filename + ".jpg";
    		ContentValues values = new ContentValues();
    		values.put(Media.TITLE, fl);
    		values.put(Media.DISPLAY_NAME, fl);
    		values.put(Media.DESCRIPTION, "Image capture by camera for TakePicture");
    		long dt = new Date().getTime();
    		values.put(Media.DATE_TAKEN, dt);
    		values.put(Media.MIME_TYPE, "image/jpeg");
    		
    		name = "/sdcard/DCIM/Camera/" + String.valueOf(dt) + ".jpg";
    		
    		taken = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    		
    		filoutputStream = getContentResolver().openOutputStream(taken);
    		
    		camera.takePicture(mShutterCallback, mPictureCallbackRaw, mPictureCallbackJpeg);
    	} catch(Exception ex ){
    		ex.printStackTrace();
    		Log.e(getClass().getSimpleName(), ex.getMessage(), ex);
    	}
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
    	{
    		takeThePicture ();
    		return true;
    	}
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.camera, menu);
    	return true;
    }
    

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
	        case R.id.take:
	        	takeThePicture ();
				return true;
	        case R.id.pictures:
	        	Intent activityIntent = new Intent();
	    		activityIntent.setAction("android.intent.action.GET_CONTENT");
	    		activityIntent.setType("image/*");
	    		startActivityForResult(activityIntent, REQUEST_CODE_IMAGE_GALLERY);
				return true;
        }
        return true;
    }

    protected void onResume()
    {
        Log.e(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    protected void onStop()
    {
        Log.e(getClass().getSimpleName(), "onStop");
        super.onStop();
    }
    
    public void requestAutoFocus(Handler handler, int message)
    {
		if (camera != null)
		{
			mAutoFocusHandler = handler;
			mAutoFocusMessage = message;
			camera.autoFocus(autoFocusCallback);
		}
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.e(getClass().getSimpleName(), "surfaceCreated");
        camera = Camera.open();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {
        Log.e(getClass().getSimpleName(), "surfaceChanged");
        if (isPreviewRunning) {
            camera.stopPreview();
        }
        Camera.Parameters p = camera.getParameters();
        p.setPreviewSize(w, h);
        camera.setParameters(p);
        try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
		}
        camera.startPreview();
        isPreviewRunning = true;
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.e(getClass().getSimpleName(), "surfaceDestroyed");
        camera.stopPreview();
        isPreviewRunning = false;
        camera.release();
    }
    
    private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
          if (mAutoFocusHandler != null) {
            Message message = mAutoFocusHandler.obtainMessage(mAutoFocusMessage, success);
            mAutoFocusHandler.sendMessageDelayed(message, 1500);
            mAutoFocusHandler = null;
          }
        }
      };
}