package it.libreriaPersonale.memento;

import java.util.List;
import it.libreriaPersonale.model.Libro;

public class Memento {
    private final List<Libro> state;

    public Memento(List<Libro> state) {
        this.state = state.stream()
                .map(Libro::new)
                .toList();
    }

    public List<Libro> getState() {
        return state;
    }
}
