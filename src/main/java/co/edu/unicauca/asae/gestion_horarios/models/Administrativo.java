package co.edu.unicauca.asae.gestion_horarios.models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor
public class Administrativo extends Persona {
    private String rol;
}