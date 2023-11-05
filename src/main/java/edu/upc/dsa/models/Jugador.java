package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Jugador {
    String id;
    Partida partida;

    public Jugador() {
        this.id = RandomUtils.getId();
        this.partida = null;
    }
    public String getId(){
        return this.id;
    }

    public Partida getPartida(){
        return this.partida;
    }
    public void setPartida(Partida partida){
        this.partida = partida;
    }
}
