package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Docente extends Persona {

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Oficina oficina;
}
