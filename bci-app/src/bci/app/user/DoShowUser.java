package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        addIntegerField("userId", Prompt.userId());
    
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int userId = integerField("userId");
            String user = _receiver.showUser(userId);

            _display.popup(user);
        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getId());
          }
    }

}
