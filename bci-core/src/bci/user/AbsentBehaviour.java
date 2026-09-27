package bci.user;

public class AbsentBehaviour extends UserBehaviour {


    @Override
    public int getMaxRequest() {
        return 1;
    }

    @Override
    public boolean canRequestExpensive() {
        return false;
    }

    @Override
    public int getDeadline(int copies) {
        return 2;
    }

    @Override
    public boolean isNormal() {return false;}

    @Override
    public boolean isCompliant() {return false;}

    @Override
    public boolean isAbsent() {return true;}
    
    @Override
    public String toString() {
        return "FALTOSO";
    }
    
}
