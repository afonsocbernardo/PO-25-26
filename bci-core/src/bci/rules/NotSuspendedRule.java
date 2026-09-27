package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;

public class NotSuspendedRule extends Rule {
    
    public NotSuspendedRule() {
        super(2);
    }

    @Override
    public void check(Work work, User user) throws RuleFailedException {
        if (!user.getStatus().canRequest()) {
            throw new RuleFailedException(user.getId(), work.getId(), getId());
        }
    }
}
