package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("userId", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {

        try {
            int userId = integerField("userId");
            for(String notification: _receiver.showUserNotifications(userId)) {
                _display.popup(notification);
            }

        
        } catch (bci.exceptions.NoSuchUserException e) {

            throw new NoSuchUserException(e.getId());
        }
    }

}
