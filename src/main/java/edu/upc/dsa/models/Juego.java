package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Juego {

    String id;
    String descripcion;
    public int nivel;

    public Juego() {
        this.id = RandomUtils.getId();
    }


    public Juego(String descripcion, int niveles) {
        this();
        this.setDescripcion(descripcion);
        this.setNivel(niveles);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

   /* @Override
    public String toString() {
        return "Track [id="+id+", title=" + title + ", singer=" + singer +"]";
    }
*/
}