/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antoi_000
 */
public class ListAdapter extends ArrayAdapter<String>
{
 private List<String> associations;
    private Context context;
    private LayoutInflater inflater;
    private TextView title;
    private ImageView image;
    private static int sIconWidth = -1;
    private static int sIconHeight = -1;
    private static int sIconTextureWidth = -1;
    private static int sIconTextureHeight = -1;
    private static final Paint sBlurPaint = new Paint();
    private static final Paint sGlowColorPressedPaint = new Paint();
    private static final Paint sGlowColorFocusedPaint = new Paint();
    private static final Paint sDisabledPaint = new Paint();
    private static final Rect sOldBounds = new Rect();
    private static final Canvas sCanvas = new Canvas();
    static int sColors[] = {0xffff0000, 0xff00ff00, 0xff0000ff};
    static int sColorIndex = 0;

    public ListAdapter(Context context, ArrayList<String> produits) {
        super(context, 0, produits);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.associations = produits;
    }

    private static void initStatics(Context context) {
        final Resources resources = context.getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        final float density = metrics.density;

        sIconWidth = sIconHeight = (int) resources.getDimension(80);
        sIconTextureWidth = sIconTextureHeight = sIconWidth - 2;

        sBlurPaint.setMaskFilter(new BlurMaskFilter(5 * density, BlurMaskFilter.Blur.NORMAL));
        sGlowColorPressedPaint.setColor(0xffffc300);
        sGlowColorFocusedPaint.setColor(0xffff8e00);


        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.2f);
        sDisabledPaint.setColorFilter(new ColorMatrixColorFilter(cm));
        sDisabledPaint.setAlpha(0x88);
    }

    
    static Bitmap createIconBitmap(Drawable icon, Context context) {

        if (sIconWidth == -1) {
            initStatics(context);
        }

        int width = sIconWidth;
        int height = sIconHeight;


        if (icon instanceof PaintDrawable) {
            PaintDrawable painter = (PaintDrawable) icon;
            painter.setIntrinsicWidth(width);
            painter.setIntrinsicHeight(height);
        } else if (icon instanceof BitmapDrawable) {
            // Ensure the bitmap has a density.
            BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
            }
        }
        int sourceWidth = icon.getIntrinsicWidth();
        int sourceHeight = icon.getIntrinsicHeight();
//            System.out.println("sourceWidth=="+sourceWidth+"sourceHeight"+sourceHeight);
        if (sourceWidth > 0 && sourceWidth > 0) {
            // There are intrinsic sizes.
            if (width < sourceWidth || height < sourceHeight) {
                // It's too big, scale it down.
                final float ratio = (float) sourceWidth / sourceHeight;
                if (sourceWidth > sourceHeight) {
                    height = (int) (width / ratio);
                } else if (sourceHeight > sourceWidth) {
                    width = (int) (height * ratio);
                }
            } else if (sourceWidth < width && sourceHeight < height) {
                // It's small, use the size they gave us.
                width = sourceWidth;
                height = sourceHeight;
            }
        }
//            System.out.println("width=="+width+"height"+height);
        // no intrinsic size --> use default size
        int textureWidth = sIconTextureWidth;
        int textureHeight = sIconTextureHeight;

        final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);

        final int left = (textureWidth - width) / 2;
        final int top = (textureHeight - height) / 2;
        /*
         if (false) {
         // draw a big box for the icon for debugging
         canvas.drawColor(sColors[sColorIndex]);
         if (++sColorIndex >= sColors.length) sColorIndex = 0;
         Paint debugPaint = new Paint();
         debugPaint.setColor(0xffcccc00);
         canvas.drawRect(left, top, left+width, top+height, debugPaint);
         }
         */
        sOldBounds.set(icon.getBounds());
        icon.setBounds(left, top, left + width, top + height);
        icon.draw(canvas);
        icon.setBounds(sOldBounds);

        return bitmap;

    }

    @Override
    public int getCount() {
        if (null != associations) {
            return associations.size();
        } else {
            return 0;
        }
    }

   
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String product = this.associations.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.items_association, null);
        title = (TextView) convertView.findViewById(R.id.association_infos);
        title.setText(product.toString());
        return convertView;
    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            System.err.println("Error Img source");
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();

        /*        int fgWidth = foreground.getWidth();   
         int fgHeight = foreground.getHeight();   
         System.out.println("bgwidht="+bgHeight+"bgheight"+bgWidth);
         System.out.println("fgwidht="+fgHeight+"fgheight"+fgWidth);*/

        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        cv.drawBitmap(background, 0, 0, null);
        cv.drawBitmap(foreground, 9, 9, null);
//        cv.save(Canvas.ALL_SAVE_FLAG);    
//        cv.restore();
        return newbmp;
    }   
}
