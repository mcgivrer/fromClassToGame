package fr.snapgames.fromclasstogame.core.exceptions.io;

public class UnknownResource extends Exception {
    public UnknownResource(String message,Exception e) {
        super(message,e);
    }
}
