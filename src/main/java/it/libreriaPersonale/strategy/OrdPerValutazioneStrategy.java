package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class OrdPerValutazioneStrategy implements IOrdinamentoStrategy {

    @Override
    public List<Libro> applica(List<Libro> input) {
        return input.stream()
                .sorted(Comparator.comparingInt(Libro::getValutazione).reversed())
                .collect(Collectors.toList());
    }


}
