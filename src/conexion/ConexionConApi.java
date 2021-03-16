package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import modelo.Pelicula;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConexionConApi {

    private static final String URL_API = 
            "http://www.omdbapi.com/?&apikey=d905fa6b&type=movie&s=";
    private static final String URL_API_ID = 
            "http://www.omdbapi.com/?&apikey=d905fa6b&type=movie&i=";

    public static void buscarPelicula(String busqueda, Map<Integer, 
            Pelicula> listaDePeliculas, int index) {
        String linea;
        try {
            URL url = new URL(URL_API + busqueda.toLowerCase() + "&page=" + index);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            try (BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream()))) {
                linea = rd.readLine();
            }

            JSONObject objetoJson = new JSONObject(linea);
            
            JSONArray elArrayJson = objetoJson.getJSONArray("Search");
            
            listaDePeliculas.clear();
            
            if (!elArrayJson.isEmpty()) {
                for (int i = 0; i < elArrayJson.length(); i++) {
                    Pelicula p = new Pelicula();
                    
                    p.setImdbID(elArrayJson.getJSONObject(i).getString("imdbID"));
                    p.setTitulo(elArrayJson.getJSONObject(i).getString("Title"));
                    p.setLanzamiento(elArrayJson.getJSONObject(i).getString("Year"));
                    
                    listaDePeliculas.put(i, p);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR PELICULA\n" + e.getMessage());
        }
    }

    public static Pelicula encontrarPeliculaPorID(String id) {
        Pelicula p = new Pelicula();
        String linea;
        
        try {
            URL url = new URL(URL_API_ID + id);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            try (BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream()))) {
                linea = rd.readLine();
            }

            JSONObject objetoJson = new JSONObject(linea);
            
            p.setImdbID(objetoJson.getString("imdbID"));
            p.setTitulo(objetoJson.getString("Title"));
            p.setDirector(objetoJson.getString("Director"));
            p.setLanzamiento(objetoJson.getString("Year"));
            p.setTrama(objetoJson.getString("Plot"));
            p.setElenco(objetoJson.getString("Actors"));
            p.setPaisOrigen(objetoJson.getString("Country"));
            p.setLenguaje(objetoJson.getString("Language"));
            p.setDuracion(objetoJson.getString("Runtime"));
            p.setImdbRating(objetoJson.getString("imdbRating"));
            
        } catch (IOException | JSONException e) {
            System.out.println("ERROR AL CARGAR PELICULA\n" + e.getMessage());
        }

        return p;
    }
}
