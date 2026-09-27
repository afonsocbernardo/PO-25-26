package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;
import java.io.Serializable;

public abstract class Rule implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _id;

    public Rule(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public abstract void check(Work work, User user) throws RuleFailedException;
}
