package it.libreriaPersonale.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatoLetturaConverter implements AttributeConverter<StatoLettura, String> {

    @Override
    public String convertToDatabaseColumn(StatoLettura stato) {
        return stato != null ? stato.toString() : null;
    }

    @Override
    public StatoLettura convertToEntityAttribute(String dbData) {
        return dbData != null ? StatoLettura.fromLabel(dbData) : null;
    }
}
