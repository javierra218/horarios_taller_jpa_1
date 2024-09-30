package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Curso {
    @Id
    private int id;

    private String nombre;

    @ManyToOne(cascade = CascadeType.PERSIST)  // Usamos cascada para persistir la asignatura si no existe
    private Asignatura asignatura;

    @ManyToMany
    private List<Docente> docentes;

    @OneToMany(mappedBy = "curso")
    private List<FranjaHoraria> franjasHorarias;
}