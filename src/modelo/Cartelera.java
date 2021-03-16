package modelo;

import java.util.ArrayList;

public class Cartelera {

    private Pelicula[][] lasPeliculas;
    private final String[] losHorarios = {"16hs", "19hs", "22hs"};

    public Cartelera() {
        lasPeliculas = new Pelicula[3][7];
        llenarEnBlanco();
    }

    public Pelicula[][] getLasPeliculas() {
        return lasPeliculas;
    }

    public void setLasPeliculas(Pelicula[][] lasPeliculas) {
        this.lasPeliculas = lasPeliculas;
    }

    public String toStringCartelera() {
        String cadena = String.format("|%-32s||%-32s||%-32s||%-32s||%-32s|"
                + "|%-32s||%-32s||%-32s|",
                "", "Domingo (Dia 1)", "Lunes (Dia 2)", "Martes (Dia 3)", 
                "Miercoles (Dia 4)","Jueves (Dia 5)", "Viernes (Dia 6)", 
                "Sabado (Dia 7)") + "\n";

        for (int i = 0; i < lasPeliculas.length; i++) {
            cadena += String.format("|%-32s|", "Funcion " + (i + 1) + " (" 
                    + losHorarios[i] + ")");

            for (int j = 0; j < lasPeliculas[0].length; j++) {

                if (lasPeliculas[i][j].getTitulo() != null) {
                    if (lasPeliculas[i][j].getTitulo().length() > 30) {
                        cadena += String.format("|%-32s|", 
                                lasPeliculas[i][j].getTitulo().substring(0, 30));
                    } else {
                        cadena += String.format("|%-32s|", 
                                lasPeliculas[i][j].getTitulo());
                    }
                } else {
                    cadena += String.format("|%-32s|", "VACIO");
                }
            }

            if (i != lasPeliculas.length - 1) {
                cadena += "\n";
            }
        }
        return "\n" + cadena + "\n";
    }

    private void llenarEnBlanco() {
        for (int i = 0; i < lasPeliculas.length; i++) {
            for (int j = 0; j < lasPeliculas[0].length; j++) {
                lasPeliculas[i][j] = new Pelicula();
            }
        }
    }

    public void insertarUnaPelicula(Pelicula laPelicula, int c, int f) {
        lasPeliculas[f][c] = laPelicula;
    }

    public void eliminarPelicula(int c, int f) {
        lasPeliculas[f][c] = new Pelicula();
    }

    public boolean existeEnLaCartelera(Pelicula laPelicula) {
        for (int i = 0; i < lasPeliculas.length; i++) {
            for (int j = 0; j < lasPeliculas[0].length; j++) {
                if (lasPeliculas[i][j].equals(laPelicula)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> arrayDeTodosLosDatosExistentes() {
        ArrayList<String> aux = new ArrayList();

        for (int i = 0; i < lasPeliculas.length; i++) {
            for (int j = 0; j < lasPeliculas[0].length; j++) {
                if (lasPeliculas[i][j].getTitulo() != null) {
                    aux.add(lasPeliculas[i][j].getImdbID());
                    aux.add(lasPeliculas[i][j].getTitulo());
                    aux.add(lasPeliculas[i][j].getDirector());
                    aux.add(lasPeliculas[i][j].getLanzamiento());
                    aux.add(lasPeliculas[i][j].getTrama());
                    aux.add(lasPeliculas[i][j].getElenco());
                    aux.add(lasPeliculas[i][j].getPaisOrigen());
                    aux.add(lasPeliculas[i][j].getLenguaje());
                    aux.add(lasPeliculas[i][j].getDuracion());
                    aux.add(lasPeliculas[i][j].getImdbRating());
                    aux.add(Integer.toString(i));
                    aux.add(Integer.toString(j));
                }
            }
        }
        return aux;
    }

    public Pelicula getUnaPelicula(int i, int j) {
        return lasPeliculas[j][i];
    }

    public boolean hayPeliculasCargadas() {
        int count = 0;
        boolean x = false;

        for (int i = 0; i < lasPeliculas.length; i++) {
            for (int j = 0; j < lasPeliculas[0].length; j++) {
                if (lasPeliculas[i][j].getTitulo() != null) {
                    count++;
                }
            }
        }
        
        if (count > 0) {
            x = true;
        }
        
        return x;
    }
}
