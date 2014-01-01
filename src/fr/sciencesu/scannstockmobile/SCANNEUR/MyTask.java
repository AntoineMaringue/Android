/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNEUR;

/**
 *
 * @author antoi_000
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

public class MyTask extends AsyncTask<String, MyStatus, MyResult> {

    private Activity act;
    private ProgressDialog dialog;
    private OnMyTaskDone callback;
    private String name;

    public interface OnMyTaskDone {

        void onMyTaskDone(int result);
    }

    public MyTask(Activity _act, OnMyTaskDone _callback) {
        act = _act;
        callback = _callback;
    }

    @Override
    protected void onPostExecute(MyResult result) {
        dialog.dismiss();
        switch (result) {
            case RESULT_OK:
                if (callback != null) {
                    callback.onMyTaskDone(name.length());
                }
                break;
            case RESULT_ERROR:
            case RESULT_FATAL_ERROR:
                Dialog.showMessage(act, "Une erreur est survenue");
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(act, "Chargement",
                "Chargement en cours...", true, true, new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                cancel(true);
            }
        });
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(act, "Requête annulée.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(MyStatus... values) {
        switch (values[0]) {
            case COMPUTING:
                dialog.setMessage("Calculs en cours...");
                break;
            case FINISHED:
                dialog.setMessage("Calculs terminés.");
                break;
        }
    }

    @Override
    protected MyResult doInBackground(String... params) {
        try {
            name = params[0];
            Thread.sleep(2000);
            publishProgress(MyStatus.COMPUTING);
            Thread.sleep(2000);
            publishProgress(MyStatus.FINISHED);
            Thread.sleep(1000);
            return MyResult.RESULT_OK;
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            return MyResult.RESULT_FATAL_ERROR;
        }
    }
}
