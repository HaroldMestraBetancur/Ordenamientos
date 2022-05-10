/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ord.ordenamientos;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Anzul
 */
public class Burbuja_BurbujaMejorado {
    
    
       static String ruta = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaNativo.txt";
    static String ruta2 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaMejoraNativo.txt";

    
    
       /*
     * *************************
     * Nombre Método: burbuja. 
     * Propósito: Ordenar de forma ascendente un vector de numeros enteros. 
     * Variables utilizadas: aux, Vec. 
     * Precondición: El vector no puede estar vacio. 
     * Postcondición: Retorna el vector ordenado de forma ascendente.
     * *************************
     */
    public static int[] burbuja(int[] vec) throws IOException {

        int numPasadas;
        long inicio;
        long fin;
        long tiempo;
        int pasadas = 0, comparaciones = 0;
    
        try ( //Creamos el archivo
                FileWriter archivoBurbuja = new FileWriter(ruta)) {

            int aux;

            inicio = System.nanoTime();
            for (int i = 0; i <= vec.length - 1; i++) {
                for (int j = 0; j < vec.length - 1; j++) {
                 comparaciones++;
                    if (vec[j] > vec[j + 1]) {
                        aux = vec[j];
                        vec[j] = vec[j + 1];
                        vec[j + 1] = aux;

                    }
                    pasadas++;
                }
                //Ya lo ordeno
                fin = System.nanoTime();
                tiempo = (fin - inicio);
                //Se escribe en el archivo
                archivoBurbuja.write((i + 1) + ":" + tiempo + "\n");
                
            }
            //Se cierra el archivo
            archivoBurbuja.close();
            estadisticas(pasadas, comparaciones, 0);

        }
        System.out.println("\n" + "**Numero de pasadas Burbuja: " + pasadas);
        return vec;
    }
    
    
    
    /*
     * *************************
     * Nombre Método: burbujaMejorado. 
     * Propósito: Ordenar de forma ascendente un vector de numeros enteros, disminuyendo
       el tiempo de comparaciones. 
     * Variables utilizadas: aux, vec. 
     * Precondición: El vector no puede estar vacio. 
     * Postcondición: Retorna el vector ordenado de forma ascendente.
     * *************************
     */
    public static int[] burbujaMejorado(int[] Vec) throws IOException {

        int numPasadas = 0;
        FileWriter archivoMejorado = new FileWriter(ruta2);
        long inicio;
        long fin;
        long tiempo;
        int pasadas = 0, comparaciones = 0;

        int aux = 0, i = 0;
        boolean band = false;

        inicio = System.nanoTime();
        while (i < Vec.length - 1 && band == false) {
            band = true;
            for (int j = 0; j < Vec.length - i - 1; j++) {
                comparaciones++;
                if (Vec[j] > Vec[j + 1]) {
                    pasadas++;
                    aux = Vec[j];
                    Vec[j] = Vec[j + 1];
                    Vec[j + 1] = aux;
                    band = false;
                }

            }
            //Ya lo ordeno
            fin = System.nanoTime();
            tiempo = (fin - inicio);

            //Se escribe en el archivo
            archivoMejorado.write((i + 1) + ":" + tiempo + "\n");
            i = i + 1;

        }
        archivoMejorado.close();
        estadisticas(pasadas, comparaciones, 0);
        return Vec;
    }

    private static void estadisticas(int pasadas, int comparaciones, int bm) {
        if (bm == 1) {
            System.out.println("Burbuja");

        } else {
            System.out.println("Burbuja Mejorado");
        }
        System.out.print("Pasadas: " + pasadas);
        System.out.print(" Comparaciones: " + comparaciones);
        System.out.println("");
    }

    public static void llenarDatos(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] = (int) (Math.random() * 100) + 1;
        }
    }
    
}
