package edu.upc.dsa;

import java.util.LinkedList;
import java.util.List;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import org.apache.log4j.Logger;
import java.util.*;


public class JuegoManagerImpl implements JuegoManager {
    private static JuegoManager instance;
    protected List<Partida> partidas;
    protected Map<String, Jugador> jugadores;
    protected Map<String, Juego> juegos;
    final static Logger logger = Logger.getLogger(JuegoManagerImpl.class);

    private JuegoManagerImpl() {
        this.partidas = new LinkedList<>();
        this.jugadores = new HashMap<>();
        this.juegos = new HashMap<>();
    }

    public static JuegoManager getInstance() {
        if (instance == null) {
            instance = new JuegoManagerImpl();
        }
        return instance;
    }

    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException {
        logger.info("El usuario " + identificadorUsuario + " quiere iniciar una partida del juego " + identificadorJuego);
        Jugador jugador = jugadores.get(identificadorUsuario);
        if (jugador != null) {
            if (jugador.getPartida() == null) {
                Juego juego = juegos.get(identificadorJuego);
                if (juego != null) {
                    Partida partida = new Partida(juego, identificadorUsuario);
                    jugador.setPartida(partida);
                    partidas.add(partida);
                    logger.info("Partida " + partida + " está iniciada");
                    logger.info("El jugador " + identificadorUsuario + " empieza el juego " + juego.getId());
                    return partida;
                } else {
                    logger.info("El juego no existe");
                    throw new JuegoNotFoundException();
                }
            }
            else {
                logger.info("El usuario ya tiene una partida activa");
                throw new UserEnPartidaException();
            }
        } else {
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
    }

    public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("Los puntos conseguidos por el jugador " + id + " en el nivel son " + puntosConseguidos);
        Date fecha = new Date();
        Jugador jugador = jugadores.get(id);
        if (jugador == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (jugador.getPartida() == null){
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
            else{
                Partida p = jugador.getPartida();
                if (Integer.parseInt(p.getNivelActual()) < p.getJuego().getNivel()) {
                    int nivel = Integer.parseInt(p.getNivelActual()) + 1;
                    p.setNivelActual(String.valueOf(nivel));
                    p.setPuntosAcumulados(p.getPuntosAcumulados() + puntosConseguidos);
                    p.setFechaCambioNivel(fecha);
                    logger.info("Pasa al nivel " + p.getNivelActual() + " el día " + fecha + " y tiene en total " + p.getPuntosAcumulados() + " puntos");
                    return p;
                } else {
                    p.setPuntosAcumulados(p.getPuntosAcumulados() + puntosConseguidos + 100);
                    p.setNivelActual("Todos los niveles superados");
                    p.setFechaCambioNivel(fecha);
                    jugador.setPartida(null);
                    logger.info("Acabó el último nivel el " + fecha + " con " + p.getPuntosAcumulados() + " puntos");
                    return p;
                }
            }
        }
    }

    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info(" El jugador " + identificadorUsuario + " quiere consultar su nivel actual");
        Jugador j = jugadores.get(identificadorUsuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else {
            if (j.getPartida() != null) {
                Partida partida = j.getPartida();
                String nivel = partida.getNivelActual();
                logger.info("El jugador está en el nivel " + nivel + " de la partida " + partida);
                return partida;
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public String consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("El jugador con id " + identificadorUsuario + " quiere consultar su puntuación");
        Jugador j = jugadores.get(identificadorUsuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPartida() != null) {
                Partida partida = j.getPartida();
                int puntos = partida.getPuntosAcumulados();
                logger.info("El usuario tiene "+ puntos + " puntos");
                return "El usuario tiene "+ puntos + " puntos";
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("El jugador con id = " + id + " quiere acabar la partida");
        Jugador j = jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPartida() != null) {
                j.setPartida(null);
                logger.info("El usuario con id " + id + " ha finalizado la partida actual");
                return "El usuario con id " + id + " ha finalizado la partida actual";
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public List<Partida> consultarPartidas(String id) throws UserNotFoundException {
        logger.info("El usuario " + id + " quiere consultar una lista con sus partidas");
        List<Partida> par = new LinkedList<Partida>();
        Jugador j = jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            for (Partida p:partidas){
                if (p.getIdJugador().equals(id)){
                    par.add(p);
                }
            }
            if (par.isEmpty()) {
                logger.info("El usuario aun no jugó ninguna partida");
                return Collections.emptyList();
            }
            else {
                return par;
            }
        }
    }

    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException {
        logger.info("Se quiere consultar la lista de usuarios ordenada por puntuación descendente que han participado en el juego con id " + idJuego);
        Juego juego = juegos.get(idJuego);
        if (juego == null) {
            logger.info("El juego no existe");
            throw new JuegoNotFoundException();
        }

        List<Partida> partidasJuego = new ArrayList<>();
        for (Partida p : partidas) {
            if (p.getJuego().getId().equals(idJuego)) {
                partidasJuego.add(p);
            }
        }

        if (partidasJuego.isEmpty()) {
            logger.info("Nadie jugó a ese juego por ahora");
            return Collections.emptyList();
        } else {
            // Ordenar por puntuación descendente
            partidasJuego.sort(Comparator.comparingInt(Partida::getPuntosAcumulados).reversed());
            return partidasJuego;
        }
    }

    public Juego addJuego(Juego j) {
        this.juegos.put(j.getId(), j);
        logger.info("Nuevo juego añadido");
        return j;
    }

    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException {
        logger.info("Crear un nuevo juego con id " + id + " descripcion = " + descripcion + " y " + niveles + " niveles");
        Juego j = juegos.get(id);
        if (j != null){
            logger.info("Ese juego ya existe (el ID tiene que ser único)");
            throw new JuegoYaExisteException();
        }
        else{
            if (niveles > 0){
                return this.addJuego(new Juego(id, descripcion, niveles));
            }
            logger.info("El juego tiene que tener al menos un nivel");
            throw new NoNivelException();
        }
    }

    public Jugador addJugador(Jugador jugador){
        this.jugadores.put(jugador.getId(), jugador);
        logger.info("Nuevo jugador añadido con id: " + jugador.getId());
        return jugador;
    }

    public Jugador addJugador() {
        return this.addJugador(new Jugador());
    }

    public int size() {
        int ret = this.jugadores.size();
        logger.info("size " + ret);

        return ret;
    }

    public String stringToJSON(String args) {
        String json = "{\"Resultado\": \"" + args + "\"}";
        return json;
    }
}