package code;

import java.io.*;
import java.util.*;

public class Suggestion {

    private static final String FILENAME = "C:/Users/ade15/Documents/IFT785 POO/projet/baseDeDonnees/suggestion.json";

    public static void incrementerScore(String isbn) throws IOException {
        File fichier = new File(FILENAME);

        // Si le fichier n'existe pas, on le crée avec un objet JSON vide
        if (!fichier.exists()) {
            fichier.createNewFile();
            try (FileWriter writer = new FileWriter(fichier)) {
                writer.write("{}");
            }
        }

        // On lit le contenu du fichier
        String contenu = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne = reader.readLine();
            while (ligne != null) {
                contenu += ligne;
                ligne = reader.readLine();
            }
        }

        // On parse le contenu du fichier pour obtenir un objet Map
        Map<String, Object> livres = new HashMap<>();
        if (!contenu.isEmpty()) {
            livres = parseJSON(contenu);
        }

        // On recherche l'ISBN dans le fichier
        Object livreNode = livres.get(isbn);

        // Si l'ISBN est déjà présent, on incrémente le score
        if (livreNode != null) {
            int score = Integer.parseInt(((Map) livreNode).get("score").toString()) + 1;
            ((Map) livreNode).put("score", score);
        } else { // Sinon, on crée une nouvelle entrée avec un score initial de 1
            Map<String, Object> nouveauLivre = new HashMap<>();
            nouveauLivre.put("score", 1);
            livres.put(isbn, nouveauLivre);
        }

        // On écrit les modifications dans le fichier
        try (FileWriter writer = new FileWriter(fichier)) {
            String json = toJSON(livres);
            writer.write(json);
        }
    }

    // Parse une chaîne JSON pour retourner un objet Map
    private static Map<String, Object> parseJSON(String json) {
        Map<String, Object> map = new HashMap<>();
        String[] pairs = json.substring(1, json.length() - 1).split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].substring(1, keyValue[0].length() - 1);
            Object value;
            try {
                value = Integer.parseInt(keyValue[1]);
            } catch (NumberFormatException e) {
                value = keyValue[1].substring(1, keyValue[1].length() - 1);
            }
            map.put(key, value);
        }
        return map;
    }

    // Convertit un objet Map en une chaîne JSON
    private static String toJSON(Map<String, Object> map) {
        String json = "{";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            json += "\"" + key + "\":";
            if (value instanceof String) {
                json += "\"" + value + "\",";
            } else {
                json += value + ",";
            }
        }
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1);
        }
        json += "}";
        return json;
    }

}