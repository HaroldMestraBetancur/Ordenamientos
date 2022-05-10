/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ord.ordenamientos;

import static Ord.ordenamientos.BurbujaMejorado_Insercion.ruta;
import static Ord.ordenamientos.Burbuja_BurbujaMejorado.ruta2;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Anzul
 */
public class Intercalacion_Insercion {
    
    static String ruta = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\IntercalacionNativo.txt";
    static String ruta2 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\InsercionNativo.txt";
     static String ruta3 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaMejoraNativo.txt";

        
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
        FileWriter archivoMejorado = new FileWriter(ruta3);
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

    /*
     * *************************
     * Nombre Método: intercalación. 
     * Propósito: Ordenar dos vectores previamente ordenados utilizando el metodo burbujaMejorado,
        intercalando sus elementos, para almacenarlos de manera ordenada en su tercer vector.
     * Variables utilizadas: vec1, vec2 y vec3.
     * Precondición: Los vectores no pueden estar vacios y deben estar ordenados. 
     * Postcondición: Retorna el vector ordenado de forma ascendente.
     * *************************
     */
    public static int[] intercalacion(int[] vec1, int[] vec2) throws IOException {
        int j, i, k;
        int movi = 0;
        long inicio;
        long fin;
        long tiempo;
        int pasadas = 0;
        int comparaciones = 0;
        
        int vec3[] = new int[vec1.length + vec2.length];
        try ( //Creamos el archivo
                FileWriter archivoIntercalacion = new FileWriter(ruta)) {
            inicio = System.nanoTime();
            for (i = j = k = 0; i < vec1.length && j < vec2.length; k++) {
                pasadas++;
                comparaciones++;
                if (vec1[i] <= vec2[j]) {
                    vec3[k] = vec1[i];
                    i++;
                    movi++;
                } else {
                    vec3[k] = vec2[j];
                    j++;
                    movi++;
                }
                fin = System.nanoTime();
                tiempo = (fin - inicio);

                //Se escribe en el archivo
                archivoIntercalacion.write((k + 1) + ":" + tiempo + "\n");

            }
            //Añadir los elementos sobrantes en caso que A sea mayor  
            for (; i < vec1.length; i++, k++) {
                vec3[k] = vec1[i];
                movi++;
            }
            //Añadir los elementos sobrantes en caso que B sea mayor
            for (; j < vec2.length; j++, k++) {
                vec3[k] = vec2[j];
                movi++;
            }
             archivoIntercalacion.close();
             estadisticas(pasadas, comparaciones, 0);
            
        }
        System.out.println("\n" + "Numero de movimientos de intercalación: " + movi);
        System.out.println("Numero de comparaciones de intercalación: " + comparaciones);
        return vec3;
    }

    /*
     * *************************
     * Nombre Método: insercion. 
     * Propósito: Ordenar de forma ascendente un vector de numeros enteros. 
     * Variables utilizadas: p, j, aux, inicio, fin, tiempo, vec. 
     * Precondición: El vector a ordenar no puede estar vacio. 
     * Postcondición: Retorna el vector ordenado de forma ascendente.
     * *************************
     */
    public static int[] insercion(int vec[]) throws IOException {

        //JOptionPane.showMessageDialog(null, "Entro al metodo insercion");
        int p, j;
        int aux;
        long inicio;
        long fin;
        long tiempo;
        int pasadas = 0;
        int comparaciones = 0;
        FileWriter archivoInsercion = new FileWriter(ruta2);

        inicio = System.nanoTime();
        for (p = 1; p < vec.length; p++) {
            aux = vec[p];
            j = p - 1;
            pasadas++;
            while ((j >= 0) && (aux < vec[j])) {
                vec[j + 1] = vec[j];
                j--;
                comparaciones++;
            }
            //Acomoda el elemento en la posicion adecuada
            vec[j + 1] = aux;
            fin = System.nanoTime();
            tiempo = (fin - inicio);
            archivoInsercion.write(p + ":" + tiempo + "\n");
        }

        archivoInsercion.close();
        estadisticas(pasadas, comparaciones, 1);
        return vec;
    }

    private static void estadisticas(int pasadas, int comparaciones, int bm) {
        //System.out.println((bm == 1) ? "INSERCION" : "BURBUJA MEJORADO");
        if (bm == 1) {
            System.out.println("Inserción");

        } else {
            System.out.println("Intercalación");
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
