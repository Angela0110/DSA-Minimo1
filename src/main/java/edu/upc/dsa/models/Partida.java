package edu.upc.dsa.models;

import java.util.*;
public class Partida {
    Juego juego;
    String idJugador;
    int nivelActual;
    int puntosAcumulados;
    Date fechaCambioNivel;

    public Partida(Juego juego, String idJugador) {
        this.juego = juego;
        this.idJugador = idJugador;
        this.nivelActual = 1;
        this.puntosAcumulados = 50;
        this.fechaCambioNivel = null;
    }

    public String getIdJugador(){
        return this.idJugador;
    }
    public int getPuntosAcumulados(){
        return this.puntosAcumulados;
    }
    public void setPuntosAcumulados(int puntosAcumulados){
        this.puntosAcumulados = puntosAcumulados;
    }
    public void setFechaCambioNivel(Date fechaCambioNivel){
        this.fechaCambioNivel = fechaCambioNivel;
    }

    public int getNivelActual(){
        return this.nivelActual;
    }
    public void setNivelActual(int nivelActual){
        this.nivelActual = nivelActual;
    }
    public Juego getJuego(){
        return this.juego;
    }

}
