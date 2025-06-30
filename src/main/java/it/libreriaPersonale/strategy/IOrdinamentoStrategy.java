package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.List;

public interface IOrdinamentoStrategy {
    List<Libro> applica(List<Libro> input);
}
