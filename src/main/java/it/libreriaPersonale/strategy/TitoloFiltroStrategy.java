package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.List;
import java.util.stream.Collectors;

public class TitoloFiltroStrategy implements IFiltroStrategy {
    @Override
    public List<Libro> applica(List<Libro> input, String parametro) {
        String q = parametro.toLowerCase();
        return input.stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}

