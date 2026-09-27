package bci.exceptions;

public class NotEnoughInventoryException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _id;

    public NotEnoughInventoryException(int id) {
        _id = id;

    }

    public int getId() {
        return _id;
    }

}