package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("workId", Prompt.workId());
        
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int workId = integerField("workId");
            String work = _receiver.showWork(workId);
            _display.popup(work);

        } catch(bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getId());
          }
    }

}
    
