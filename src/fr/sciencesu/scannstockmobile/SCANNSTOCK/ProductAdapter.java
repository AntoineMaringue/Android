/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

/**
 * Adapteur de produit
 * 
 * @author antoi_000
 */
public class ProductAdapter extends ArrayAdapter<Produit> {

	private List<Produit> produits;
	private Context context;
	private LayoutInflater inflater;
	private TextView title;
	private ImageView image;

	public ProductAdapter(Context context, ArrayList<Produit> produits) {
		super(context, 0, produits);
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.produits = produits;
	}

	@Override
	public int getCount() {
		if (null != produits) {
			return produits.size();
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
		final Produit product = this.produits.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.item_product, null);

		title = (TextView) convertView.findViewById(R.id.product_infos);
		image = (ImageView) convertView.findViewById(R.id.product_img);

		new DownloadImageTask(image).execute(product.getImgSrc());

		String text = correct(product.getName()+"\n")+
				//correct(product.getMarque()+"\n")+
				correct(product.getDescription()+"\n")+
				//correct(product.getPrix()+"\n")+
				correct(product.getUnite())+" "+correct(product.getContenance());

		title.setText(text);

		return convertView;
	}

	private String correct(String name) {
		return (name!=null)?name:"";
		
	}

	/**
	 * Téléchargement asynchrone des images 
	 * 
	 * @author Antoine
	 *
	 */
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
}
