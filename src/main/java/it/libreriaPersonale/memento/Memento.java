package it.libreriaPersonale.memento;

import java.util.List;
import it.libreriaPersonale.model.Libro;

public class Memento {
    private final List<Libro> state;

    public Memento(List<Libro> state) {
        // Copia difensiva per evitare modifiche esterne
        this.state = state.stream()
                .map(Libro::new)  // Assumendo che Libro abbia un costruttore di copia
                .toList();
    }

    public List<Libro> getState() {
        return state;
    }
}
