package edu.upc.dsa;

import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by juan on 16/11/16.
 */
public class JSONServiceTest {
    private HttpServer server;
    private WebTarget target;

    JuegoManager jm;
    Partida p;

    @Before
    public void setUp() throws Exception {

        this.jm = JuegoManagerImpl.getInstance();
        if (jm.size() == 0) {
            this.jm.addJuego("Pokemon", 2);
            this.jm.addJuego("GTA", 3);
            this.jm.addJuego("Super Mario", 5);
            Jugador j = new Jugador();
            Jugador j2 = new Jugador();
            this.jm.addJugador(j);
            p = this.jm.iniciarPartida(this.jm.getId("GTA"), j.getId());
        }
    }


    @Test
    public void ConsultarPuntuaciones() throws Exception {
        List<Jugador> jue = jm.findAll();
        String id = null;
        for (Jugador j : jue) {
            id = j.getId();
        }
        int msg1 = jm.consultarPuntuacion(id);
        Assert.assertEquals(50, msg1);
    }

    @Test
    public void ConsultarEstadoActual() throws Exception{
        List<Jugador> jue = jm.findAll();
        String id = null;
        for (Jugador j : jue) {
            id = j.getId();
        }
        String respuesta = jm.consultarNivelActual(id);
        Assert.assertEquals("El jugador está en el nivel 1 de la partida " + p, respuesta);
    }


}
