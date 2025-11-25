package ppoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class PPOO {

    // catálogo de materias por semestre
    static String[][] materiasPorSemestre = {
        {"Matemáticas I", "Programación I", "Física I", "Química", "Introducción a Ingeniería"},
        {"Matemáticas II", "Programación II", "Física II", "Estructuras Discretas", "Inglés Técnico"},
        {"Ecuaciones Diferenciales", "Estructura de Datos", "Electromagnetismo", "Estadística", "Probabilidad"},
        {"Bases de Datos", "Algoritmos Avanzados", "Cálculo Vectorial", "Finanzas", "Arquitectura de Computadoras"},
        {"Redes de Computadoras", "Sistemas Operativos", "Métodos Numéricos", "Métodos Ágiles", "Ingeniería Económica"},
        {"Simulación", "Programación Web", "Compiladores", "Software Seguro", "Minería de Datos"},
        {"Inteligencia Artificial", "IoT", "Sistemas Distribuidos", "Optimización", "Big Data"},
        {"Seguridad Informática", "Desarrollo Móvil", "Robótica", "Machine Learning", "Blockchain"},
        {"Sistemas Embebidos", "Visión Artificial", "Cómputo en la Nube", "Análisis de Datos", "Deep Learning"},
        {"Proyecto Terminal I", "Proyecto Terminal II", "Ética Profesional", "Innovación", "Gestión Empresarial"}
    };

    // créditos ideales por semestre
    public static final int[] creditosPorSemestre = {
        60, 210, 270, 320, 370, 420, 480, 530, 580, 630
    };

    // generación de registro académico
    public static RegistroAcademico generarRegistro(int semestre, String[][] materiasPorSemestre) {
        Random random = new Random();
        ArrayList<Materia> lista = new ArrayList<>();

        int cantidadMaterias = (semestre == 1) ? 5 : 5 + random.nextInt(3);

        for (int i = 0; i < cantidadMaterias; i++) {
            String nombreMateria = materiasPorSemestre[semestre - 1][i % 5];
            int calificacion = random.nextInt(11);

            String estado;
            int creditos;

            if (calificacion >= 6) {
                estado = "APROBADA";
                creditos = 10;
            } else {
                estado = "INSCRITA";
                creditos = 0;
            }

            lista.add(new Materia(nombreMateria, calificacion, estado, creditos));
        }

        return new RegistroAcademico(lista);
    }
    
    // Importar un archivo CSV previo
    public static int importarAlumnosDesdeCSV(Alumno[] alumnos, String rutaCSV) {
    int total = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
        // Leer y descartar encabezado 
        String linea = br.readLine();

        while ((linea = br.readLine()) != null && total < alumnos.length) {
            if (linea.trim().isEmpty()) continue;

            // Detectar el separador: ';' o ',' 
            String separador;
            if (linea.contains(";")) {
                separador = ";";
            } else {
                separador = ",";
            }

            String[] partes = linea.split(separador);

            // FORMATO (con numeroCuenta, direccion y nuevos campos)
            // NumeroCuenta,Nombres,Apellidos,Direccion,Edad,Semestre,
            // NumeroInscripcion,Promedio,CreditosActuales,CreditosIdeales,Inscritas,Aprobadas,Materias
            if (partes.length >= 13) {

                String numeroCuenta = partes[0].trim();
                String nombres      = partes[1].trim();
                String apellidos    = partes[2].trim();
                String direccion    = partes[3].trim();
                int    edad         = Integer.parseInt(partes[4].trim());
                int    semestre     = Integer.parseInt(partes[5].trim());
                int    numInsc      = Integer.parseInt(partes[6].trim());
                double promedio     = Double.parseDouble(partes[7].trim());

                // Estos 4 se leen por consistencia pero RegistroAcademico puede volver a calcularlos
                int creditosActuales = Integer.parseInt(partes[8].trim());
                int creditosIdeales  = Integer.parseInt(partes[9].trim());
                int inscritas        = Integer.parseInt(partes[10].trim());
                int aprobadas        = Integer.parseInt(partes[11].trim());

                String materiasTexto = partes[12].trim();

                // Quitar comillas de materias 
                if (materiasTexto.startsWith("\"") && materiasTexto.endsWith("\"") && materiasTexto.length() >= 2) {
                    materiasTexto = materiasTexto.substring(1, materiasTexto.length() - 1);
                }

                ArrayList<Materia> listaMaterias = reconstruirMateriasDesdeTexto(materiasTexto);

                RegistroAcademico reg = new RegistroAcademico();
                reg.setMaterias(listaMaterias);
                reg.setPromedio(promedio);

                AlumnoCRUD a = new AlumnoCRUD();
                a.setNumeroCuenta(numeroCuenta);
                a.setNombreS(nombres);      // o setNombres()
                a.setApellidos(apellidos);
                a.setDireccion(direccion);
                a.setEdad(edad);
                a.setSemestre(semestre);
                a.setNumeroReinscripcion(numInsc);
                a.setRegistro(reg);

                alumnos[total] = a;
                total++;
                continue;
            }


            System.out.println("Línea ignorada (formato no reconocido): " + linea);
        }

    } catch (IOException | NumberFormatException e) {
        System.out.println("ERROR al leer CSV: " + e.getMessage());
    }

    return total;
    }



    // Exportar todos los alumnos a CSV
    public static void exportarAlumnosACSV(Alumno[] alumnos, int totalAlumnos) {
    String rutaCSV = "C:\\Users\\destr\\Downloads\\PPOO_3\\PPOO_2\\PPOO_2\\PPOO\\alumnos.csv";

    String sep = ",";   // separador principal

    try (FileWriter fw = new FileWriter(rutaCSV)) {

        // Encabezado NUEVO:
        fw.write("NumeroCuenta" + sep +
                 "Nombres" + sep +
                 "Apellidos" + sep +
                 "Direccion" + sep +
                 "Edad" + sep +
                 "Semestre" + sep +
                 "NumeroInscripcion" + sep +
                 "Promedio" + sep +
                 "CreditosActuales" + sep +
                 "CreditosIdeales" + sep +
                 "Inscritas" + sep +
                 "Aprobadas" + sep +
                 "Materias\n");

        for (int i = 0; i < totalAlumnos; i++) {
            Alumno a = alumnos[i];
            if (a == null) continue;

            RegistroAcademico reg = a.getRegistro();
            if (reg == null) continue;

            ArrayList<Materia> materias = reg.getMaterias();
            if (materias == null) materias = new ArrayList<>();

            int inscritas = materias.size();
            int aprobadas = 0;
            for (Materia m : materias) {
                if (m != null && "APROBADA".equalsIgnoreCase(m.getEstado())) {
                    aprobadas++;
                }
            }

            int creditosActuales = reg.getCreditosTotales();
            int creditosIdeales  = PPOO.creditosPorSemestre[a.getSemestre() - 1];

            double promedio = reg.getPromedio();

            // Construir cadena de materias
            StringBuilder materiasSB = new StringBuilder();
            for (Materia m : materias) {
                if (m == null) continue;
                materiasSB.append(m.getNombre())
                          .append(" (")
                          .append(m.getEstado())
                          .append(", ")
                          .append(m.getCalificacion())
                          .append(") | ");
            }

            // Escapar comillas dobles dentro del texto de materias
            String materiasTexto = materiasSB.toString().trim().replace("\"", "\"\"");

            fw.write(a.getNumeroCuenta() + sep +
                     a.getNombres() + sep +              // o getNombreS()
                     a.getApellidos() + sep +
                     a.getDireccion() + sep +
                     a.getEdad() + sep +
                     a.getSemestre() + sep +
                     a.getNumeroReinscripcion() + sep +
                     promedio + sep +
                     creditosActuales + sep +
                     creditosIdeales + sep +
                     inscritas + sep +
                     aprobadas + sep +
                     "\"" + materiasTexto + "\"" + "\n");
        }

        System.out.println("CSV exportado correctamente en: " + rutaCSV);

    } catch (Exception e) {
        System.out.println("ERROR al escribir CSV: " + e.getMessage());
    }
}



    //        MAIN FINAL
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        GeneradorNombres gen = new GeneradorNombres();

        int totalAlumnos = 10;
        Alumno[] alumnos = new Alumno[totalAlumnos];

        boolean nombresGenerados = false;
        boolean datosGenerados = false;
        boolean registrosGenerados = false;

        String rutaCSV = "C:\\Users\\destr\\Downloads\\PPOO_2\\direcciones.csv";
        String[] direcciones = LectorDirecciones.leerDirecciones(rutaCSV);

        if (direcciones.length == 0) {
            System.out.println("ERROR: No se pudieron leer direcciones desde el archivo CSV.");
            return;
        }

        int opcion = 0;

        do {
            System.out.println("\nMENÚ PRINCIPAL");
            System.out.println("1. Generar alumnos");
            System.out.println("2. Generar datos personales");
            System.out.println("3. Generar registros académicos");
            System.out.println("4. Módulo CRUD alumnos");
            System.out.println("5. Generar número de inscripción (FI)");
            System.out.println("6. Exportar CSV");
            System.out.println("7. Importar CSV");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida.");
                sc.nextLine();
                continue;
            }

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer


            switch (opcion) {

                case 1:
                    System.out.println("\nGenerando alumnos...");
                    int alumnosDosNombres = totalAlumnos * 20 / 100;

                    for (int i = 0; i < totalAlumnos; i++) {
                        int cantNombres = (i < alumnosDosNombres) ? 2 : 1;

                        String nombres = gen.generarNombres(cantNombres);
                        String apellidos = gen.generarApellidos();

                        // Usamos el modelo CRUD (extiende de la clase Alumno)
                        alumnos[i] = new AlumnoCRUD();
                        alumnos[i].setNombreS(nombres);
                        alumnos[i].setApellidos(apellidos);
                        alumnos[i].setRegistro(new RegistroAcademico());
                    }

                    nombresGenerados = true;
                    System.out.println("✔ Alumnos generados.");
                    break;

                case 2:
                    if (!nombresGenerados) {
                        System.out.println("ERROR: Primero genera los alumnos.");
                        break;
                    }

                    System.out.println("\nGenerando datos personales...");
                    for (int i = 0; i < totalAlumnos; i++) {

                        int edad = 18 + random.nextInt(10);

                        int maxSemestre = switch (edad) {
                            case 18 -> 2;
                            case 19 -> 4;
                            case 20 -> 6;
                            case 21 -> 8;
                            default -> 10;
                        };

                        int semestre = 1 + random.nextInt(maxSemestre);

                        alumnos[i].setEdad(edad);
                        alumnos[i].setSemestre(semestre);
                        alumnos[i].setNumeroCuenta("3214" + i);

                        String direccion = direcciones[random.nextInt(direcciones.length)];
                        alumnos[i].setDireccion(direccion);
                    }

                    datosGenerados = true;
                    System.out.println("✔ Datos personales generados.");
                    break;

                case 3:
                    if (!datosGenerados) {
                        System.out.println("ERROR: Primero genera los datos personales.");
                        break;
                    }

                    System.out.println("\nGenerando registros académicos...");
                    for (int i = 0; i < totalAlumnos; i++) {
                        int semestre = alumnos[i].getSemestre();
                        alumnos[i].setRegistro(generarRegistro(semestre, materiasPorSemestre));
                    }

                    registrosGenerados = true;
                    System.out.println(" Registros académicos generados.");
                    break;
                    
                case 4:
                    if (!nombresGenerados) {
                        System.out.println("ERROR: Primero genera los alumnos (opción 1).");
                        break;
                    }
                    // Módulo CRUD alumnos
                    totalAlumnos = moduloCRUDAlumnos(alumnos, totalAlumnos, sc);
                    break;

                case 5:
                    if (!registrosGenerados) {
                        System.out.println("ERROR: Primero genera los registros académicos (opción 3).");
                        break;
                    }

                    System.out.println("\nCalculando número de inscripción (indicador escolar FI)...");
                    CalculadoraNumeroInscripcion.asignarNumeroInscripcion(alumnos, totalAlumnos);
                    break;

                case 6:
                    exportarAlumnosACSV(alumnos, totalAlumnos);
                    break;

                case 7:
                    System.out.print("Ruta del archivo CSV: ");
                    String rutaImportar = sc.nextLine();

                    int importados = importarAlumnosDesdeCSV(alumnos, rutaImportar);
                    totalAlumnos = importados;  

                    System.out.println("Se importaron " + importados + " alumnos.");

                    // marcar que ya tenemos alumnos completos
                    if (importados > 0) {
                        nombresGenerados   = true;
                        datosGenerados     = true;
                        registrosGenerados = true;
                    }
                    break;

                case 8:
                    System.out.println("Saliendo...");
                    break;    
                    
                default:
                    System.out.println("Opción inválida.");
            }



        } while (opcion != 8);

        sc.close();
    }
    
        // MÓDULO CRUD ALUMNOS
    public static int moduloCRUDAlumnos(Alumno[] alumnos, int totalAlumnos, Scanner sc) {

        int opcion;
        do {
            System.out.println("\n--- MÓDULO CRUD ALUMNOS ---");
            System.out.println("1. Listar alumnos");
            System.out.println("2. Editar alumno");
            System.out.println("3. Eliminar alumno");
            System.out.println("4. Regresar al menú principal");
            System.out.print("Opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida.");
                sc.nextLine();
                System.out.print("Opción: ");
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> {
                    System.out.println("\nListado de alumnos:");
                    for (int i = 0; i < totalAlumnos; i++) {
                        Alumno a = alumnos[i];
                        if (a != null) {
                            System.out.printf("%d) %s", i, a.toString());
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Número de cuenta del alumno a editar: ");
                    String cuenta = sc.nextLine();

                    Alumno a = buscarAlumnoPorCuenta(alumnos, totalAlumnos, cuenta);
                    if (a == null) {
                        System.out.println("Alumno no encontrado.");
                        break;
                    }

                    System.out.println("Editar:");
                    System.out.println("1. Nombre y apellidos");
                    System.out.println("2. Edad");
                    System.out.println("3. Semestre");
                    System.out.println("4. Dirección");
                    System.out.print("Campo a editar: ");

                    int campo;
                    while (!sc.hasNextInt()) {
                        System.out.println("Entrada inválida.");
                        sc.nextLine();
                        System.out.print("Campo a editar: ");
                    }
                    campo = sc.nextInt();
                    sc.nextLine();

                    if (a instanceof AlumnoCRUD) {
                        AlumnoCRUD crud = (AlumnoCRUD) a;

                        switch (campo) {
                            case 1 -> {
                                System.out.print("Nuevo(s) nombre(s): ");
                                String nuevoNombre = sc.nextLine();
                                System.out.print("Nuevos apellidos: ");
                                String nuevoApellido = sc.nextLine();
                                crud.actualizarNombre(nuevoNombre, nuevoApellido);
                            }
                            case 2 -> {
                                System.out.print("Nueva edad: ");
                                int nuevaEdad = sc.nextInt();
                                sc.nextLine();
                                crud.actualizarEdad(nuevaEdad);
                            }
                            case 3 -> {
                                System.out.print("Nuevo semestre: ");
                                int nuevoSem = sc.nextInt();
                                sc.nextLine();
                                crud.actualizarSemestre(nuevoSem);
                            }
                            case 4 -> {
                                System.out.print("Nueva dirección: ");
                                String nuevaDir = sc.nextLine();
                                crud.actualizarDireccion(nuevaDir);
                            }
                            default -> System.out.println("Opción de campo inválida.");
                        }

                        System.out.println("✔ Alumno actualizado.");
                    } else {
                        System.out.println("El alumno no es de tipo AlumnoCRUD.");
                    }
                }
                case 3 -> {
                    System.out.print("Índice del alumno a eliminar (como en la lista): ");

                    int indice;
                    while (!sc.hasNextInt()) {
                        System.out.println("Entrada inválida.");
                        sc.nextLine();
                        System.out.print("Índice del alumno a eliminar: ");
                    }
                    indice = sc.nextInt();
                    sc.nextLine(); // limpiar buffer

                    if (indice < 0 || indice >= totalAlumnos) {
                        System.out.println("Índice fuera de rango.");
                        break;
                    }

                    totalAlumnos = eliminarAlumnoPorIndice(alumnos, totalAlumnos, indice);
                    System.out.println("✔ Alumno eliminado. Ahora hay " + totalAlumnos + " alumnos.");
                }

                case 4 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
        return totalAlumnos;
    }

    public static Alumno buscarAlumnoPorCuenta(Alumno[] alumnos, int totalAlumnos, String cuenta) {
        if (alumnos == null || cuenta == null) {
            return null;
        }

        for (int i = 0; i < totalAlumnos; i++) {
            Alumno a = alumnos[i];
            if (a != null && cuenta.equals(a.getNumeroCuenta())) {
                return a;
            }
        }
        return null;
    }
    
    public static int eliminarAlumnoPorIndice(Alumno[] alumnos, int totalAlumnos, int indice) {
    if (indice < 0 || indice >= totalAlumnos) {
        return totalAlumnos;  
    }

    // Recorrer todos una posición hacia arriba
    for (int i = indice; i < totalAlumnos - 1; i++) {
        alumnos[i] = alumnos[i + 1];
    }

    // El último queda libre
    alumnos[totalAlumnos - 1] = null;

    // Nuevo total de alumnos
    return totalAlumnos - 1;
}
    // Convierte el texto de materias del CSV a una lista de objetos Materia
    public static ArrayList<Materia> reconstruirMateriasDesdeTexto(String materiasTexto) {
    ArrayList<Materia> listaMaterias = new ArrayList<>();

    String[] materiasSplit = materiasTexto.split("\\|");
    for (String s : materiasSplit) {
        s = s.trim();
        if (s.isEmpty()) continue;

        int idx1 = s.indexOf("(");
        int idx2 = s.indexOf(",");
        int idx3 = s.lastIndexOf(")");

        if (idx1 == -1 || idx2 == -1 || idx3 == -1) {
            continue;   // formato raro, lo saltamos
        }

        String nombreMat = s.substring(0, idx1).trim();
        String estado    = s.substring(idx1 + 1, idx2).trim();
        int cal          = Integer.parseInt(s.substring(idx2 + 1, idx3).trim());

        int creditos = estado.equalsIgnoreCase("APROBADA") ? 10 : 0;
        listaMaterias.add(new Materia(nombreMat, cal, estado, creditos));
    }

    return listaMaterias;
}


}

