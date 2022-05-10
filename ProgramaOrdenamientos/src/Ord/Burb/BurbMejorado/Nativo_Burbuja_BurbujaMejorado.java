/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ord.Burb.BurbMejorado;

import Ord.BurbMej.Insercion.Nativo_BurbujaMejorado_Insercion;
import Ord.ordenamientos.Burbuja_BurbujaMejorado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Anzul
 */
public class Nativo_Burbuja_BurbujaMejorado extends JFrame{
    
    
    static String ruta = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaNativo.txt";
    static String ruta2 = "D:\\Harold\\Harold\\Harold\\Areas\\Tareas\\Analisis\\BurbujaMejoradoNativo.txt";

    private int n;
    private Burbuja_BurbujaMejorado ordenamientos;
    private int altoVentana, anchoVentana, borde, margen;
    private long mayorY = 0;
    private int saltoX, saltoY, topeX;
    private long incioSimulacion, finSimulacion, totalSimulacion;
    private long[] pasadasBurbuja, tiemposBurbuja, pasadasMejorado, tiemposMejorado;

    public Nativo_Burbuja_BurbujaMejorado() throws IOException {
        super("Ordenamientos");

        n = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de elementos a ordenar."));
        altoVentana = 350;
        anchoVentana = 1024;

        borde = 50;
        margen = 70;

        pasadasBurbuja = new long[n];
        tiemposBurbuja = new long[n];
        pasadasMejorado = new long[n];
        tiemposMejorado = new long[n];
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
        g.drawString("ORDENAMIENTO BURBUJA VS BURBUJA MEJORADO", (borde + anchoVentana) / 2, borde - 5);
        g.drawString("Datos", ((borde + anchoVentana) / 2) + 100,
                borde + (margen * 2) + altoVentana - 5);
        g.drawString("Tiempo", borde + 5, borde + margen - 40);

    }

    public void dibujarLineas(Graphics g) {

        //Se dibuja el marco
        dibujarMarco(g);

        g.setColor(Color.RED);//Burbuja simple
        for (int i = 1; i < pasadasBurbuja.length; i++) {
            g.drawLine((((i - 1) * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposBurbuja[i - 1] * altoVentana) / mayorY)) + borde + margen,
                    ((i * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposBurbuja[i - 1] * altoVentana) / mayorY)) + borde + margen);

        }
        g.drawString("Burbuja", (anchoVentana / 2) + margen + borde , borde + margen);

        g.setColor(Color.GREEN);//Burbuja mejorado
        for (int i = 1; i < n; i++) {
            g.drawLine((((i - 1) * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposMejorado[i - 1] * altoVentana) / mayorY)) + borde + margen,
                    ((i * anchoVentana) / topeX) + margen + borde,
                    (int) (altoVentana - ((tiemposMejorado[i - 1] * altoVentana) / mayorY)) + borde + margen);
        }
        g.drawString("BubMejorado", (anchoVentana / 2) + margen + borde + 60 , borde + margen);
    }

    public void manejoDatos() throws IOException {
        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        System.out.println("Inicio de Simulación: " + dateformat.format(new Date()));

        int arreglo[] = new int[n];

        incioSimulacion = System.nanoTime();
        ordenamientos.llenarDatos(arreglo);

        int arreglo2[] = arreglo.clone();

        ordenamientos.burbuja(arreglo);
        ordenamientos.burbujaMejorado(arreglo2);

        //System.out.println("Vector : " + Arrays.toString(arreglo));
        //System.out.println("Vector: " + Arrays.toString(arreglo2));
        leerArchivo(ruta, ruta2);

        finSimulacion = System.nanoTime();
        System.out.println("Fin de la Simulación: " + dateformat.format(new Date()));

        //Caclula el tiempo final de la simulacion        
        totalSimulacion = finSimulacion - incioSimulacion;
        System.out.println("Tiempo de la Simulación: " + TimeUnit.NANOSECONDS.toSeconds(totalSimulacion) + " SEG");
        System.out.println("Tiempo de la Simulación: " + (totalSimulacion) + " Nanosegundos");

        //Obtiene el tiempo mayor entre las pasadas y lo guarda en la variable de mayorY
        for (int j = 1; j < n; j++) {
            if (mayorY < tiemposBurbuja[j]) {
                mayorY = tiemposBurbuja[j];
            } else {
                mayorY = mayorY;
            }

            if (mayorY < tiemposMejorado[j]) {
                mayorY = tiemposMejorado[j];
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
            pasadasBurbuja[iterador1] = Long.parseLong(puntos[0]);
            tiemposBurbuja[iterador1] = Long.parseLong(puntos[1]);
            iterador1++;
            cadena = entrada.readLine();       //se lee la siguiente línea del fichero                        
        }
        archivoLeer1.close();

        int iterador2 = 0;
        String cadenaMejo = entrada2.readLine();    //se lee la primera línea del fichero
        while (cadenaMejo != null) {               //mientras no se llegue al final del fichero                   
            String[] puntos = cadenaMejo.split(":");
            //Se pasan los puntos del archivo al vector pasadasBS
            pasadasMejorado[iterador2] = Long.parseLong(puntos[0]);
            tiemposMejorado[iterador2] = Long.parseLong(puntos[1]);
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
        Nativo_Burbuja_BurbujaMejorado nativo = new Nativo_Burbuja_BurbujaMejorado();
        nativo.repaint();
    }
    
}
