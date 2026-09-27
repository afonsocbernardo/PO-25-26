package bci.exceptions;

public class HasFineException extends Exception {

    private int _userId;
    private int _fine;

    public HasFineException(int userId, int fine) {
        _userId = userId;
        _fine = fine;
    }

    public int getUserId() {
        return _userId;
    }

    public int getFine() {
        return _fine;
    }

}
