package DAO;

import java.util.ArrayList;

/**
 * 
 * @author Antoine
 *
 */
public interface IBase 
{

    /**
     * Connection � une base de donn�es
     */
    public void connection();
	
	    /**
     *Insertion de donn�es dans une table
     * @param table la table dans laquelle on souhaite inserer les donn�es
     * @param values listes des valeurs � ins�rer COMPRENANT les colonnes
     * @return
     */
    public boolean insertInto(String table, ArrayList<String> values);

    /**
     * Deconnection d'une base
     */
    public void deconnection();
    
    /**
     *Selection et affichage d'une vue ou du contenu d'une table
     * @param name
     */
    public void selectVisual(String name);
    
    /**
     *Affichage de la description des �l�ments composant une vue ou une table
     * @param name
     */
    public void selectDesc(String name);
}
