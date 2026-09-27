package bci.user;
import bci.work.Work;

public class AvailabilityNotification extends Notification {

    public AvailabilityNotification(Work work) {
        super(work);

    }
    
    @Override
    public String getSituation() {
        return "DISPONIBILIDADE";
    }


}
