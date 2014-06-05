/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

/**
 * 
 * @author antoi_000
 */
public class ProductActivity extends AbstractUtilsActivity {
	static String informationsNewProduct;
	private Button bouton_validation;
	private EditText id_product, brand_product, desc_product, cat_product,qteProduct,
			price_product, url_img,day,month,year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fiche_new_product);
		informationsNewProduct = "";
		id_product = (EditText) findViewById(R.id.id_product);
		brand_product = (EditText) findViewById(R.id.brand_product);
		desc_product = (EditText) findViewById(R.id.desc_product);
		cat_product = (EditText) findViewById(R.id.cat_product);
		price_product = (EditText) findViewById(R.id.price_product);
		url_img = (EditText) findViewById(R.id.url_img);
		qteProduct = (EditText) findViewById(R.id.qte_product);
		day= (EditText) findViewById(R.id.edtDay);
		month= (EditText) findViewById(R.id.edtMonth);
		year= (EditText) findViewById(R.id.edtYear);
		

		bouton_validation = (Button) findViewById(R.id.btn_validation_new_product);

		bouton_validation.setOnClickListener(onClickListener);

	}

	View.OnClickListener onClickListener = new View.OnClickListener() {

		public void onClick(View view) {
			// Prise en compte des informations
			informationsNewProduct = id_product.getText() + ";"
					+ brand_product.getText() + ";" + desc_product.getText()+";" + cat_product.getText()
					+ ";" + price_product.getText() + ";" + url_img.getText()
					+ ";"+day+";"+month+";"+year+";"+qteProduct.toString();
			new GetTaskBackground("Chargement",
					"Creation nouveau produit dans la base ....").execute();
			finish();
		}
	};
}
