package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.List;
import java.util.stream.Collectors;

public class ValutazioneFiltroStrategy implements IFiltroStrategy {

    @Override
    public List<Libro> applica(List<Libro> input, String parametro) {
        int soglia = Integer.parseInt(parametro);
        return input.stream()
                .filter(l -> l.getValutazione() >= soglia)
                .collect(Collectors.toList());
    }
}
