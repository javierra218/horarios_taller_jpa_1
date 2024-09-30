package co.edu.unicauca.asae.gestion_horarios.repositories;

import org.springframework.data.repository.CrudRepository;
import co.edu.unicauca.asae.gestion_horarios.models.Docente;

public interface DocenteRepository extends CrudRepository<Docente, Integer> {
}