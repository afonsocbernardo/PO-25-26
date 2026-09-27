package bci.user;
import bci.work.Work;

public class RequestNotification extends Notification {

    public RequestNotification(Work work) {
        super(work);
    }

    @Override
    public String getSituation() {
        return "REQUISIÇÃO";
    }
    
}
