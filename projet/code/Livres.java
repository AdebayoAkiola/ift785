package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Livres {

    private String isbn;
    private String auteur;
    private String titre;

    public Livres(String isbn, String auteur, String titre) {
        this.isbn = isbn;
        this.auteur = auteur;
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getTitre() {
        return titre;
    }

    public static List<Livres> chercherLivres(String cheminFichier, Livres book) throws IOException {
        List<Livres> livresTrouves = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            String isbn = null;
            String auteur = null;
            String titre = null;
            String[] attribut =new String[2];
            Livres livre = new Livres("", "", "");;
            while ((ligne = reader.readLine()) != null) {
                //System.out.println(ligne);
                attribut = parseLivre(ligne);
                switch (attribut[0]) {
                    case "isbn":
                        isbn = attribut[1];
                        break;
                    case "auteur":
                        auteur = attribut[1];
                        break;
                    case "titre":
                        titre = attribut[1];
                        livre = new Livres(isbn, auteur, titre);
                        if ((book.isbn == null || isbn.equals(book.getIsbn())) &&
                            (book.auteur == null || auteur.equals(book.getAuteur())) &&
                            (book.titre == null || titre.equals(book.getTitre())))
                        livresTrouves.add(livre);
                        break;
                    default:
                        // Ignorer les champs inconnus
                        break;
                }
                /**if (livre != null && (isbn == null || livre.getIsbn().equals(isbn))
                        && (auteur == null || livre.getAuteur().equals(auteur))
                        && (titre == null || livre.getTitre().equals(titre))) {
                    
                }*/
            }
        }

        return livresTrouves;
    }

    private static String[] parseLivre(String ligne) {

        String[] champs = ligne.split(",");
        String[] paire =new String[2];
        for (String champ : champs) {
            paire = champ.split(":");
            
            if(paire.length>1) { 
                String cle = paire[0].trim().replace("\"", "");
                String valeur = paire[1].trim().replace("\"", "");
                paire[0] = cle;
                paire[1] = valeur;
                
                //System.out.println(titre+" vv "+isbn+" vv "+auteur+" vv ");
            }
        }
        
        return paire;
       /** if (isbn != null && auteur != null && titre != null) {
            System.out.println("1111111111111111");
            return new Livres(isbn, auteur, titre);
        } else {
            System.out.println("000000000000");
            return null;
        } */
    }
}
