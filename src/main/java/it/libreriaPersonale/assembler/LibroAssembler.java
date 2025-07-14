package it.libreriaPersonale.assembler;

import it.libreriaPersonale.dto.LibroDTO;
import it.libreriaPersonale.model.Libro;

public class LibroAssembler {


    public static LibroDTO toDTO(Libro entity) {
        if (entity == null) {
            return null;
        }
        LibroDTO dto = new LibroDTO();
        dto.getId();
        dto.setTitolo(entity.getTitolo());
        dto.setAutore(entity.getAutore());
        dto.setGenere(entity.getGenere());
        dto.setIsbn(entity.getIsbn());
        dto.setStatoLettura(entity.getStatoLettura());
        dto.setValutazione(entity.getValutazione());
        dto.setCopertinaUrl(entity.getCopertinaUrl());
        return dto;
    }


    public static Libro createDomainObject(LibroDTO dto) {
        if (dto == null) {
            return null;
        }
        Libro entity = new Libro();
        entity.getId();
        entity.setTitolo(dto.getTitolo());
        entity.setAutore(dto.getAutore());
        entity.setGenere(dto.getGenere());
        entity.setIsbn(dto.getIsbn());
        entity.setStatoLettura(dto.getStatoLettura());
        entity.setValutazione(dto.getValutazione());
        entity.setCopertinaUrl(dto.getCopertinaUrl());
        return entity;
    }


    public static void updateDomainObject(LibroDTO dto, Libro entity) {
        if (dto == null) {
            return ;
        }
        entity.getId();
        entity.setTitolo(dto.getTitolo());
        entity.setAutore(dto.getAutore());
        entity.setGenere(dto.getGenere());
        entity.setIsbn(dto.getIsbn());
        entity.setStatoLettura(dto.getStatoLettura());
        entity.setValutazione(dto.getValutazione());
        entity.setCopertinaUrl(dto.getCopertinaUrl());

        if (dto.getId() != null) {
            try {
                java.lang.reflect.Field f = Libro.class.getDeclaredField("id");
                f.setAccessible(true);
                f.set(entity, dto.getId());
            } catch (Exception e) {
                throw new RuntimeException("Errore impostazione id su Entity", e);
            }
        }

    }
}
