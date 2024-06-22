package aed;

import java.util.List;

public class SistemaSIU {

    enum CargoDocente {
        AY2,
        AY1,
        JTP,
        PROF
    }

    private int size; // no se para q pero siento q lo vamos a necesitar
    // private String[] carreras;
    // private String[] estudiantes;
    // private List<String>[] materias;
    // private String[] inscriptos(carrera: string, nombreMateria: string);
    // private int inscripciones(String estudiante);
    // private int docentes(cargo: CargoDocente, carrera: string, nombreMateria:
    // string);
    // private boolean sonMismaMateria(carrera1: string, nombreMateria1: string;
    // carrera2: string, nombreMateria2: string);

    // private List<String> carreras;
    // private List<String> estudiantes;
    // private List<ParCarreraMateria> materias;
    private List<ParCarreraMateria> inscriptos;
    private List<String> inscripcionesPorEstudiante;

    Trie<Trie<Materia>> Triecarreras = new Trie<>();
    Trie<Materia> Triematerias = new Trie<>();

    public SistemaSIU(SecParCarreraMateriaEquivalentes[] secParCarreraMateriaEquivalentes,
            String[] libretasUniversitarias) {
        // Materia laMateria = new Materia();
        for (SecParCarreraMateriaEquivalentes secParCarreraMateriaEquivalente : secParCarreraMateriaEquivalentes) {
            ParCarreraMateria[] secParCarreraMateriaEquivalentes2 = secParCarreraMateriaEquivalente
                    .getParesCarreraMateria();
            Materia laMateria = new Materia();
            for (ParCarreraMateria parCarreraMateria : secParCarreraMateriaEquivalentes2) {
                String carrera = parCarreraMateria.getCarrera();
                String materia = parCarreraMateria.getNombreMateria();
                Triecarreras.insertar(carrera, Triematerias);
                Triematerias.insertar(materia, laMateria);
                if (laMateria.m == null) {
                    laMateria.m = materia;
                }
                laMateria.cantidadInscripciones = 0;
                for (int i = 0; i < 4; i++) {
                    laMateria.docentes[i] = 0;
                }
                for (String estudiante : libretasUniversitarias) {
                    laMateria.estudiantes.insertar(estudiante, 0);
                }
            }
        }
    }

    private int cantinscripciones(String estudiante) {
        int count = 0;
        for (Trie<Materia> trieMaterias : Triecarreras) {
            for (Materia materia : Triematerias) {
                if (materia.estudiantes.pertenece(estudiante)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void inscribir(String estudiante, String carrera, String materia) {
        Trie<Materia> mat = Triecarreras.def(carrera);
        Materia valor = mat.def(materia);
        // int inscripcionesPrevias = cantinscripciones(estudiante);
        valor.estudiantes.insertar(estudiante, 1);
        valor.cantidadInscripciones += 1;
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        Trie<Materia> mat = Triecarreras.def(carrera);
        Materia valor = mat.def(materia);
        if (CargoDocente.AY2 == cargo) {
            valor.docentes[3] += 1;
        } else if (CargoDocente.AY1 == cargo) {
            valor.docentes[2] += 1;
        } else if (CargoDocente.JTP == cargo) {
            valor.docentes[1] += 1;
        } else {
            valor.docentes[0] += 1;
        }
    }

    public int[] plantelDocente(String materia, String carrera) {
        Trie<Materia> mat = Triecarreras.def(carrera);
        Materia valor = mat.def(materia);
        return valor.docentes;
    }

    public void cerrarMateria(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int inscriptos(String materia, String carrera) {
        Trie<Materia> mat = Triecarreras.def(carrera);
        Materia valor = mat.def(materia);
        return valor.cantidadInscripciones;
    }

    public boolean excedeCupo(String materia, String carrera) {
        int cantCupos = 0;
        Trie<Materia> mat = Triecarreras.def(carrera);
        Materia valor = mat.def(materia);
        int[] cantDocentes = valor.docentes;
        cantCupos += cantDocentes[0] * 250 + cantDocentes[1] * 100 + cantDocentes[2] * 20 + cantDocentes[3] * 30;
        if (inscriptos(carrera, materia) > cantCupos) {
            return true;
        } else {
            return false;
        }
    }

    public String[] carreras() {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public String[] materias(String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int materiasInscriptas(String estudiante) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }
}