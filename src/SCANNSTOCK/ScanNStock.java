package SCANNSTOCK;

import java.util.ArrayList;

import API.ShoppingApi;
import DAO.Base;
import DAO.IBase;

public class ScanNStock implements IScanNStock
{

	private IBase _basePersoScanNStock;
	private ShoppingApi _shop;
	private String _productName;
	private ArrayList<String> _values;
	private String _tableName;
	
	public ScanNStock()
	{
		_shop = new ShoppingApi();		
		_basePersoScanNStock = new Base();
	}
	
	public ScanNStock(String nameProductSearch)
	{
		_shop = new ShoppingApi();	
		_basePersoScanNStock = new Base();
		_productName = nameProductSearch;
	}

	@Override
	public void takePhoto() 
	{
		//Fonction Andro�d
	}

	@Override
	public void scanBarCode() 
	{
		//_scanner.runScan(false);
	}

	@Override
	public void getInfosProduct() 
	{
		String ean = "";//_scanner.getCodeEAN();
		_shop.runSearchProduct(ean,_productName);
	}

	@Override
	public void InsertToBase() 
	{
		_basePersoScanNStock.connection();
		_basePersoScanNStock.insertInto(_tableName, _values);
		_basePersoScanNStock.deconnection();
	}

	public void launch() 
	{
		takePhoto();
		scanBarCode();
		getInfosProduct();
		InsertToBase();
	}
}
