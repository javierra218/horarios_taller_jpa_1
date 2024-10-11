package co.edu.unicauca.asae.gestion_horarios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import co.edu.unicauca.asae.gestion_horarios.models.*;
import co.edu.unicauca.asae.gestion_horarios.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
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

    @Autowired
    private OficinaRepository oficinaRepository;

    public static void main(String[] args) {
        SpringApplication.run(GestionHorariosApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        StringBuilder log = new StringBuilder();

        Docente docente = crearDocente();
        log.append("Docente creado:\n")
                .append("ID: ").append(docente.getId()).append("\n")
                .append("Nombre: ").append(docente.getNombre()).append("\n")
                .append("Apellido: ").append(docente.getApellido()).append("\n")
                .append("Correo: ").append(docente.getCorreo()).append("\n")
                .append("Oficina: ").append(docente.getOficina().getNombre()).append("\n\n");

        EspacioFisico espacioFisico = crearEspacioFisico();
        log.append("Espacio Físico creado:\n")
                .append("ID: ").append(espacioFisico.getId()).append("\n")
                .append("Nombre: ").append(espacioFisico.getNombre()).append("\n")
                .append("Capacidad: ").append(espacioFisico.getCapacidad()).append("\n\n");

        Curso curso = crearCurso(docente);
        log.append("Curso creado:\n")
                .append("ID: ").append(curso.getId()).append("\n")
                .append("Nombre: ").append(curso.getNombre()).append("\n")
                .append("Asignatura: ").append(curso.getAsignatura().getNombre()).append("\n")
                .append("Docente asociado: ").append(docente.getNombre()).append("\n\n");

        FranjaHoraria franjaHoraria = crearFranjaHoraria(curso, espacioFisico);
        log.append("Franja horaria creada:\n")
                .append("ID: ").append(franjaHoraria.getId()).append("\n")
                .append("Día: ").append(franjaHoraria.getDia()).append("\n")
                .append("Hora Inicio: ").append(franjaHoraria.getHoraInicio()).append("\n")
                .append("Hora Fin: ").append(franjaHoraria.getHoraFin()).append("\n")
                .append("Curso: ").append(curso.getNombre()).append("\n")
                .append("Espacio Físico: ").append(espacioFisico.getNombre()).append("\n\n");

        log.append(listarFranjasHorarias()).append("\n");
        log.append(consultarFranjasPorDocente(docente)).append("\n");
        // log.append(eliminarCurso(curso)).append("\n");

        // Imprimir el log completo
        System.out.println(log.toString());
    }

    // Crear docente
    private Docente crearDocente() {
        Oficina oficina = new Oficina();
        oficina.setNombre("Oficina 1");
        oficina.setUbicacion("Edificio A");
        oficinaRepository.save(oficina);

        Docente docente = new Docente();
        docente.setNombre("Juan");
        docente.setApellido("Pérez");
        docente.setCorreo("juan.perez@unicauca.edu.co");
        docente.setOficina(oficina);

        docenteRepository.save(docente);

        return docente;
    }

    // Crear espacio físico
    private EspacioFisico crearEspacioFisico() {
        EspacioFisico espacioFisico = new EspacioFisico();
        espacioFisico.setNombre("Aula 101");
        espacioFisico.setCapacidad(40);
        espacioFisicoRepository.save(espacioFisico);

        return espacioFisico;
    }

    // Crear curso
    private Curso crearCurso(Docente docente) {
        Asignatura asignatura = asignaturaRepository.findByCodigo("MAT101").orElse(null);
        if (asignatura == null) {
            asignatura = new Asignatura();
            asignatura.setNombre("Matemáticas");
            asignatura.setCodigo("MAT101");
            asignaturaRepository.save(asignatura);
        }

        Curso curso = new Curso();
        curso.setNombre("Matemáticas 101");
        curso.setAsignatura(asignatura);

        // Usar una ArrayList modificable
        List<Docente> listaDocentes = new ArrayList<>();
        listaDocentes.add(docente);
        curso.setDocentes(listaDocentes);

        cursoRepository.save(curso);

        return curso;
    }

    // Crear franja horaria
    private FranjaHoraria crearFranjaHoraria(Curso curso, EspacioFisico espacioFisico) {
        FranjaHoraria franjaHoraria = new FranjaHoraria();
        franjaHoraria.setDia("Lunes");
        franjaHoraria.setHoraInicio(Time.valueOf("08:00:00"));
        franjaHoraria.setHoraFin(Time.valueOf("10:00:00"));
        franjaHoraria.setCurso(curso);
        franjaHoraria.setEspacioFisico(espacioFisico);

        // Guardar la franja horaria directamente para obtener el ID
        franjaHorariaRepository.save(franjaHoraria);

        // Agregar la franja horaria a la lista del curso
        if (curso.getFranjasHorarias() == null) {
            curso.setFranjasHorarias(new ArrayList<>());
        }
        curso.getFranjasHorarias().add(franjaHoraria);

        return franjaHoraria;
    }

    // Listar franjas horarias
    private String listarFranjasHorarias() {
        List<FranjaHoraria> franjas = (List<FranjaHoraria>) franjaHorariaRepository.findAll();

        StringBuilder listado = new StringBuilder("Listando Franjas Horarias:\n");
        franjas.forEach(f -> listado.append("Franja ID: ").append(f.getId())
                .append(" - Curso: ").append(f.getCurso().getNombre())
                .append(" - Día: ").append(f.getDia())
                .append(" - Hora Inicio: ").append(f.getHoraInicio())
                .append(" - Hora Fin: ").append(f.getHoraFin())
                .append(" - Espacio Físico: ").append(f.getEspacioFisico().getNombre()).append("\n"));

        return listado.toString();
    }

    // Consultar franjas por docente
    private String consultarFranjasPorDocente(Docente docente) {
        List<FranjaHoraria> franjas = franjaHorariaRepository.findByCursoDocentes_Id(docente.getId());

        StringBuilder listado = new StringBuilder(
                "Consultando Franjas Horarias para el Docente: " + docente.getNombre() + "\n");
        franjas.forEach(f -> listado.append("Franja ID: ").append(f.getId())
                .append(" - Curso: ").append(f.getCurso().getNombre())
                .append(" - Día: ").append(f.getDia())
                .append(" - Hora Inicio: ").append(f.getHoraInicio())
                .append(" - Hora Fin: ").append(f.getHoraFin())
                .append(" - Espacio Físico: ").append(f.getEspacioFisico().getNombre()).append("\n"));

        return listado.toString();
    }

    // // Eliminar curso
    // private String eliminarCurso(Curso curso) {
    //     cursoRepository.deleteById(curso.getId());
    //     return "Curso eliminado junto con las franjas horarias asociadas.";
    // }
}
