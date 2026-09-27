package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;

public class MaxRequestPrice extends Rule {
    
    public MaxRequestPrice() {
        super(6);
    }

    @Override
    public void check(Work work, User user) throws RuleFailedException {
        if(work.getPrice() > 25 && !user.getBehaviour().canRequestExpensive()) {
            throw new RuleFailedException(user.getId(), work.getId(), getId());
        }
    }
}
