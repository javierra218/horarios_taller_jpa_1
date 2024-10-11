package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.PERSIST)
    private List<Curso> cursos;
}
