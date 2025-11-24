/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

import java.util.ArrayList;

public class RegistroAcademico {
    private ArrayList<Materia> materias;
    private double promedio;
    private int creditosTotales;

    public RegistroAcademico() {
        this.materias = new ArrayList<>();
        this.promedio = 0;
        this.creditosTotales = 0;
    }

    public RegistroAcademico(ArrayList<Materia> materias) {
        this.materias = materias;
        recalcularDatos();
    }

    public ArrayList<Materia> getMaterias() { return materias; }
    public double getPromedio() { return promedio; }
    public int getCreditosTotales() { return creditosTotales; }
    
    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public void agregarMateria(Materia m) {
        materias.add(m);
        recalcularDatos();
    }

    public void recalcularDatos() {
        int suma = 0;
        int count = 0;
        int creditos = 0;

        for (Materia m : materias) {
            if (m.getEstado().equals("APROBADA")) {
                suma += m.getCalificacion();
                count++;
                creditos += m.getCreditos();
            }
        }

        this.creditosTotales = creditos;
        this.promedio = (count == 0) ? 0 : (double)suma / count;
    }
}



