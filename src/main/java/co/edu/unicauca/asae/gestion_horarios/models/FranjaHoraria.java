package co.edu.unicauca.asae.gestion_horarios.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Time;

@Entity
@Getter @Setter @NoArgsConstructor
public class FranjaHoraria {
    @Id
    private int id;

    private String dia;
    private Time horaInicio;
    private Time horaFin;

    @ManyToOne
    private Curso curso;

    @ManyToOne
    private EspacioFisico espacioFisico;
}
