package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.Collections;
import java.util.List;

public class ReverseStrategy implements IOrdinamentoStrategy {
    private final IOrdinamentoStrategy base;

    public ReverseStrategy(IOrdinamentoStrategy base) {
        this.base = base;
    }

    @Override
    public List<Libro> applica(List<Libro> input) {
        List<Libro> sorted = base.applica(input);
        Collections.reverse(sorted);
        return sorted;
    }
}
