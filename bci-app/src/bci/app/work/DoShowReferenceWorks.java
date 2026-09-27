package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

public class DoShowReferenceWorks extends Command<LibraryManager> {

    DoShowReferenceWorks(LibraryManager receiver) {
        super(Label.SHOW_REFERENCE_WORKS, receiver);
    }

    @Override
    protected final void execute() {
        for (String works: _receiver.showAllReferenceWorks()) {
            _display.popup(works);
        }
    }
    
}
