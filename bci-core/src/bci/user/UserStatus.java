package bci.user;

import java.io.Serializable;

public abstract class UserStatus implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public abstract boolean canRequest();
    public abstract boolean isSuspended();
    
    @Override
    public abstract String toString();


}
