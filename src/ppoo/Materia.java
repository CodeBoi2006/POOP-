/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

public class Materia {
    private String nombre;
    private int calificacion;
    private String estado;   // "APROBADA" o "INSCRITA"
    private int creditos;    // 10 si aprobada, 0 si inscrita

    public Materia(String nombre, int calificacion, String estado, int creditos) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.estado = estado;
        this.creditos = creditos;
    }

    public String getNombre() { return nombre; }
    public int getCalificacion() { return calificacion; }
    public String getEstado() { return estado; }
    public int getCreditos() { return creditos; }

    @Override
    public String toString() {
        return nombre + " (" + estado + ", " + calificacion + ", " + creditos + " créditos)";
    }
}




