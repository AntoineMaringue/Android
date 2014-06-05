/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 
 * @author Antoine
 */

public class Client implements Runnable {

	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Object message;
	private PipedOutputStream output = new PipedOutputStream();
	public PrintWriter writer = new PrintWriter(output);
	public static String traitement = "";
	private String ip;
	private int port;
	public String data = "";
	public String response = "";
	private String id, mdp, idStock;
	private String isbn;
	private ArrayList<String> associations;
	private ConnexionActivity c;

	public Client(String IP, int Port) {
		ip = IP;
		port = Port;
		associations = new ArrayList();
		produits = new ArrayList<String>();
	}

	private boolean isConnected;

	public boolean isIsConnected() {
		return isConnected;
	}

	public void run() {
		try {

			// Création de la socket de connection au serveur
			requestSocket = new Socket(ip, port);
			// informClientIHM("Connexion serveur en cours","start");
			if (requestSocket.isConnected()) {
				isConnected = true;
			} else {
				isConnected = false;
				return;
			}
			System.out.println("Connected to " + ip + " in port " + port + "");
			// Ouverture des streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// informClientIHM("Connexion serveur etablie","stop");
			// Communication établie
			do {
				try {
					// Envoi en permannence
					message = in.readObject();
					// message = ois.readObject();

					if (!message.equals("") && !message.equals("_")) {
						setResponseLine(message.toString());
					}

					if (!message.toString().equals("")
							&& !message.toString().equals("_")
							&& message.toString().contains("ASSS")) {
						setAssociations(message.toString().split(",")[1]);
					}

					if (!message.toString().equals("")
							&& !message.toString().equals("_")
							&& message.toString().contains("PRODUCTS")) {
						setProducts(message.toString());
					}

					if (!message.equals("_")) {
						System.out.println("server>" + message);
						System.out.println("traitement :");

					}

					// Si validation envoi message
					if (sendEvent()) {

						switch (data.charAt(0)) {
						case 'e': {
							message = "bye";
							sendMessage(message);
							setEvent(false);
							break;
						}
						case '1': {

							message = "Validation," + getId();
							sendMessage(message);
							setEvent(false);
							// informClientIHM("Connexion BDD en cours","start");

							break;
						}
						case '2': {

							message = "Recupération de la liste des associations,";
							sendMessage(message);
							setEvent(false);
							break;
						}
						case '3': {

							message = "isbn," + getISBN() + ";" + idStock;
							sendMessage(message);
							setEvent(false);
							break;
						}
						case '4': {

							message = "produits," + getISBN();
							sendMessage(message);
							setEvent(false);
							break;
						}
						case '5': {

							message = "newproduits,"
									+ infos
									+ "::" + getISBN() + "::" + getId();
							sendMessage(message);
							setEvent(false);
							break;
						}
						default: {
							sendMessage("_");
							break;
						}
						}

					} else {
						sendMessage("_");
					}

				} catch (Exception classNot) {
					System.err.println("Données reçu format ¤");
				}
			} while (!message.equals("bye"));
		} catch (Exception unknownHost) {
			System.err.println("connexion à un Hote inconnu");
			isConnected = false;
		} finally {
			// 4: Fermeture de la connexion
			try {
				in.close();
				out.close();
				requestSocket.close();
				System.exit(0);
			} catch (Exception ioException) {
				System.err.println(ioException.toString());
			}
		}
	}

	/**
	 * Envoi du message au serveur
	 * 
	 * @param msg
	 */
	private void sendMessage(Object msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (Exception ioException) {
			System.err.println(ioException.toString());
		}
	}

	private boolean event = false;

	public boolean sendEvent() {
		return event;
	}

	public void setEvent(boolean e) {
		event = e;
	}
	
	private String infos;
	public void setInfos(String infos)
	{
		this.infos = infos;
	}

	public String getResponseLine() {
		return response;
	}

	void setResponseLine(String response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String Mdp) {
		this.mdp = Mdp;
	}

	public void setISBN(String bn) {
		isbn = bn;
	}

	public String getISBN() {
		return this.isbn;
	}

	private void setAssociations(String string) {
		associations.addAll(Arrays.asList(string.split(";")));
	}

	private void setProducts(String produit) {
		if (produit != null) {
			produits.clear();
		}
		boolean addAll = produits.addAll(Arrays.asList(produit.trim()
				.split(",")));
	}

	ArrayList<String> produits;

	public ArrayList<String> getProducts() {
		return produits;
	}

	public ArrayList<String> getAssociations() {
		return associations;
	}

	public void setIdStock(String __STOCK) {
		idStock = __STOCK;
	}
}
