package bci.user;


import bci.request.Request;
import bci.work.Work;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private String _name;
    private int _id;
    private String _email;
    private UserStatus _status;
    private UserBehaviour _behaviour;
    private int _fine;
    private List<Request> _requests = new ArrayList<>();
    private List<Notification> _notifications = new ArrayList<>(); 

    private List<Work> _availabilityInterests = new ArrayList<>();
    private List<Work> _requestInterests = new ArrayList<>();
 
    /**
     * Constructor
     * @param name
     * @param id
     * @param email
     */
    public User(String name, int id, String email) {
        _name = name;
        _id = id;
        _email = email;
        _status = new ActiveStatus();
        _behaviour = new NormalBehaviour();
        _fine = 0;
    }

    public String getName() {
        return _name;
    }

    public int getId() {
        return _id;
    }

    public String getEmail() {
        return _email;
    }

    public UserStatus getStatus() {
        return _status;
    }

    public UserBehaviour getBehaviour() {
        return _behaviour;
    }

    public int getFine() {
        return _fine;
    }

    public List<Request> getRequests() {
        return _requests;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public void setStatus(UserStatus status) {
        _status = status;
    }

    public void setBehaviour(UserBehaviour behaviour) {
        _behaviour = behaviour;
    }

    public void setFine(int fine) {
        _fine = fine;
    }

    public void addRequest(Request request) {
        _requests.add(request);
    }

    public  boolean hasSameActiveRequest(Work work) {
        for (Request request: _requests) {
            if (request.isActive() && request.getWork().getId() == work.getId()) 
                return true;
        }
        return false;
    }

    public int getNumberActiveRequest() {
        int count = 0;
        for (Request request: _requests) {
            if (request.isActive()) {
                count++;
            }
        }
        return count;
    }


    public Request getActiveRequest(Work work) {
        for(Request request: _requests) {
            if (request.isActive() && request.getWork().getId() == work.getId()) {
                return request;
            }
        }
        return null;
    }


    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    public List<Notification> showAndRemoveNotification() {
        List<Notification> notifications = new ArrayList<>(_notifications);
        _notifications.clear();
        return notifications;
    }

    public void interestAvailabilityNotification(Work work, boolean wantsNotification) {
        if (wantsNotification && !hasAvailabilityInterest(work)) {
            _availabilityInterests.add(work);
        }
    }

    public void removeAvailability(Work work) {
        _availabilityInterests.remove(work);
    }

    public boolean hasAvailabilityInterest(Work work) {
        return _availabilityInterests.contains(work);
    }

    public void interestRequestNotification(Work work) {
        if (!hasRequestInterest(work)) {
            _requestInterests.add(work);
        }
    }

    public boolean hasRequestInterest(Work work) {
        return _requestInterests.contains(work);
    } 

    public void userPayFineReturn(boolean wantsToPay, int presentDate) {

        if (!wantsToPay) {
            return;
        }
        _fine = 0;
        boolean hasLateRequests = _requests.stream()
                                   .anyMatch(request -> request.isLate(presentDate));
        if (!hasLateRequests) {
            _status = new ActiveStatus();
        }
    }


    public List<Request> getReturnedRequests() {
        List<Request> returnedRequests = new ArrayList<>();
        for (Request request : _requests) {
            if (!request.isActive()) {
                returnedRequests.add(request);
            }
        }
        return returnedRequests;
    }

    public boolean lateRequests(int number, int presentDate) {
        List<Request> returnedRequests = getReturnedRequests();
        if (returnedRequests.size() < number) {
            return false;
        }
        int startId = returnedRequests.size() - number;
        for (int i = startId; i < returnedRequests.size(); i++) {
            if (!returnedRequests.get(i).wasReturnedLate()) {
                return false;
            }
        }

        return true;
    }


    public boolean onTimeRequests(int number, int presentDate) {
        List<Request> returnedRequests = getReturnedRequests();
        if (returnedRequests.size() < number) {
            return false;
        }
        int startId = returnedRequests.size() - number;
        for (int i = startId; i < returnedRequests.size(); i++) {
            if (returnedRequests.get(i).wasReturnedLate()) {
                return false;
            }
        }

        return true;
    }


    public void updateBehaviour(int presentDate) {
    
        if (onTimeRequests(5, presentDate)) {
            _behaviour = new CompliantBehaviour();
        }
   
        else if (lateRequests(3, presentDate)) {
            _behaviour = new AbsentBehaviour();
        }

        else if (_behaviour.isAbsent() && onTimeRequests(3, presentDate)) {
            _behaviour = new NormalBehaviour();
        }

        else if (_behaviour.isCompliant() && !onTimeRequests(5, presentDate)) {
            _behaviour = new NormalBehaviour();
        }
 
    }



    @Override
    public String toString() {
        String string = _id + " - " + _name + " - " + _email + " - " + _behaviour + " - " + _status;
        if (_status.isSuspended()) {
            string += _fine;
        }
        return string;
    }
}
