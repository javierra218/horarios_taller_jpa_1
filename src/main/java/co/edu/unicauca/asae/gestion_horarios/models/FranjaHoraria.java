package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FranjaHoraria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dia;
    private Time horaInicio;
    private Time horaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;
    @ManyToOne(fetch = FetchType.LAZY) // LAZY loading para espacio físico
    private EspacioFisico espacioFisico;
}
