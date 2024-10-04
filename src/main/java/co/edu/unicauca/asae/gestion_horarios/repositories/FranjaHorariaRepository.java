package co.edu.unicauca.asae.gestion_horarios.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.asae.gestion_horarios.models.FranjaHoraria;

public interface FranjaHorariaRepository extends CrudRepository<FranjaHoraria, Integer> {
    List<FranjaHoraria> findByCursoDocentes_Id(int docenteId);
}
