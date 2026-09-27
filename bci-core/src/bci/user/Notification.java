package bci.user;

import java.io.Serializable;
import bci.work.Work;

public abstract class Notification implements Serializable {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private Work _work;
    private String _workString;

    public Notification(Work work) {
        _work = work;
        _workString = work.toString();
    }

    public Work getWork() {
        return _work;
    }

    public abstract String getSituation();
    
    @Override
    public String toString() {
        return getSituation() + ": " + _workString;
    }
}
