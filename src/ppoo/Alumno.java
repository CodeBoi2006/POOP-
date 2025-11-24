/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

/**
 *
 * @author bekis
 */
public class Alumno {
    private String numeroCuenta;            
    private String nombres;        
    private String apellidos;              
    private int edad;                     
    private int semestre;                 
    private String direccion;  
    private RegistroAcademico registro;
    private double promedio;
    private int creditos;
    private int creditosIdeales;
    private int numeroReinscripcion;

 public Alumno() {
    this.numeroCuenta = "";
    this.nombres = "";
    this.apellidos = "";
    this.edad = 0;
    this.semestre = 0;
    this.direccion = "";
    this.registro = new RegistroAcademico(); // evita NullPointerException al usar getRegistro()
}


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombreS(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public RegistroAcademico getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroAcademico registro) {
        this.registro = registro;
    }

    public double getPromedio() {
    return registro.getPromedio();
    }

    public int getCreditos() {
    return registro.getCreditosTotales();
    }

    public int getCreditosIdeales() {
    switch (this.semestre) {
        case 1: return 60;
        case 2: return 210;
        case 3: return 270;
        case 4: return 320;
        case 5: return 370;
        case 6: return 420;
        case 7: return 480;
        case 8: return 530;
        case 9: return 580;
        case 10: return 630;
        default: return 0;
    }

    }

    public int getNumeroReinscripcion() {
        return numeroReinscripcion;
    }

    public void setNumeroReinscripcion(int numeroReinscripcion) {
        this.numeroReinscripcion = numeroReinscripcion;
    }
    
    
    


    
    

    @Override
public String toString() {
    return numeroCuenta + " - " + nombres + " " + apellidos
           + " (Semestre: " + semestre + ", Edad: " + edad + ")"
           + "\nDireccion: " + direccion + "\n";
}

}

   
