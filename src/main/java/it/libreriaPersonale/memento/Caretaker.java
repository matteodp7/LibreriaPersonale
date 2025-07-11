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
        history.push(originator.save());  // Salva lo stato prima della modifica
        originator.setState(newState);    // Applica il nuovo stato
    }

    public void undo() {
        if (!history.isEmpty()) {
            Memento previous = history.pop();
            originator.restore(previous);
        }
    }

}
