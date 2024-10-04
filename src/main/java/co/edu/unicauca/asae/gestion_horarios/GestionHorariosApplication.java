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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
@Transactional
public class GestionHorariosApplication implements CommandLineRunner {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FranjaHorariaRepository franjaHorariaRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private EspacioFisicoRepository espacioFisicoRepository;

    public static void main(String[] args) {
        SpringApplication.run(GestionHorariosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StringBuilder log = new StringBuilder();

        log.append(crearDocente()).append("\n");
        log.append(crearEspacioFisico()).append("\n");  // Crear espacio físico antes de usarlo
        log.append(crearCurso()).append("\n");
        log.append(crearFranjaHoraria()).append("\n");
        log.append(listarFranjasHorarias()).append("\n");
        log.append(consultarFranjasPorDocente()).append("\n");
        log.append(eliminarCurso()).append("\n");

        // Al final de todos los procesos, imprimimos el log completo
        System.out.println(log.toString());
    }

    // (v 1.0) Crear docente
    private String crearDocente() {
        Oficina oficina = new Oficina(1, "Oficina 1", "Edificio A");
        Docente docente = new Docente();
        docente.setNombre("Juan");
        docente.setApellido("Pérez");
        docente.setCorreo("juan.perez@unicauca.edu.co");
        docente.setOficina(oficina);

        docenteRepository.save(docente);

        return "Docente creado:\n" +
               "ID: " + docente.getId() + "\n" +
               "Nombre: " + docente.getNombre() + "\n" +
               "Apellido: " + docente.getApellido() + "\n" +
               "Correo: " + docente.getCorreo() + "\n" +
               "Oficina: " + docente.getOficina().getNombre();
    }

    // (v 1.0) Crear espacio físico
    private String crearEspacioFisico() {
        EspacioFisico espacioFisico = new EspacioFisico();
        espacioFisico.setId(1);
        espacioFisico.setNombre("Aula 101");
        espacioFisico.setCapacidad(40);
        espacioFisicoRepository.save(espacioFisico);

        return "Espacio Físico creado:\n" +
               "ID: " + espacioFisico.getId() + "\n" +
               "Nombre: " + espacioFisico.getNombre() + "\n" +
               "Capacidad: " + espacioFisico.getCapacidad();
    }

    // (v 1.0) Crear curso
    private String crearCurso() {
        Asignatura asignatura = asignaturaRepository.findById(1).orElse(null);
        if (asignatura == null) {
            asignatura = new Asignatura();
            asignatura.setId(1);
            asignatura.setNombre("Matemáticas");
            asignatura.setCodigo("MAT101");
            asignaturaRepository.save(asignatura);
        }

        Curso curso = new Curso();
        curso.setId(1);
        curso.setNombre("Matemáticas 101");
        curso.setAsignatura(asignatura);

        Docente docente = docenteRepository.findById(1).orElse(null);
        if (docente != null) {
            curso.setDocentes(Arrays.asList(docente));
            cursoRepository.save(curso);

            return "Curso creado:\n" +
                   "ID: " + curso.getId() + "\n" +
                   "Nombre: " + curso.getNombre() + "\n" +
                   "Asignatura: " + asignatura.getNombre() + "\n" +
                   "Docente asociado: " + docente.getNombre();
        }

        return "Error al crear el curso. Docente no encontrado.";
    }

    // (v 1.0) Crear franja horaria
    private String crearFranjaHoraria() {
        Curso curso = cursoRepository.findById(1).orElse(null);
        EspacioFisico espacioFisico = espacioFisicoRepository.findById(1).orElse(null);

        if (curso != null && espacioFisico != null) {
            FranjaHoraria franjaHoraria = new FranjaHoraria();
            franjaHoraria.setId(1);
            franjaHoraria.setDia("Lunes");
            franjaHoraria.setHoraInicio(Time.valueOf("08:00:00"));
            franjaHoraria.setHoraFin(Time.valueOf("10:00:00"));
            franjaHoraria.setCurso(curso);
            franjaHoraria.setEspacioFisico(espacioFisico);

            franjaHorariaRepository.save(franjaHoraria);

            return "Franja horaria creada:\n" +
                   "ID: " + franjaHoraria.getId() + "\n" +
                   "Día: " + franjaHoraria.getDia() + "\n" +
                   "Hora Inicio: " + franjaHoraria.getHoraInicio() + "\n" +
                   "Hora Fin: " + franjaHoraria.getHoraFin() + "\n" +
                   "Curso: " + curso.getNombre() + "\n" +
                   "Espacio Físico: " + espacioFisico.getNombre();
        }

        return "Error al crear la franja horaria. Curso o espacio físico no encontrado.";
    }

    // (v 0.5) Listar franjas horarias
    private String listarFranjasHorarias() {
        List<FranjaHoraria> franjas = StreamSupport.stream(franjaHorariaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        StringBuilder listado = new StringBuilder("Listando Franjas Horarias:\n");
        franjas.forEach(f -> listado.append("Franja ID: ").append(f.getId())
                .append(" - Curso: ").append(f.getCurso().getNombre())
                .append(" - Espacio Físico: ").append(f.getEspacioFisico().getNombre()).append("\n"));

        return listado.toString();
    }

    // (v 1.0) Consultar franjas por docente
    private String consultarFranjasPorDocente() {
        Docente docente = docenteRepository.findById(1).orElse(null);
        if (docente != null) {
            List<FranjaHoraria> franjas = StreamSupport.stream(franjaHorariaRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());

            StringBuilder listado = new StringBuilder("Consultando Franjas Horarias para el Docente: " + docente.getNombre() + "\n");
            franjas.forEach(f -> listado.append("Franja ID: ").append(f.getId())
                    .append(" - Curso: ").append(f.getCurso().getNombre())
                    .append(" - Espacio Físico: ").append(f.getEspacioFisico().getNombre()).append("\n"));

            return listado.toString();
        }

        return "Docente no encontrado.";
    }

    // (v 0.5) Eliminar curso
    private String eliminarCurso() {
        cursoRepository.deleteById(1);
        return "Curso eliminado junto con las franjas horarias asociadas.";
    }
}
