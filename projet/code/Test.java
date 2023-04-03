package code;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Test {
    
    public static void main(String[] args) throws IOException, ParseException {
        String cheminFichier = "C:/Users/ade15/Documents/IFT785 POO/projet/baseDeDonnees/livres.json";
        String isbn = "01234567890";
        String auteur = "John Doe";
        String titre = null;
        Livres livre = new Livres(isbn, auteur, titre);

        List<Livres> livresTrouves = Livres.chercherLivres(cheminFichier, livre);
        if (!livresTrouves.isEmpty())
            for (Livres livreTrouve : livresTrouves) {
                System.out.println("ISBN : " + livreTrouve.getIsbn());
                System.out.println("Auteur : " + livreTrouve.getAuteur());
                System.out.println("Titre : " + livreTrouve.getTitre());
                System.out.println();
            }
        else{
            System.out.println("Le document recherche n'est pas present dans la base de donnees");
            Suggestion.incrementerScore(isbn);
        }
    }
}