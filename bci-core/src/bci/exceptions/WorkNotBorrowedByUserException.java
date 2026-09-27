package bci.exceptions;

public class WorkNotBorrowedByUserException extends Exception {

    private int _userId;
    private int _workId;

    public WorkNotBorrowedByUserException(int workId, int userId) {
        _workId = workId;
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }
    
}
