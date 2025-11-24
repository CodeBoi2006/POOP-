/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;

/**
 *
 * @author bekis
 */
import java.util.Random;

public class GeneradorNombres {

    String[] nombresMasc = {
        "Daniel", "Juan", "Pedro", "Luis", "Carlos", "Antonio", "Leonardo",
        "Santiago", "Joshua", "Emilio", "Emiliano", "Emanuel", "Jaziel",
        "Sergio", "Iker", "Diego", "Alan", "Alejandro", "Michel", "Eddie",
        "Tyler", "Andres", "Kevin"
    };
    
    String[] nombresFem = {
        "Rebeca", "Sofia", "Sarai", "Regina", "Angela", "Wendy", "Hilda",
        "Maria", "Estefani", "Sol", "Alejandra", "Melisa", "Carolina",
        "Natalia", "Camila", "Erika", "Ximena", "Daniela", "Monserrat",
        "Alitzel", "Sara", "Fernanda", "Vivian", "Hope", "Alicia"
    };

    String[] apellidos = {
        "Perez", "Garcia", "Lopez", "Hernandez", "Reyes", "Bennett",
        "Ceballos", "Rebolledo", "Nieto", "De la Rosa", "Torres", "Rios",
        "Espinoza", "Galindo", "Mendez", "Gonzales", "Barbosa", "Ayala",
        "Santos", "Carmona", "Almaguer", "Miranda", "Leyba", "Campos",
        "Joseph"
    };

    Random random = new Random();


    public String generarNombres(int cantidadNombres) {
        int genero = random.nextInt(2);

        String[] arregloNombres;
        if (genero == 0) {
            arregloNombres = nombresMasc;
        } else {
            arregloNombres = nombresFem;
        }

        if (cantidadNombres < 1) {
            cantidadNombres = 1;
        }
        if (cantidadNombres > 2) {
            cantidadNombres = 2;
        }

        String nombres = "";


        for (int i = 0; i < cantidadNombres; i++) {
            int indice = random.nextInt(arregloNombres.length);
            if (i == 0) {
                nombres = arregloNombres[indice];
            } else {
                nombres = nombres + " " + arregloNombres[indice];
            }
        }

        return nombres;
    }

    public String generarApellidos() {
        String resultado = "";

        for (int i = 0; i < 2; i++) {
            int indice = random.nextInt(apellidos.length);
            if (i == 0) {
                resultado = apellidos[indice];
            } else {
                resultado = resultado + " " + apellidos[indice];
            }
        }

        return resultado;
    }
}
