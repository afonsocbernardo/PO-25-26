package bci.rules;

import bci.exceptions.RuleFailedException;
import bci.user.User;
import bci.work.Category;
import bci.work.Work;

public class NotReferenceRule extends Rule {
    
    public NotReferenceRule() {
        super(5);
    }


    @Override
    public void check(Work work, User user) throws RuleFailedException {
        if (work.getCategory() == Category.REFERENCE) {
            throw new RuleFailedException(user.getId(), work.getId(), getId());
        }
    }
}
