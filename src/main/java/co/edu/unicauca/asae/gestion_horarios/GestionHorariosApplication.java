package co.edu.unicauca.asae.gestion_horarios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import co.edu.unicauca.asae.gestion_horarios.models.*;
import co.edu.unicauca.asae.gestion_horarios.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Transactional
public class GestionHorariosApplication implements CommandLineRunner {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private OficinaRepository oficinaRepository;  // Añadido para manejar oficinas

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private FranjaHorariaRepository franjaHorariaRepository;  // Añadido para manejar franjas horarias

    @Autowired
    private EspacioFisicoRepository espacioFisicoRepository;  // Añadido para manejar espacios físicos

    public static void main(String[] args) {
        SpringApplication.run(GestionHorariosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        crearOficina();
        crearDocente();
        crearAsignatura();
        crearCurso();
        crearEspacioFisico();
        crearFranjaHoraria();
        
        consultarDocentes();
        consultarCursos();
    }

    private void crearOficina() {
        // Crear y guardar oficina
        Oficina oficina = new Oficina(1, "Oficina 1", "Edificio A");
        oficinaRepository.save(oficina);
    }

    private void crearDocente() {
        // Obtener oficina existente
        Oficina oficina = oficinaRepository.findById(1).orElse(null);

        if (oficina != null) {
            Docente docente = new Docente();
            docente.setNombre("Juan");
            docente.setApellido("Pérez");
            docente.setCorreo("juan.perez@unicauca.edu.co");
            docente.setOficina(oficina);

            docenteRepository.save(docente);
        } else {
            System.out.println("La oficina no fue encontrada.");
        }
    }

    private void crearAsignatura() {
        // Verificar si la asignatura ya está en la base de datos
        Asignatura asignatura = asignaturaRepository.findById(1).orElse(null);
        if (asignatura == null) {
            asignatura = new Asignatura();
            asignatura.setId(1);
            asignatura.setNombre("Matemáticas");
            asignatura.setCodigo("MAT101");
            asignaturaRepository.save(asignatura);
        }
    }

    private void crearCurso() {
        // Obtener la asignatura persistida
        Asignatura asignatura = asignaturaRepository.findById(1).orElse(null);

        if (asignatura != null) {
            Curso curso = new Curso();
            curso.setId(1);
            curso.setNombre("Matemáticas 101");
            curso.setAsignatura(asignatura);

            // Asignar un docente al curso
            Docente docente = docenteRepository.findById(1).orElse(null);
            if (docente != null) {
                curso.setDocentes(Arrays.asList(docente));
                cursoRepository.save(curso);
            } else {
                System.out.println("El docente no fue encontrado.");
            }
        } else {
            System.out.println("La asignatura no fue encontrada.");
        }
    }

    private void crearEspacioFisico() {
        // Crear y guardar un espacio físico
        EspacioFisico espacioFisico = new EspacioFisico();
        espacioFisico.setId(1);
        espacioFisico.setNombre("Aula 101");
        espacioFisico.setCapacidad(30);
        espacioFisicoRepository.save(espacioFisico);
    }

    private void crearFranjaHoraria() {
    // Obtener el curso y espacio físico previamente creados
    Curso curso = cursoRepository.findById(1).orElse(null);
    EspacioFisico espacioFisico = espacioFisicoRepository.findById(1).orElse(null);

    if (curso != null && espacioFisico != null) {
        FranjaHoraria franjaHoraria = new FranjaHoraria();
        franjaHoraria.setId(1);
        franjaHoraria.setDia("Lunes");
        // Convertir String a Time usando Time.valueOf()
        franjaHoraria.setHoraInicio(Time.valueOf("08:00:00"));  // Convierte a Time
        franjaHoraria.setHoraFin(Time.valueOf("10:00:00"));     // Convierte a Time
        franjaHoraria.setCurso(curso);
        franjaHoraria.setEspacioFisico(espacioFisico);

        franjaHorariaRepository.save(franjaHoraria);
    } else {
        System.out.println("El curso o el espacio físico no fueron encontrados.");
    }
}

    private void consultarDocentes() {
        System.out.println("Consultando docentes...");

        List<Docente> docentes = (List<Docente>) docenteRepository.findAll();
        for (Docente docente : docentes) {
            System.out.println("Docente ID: " + docente.getId());
            System.out.println("Nombre: " + docente.getNombre() + " " + docente.getApellido());
            System.out.println("Correo: " + docente.getCorreo());
            System.out.println("Oficina: " + docente.getOficina().getNombre());
            System.out.println("---- ---- ----");
        }
    }

    private void consultarCursos() {
        System.out.println("Consultando cursos...");

        List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
        for (Curso curso : cursos) {
            System.out.println("Curso ID: " + curso.getId());
            System.out.println("Nombre del curso: " + curso.getNombre());
            System.out.println("Asignatura: " + curso.getAsignatura().getNombre());
            System.out.println("Docentes: ");
            for (Docente docente : curso.getDocentes()) {
                System.out.println("  - " + docente.getNombre() + " " + docente.getApellido());
            }
            System.out.println("---- ---- ----");
        }
    }
}
