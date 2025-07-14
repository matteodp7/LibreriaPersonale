package it.libreriaPersonale.memento;

import it.libreriaPersonale.model.Libro;
import java.util.List;
import java.util.Stack;

public class Caretaker {

    private final Stack<Memento> history = new Stack<>();
    private final Originator originator;

    public Caretaker(Originator originator) {
        this.originator = originator;
    }

    public void doAction(List<Libro> newState) {
        history.push(originator.save());
        originator.setState(newState);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Memento previous = history.pop();
            originator.restore(previous);
        }
    }

}
