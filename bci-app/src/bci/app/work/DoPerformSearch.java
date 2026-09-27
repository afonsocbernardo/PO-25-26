package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
//FIXME maybe import classes

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
        addStringField("term", Prompt.searchTerm());
    }

    @Override
    protected final void execute() {
        String term = stringField("term");  

        for (String work: _receiver.search(term)) {
            _display.popup(work);
        }

        
    }

}
