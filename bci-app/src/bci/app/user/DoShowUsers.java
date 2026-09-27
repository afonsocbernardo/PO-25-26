package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;


/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

    DoShowUsers(LibraryManager receiver) {
        super(Label.SHOW_USERS, receiver);
	
    }

    @Override
    protected final void execute() {
        for (String users: _receiver.showAllUsers()) {
            _display.popup(users);
        }
    }

}
