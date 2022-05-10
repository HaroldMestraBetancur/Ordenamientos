package Ord.ordenamientos;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class BurbujaMejorado_Insercion {

    static String ruta = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\InsercionNativo.txt";
    static String ruta2 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaMejoraNativo.txt";

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
        FileWriter archivoInsercion = new FileWriter(ruta);

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
            System.out.println("Insercion");

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
