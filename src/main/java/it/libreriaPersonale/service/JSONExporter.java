package it.libreriaPersonale.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.libreriaPersonale.assembler.LibroAssembler;
import it.libreriaPersonale.dto.LibroDTO;
import it.libreriaPersonale.model.Libro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class JSONExporter {

    public static void esportaJson(File file, List<Libro> libri) {
        List<LibroDTO> dtos = libri.stream().map(LibroAssembler::toDTO).collect(Collectors.toList());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer w = new FileWriter(file)) {
            gson.toJson(dtos, w);
        } catch (IOException e) {
            throw new RuntimeException("Errore esportazione JSON", e);
        }
    }
}
