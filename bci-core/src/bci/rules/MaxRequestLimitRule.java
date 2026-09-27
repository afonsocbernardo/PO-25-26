package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Work;

public class MaxRequestLimitRule extends Rule {

    public MaxRequestLimitRule() {
        super(4);
    }
    
    @Override
    public void check(Work work, User user) throws RuleFailedException{
        int limit = user.getBehaviour().getMaxRequest();
        if (user.getNumberActiveRequest() >= limit){
            throw new RuleFailedException(user.getId(), work.getId(),getId());
        }
    }
}
