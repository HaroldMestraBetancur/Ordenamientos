/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ord.Intercalación.Inserción;

import Ord.BurbMej.Insercion.Nativo_BurbujaMejorado_Insercion;
import Ord.ordenamientos.Intercalacion_Insercion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Anzul
 */
public class Nativo_Intercalacion_Insercion extends JFrame{
    
    static String ruta = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\IntercalacionNativo.txt";
    static String ruta2 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\InsercionNativo.txt";
    static String ruta3 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaMejoraNativo.txt";
    
    private int n;
    private Intercalacion_Insercion ordenamientos;
    private int altoVentana, anchoVentana, borde, margen;
    private long mayorY = 0;
    private int saltoX, saltoY, topeX;
    private long incioSimulacion, finSimulacion, totalSimulacion;
    private long[] pasadasInsercion, tiemposInsercion, pasadasIntercalacion, tiemposIntercalacion;

    public Nativo_Intercalacion_Insercion() throws IOException {
        super("Ordenamientos");

        n = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de elementos a ordenar."));
        altoVentana = 350;
        anchoVentana = 1024;

        borde = 50;
        margen = 70;

        pasadasInsercion = new long[n];
        tiemposInsercion = new long[n];
        pasadasIntercalacion = new long[n];
        tiemposIntercalacion = new long[n];
        manejoDatos();

        //Darle espacio entre grafica y la ventana
        this.setPreferredSize(new Dimension(anchoVentana + (2 * (margen + borde)),
                altoVentana + (2 * (margen + borde))));
        this.setVisible(true);
        //El método de empaquetado empaqueta los componentes dentro de la ventana 
        //según los tamaños preferidos del componente.
        this.pack();
        // this.setSize(anchoVentana, altoVentana);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        this.dibujarLineas(g);
        //this.dibujarMarco(g);
    }

    public void dibujarMarco(Graphics g) {
        //Color de fondo
        Color c = new Color(227, 226, 226);
        g.setColor(c);
        g.fillRect(0, 0, anchoVentana + (2 * (margen + borde)), altoVentana + (2 * (margen + borde)));

        //Ventana interna donde se pintan los puntos
        g.setColor(Color.WHITE);

        //(x1=100, y1=100, width, heigth)
        g.fillRect(borde, borde, anchoVentana + (margen * 2), altoVentana + (margen * 2));

        g.setColor(Color.BLACK);
        //Pinta el Borde negro de la ventana anterior
        g.drawRect(borde, borde, anchoVentana + (margen * 2), altoVentana + (margen * 2));

        //Linea en Y
        g.drawLine(borde + margen, borde + margen, borde + margen, borde + margen + altoVentana);

        //Linea en X
        g.drawLine(borde + margen, borde + margen + altoVentana, borde + margen + anchoVentana, borde + margen + altoVentana);

        //Se obtiene cada cuando es el salto en el eje X
        saltoX = obtenerSaltoX(n);

        //Calcula el valor del tope maximo en eje X
        topeX = Math.round(n / saltoX) * saltoX;

        //Añade un tope mas 
        if ((n % topeX) > 0) {
            topeX = topeX + saltoX;
        } else {
            topeX = topeX;
        }
        //topeX = ((n % topeX) > 0) ? topeX + saltoX : topeX;
        //Pinta los intervalos en el eje X
        for (int i = 0; i <= topeX; i = i + saltoX) {
            //Pinta los saltos ("Numero", X, Y);
            g.drawString(String.valueOf(i), ((i * anchoVentana) / topeX) + borde + margen,
                    borde + margen + altoVentana + 40);

            /* System.out.println("Iteración en X: " + i);
            System.out.println("X:" + ((i * anchoVentana) / topeX) + borde + margen);
            System.out.println("Y:" + borde + margen + altoVentana + 40);*/
        }

        //Obtiene el salto en Y
        saltoY = obtenerSaltoY(mayorY);

        //Pinta los intervalos en el eje Y
        for (long i = 0; i <= mayorY; i = i + saltoY) {
            g.drawString(String.valueOf((int) (mayorY - i)), borde + 5,
                    (int) (((i * altoVentana) / mayorY) + borde + margen));

            /*System.out.println("Iteración en Y: " + i);
            System.out.println("X:" + ((i * anchoVentana) / topeX) + borde + margen);
            System.out.println("Y:" + borde + margen + altoVentana + 40);*/
        }

        //Pinta el titulo y los string del eje X y Y
        g.drawString("ORDENAMIENTO INSERCIÓN VS INTERCALACIÓN", (borde + anchoVentana) / 2, borde - 5);
        g.drawString("Datos", ((borde + anchoVentana) / 2) + 100,
                borde + (margen * 2) + altoVentana - 5);
        g.drawString("Tiempo", borde + 5, borde + margen - 40);

    }

    public void dibujarLineas(Graphics g) {

        //Se dibuja el marco
        dibujarMarco(g);

        g.setColor(Color.RED);//Burbuja simple
        //for (int i = 1; i < n; i++) {
        for (int i = 1; i < pasadasIntercalacion.length; i++) {
            g.drawLine((((i - 1) * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposIntercalacion[i - 1] * altoVentana) / mayorY)) + borde + margen,
                    ((i * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposIntercalacion[i - 1] * altoVentana) / mayorY)) + borde + margen);

