package bci.request;

import bci.user.User;
import bci.work.Work;
import java.io.Serializable;

public class Request implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private User _user;
    private Work _work;
    private int _requestDate;
    private int _deadline;
    private int _returnDate;

    public Request(User user, Work work, int requestDate, int deadline) {
        _user = user;
        _work = work;
        _requestDate = requestDate;
        _deadline = deadline;
        _returnDate = 0;
    }

    public User getUser() {
        return _user;
    }

    public Work getWork() {
        return _work;
    }

    public int getRequestDate() {
        return _requestDate;
    }

    public int getDeadline() {
        return _deadline;
    }

    public int getReturnDate() {
        return _returnDate;
    }

    public void setReturnDate(int returnDate) {
        _returnDate = returnDate;
    }

    public boolean isActive() {
        return _returnDate == 0;
    }

    public boolean isLate(int presentDate) {
        return isActive() && presentDate > _deadline;
    }

    public int getDaysAfter(int presentDate) {
        if(!wasReturnedLate()) {
            return 0;
        }
        return presentDate - _deadline; 
    }

    public boolean wasReturnedLate() {
        return !isActive() && _returnDate > _deadline;
    }


}
