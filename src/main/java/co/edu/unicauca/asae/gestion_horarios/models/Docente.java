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
public class Docente {
    @Id
    private int id;

    private String nombre;
    private String apellido;
    private String correo;

    @ManyToOne(cascade = CascadeType.PERSIST)  // Agregar CascadeType.PERSIST para que la oficina se guarde automáticamente
    private Oficina oficina;
}