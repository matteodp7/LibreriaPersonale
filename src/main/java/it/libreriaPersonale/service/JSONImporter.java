package it.libreriaPersonale.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.libreriaPersonale.assembler.LibroAssembler;
import it.libreriaPersonale.dto.LibroDTO;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.repository.LibroRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONImporter {
    private LibroService service;
    private LibroRepository repository;

    public JSONImporter(LibroService service, LibroRepository repository) {
        this.service = service;
        this.repository = repository;
    }


    public void importaJson(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<LibroDTO>>(){}.getType();
        try (FileReader r = new FileReader(file)) {
            List<LibroDTO> dtos = gson.fromJson(r, listType);
            for (LibroDTO dto : dtos) {
                String isbn_d= dto.getIsbn();
                Libro presente = repository.trovaTutti().stream().filter(l -> isbn_d.equalsIgnoreCase(l.getIsbn())).findFirst().orElse(null);
                if (presente != null) {
                    LibroAssembler.updateDomainObject(dto, presente);
                    repository.aggiorna(presente);
                }else {
                    Libro nuovo= LibroAssembler.createDomainObject(dto);
                    repository.salva(nuovo);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore importazione JSON", e);
        }
    }
}
