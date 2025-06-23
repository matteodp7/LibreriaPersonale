package it.libreriaPersonale.model;

public enum StatoLettura {
    DA_LEGGERE("Da leggere"),
    IN_LETTURA("In lettura"),
    LETTO("Letto");

    private final String label;

    StatoLettura(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static StatoLettura fromLabel(String label) {
        for (StatoLettura stato : values()) {
            if (stato.label.equalsIgnoreCase(label)) {
                return stato;
            }
        }
        throw new IllegalArgumentException("Nessuno stato corrisponde a: " + label);
    }
}
