package it.libreriaPersonale.memento;

import java.util.List;
import it.libreriaPersonale.model.Libro;

public class Originator {
    private List<Libro> state;

    public void setState(List<Libro> state) {
        this.state = state;
    }

    public Memento save() {
        return new Memento(state);
    }

    public void restore(Memento memento) {
        this.state = memento.getState();
    }

    public List<Libro> getState() {
        return state;
    }
}
