package recursos;

import java.util.Scanner;

public class Selector {

    private final String[] opciones;
    private final int opcion;

    public Selector(String lasOpciones) {
        opciones = lasOpciones.split(",");
        opcion = 0;
    }

    public int getOpcion() {
        int j;
        System.out.println("------------------------------------");
        System.out.println(opciones[0] + " ");
        System.out.println("------------------------------------");
        for (j = 1; j < opciones.length; j++) {
            System.out.println("(" + j + ")" + " ---------------- " + opciones[j]);
        }
        System.out.println("------------------------------------");
        System.out.println("(" + j + ")" + " --------------- " + "SALIR");
        System.out.println("------------------------------------");

        int op = leerInt("INGRESE UNA OPCION: ", 1, opciones.length);
        return op == opciones.length ? 99 : op;
    }

    private int leerInt(String mensaje, int limInf, int limSup) {
        int n = 0;
        Scanner teclado = new Scanner(System.in);
        String errorNumerico = "Debe ingesar un numero.";
        do {
            try {
                System.out.print(mensaje);
                n = new Integer(teclado.nextLine());
                if (n < limInf || n > limSup) {
                    System.out.println("Dato fuera de rango (" + limInf + " ... " + limSup + ") \n");
                }
            } catch (NumberFormatException e) {
                System.out.println(errorNumerico + "\n");
                n = limInf - 1;
            }
        } while (n < limInf || n > limSup);

        return n;
    }
}
