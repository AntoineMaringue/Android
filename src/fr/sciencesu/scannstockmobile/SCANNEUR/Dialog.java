/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNEUR;

/**
 *
 * @author antoi_000
 */
import android.app.AlertDialog;
import android.content.Context;

public class Dialog {

    public static void showMessage(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Fermer", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
