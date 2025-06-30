package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.List;
import java.util.stream.Collectors;

public class AutoreFiltroStrategy implements IFiltroStrategy {
    @Override
    public List<Libro> applica(List<Libro> input, String parametro) {
        String q = parametro.toLowerCase();
        return input.stream()
                .filter(l -> l.getAutore().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}

