package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userId", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int userId = integerField("userId");
            
            _receiver.payFine(userId);

        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getId());
        } catch (bci.exceptions.UserIsActiveException e) {
            throw new UserIsActiveException(e.getId());
        }
    }

}