            //System.out.println("Iteracion: " + i);
            //System.out.println("X1: " + (((i - 1) * anchoVentana) / topeX) + margen + borde);
            //System.out.println("Y1: " + (int) (altoVentana - ((tiemposBS[i - 1] * altoVentana) / mayorY)) + borde + margen);
            //System.out.println("X2: " + ((i * anchoVentana) / topeX) + margen + borde);
            //System.out.println("Y2: " + (int) (altoVentana - ((tiemposBS[i - 1] * altoVentana) / mayorY)) + borde + margen);
        }
        g.drawString("Intercalación", (anchoVentana / 2) + margen + borde + 80, borde + margen);

        g.setColor(Color.GREEN);//Burbuja mejorado
        for (int i = 1; i < pasadasInsercion.length; i++) {
            g.drawLine((((i - 1) * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposInsercion[i - 1] * altoVentana) / mayorY)) + borde + margen,
                    ((i * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposInsercion[i - 1] * altoVentana) / mayorY)) + borde + margen);
        }
        g.drawString("Insersión",(anchoVentana / 2) + margen + borde , borde + margen);
    }

    public void manejoDatos() throws IOException {
        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        System.out.println("Inicio de Simulación: " + dateformat.format(new Date()));
        // creo los array
        int array[] = new int[n / 2];
        int array2[] = new int[n / 2];
        int todo[] = new int[n];

        incioSimulacion = System.nanoTime();
        // Llena al vector de aleatorios
        ordenamientos.llenarDatos(array);
        ordenamientos.llenarDatos(array2);
        
        //2.Rellena el vector con los numeros aleatorios.
        
        int iterador = 0;
        for (int i = 0; i < array.length; i++) {
            todo[iterador] = array[i];
            iterador++;
        }

        
        
        for (int i = 0; i < array2.length; i++) {
            todo[iterador] = array2[i];
            iterador++;
        }

        
         int array1_1[] = ordenamientos.burbujaMejorado(array);
        System.out.println("**Vector A ordenado Burbuja:**");
        System.out.println(Arrays.toString(array1_1));
        System.out.println("*********************************************************************************************************************************************************");

        int array1_2[] = ordenamientos.burbujaMejorado(array2);
        System.out.println("**Vector B ordenado Burbuja:**");
        System.out.println(Arrays.toString(array1_2));
        System.out.println("*********************************************************************************************************************************************************");

        // 5.Metodo de Ordenamiento Intercalación
        int array1_3[] = ordenamientos.intercalacion(array1_1, array1_2);
        System.out.println("**Vector ordenado Intercalación:**");
        System.out.println(Arrays.toString(array1_3));
        System.out.println("*********************************************************************************************************************************************************");

        // 6.Metodo de Ordenamiento Inserción
        ordenamientos.insercion(todo);
        System.out.println("**Vector ordenado Insercion:**");
        System.out.println(Arrays.toString(todo));
        System.out.println("*********************************************************************************************************************************************************");


        leerArchivo(ruta, ruta2);

        finSimulacion = System.nanoTime();
        System.out.println("Fin de la Simulación: " + dateformat.format(new Date()));

        //Caclula el tiempo final de la simulacion        
        totalSimulacion = finSimulacion - incioSimulacion;
        //System.out.println("Tiempo de la Simulación: " + TimeUnit.NANOSECONDS.toSeconds(totalSimulacion) + " SEG");
        System.out.println("Tiempo de la Simulación: " + (totalSimulacion) + " Nanosegundos");

        //Obtiene el tiempo mayor entre las pasadas y lo guarda en la variable de mayorY
        for (int j = 1; j < n; j++) {
            if (mayorY < tiemposInsercion[j]) {
                mayorY = tiemposInsercion[j];
            } else {
                mayorY = mayorY;
            }

            if (mayorY < tiemposIntercalacion[j]) {
                mayorY = tiemposIntercalacion[j];
            } else {
                mayorY = mayorY;
            }
        }
    }

    public void leerArchivo(String rutaLeer1, String rutaLeer2) throws FileNotFoundException, IOException {
        FileReader archivoLeer1 = new FileReader(rutaLeer1);
        BufferedReader entrada = new BufferedReader(archivoLeer1);

        FileReader archivoLeer2 = new FileReader(rutaLeer2);
        BufferedReader entrada2 = new BufferedReader(archivoLeer2);

        int iterador1 = 0;
        String cadena = entrada.readLine();    //se lee la primera línea del fichero
        while (cadena != null) {               //mientras no se llegue al final del fichero                   
            String[] puntos = cadena.split(":");
            //Se pasan los puntos del archivo al vector pasadasBS
            pasadasIntercalacion[iterador1] = Long.parseLong(puntos[0]);
            tiemposIntercalacion[iterador1] = Long.parseLong(puntos[1]);
            iterador1++;
            cadena = entrada.readLine();       //se lee la siguiente línea del fichero                        
        }
        archivoLeer1.close();

        int iterador2 = 0;
        String cadenaMejo = entrada2.readLine();    //se lee la primera línea del fichero
        while (cadenaMejo != null) {               //mientras no se llegue al final del fichero                   
            String[] puntos = cadenaMejo.split(":");
            //Se pasan los puntos del archivo al vector pasadasBS
            pasadasInsercion[iterador2] = Long.parseLong(puntos[0]);
            tiemposInsercion[iterador2] = Long.parseLong(puntos[1]);
            iterador2++;
            cadenaMejo = entrada2.readLine();       //se lee la siguiente línea del fichero                        
        }
        archivoLeer2.close();
    }

    //Obtener el valor del salto en el eje x
    public int obtenerSaltoX(int num) {
        int cont = 0;
        while (num > 0) {
            num = num / 10;
            cont++;
        }
        return (int) (Math.pow(10, cont - 2) * (n / Math.pow(10, cont - 1)));
    }

    //Obtener el valor del salto en el eje x
    public int obtenerSaltoY(long num) {
        return (int) num / 10;
    }

    public static void main(String[] args) throws IOException {
        Nativo_Intercalacion_Insercion nativo = new Nativo_Intercalacion_Insercion();
        nativo.repaint();
    }
    
}
