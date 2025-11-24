package ppoo;

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
    public static int importarAlumnosDesdeCSV(Alumno[] alumnos, String archivo) {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo));
            String linea = br.readLine(); // Saltar encabezado

            int contador = 0;

            while ((linea = br.readLine()) != null && contador < alumnos.length) {

                String[] partes = linea.split(",", 10); // 10 columnas , la última es materias

                AlumnoCRUD a = new AlumnoCRUD();

                a.setNombreS(partes[0]);
                a.setApellidos(partes[1]);
                a.setSemestre(Integer.parseInt(partes[2]));

                // registro académico
                double promedio = Double.parseDouble(partes[3]);
                int creditosActuales = Integer.parseInt(partes[4]);
                int creditosIdeales = Integer.parseInt(partes[5]);

                int inscritas = Integer.parseInt(partes[6]);
                int aprobadas = Integer.parseInt(partes[7]);
                int numeroReinscripcion = Integer.parseInt(partes[8]);

                a.setNumeroReinscripcion(numeroReinscripcion);

                // Reconstrucción de materias
                String materiasTexto = partes[9];
                String[] materiasSplit = materiasTexto.split("\\|");

                ArrayList<Materia> lista = new ArrayList<>();

                for (String s : materiasSplit) {
                    s = s.trim();
                    if (s.isEmpty()) continue;

                    // "Nombre (ESTADO, calificacion)"
                    int idx1 = s.indexOf("(");
                    int idx2 = s.indexOf(",");

                    String nombre = s.substring(0, idx1).trim();
                    String estado = s.substring(idx1 + 1, idx2).trim();
                    int cal = Integer.parseInt(s.substring(idx2 + 1, s.indexOf(")")).trim());

                    int creditos = estado.equals("APROBADA") ? 10 : 0;
                    lista.add(new Materia(nombre, cal, estado, creditos));
                }

                RegistroAcademico reg = new RegistroAcademico(lista);
                a.setRegistro(reg);

                alumnos[contador] = a;
                contador++;
            }

            br.close();
            return contador;

        } catch (Exception e) {
            System.out.println("ERROR al leer CSV: " + e.getMessage());
            return 0;
        }
    }

    // Exportar todos los alumnos a CSV
    public static void exportarAlumnosACSV(Alumno[] alumnos, int totalAlumnos) {
    try {
        FileWriter fw = new FileWriter("alumnos.csv");

        // Encabezado
        fw.write("Nombre,Apellidos,Semestre,Promedio,CreditosActuales,CreditosIdeales,Inscritas,Aprobadas,NumeroReinscripcion,Materias\n");

        for (int i = 0; i < totalAlumnos; i++) {
            Alumno a = alumnos[i];
            if (a == null) continue;

            RegistroAcademico reg = a.getRegistro();
            ArrayList<Materia> materias = reg.getMaterias();

            // Contadores
            int inscritas = materias.size();
            int aprobadas = 0;
            for (Materia m : materias) {
                if ("APROBADA".equals(m.getEstado())) {
                    aprobadas++;
                }
            }

            int creditosActuales = reg.getCreditosTotales();
            int creditosIdeales = PPOO.creditosPorSemestre[a.getSemestre() - 1];

            // Construir la cadena de materias
            String materiasTexto = "";
            for (Materia m : materias) {
                materiasTexto += m.getNombre() + " (" + m.getEstado() + ", " + m.getCalificacion() + ") | ";
            }

            // Fila CSV
            fw.write(
                a.getNombres() + "," +
                a.getApellidos() + "," +
                a.getSemestre() + "," +
                reg.getPromedio() + "," +
                creditosActuales + "," +
                creditosIdeales + "," +
                inscritas + "," +
                aprobadas + "," +
                a.getNumeroReinscripcion() + "," +
                "\"" + materiasTexto + "\"" + "\n"
            );

        }

        fw.close();
        System.out.println(" Archivo alumnos.csv generado correctamente.");

    } catch (Exception e) {
        System.out.println("ERROR al generar CSV: " + e.getMessage());
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

        String rutaCSV = "C:\\Users\\Michel\\Desktop\\PPOO\\direcciones.csv";
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
            System.out.println("7. Salir");
            System.out.println("8. Importar CSV");
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
                    System.out.println("Saliendo...");
                    break;
                case 8:
                    System.out.print("Ruta del archivo CSV: ");
                    String ruta = sc.nextLine();
                    totalAlumnos = importarAlumnosDesdeCSV(alumnos, ruta);
                    System.out.println("Se importaron " + totalAlumnos + " alumnos.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }



        } while (opcion != 7);

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


}
