package fr.snapgames.fromclasstogame.core.entity.tilemap;

public class UnkownGameObjectException extends Exception {
    public UnkownGameObjectException(String objectName) {
        super(String.format("the GameObject neamed %s is unkown", objectName));
    }
}
