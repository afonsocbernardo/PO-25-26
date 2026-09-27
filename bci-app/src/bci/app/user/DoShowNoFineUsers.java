package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

public class DoShowNoFineUsers extends Command<LibraryManager> {

    DoShowNoFineUsers(LibraryManager receiver) {
        super(Label.SHOW_NOFINE_USERS, receiver);
	
    }

    @Override
    protected final void execute() {
        for (String users: _receiver.showAllNoFineUsers()) {
            _display.popup(users);
        }
    }
    
}
