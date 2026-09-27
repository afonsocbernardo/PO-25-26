package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;


/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

    DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);

    }

    @Override
    protected final void execute() {
        for (String works: _receiver.showAllWorks()) {
            _display.popup(works);
        }
    }
}
