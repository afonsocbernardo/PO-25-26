package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;

public class NotAvailableRule extends Rule {

    public NotAvailableRule() {
        super(3);
    }

    @Override
    public void check(Work work, User user) throws RuleFailedException {
        if (!work.isAvailable()) {
            throw new RuleFailedException(user.getId(), work.getId(), getId());
        }
    }  
    
}
