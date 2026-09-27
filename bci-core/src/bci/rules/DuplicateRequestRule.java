package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;

public class DuplicateRequestRule extends Rule {

    public DuplicateRequestRule() {
        super(1);
    }
    
    @Override
    public void check(Work work, User user) throws RuleFailedException {
        if (user.hasSameActiveRequest(work)) {
            throw new RuleFailedException(user.getId(), work.getId(), getId());
        
        }
    }
}
