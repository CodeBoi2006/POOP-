/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

public class AlumnoCRUD extends Alumno {

    public AlumnoCRUD() {
        super();
    }

    public AlumnoCRUD(String numeroCuenta,
                      String nombres,
                      String apellidos,
                      int    edad,
                      int    semestre,
                      String direccion,
                      RegistroAcademico registro) {
        super();
        setNumeroCuenta(numeroCuenta);
        setNombreS(nombres);
        setApellidos(apellidos);
        setEdad(edad);
        setSemestre(semestre);
        setDireccion(direccion);
        setRegistro(registro);
    }

    // ----- Operaciones CRUD básicas -----

    // C: crear / inicializar un alumno
    public void crear(String numeroCuenta,
                      String nombres,
                      String apellidos,
                      int    edad,
                      int    semestre,
                      String direccion,
                      RegistroAcademico registro) {

        setNumeroCuenta(numeroCuenta);
        setNombreS(nombres);
        setApellidos(apellidos);
        setEdad(edad);
        setSemestre(semestre);
        setDireccion(direccion);
        setRegistro(registro);
    }

    // R: leer (devolvemos una representación en texto)
    public String leer() {
        return toString();
    }

    // U: actualizar campos (sin tocar el número de reinscripción)

    public void actualizarNombre(String nombres, String apellidos) {
        setNombreS(nombres);
        setApellidos(apellidos);
    }

    public void actualizarEdad(int edad) {
        setEdad(edad);
    }

    public void actualizarSemestre(int semestre) {
        setSemestre(semestre);
    }

    public void actualizarDireccion(String direccion) {
        setDireccion(direccion);
    }

    public void actualizarRegistro(RegistroAcademico registro) {
        setRegistro(registro);
    }

    // D: "eliminar" lógicamente
    public void eliminar() {
        setNumeroCuenta("");
        setNombreS("");
        setApellidos("");
        setEdad(0);
        setSemestre(0);
        setDireccion("");
        setRegistro(new RegistroAcademico());
        setNumeroReinscripcion(0);
    }
}

