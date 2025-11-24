/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

import java.util.ArrayList;
public class CalculadoraNumeroInscripcion {

    /** Calcula el indicador escolar de un alumno */
    public static double calcularIndicadorEscolar(Alumno alumno) {

        if (alumno == null) {
            return 0.0;
        }

        RegistroAcademico reg = alumno.getRegistro();
        if (reg == null) {
            return 0.0;
        }

        ArrayList<Materia> materias = reg.getMaterias();
        if (materias == null || materias.isEmpty()) {
            return 0.0;
        }

        int aprobadas = 0;
        int inscritas = materias.size();
        int creditosActuales = reg.getCreditosTotales();

        for (Materia m : materias) {
            if (m != null && "APROBADA".equalsIgnoreCase(m.getEstado())) {
                aprobadas++;
            }
        }

        double promedio = reg.getPromedio();

        // créditos ideales según el semestre del alumno
        int creditosIdeales = alumno.getCreditosIdeales();
        if (creditosIdeales == 0) {
            return 0.0;
        }

        double escolaridad = ((double) aprobadas / (double) inscritas) * 100.0;
        double velocidad   = ((double) creditosActuales / (double) creditosIdeales) * 100.0;

        return promedio * escolaridad * velocidad;
    }

    /**
     * Calcula el indicador de todos los alumnos, los ordena de forma
     * DESCENDENTE y asigna el número de inscripción:
     * 1 para el mejor indicador, 2 para el siguiente, etc.
     */
    public static void asignarNumeroInscripcion(Alumno[] alumnos, int totalAlumnos) {

        if (alumnos == null || totalAlumnos <= 0) {
            System.out.println("No hay alumnos cargados.");
            return;
        }

        int[] indices = new int[totalAlumnos];
        double[] indicadores = new double[totalAlumnos];

        for (int i = 0; i < totalAlumnos; i++) {
            indices[i] = i;

            Alumno a = alumnos[i];
            if (a == null) {
                indicadores[i] = 0.0;
            } else {
                indicadores[i] = calcularIndicadorEscolar(a);
            }
        }

        // Ordenar por indicador de MAYOR a MENOR (burbuja)
        for (int i = 0; i < totalAlumnos - 1; i++) {
            for (int j = i + 1; j < totalAlumnos; j++) {
                if (indicadores[indices[j]] > indicadores[indices[i]]) {
                    int aux = indices[i];
                    indices[i] = indices[j];
                    indices[j] = aux;
                }
            }
        }

        System.out.println("\nListado ordenado por INDICADOR ESCOLAR:");
        int numeroInscripcion = 1;
        for (int pos = 0; pos < totalAlumnos; pos++) {
            int idx = indices[pos];
            Alumno a = alumnos[idx];
            if (a == null) {
                continue;
            }

            double indicador = indicadores[idx];

            // Guardar el número de inscripción 
            a.setNumeroReinscripcion(numeroInscripcion);

            System.out.printf("%2d) %s %s  Indicador: %.2f  NumeroInscripcion: %d%n",
                    numeroInscripcion,
                    a.getNombres(),
                    a.getApellidos(),
                    indicador,
                    numeroInscripcion);

            numeroInscripcion++;
        }

        System.out.println("✔ Números de inscripción asignados con base en el indicador escolar.");
    }
}
