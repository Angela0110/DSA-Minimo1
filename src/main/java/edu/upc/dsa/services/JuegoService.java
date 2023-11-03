package edu.upc.dsa.services;


import edu.upc.dsa.JuegoManager;
import edu.upc.dsa.JuegoManagerImpl;
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

@Api(value = "/tracks", description = "Endpoint to Juego Service")
@Path("/tracks")
public class JuegoService {

    private JuegoManager jm;

    public JuegoService() {
        this.jm = JuegoManagerImpl.getInstance();
        if (jm.size()==0) {
            this.jm.addJuego("Pokemon", 2);
            this.jm.addJuego("GTA", 3);
            this.jm.addJuego("Super Mario", 5);
            Jugador j =new Jugador();
            Jugador j2=new Jugador();
            this.jm.addJugador(j);
            this.jm.addJugador(j2);
            this.jm.iniciarPartida(this.jm.getId("GTA"),j.getId());
        }


    }
    @GET
    @ApiOperation(value = "get all jugadores")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Juego.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJugadores() {

        List<Jugador> j = this.jm.findAll();

        GenericEntity<List<Jugador>> entity = new GenericEntity<List<Jugador>>(j) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "puntuaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/puntuaciones/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuaciones(@PathParam("id") String id) {
        int respuesta  = this.jm.consultarPuntuacion(id);
        if (id == null) return Response.status(404).build();
        else  return Response.status(201).entity(respuesta).build();
    }

    @GET
    @ApiOperation(value = "nivel de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/nivel/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivel(@PathParam("id") String id) {
        String respuesta  = this.jm.consultarNivelActual(id);
        if (id == null) return Response.status(404).build();
        else  return Response.status(201).entity(respuesta).build();
    }

//    @GET
//    @ApiOperation(value = "inicio de partida")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful", response = Partida.class),
//    })
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response inicioPartida(String id, String idUsuario) {
//
//        if (id==null || idUsuario==null)  return Response.status(500).entity("ID de juego o ID de usuario nulos").build();
//        Partida respuesta = this.jm.iniciarPartida(id,idUsuario);
//        return Response.status(201).entity(respuesta).build();
//
//    }

   /* @GET
    @ApiOperation(value = "get a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Track.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) {
        Track t = this.tm.getTrack(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    public Response deleteTrack(@PathParam("id") String id) {
        Track t = this.tm.getTrack(id);
        if (t == null) return Response.status(404).build();
        else this.tm.deleteTrack(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/")
    public Response updateTrack(Track track) {

        Track t = this.tm.updateTrack(track);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Track.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Track track) {

        if (track.getSinger()==null || track.getTitle()==null)  return Response.status(500).entity(track).build();
        this.tm.addTrack(track);
        return Response.status(201).entity(track).build();
    }
*/
}