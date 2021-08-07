package fr.snapgames.fromclasstogame.io.exception;

public class UnknownResource extends Exception {
    public UnknownResource(String message,Exception e) {
        super(message,e);
    }
}
