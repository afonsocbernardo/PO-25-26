package bci.user;

public class SuspendedStatus extends UserStatus{


    @Override
    public boolean canRequest() {
        return false;
    }

    @Override
    public boolean isSuspended() {
        return true;
    }
    
    @Override
    public String toString() {
        return "SUSPENSO - EUR ";
    }
}
