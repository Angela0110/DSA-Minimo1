package edu.upc.dsa;

import java.util.LinkedList;
import java.util.List;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import org.apache.log4j.Logger;
import java.util.*;

public class JuegoManagerImpl implements JuegoManager {
    private static JuegoManager instance;
    protected List<Partida> partidas;
    protected List<Jugador> jugadores;
    protected List<Juego> juegos;
    final static Logger logger = Logger.getLogger(JuegoManagerImpl.class);

    private JuegoManagerImpl() {
        this.partidas = new LinkedList<>();
        this.jugadores = new LinkedList<>();
        this.juegos = new LinkedList<>();
    }

    public static JuegoManager getInstance() {
        if (instance==null) instance = new JuegoManagerImpl();
        return instance;
    }
    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) {
        for (Jugador j:jugadores){
            if(j.getId().equals(identificadorUsuario) && j.getPartida()==null){
                for (Juego juego: juegos){
                    if (juego.getId().equals(identificadorJuego)) {
                        Partida partida = new Partida(juego,identificadorUsuario);
                        j.setPartida(partida);
                        partidas.add(partida);
                        logger.info("partida "+ partida +" está iniciada");
                        logger.info("El jugador" + identificadorUsuario + " empieza el juego " + juego);
                        return partida;
                    }
                }
                logger.info("El juego no existe.");
            }
        }
        logger.info ("El usuario no existe o ya tiene una partida activa.");
        return null;
    }
    public void pasarDeNivel(int puntosConseguidos, Date fecha, String id) {
        for (Partida p:partidas){
            if (p.getIdJugador().equals(id)){
                if (p.getNivelActual() < p.getJuego().getNivel()) {
                    p.setNivelActual(p.getNivelActual()+1);
                    p.setPuntosAcumulados(p.getPuntosAcumulados() + puntosConseguidos);
                } else {
                    p.setPuntosAcumulados(p.getPuntosAcumulados()+puntosConseguidos+100);
                    p.setFechaCambioNivel(fecha);
                    for (Jugador j:jugadores){
                        if (j.getId().equals(id)){
                            j.setPartida(null);
                        }
                    }
                }
            }
            else {
                throw new IllegalArgumentException("El usuario no existe o no está en una partida en curso.");
            }
        }
    }

    public String consultarNivelActual(String identificadorUsuario) {
        logger.info("Parametro id = " + identificadorUsuario);
        for (Jugador j:jugadores) {
            if (j.getId().equals(identificadorUsuario) && j.getPartida() != null) {
                Partida partida = j.getPartida();
                int nivel = partida.getNivelActual();
                logger.info("El jugador está en el nivel " + nivel + " de la partida " + partida);
                return "El jugador está en el nivel " + nivel + " de la partida " + partida;
            }

        }
        throw new IllegalArgumentException("El usuario no existe o no tiene una partida en curso.");

    }
    public int consultarPuntuacion(String identificadorUsuario) {
        logger.info("Parametro id = " + identificadorUsuario);
        for (Jugador j:jugadores) {
            if (j.getId().equals(identificadorUsuario) && j.getPartida() != null) {
                Partida partida = j.getPartida();
                int puntos = partida.getPuntosAcumulados();
                logger.info("El usuario tiene "+ puntos + " puntos");
                return puntos;
            }

        }
        logger.info("El usuario no existe o no tiene una partida en curso.");
        throw new IllegalArgumentException("El usuario no existe o no tiene una partida en curso.");

    }

    public void FinalizarPartida(String id){
        for (Jugador j:jugadores) {
            if (j.getId().equals(id) && j.getPartida() != null) {
                j.setPartida(null);
                return;
            }
        }
        throw new IllegalArgumentException("El usuario no existe o no tiene una partida en curso.");
    }

    public Juego addJuego(Juego j) {

        this.juegos.add (j);
        logger.info("nuevo juego añadido");
        return j;
    }
    public Juego addJuego(String descripcion, int niveles) {
        return this.addJuego(new Juego(descripcion, niveles));
    }

    public Jugador addJugador(Jugador jugador){
        this.jugadores.add(jugador);
        logger.info("nuevo jugador añadido " + jugador.getId());
        return jugador;
    }

    public int size() {
        int ret = this.juegos.size();
        logger.info("size " + ret);

        return ret;
    }
    public List<Partida> consultarPartidas(String id){
        List<Partida> par = new LinkedList<Partida>();
        for (Partida p:partidas){
            if (p.getIdJugador().equals(id)){
                par.add(p);
            }
        }
        return par;
    }

    public List<Jugador> findAll(){
        return jugadores;
    }

   public String getId(String descipcion){
        for (Juego j: juegos){
            if (j.getDescripcion().equals(descipcion)){
                return j.getId();
            }
        }
        return null;
    }
}