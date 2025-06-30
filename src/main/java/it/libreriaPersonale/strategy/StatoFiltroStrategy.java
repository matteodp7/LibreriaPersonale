package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

import java.util.List;
import java.util.stream.Collectors;

public class StatoFiltroStrategy implements IFiltroStrategy {

    @Override
    public List<Libro> applica(List<Libro> input, String parametro) {
        if ("Tutti".equalsIgnoreCase(parametro)) {
            return input;
        }

        StatoLettura stato;
        try {
            stato = StatoLettura.fromLabel(parametro);
        } catch (IllegalArgumentException e) {
            return List.of();
        }

        return input.stream()
                .filter(l -> l.getStatoLettura() == stato)
                .collect(Collectors.toList());
    }
}
