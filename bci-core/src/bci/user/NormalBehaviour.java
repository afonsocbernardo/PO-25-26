package bci.user;

public class NormalBehaviour extends UserBehaviour {

    @Override
    public int getMaxRequest() {
        return 3;
    }

    @Override
    public boolean canRequestExpensive() {
        return false;
    }

    @Override
    public int getDeadline(int copies) {
        if (copies == 1) { return 3;}
        if (copies <= 5) { return 8;}
        return 15;
    }


    @Override
    public boolean isNormal() {return true;}

    @Override 
    public boolean isCompliant() {return false;}

    @Override
    public boolean isAbsent() {return false;}


    @Override
    public String toString() {
        return "NORMAL";
    }
}
