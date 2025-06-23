package it.libreriaPersonale.service;

import it.libreriaPersonale.model.Libro;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void esporta(List<Libro> libri, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,titolo,autore,genere,isbn,statolettura,valutazione,copertinaUrl\n");
            for (Libro libro : libri) {
                writer.write(
                        libro.getId() + "," +
                                escape(libro.getTitolo()) + "," +
                                escape(libro.getAutore()) + "," +
                                escape(libro.getGenere()) + "," +
                                escape(libro.getIsbn()) + "," +
                                escape(libro.getStatoLettura().name()) + "," +
                                libro.getValutazione() + "," +
                                escape(libro.getCopertinaUrl()) + "\n"
                );
            }
            System.out.println("💾 Libri salvati in: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Errore durante il salvataggio del file CSV: " + e.getMessage());
        }
    }

    private static String escape(String value) {
        if (value == null) return "";
        return value.replace(",", " ");
    }
}
