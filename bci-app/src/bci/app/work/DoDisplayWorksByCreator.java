package bci.app.work;

import java.util.List;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("creatorId", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException {
        String creatorId = stringField("creatorId");

        try {
            List<String> works = _receiver.showWorksByCreator(creatorId);
            for (String work: works) {
                _display.popup(work);
            }
        } catch (bci.exceptions.NoSuchCreatorException e) {
            throw new NoSuchCreatorException(creatorId);
        }
    
    }

}
