package edu.upc.dsa;


import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;

import java.util.List;

public interface JuegoManager {


    public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNoEnPartidaException, UserNotFoundException;
    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException;
    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException;
    public String consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException;
    public String stringToJSON(String args);
    public Juego addJuego(Juego j);
    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException;
    public int size();
    public List<Partida> consultarPartidas(String id) throws UserNotFoundException;
    public Jugador addJugador(Jugador jugador);
    public Jugador addJugador();
    }
