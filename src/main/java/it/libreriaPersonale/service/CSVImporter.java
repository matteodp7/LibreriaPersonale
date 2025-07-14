package it.libreriaPersonale.service;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.repository.LibroRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVImporter {

    private final LibroRepository libroRepository;

    public CSVImporter(LibroRepository lr) {
        this.libroRepository = lr;
    }

    public void importaLibriDaCsv(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",");


                String isbn = campi[4].trim();

                if (libroRepository.esistePerIsbn(isbn)) {
                    continue;
                }

                Libro libro = new Libro();
                libro.setTitolo(campi[1].trim());
                libro.setAutore(campi[2].trim());
                libro.setGenere(campi[3].trim());
                libro.setIsbn(isbn);

                String statoStr = campi[5].trim();
                try {
                    StatoLettura statoEnum = StatoLettura.valueOf(statoStr);
                    libro.setStatoLettura(statoEnum);
                } catch (IllegalArgumentException e) {
                    libro.setStatoLettura(StatoLettura.DA_LEGGERE);
                }

                libro.setValutazione(Integer.parseInt(campi[6].trim()));


                if (campi.length > 7) {
                    libro.setCopertinaUrl(campi[7].trim());
                } else {
                    libro.setCopertinaUrl(null);
                }

                libroRepository.salva(libro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
