package edu.upc.dsa.services;


import edu.upc.dsa.JuegoManager;
import edu.upc.dsa.JuegoManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/juegos", description = "Endpoint to Juego Service")
@Path("/juegos")
public class JuegoService {

    private JuegoManager jm;

    public JuegoService() throws UserNotFoundException, UserEnPartidaException, JuegoNotFoundException, NoNivelException {
        this.jm = JuegoManagerImpl.getInstance();
        if (jm.size()==0) {
            this.jm.addJugador();
            this.jm.addJugador();
        }
    }

    @POST
    @ApiOperation(value = "Crear juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/{id}/{descripcion}/{niveles}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearJuegos(@PathParam("id") String id, @PathParam("descripcion") String descripcion, @PathParam("niveles") int niveles){
        try {
            Juego j = this.jm.addJuego(id, descripcion, niveles);
            return Response.status(201).entity(j.toString()).build();
        } catch (NoNivelException e) {
            return Response.status(400).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (JuegoYaExisteException e){
            return Response.status(400).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Puntuaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/puntuaciones/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuaciones(@PathParam("id") String id){
        try {
            String r = this.jm.consultarPuntuacion(id);
            String respuesta = this.jm.stringToJSON(r);
            return Response.status(201).entity(respuesta).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (UserNoEnPartidaException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Nivel de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/nivel/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivel(@PathParam("id") String id){
        try {
            Partida partida = this.jm.consultarNivelActual(id);
            return Response.status(201).entity(partida.toString()).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (UserNoEnPartidaException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Inicio de partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/iniciar/{idJuego}/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioPartida(@PathParam("idJuego")String idJuego, @PathParam("idUsuario") String idUsuario){
        try {
            Partida partida = this.jm.iniciarPartida(idJuego,idUsuario);
            return Response.status(201).entity(partida.toString()).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (UserEnPartidaException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (JuegoNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Consultar partidas de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/partidas/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidas(@PathParam("id") String id){
        try {
            List<Partida> p = this.jm.consultarPartidas(id);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(p) {};
            return Response.status(201).entity(entity.toString()).build()  ;
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Pasar de nivel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/nivel/{idUsuario}/{Puntos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pasarNivel(@PathParam("idUsuario") String idUsuario, @PathParam("Puntos") int puntos){
        try {
            Partida partida = this.jm.pasarDeNivel(puntos,idUsuario);
            return Response.status(201).entity(partida.toString()).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (UserNoEnPartidaException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Finalizar partida de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/finalizar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarPartida(@PathParam("id") String id){
        try {
            String resultado = this.jm.FinalizarPartida(id);
            return Response.status(201).entity(this.jm.stringToJSON(resultado)).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        } catch (UserNoEnPartidaException e){
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }


    @GET
    @ApiOperation(value = "Lista de jugadores de un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/jugadores/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuariosPorPuntos(@PathParam("id") String id){
        try {
            List<Partida> j = this.jm.consultarUsuariosPorPuntuacion(id);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(j) {};
            return Response.status(201).entity(entity.toString()).build()  ;
        } catch (JuegoNotFoundException e) {
            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
        }
    }
}