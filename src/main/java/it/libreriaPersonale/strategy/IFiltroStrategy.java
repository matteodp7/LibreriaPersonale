package it.libreriaPersonale.strategy;

import it.libreriaPersonale.model.Libro;
import java.util.List;

public interface IFiltroStrategy {
    List<Libro> applica(List<Libro> input, String parametro);
}
