package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

public class DoShowUnavailableWorks extends Command<LibraryManager> {

    DoShowUnavailableWorks(LibraryManager receiver) {
        super(Label.SHOW_UNAVAILABLE_WORKS, receiver);
    }

    @Override
    protected final void execute() {
        for (String works: _receiver.showUnavailableWorks()) {
            _display.popup(works);
        }
    }
    
}
