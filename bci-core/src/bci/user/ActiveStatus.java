package bci.user;


public class ActiveStatus extends UserStatus {


    @Override
    public boolean canRequest() {
        return true;
    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public String toString() {
        return "ACTIVO";
    }
}