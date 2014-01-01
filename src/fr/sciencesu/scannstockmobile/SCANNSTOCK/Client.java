/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

/**
 *
 * @author Antoine
 */
/*
 public final class Client implements Runnable {

 // The client socket
 private static Socket clientSocket = null;
 // The output stream
 private static PrintStream os = null;
 // The input stream
 //private static DataInputStream is = null;
 private static final int PortNumber = 5000;
 private static BufferedReader inputLine = null;
 private static boolean closed = false;
 private String ISBN = "";
 // The default host.
 private String host;
 private String responseLine = "";
 private ArrayList<String> lst;

 public Client() {
 lst = new ArrayList();
 }

 public String getResponseLine() {
 return responseLine;
 }
    
 public Client(String IP, int PORT) {
 this.host = IP;
 lst = new ArrayList();
        
 }

 public Client(String IP, int PORT, String data) {
 this.host = IP;
 this.ISBN = data;
 lst = new ArrayList();
 Connexion();
 }


 public void Connexion() {

 try {
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

     
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(ISBN + "/quit\n");

 }


 responseLine = cli.getResponseLine();

          
 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }
    
 public void sendData(String data)
 {
 try {
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

  
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(data + "/quit\n");

 }


 responseLine = cli.getResponseLine();

 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }

 public void run() {

 getDataServer();
 }

 private void getDataServer() {
 try {
 String s;

 while ((s = inputLine.readLine()) != null) {
 System.out.println(s);
 lst.add(s);
 responseLine += s + "\n";
 if (s.indexOf("*** Fin") != -1) {
 break;
 }
 }
 closed = true;
 System.out.println("CLOSED = TRUE");
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }*/
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


/*import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.util.ArrayList;*/
/**
 *
 * @author Antoine
 */
/*public final class Client implements Runnable {

 // The client socket
 private static Socket clientSocket = null;
 // The output stream
 private static PrintStream os = null;
 // The input stream
 //private static DataInputStream is = null;
 private static final int PortNumber = 5000;
 private static BufferedReader inputLine = null;
 private static boolean closed = false;
 private String ISBN = "";
 // The default host.
 private String host;
 private String responseLine = "";
 private ArrayList<String> lst;

 public Client() {
 lst = new ArrayList();
 }

 public String getResponseLine() {
 return responseLine;
 }

 public Client(String IP, int PORT, String data) {
 this.host = IP;
 this.ISBN = data;
 lst = new ArrayList();
 Connexion();
 }

    
 public void Connexion() {

 try {
 InetAddress serverAddr = InetAddress.getByName(host);
 clientSocket = new Socket(host, PortNumber);
 //inputLine = new BufferedReader(new InputStreamReader(System.in));
 os = new PrintStream(clientSocket.getOutputStream());
 // is = new DataInputStream(clientSocket.getInputStream());           
 inputLine = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 } catch (UnknownHostException e) {
 System.err.println("Impossible d'identifier l'host " + host);
 } catch (IOException e) {
 System.err.println("Entrée/Sortie indisponible pour vous connecter à l'host "
 + host);
 }

        
 if (clientSocket != null && os != null && inputLine != null) {
 try {
 Client cli = new Client();
 new Thread(cli).start();
 while (!closed) {
 os.println(ISBN + "/quit\n");

 }


 responseLine = cli.getResponseLine();

               
 os.close();
 inputLine.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }

    
 public void run() {
        
 getDataServer();
 }

 private void getDataServer() {
 try {
 String s;

 while ((s = inputLine.readLine()) != null) {
 System.out.println(s);
 lst.add(s);
 responseLine += s + "\n";
 if (s.indexOf("*** Fin") != -1) {
 break;
 }
 }
 closed = true;
 } catch (IOException e) {
 System.err.println("IOException:  " + e);
 }
 }
 }*/
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

            //Création de la socket de connection au serveur
            requestSocket = new Socket(ip, port);
            //informClientIHM("Connexion serveur en cours","start");
            if (requestSocket.isConnected()) {
                isConnected = true;
            } else {
                isConnected = false;
                return;
            }
            System.out.println("Connected to " + ip + " in port " + port + "");
            //Ouverture des streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //informClientIHM("Connexion serveur etablie","stop");
            //Communication établie
            do {
                try {
                    //Envoi en permannence
                    message = in.readObject();
                    //message = ois.readObject();



                    if (!message.equals("") && !message.equals("_")) {
                        setResponseLine(message.toString());
                    }

                    if (!message.toString().equals("") && !message.toString().equals("_") && message.toString().contains("ASSS")) {
                        setAssociations(message.toString().split(",")[1]);
                    }

                    if (!message.toString().equals("") && !message.toString().equals("_") && message.toString().contains("PRODUCTS")) {
                        setProducts(message.toString());
                    }

                    if (!message.equals("_")) {
                        System.out.println("server>" + message);
                        System.out.println("traitement :");

                    }


                    //Si validation envoi message
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
                                //informClientIHM("Connexion BDD en cours","start");

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

                                message = "newproduits," + 
                                        ProductActivity.informationsNewProduct+
                                        "::"+getISBN()+
                                        "::"+getId();
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
            //4: Fermeture de la connexion
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
        boolean addAll = produits.addAll(Arrays.asList(produit.trim().split(",")));
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
