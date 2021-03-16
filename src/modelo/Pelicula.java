package modelo;

public class Pelicula {
    private String imdbID;
    private String titulo;
    private String director;
    private String lanzamiento;
    private String trama;
    private String elenco;
    private String paisOrigen;
    private String lenguaje;
    private String duracion;
    private String imdbRating;

    public Pelicula() {
    }

    public Pelicula(String imdbID, String titulo, String tipo,String director, String lanzamiento, String trama, 
            String elenco, String paisOrigen, String lenguaje, String duracion, String imdbRating) {
        this.imdbID = imdbID;
        this.titulo = titulo;
        this.director = director;
        this.lanzamiento = lanzamiento;
        this.trama = trama;
        this.elenco = elenco;
        this.paisOrigen = paisOrigen;
        this.lenguaje = lenguaje;
        this.duracion = duracion;
        this.imdbRating = imdbRating;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(String lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;   
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
    
    @Override
    public String toString() {
        return "Titulo: " + titulo + "\n" + 
               "Director: " + director + "\n" +
               "Lanzamiento: " + lanzamiento + "\n" +
               "Trama: " + trama + "\n" +
               "Elenco: " + elenco + "\n" +
               "Pais de Origen: " + paisOrigen + "\n" +
               "Lenguaje: " + lenguaje + "\n" +
               "Duracion: " + duracion + "\n" +
               "IMdB Rating: " + imdbRating + "/10";
    }
    
}
