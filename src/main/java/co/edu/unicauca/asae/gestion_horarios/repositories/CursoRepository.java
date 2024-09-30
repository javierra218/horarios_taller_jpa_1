package co.edu.unicauca.asae.gestion_horarios.repositories;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.gestion_horarios.models.Curso;

public interface CursoRepository extends CrudRepository<Curso, Integer> {
}