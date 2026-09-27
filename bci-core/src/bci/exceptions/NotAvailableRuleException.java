package bci.exceptions;

public class NotAvailableRuleException extends Exception {

    private int _userId;
    private int _workId;

    public NotAvailableRuleException(int userId, int workId) {

        _userId = userId;
        _workId = workId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }
    
}
