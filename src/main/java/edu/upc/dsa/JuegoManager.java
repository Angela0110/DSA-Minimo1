package edu.upc.dsa;


import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;

import java.util.Date;
import java.util.List;

public interface JuegoManager {


    public void pasarDeNivel(int puntosConseguidos, Date fecha, String id);

    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario);
    public String consultarNivelActual(String identificadorUsuario);

    public int consultarPuntuacion(String identificadorUsuario);
    public void FinalizarPartida(String id);
    public Juego addJuego(Juego j);
    public Juego addJuego(String descripcion, int niveles);
    public int size();
    public List<Partida> consultarPartidas(String id);
    public Jugador addJugador(Jugador jugador);
    public List<Jugador> findAll();
    public String getId(String descipcion);




    }
