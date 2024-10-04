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

    @ManyToOne(cascade = CascadeType.PERSIST)  // Se persiste la asignatura si no existe
    private Asignatura asignatura;

    @ManyToMany
    private List<Docente> docentes;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)  // Elimina franjas horarias asociadas
    private List<FranjaHoraria> franjasHorarias;
}
