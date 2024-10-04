package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor
public class Docente extends Persona {  // Hereda de Persona
    @Id
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)  // Almacena la oficina automáticamente
    private Oficina oficina;
}
