package myapp.jsealey.pmdm02;

import android.util.Log;

import java.util.ArrayList;

// Para la clase que hace los cáculos lo he puesto para que corra en un hilo diferente al main para no forzarlo demasiado
public class Calculos extends Thread {

    ////////////////////////////////////////////////////
    // Atributos
    int posicion;
    int numeroPrimo;

    ////////////////////////////////////////////////////
    // Constructor
    // Obtiene el número que introduce el usuario y lo asigna como atributo para ser utilizado después
    public Calculos(int posicion) {

        this.posicion = posicion;
    }

    ////////////////////////////////////////////////////
    // Método principal que es lanzado cuando sea crea el hilo
    public void run() {

        calcularPrimo(); // Lanzamos el método que calcula los números primos
    }

    ////////////////////////////////////////////////////
    // Método que calcula los números primos
    public void calcularPrimo() {

        int limite = 20 * posicion; // Este será el número para crear el tamaño de la tabla

        ArrayList<Integer> numerosPrimos = new ArrayList<Integer>(posicion); // Creamos un ArrayList donde ir añadiendo los números primos

        boolean[] criba = new boolean[limite]; // Creamos una tabla que indicará si un número es primo o no

        // Abrimos un try-catch para capturar posibles errores
        try {
            // Creamos una tabla con logitud igual a la posicion. Todos los valores se pondrán a true
            for (int i = 0; i < limite; i++) {
                criba[i] = true;
            }

            // Este bucle pasará por todos los número e irá poniendo a false los multiplos de los números que se van asignando dentro del bucle
            for (int i = 2; i < criba.length; i++) {

                for (int j = 2; i * j < criba.length; j++) {
                    criba[i * j] = false; // Esto pondrá a false todos los número pares a partir del dos y cuando termina el bucle aumenta el número a evaluar
                    // en uno (3) y seguirá elimanando los multiplos de este numero y despues a 4 etc...
                }
            }

            // Revisamos la tabla y las posiciones que son true son añadidos a otro array
            for (int i = 2; i < criba.length; i++) {
                if (criba[i]) { // Si la posicion i de la tabla criba es true

                    numerosPrimos.add(i); // añadimos el número primo al array de numeroPrimos
                }
            }

            numeroPrimo = numerosPrimos.get(posicion-1);
            // Solo para pruebas
            Log.i("NúmerosPrimos", numerosPrimos.toString());
            Log.i("Resutlado", "El primo número " +posicion+ " es el "+ numerosPrimos.get(posicion-1));

        // Manejamos posibles errores
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


