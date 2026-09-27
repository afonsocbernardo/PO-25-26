package bci.user;

public class CompliantBehaviour extends UserBehaviour {


    @Override
    public int getMaxRequest() {
        return 5;
    }

    @Override
    public boolean canRequestExpensive() {
        return true;
    }

    @Override
    public int getDeadline(int copies) {
        if (copies == 1) {return 8;}
        if (copies <= 5) {return 15;}
        return 30;
    }

    @Override
    public boolean isNormal() {return false;}

    @Override
    public boolean isCompliant() {return true;}

    @Override
    public boolean isAbsent() {return false;}

    @Override
    public String toString() {
        return "CUMPRIDOR";
    }
    
}
