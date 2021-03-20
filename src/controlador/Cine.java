package controlador;

import conexion.ConexionConApi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Pelicula;
import recursos.Selector;
import recursos.Teclado;
import java.util.HashMap;
import java.util.Map;
import modelo.Cartelera;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cine {

    private static final String CAMINO = "src\\recursos\\datos_cartelera.json";
    private static final Cartelera CARTELERA = new Cartelera();

    public Cine() {
    }

    public void abrir() {
        recuperarDatos();

        Selector menu = new Selector("M E N U,Cartelera,"
                + "Mostrar datos de una pelicula,Cargar pelicula a la cartelera,"
                + "Eliminar pelicula de la cartelera");
        int opcion = menu.getOpcion();
        while (opcion != 99) {
            switch (opcion) {
                case 1:
                    System.out.println(carteleraToString() + "\n");
                    break;
                case 2:
                    mostrarPelicula();
                    break;
                case 3:
                    cargarPelicula();
                    guardarCambios();
                    break;
                case 4:
                    eliminarPelicula();
                    guardarCambios();
                    break;
            }
            opcion = menu.getOpcion();
        }
    }

    private void cargarPelicula() {
        Map<Integer, Pelicula> listaDePeliculas = new HashMap();

        String busqueda = Teclado.leerTexto("Buscar pelicula por titulo: ");
        busqueda = busqueda.replaceAll(" ", "+");

        String nextPage = ">";
        int index = 1;

        ConexionConApi.buscarPelicula(busqueda, listaDePeliculas,
                index);

        if (!listaDePeliculas.isEmpty()) {
            do {
                System.out.println("\nResultado de la busqueda (Pagina "
                        + index + "):" + "\n");

                String cadena = "";

                for (int i = 0; i < listaDePeliculas.size(); i++) {
                    cadena += (i + 1) + ") " + listaDePeliculas.get(i)
                            .getTitulo()
                            + " (" + listaDePeliculas.get(i).getLanzamiento()
                            + ")\n";
                }

                System.out.println(cadena);

                String[] nextPageAndIndex = gestionPaginasDeResultados(index,
                        nextPage, busqueda, listaDePeliculas).split(" ");

                nextPage = nextPageAndIndex[0];
                index = Integer.parseInt(nextPageAndIndex[1]);

            } while (!nextPage.equals("n"));

            seleccionTituloID(listaDePeliculas);

        } else {
            System.out.println("No hubo resultados!" + "\n");
        }
    }

    private void seleccionTituloID(Map<Integer, Pelicula> listaDePeliculas) {
        int seleccionID;

        do {
            seleccionID = Teclado.leerInt("Ingrese el Id de la "
                    + "pelicula a seleccionar: ") - 1;
        } while (!listaDePeliculas.containsKey(seleccionID));

        System.out.println(carteleraToString() + "\n");

        Pelicula p = ConexionConApi.encontrarPeliculaPorID(listaDePeliculas
                        .get(seleccionID).getImdbID());
        
        CARTELERA.setUnaPelicula(
                p,Teclado.leerInt("Ingrese el NUMERO del dia: ", 1, 7) - 1,
                Teclado.leerInt("Ingrese el NUMERO de la funcion: ", 1, 3) - 1);
        
        if (existeEnLaCartelera(p)) {
            System.out.println("\nSE HA CARGADO LA SIGUIENTE PELICULA\n" +
                    p.getTitulo() + " (" + p.getLanzamiento() + ")\n");
        } else {
            System.out.println("No se puedo cargar la pelicula!");
        }
    }

    private String gestionPaginasDeResultados(int index, String nextPage,
            String busqueda, Map<Integer, Pelicula> listaDePeliculas) {

        int totalResultados = listaDePeliculas.size();

        if (totalResultados == 10) {
            if (index == 1) {
                nextPage = Teclado.leerTexto("Pagina siguiente (>) - "
                        + "Seleccionar pelicula (n): ").toLowerCase();

                if (nextPage.equals(">")) {
                    index = index + 1;
                }

                if (nextPage.equals("<")) {
                    System.out.println("No hay pagina anterior!");
                }
            } else if (index > 1) {
                nextPage = Teclado.leerTexto("Pagina anterior (<) - "
                        + "Pagina siguiente (>) - Seleccionar pelicula (n): ")
                        .toLowerCase();

                if (nextPage.equals(">")) {
                    index = index + 1;
                }

                if (nextPage.equals("<")) {
                    index = index - 1;
                }
            }
        }
        if (totalResultados < 10) {
            if (index == 1) {
                nextPage = "n";
            }

            if (index > 1) {
                nextPage = Teclado.leerTexto("Pagina anterior (<) - "
                        + "Seleccionar pelicula (n): ").toLowerCase();

                if (nextPage.equals("<")) {
                    index = index - 1;
                }

                if (nextPage.equals(">")) {
                    System.out.println("No hay pagina siguiente!");
                }
            }
        }

        ConexionConApi.buscarPelicula(busqueda, listaDePeliculas, index);

        return nextPage + " " + index;
    }

    private void guardarCambios() {
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();

        ArrayList<String> todasLasPelis
                = arrayDeTodosLosDatosExistentes();

        try (FileWriter file = new FileWriter(CAMINO)) {
            if (!todasLasPelis.isEmpty()) {
                while (!todasLasPelis.isEmpty()) {
                    jsonObject = new JSONObject();

                    jsonObject.put("ID", todasLasPelis.remove(0));
                    jsonObject.put("Titulo", todasLasPelis.remove(0));
                    jsonObject.put("Director", todasLasPelis.remove(0));
                    jsonObject.put("Lanzamiento", todasLasPelis.remove(0));
                    jsonObject.put("Trama", todasLasPelis.remove(0));
                    jsonObject.put("Elenco", todasLasPelis.remove(0));
                    jsonObject.put("PaisOrigen", todasLasPelis.remove(0));
                    jsonObject.put("Lenguaje", todasLasPelis.remove(0));
                    jsonObject.put("Duracion", todasLasPelis.remove(0));
                    jsonObject.put("ImdbRating", todasLasPelis.remove(0));
                    jsonObject.put("f", todasLasPelis.remove(0));
                    jsonObject.put("c", todasLasPelis.remove(0));
                    jsonArray.put(jsonObject);
                }
                file.write(jsonArray.toString());
                file.close();
            } else {
                file.write("");
                file.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR AL ESCRIBIR ARCHIVO - "
                    + e.getMessage());
        }
    }

    private void recuperarDatos() {
        File archivo;
        FileReader flujoLectura;
        BufferedReader bufferLectura;
        String linea;

        try {
            archivo = new File(CAMINO);

            if (archivo.exists()) {
                flujoLectura = new FileReader(archivo);
                bufferLectura = new BufferedReader(flujoLectura);
                linea = bufferLectura.readLine();

                while (linea != null) {
                    JSONArray arrayJson = new JSONArray(linea);

                    for (int i = 0; i < arrayJson.length(); i++) {
                        CARTELERA.setUnaPelicula(
                            ConexionConApi.encontrarPeliculaPorID(
                            arrayJson.getJSONObject(i).getString("ID")),
                            Integer.parseInt(arrayJson.getJSONObject(i)
                                    .getString("c")),
                            Integer.parseInt(arrayJson.getJSONObject(i)
                                    .getString("f"))
                        );
                    }

                    linea = bufferLectura.readLine();
                }
                bufferLectura.close();
            } else {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("ERROR - " + e.getMessage());
        }
    }

    private void mostrarPelicula() {
        if (hayPeliculasEnCartelera()) {
            System.out.println(carteleraToString());

            System.out.println("Ingrese los datos de la pelicula "
                    + "correspondiente");

            int i, j;

            do {
                i = Teclado.leerInt("Ingrese numero del dia: ", 1, 7) - 1;
                j = Teclado.leerInt("Ingrese numero de la funcion: ", 1, 3) - 1;

                if (CARTELERA.getUnaPelicula(i, j).getTitulo() == null) {
                    System.out.println("______________________________"
                            + "___________________________________"
                            + "\nLos datos no corresponden a una "
                            + "pelicula cargada en la cartelera.\nPor favor "
                            + "vuelva a ingresar los datos.\n");
                }
            } while (CARTELERA.getUnaPelicula(i, j).getTitulo() == null);

            System.out.println("\nDATOS DE LA PELICULA SOLICITADA\n"
                    + CARTELERA.getUnaPelicula(i, j).toString() + "\n");
        } else {
            System.out.println("No hay peliculas cargadas en la cartelera!");
        }
    }

    private void eliminarPelicula() {
        if (hayPeliculasEnCartelera()) {

            System.out.println(carteleraToString());

            System.out.println("Ingrese los datos de la pelicula correspondiente");

            Pelicula p;
            int i, j;

            do {
                i = Teclado.leerInt("Ingrese el dia de la funcion: ", 1, 7) - 1;
                j = Teclado.leerInt("Ingrese numero de la funcion: ", 1, 3) - 1;
                p = CARTELERA.getUnaPelicula(i, j);
            } while (p == null);

            CARTELERA.eliminarPelicula(i, j);
            
            if (!existeEnLaCartelera(p)) {
                System.out.println("\nSE HA ELIMINADO LA SIGUIENTE PELICULA\n"
                    + p.getTitulo() + " (" + p.getLanzamiento() + ")\n");
            } else {
                System.out.println("No se puedo eliminar la pelicula seleccionada!");
            }
        } else {
            System.out.println("No hay peliculas cargadas en la cartelera!");
        }
    }
    
    private ArrayList<String> arrayDeTodosLosDatosExistentes() {
        ArrayList<String> datosExistentes = new ArrayList();

        for (int i = 0; i < peliculasDeLaCartelera().length; i++) {
            for (int j = 0; j < peliculasDeLaCartelera()[0].length; j++) {
                if (peliculasDeLaCartelera()[i][j] != null) {
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getImdbID());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getTitulo());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getDirector());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getLanzamiento());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getTrama());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getElenco());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getPaisOrigen());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getLenguaje());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getDuracion());
                    datosExistentes.add(peliculasDeLaCartelera()[i][j]
                            .getImdbRating());
                    datosExistentes.add(Integer.toString(i));
                    datosExistentes.add(Integer.toString(j));
                }
            }
        }
        return datosExistentes;
    }
    
    private String carteleraToString() {
        String cadena = String.format("|%-32s||%-32s||%-32s||%-32s||%-32s|"
                + "|%-32s||%-32s||%-32s|",
                "", "Domingo (Dia 1)", "Lunes (Dia 2)", "Martes (Dia 3)", 
                "Miercoles (Dia 4)","Jueves (Dia 5)", "Viernes (Dia 6)", 
                "Sabado (Dia 7)") + "\n";
        String[] losHorarios = {"16hs", "19hs", "22hs"};

        for (int i = 0; i < peliculasDeLaCartelera().length; i++) {
            cadena += String.format("|%-32s|", "Funcion " + (i + 1) + " (" 
                    + losHorarios[i] + ")");

            for (int j = 0; j < peliculasDeLaCartelera()[0].length; j++) {

                if (peliculasDeLaCartelera()[i][j] != null) {
                    if (peliculasDeLaCartelera()[i][j].getTitulo()
                            .length() > 30) {
                        cadena += String.format("|%-32s|", 
                                peliculasDeLaCartelera()[i][j].getTitulo()
                                        .substring(0, 30));
                    } else {
                        cadena += String.format("|%-32s|", 
                                peliculasDeLaCartelera()[i][j].getTitulo());
                    }
                } else {
                    cadena += String.format("|%-32s|", "VACIO");
                }
            }

            if (i != peliculasDeLaCartelera().length - 1) {
                cadena += "\n";
            }
        }
        return "\n" + cadena + "\n";
    }
    
    private boolean existeEnLaCartelera(Pelicula laPelicula) {
        boolean x = false;
        
        for (int i = 0; i < peliculasDeLaCartelera().length; i++) {
            for (int j = 0; j < peliculasDeLaCartelera()[0].length; j++) {
                if (peliculasDeLaCartelera()[i][j] != null 
                        && peliculasDeLaCartelera()[i][j].equals(laPelicula)) {
                    x = true;
                    break;
                }
            }
        }
        return x;
    }

    private boolean hayPeliculasEnCartelera() {
        boolean x = false;

        for (int i = 0; i < peliculasDeLaCartelera().length; i++) {
            for (int j = 0; j < peliculasDeLaCartelera()[0].length; j++) {                
                if (peliculasDeLaCartelera()[i][j] != null) {
                    x = true;
                    break;
                }
            }
        }
        
        return x;
    }
    
    private Pelicula[][] peliculasDeLaCartelera() {
        return CARTELERA.getLasPeliculas();
    }
    
}
