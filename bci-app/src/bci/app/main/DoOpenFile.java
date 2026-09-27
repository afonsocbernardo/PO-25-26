package bci.app.main;

import bci.LibraryManager;
import bci.app.exceptions.FileOpenFailedException;
import bci.exceptions.MissingFileAssociationException;
import bci.exceptions.UnavailableFileException;

import java.io.FileNotFoundException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * §4.1.1 Open and load files.
 */
class DoOpenFile extends Command<LibraryManager> {

    DoOpenFile(LibraryManager receiver) {
        super(Label.OPEN_FILE, receiver);
	
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.isChanged() && Form.confirm(Prompt.saveBeforeExit())) {
                DoSaveFile sc = new DoSaveFile(_receiver);
                sc.execute();
            }
            _receiver.load(Form.requestString((Prompt.openFile())));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }
}
