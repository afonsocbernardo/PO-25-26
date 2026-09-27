package bci.user;

import java.io.Serializable;

public abstract class UserBehaviour implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public abstract int getMaxRequest();

    public abstract boolean canRequestExpensive();

    public abstract int getDeadline(int copies);

    public abstract boolean isNormal();

    public abstract boolean isCompliant();

    public abstract boolean isAbsent();

    @Override
    public abstract String toString();
}