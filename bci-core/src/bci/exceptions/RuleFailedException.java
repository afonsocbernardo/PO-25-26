package bci.exceptions;

public class RuleFailedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _userId;
    private int _workId;
    private int _ruleId = -1;

    public RuleFailedException(int userId, int workId, int ruleId) {
        _userId = userId;
        _workId = workId;
        _ruleId = ruleId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }

    public int getRuleId() {
        return _ruleId;
    }

    
}
