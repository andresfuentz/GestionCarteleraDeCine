package modelo;

public class Cartelera {

    private Pelicula[][] lasPeliculas;

    public Cartelera() {
        lasPeliculas = new Pelicula[3][7];
    }

    public Pelicula[][] getLasPeliculas() {
        return lasPeliculas;
    }

    public void setLasPeliculas(Pelicula[][] lasPeliculas) {
        this.lasPeliculas = lasPeliculas;
    }
    
    public Pelicula getUnaPelicula(int i, int j) {
        return lasPeliculas[j][i];
    }
    
    public void setUnaPelicula(Pelicula laPelicula, int c, int f) {
        lasPeliculas[f][c] = laPelicula;
    }

    public void eliminarPelicula(int c, int f) {
        lasPeliculas[f][c] = null;
    }
}
