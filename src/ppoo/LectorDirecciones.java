/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ppoo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author Michel
 */
public class LectorDirecciones {

    public static String[] leerDirecciones(String archivo) {
        ArrayList<String> direcciones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // saltar encabezado

            while ((linea = br.readLine()) != null) {
                direcciones.add(linea.trim());
            }

        } catch (Exception e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }

        return direcciones.toArray(new String[0]);
    }
}

