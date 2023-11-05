package edu.upc.dsa.exceptions;

public class JuegoYaExisteException extends Throwable {
    private static final String errorMessage = "Ese juego ya existe (el ID tiene que ser único)";
    public JuegoYaExisteException() {
        super(errorMessage);
    }
}
