package co.edu.unicauca.asae.gestion_horarios.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.gestion_horarios.models.Asignatura;

public interface AsignaturaRepository extends CrudRepository<Asignatura, Integer> {
    Optional<Asignatura> findByCodigo(String codigo);
}
