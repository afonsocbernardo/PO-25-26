package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("workId", Prompt.workId());
        addIntegerField("amount", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {

        int workId = integerField("workId");
        int amount = integerField("amount");
        try {    
            _receiver.updateInventory(workId, amount);

        } catch (bci.exceptions.NotEnoughInventoryException e) {
            _display.popup(Message.notEnoughInventory(e.getId(), amount));
        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getId());
        }
    }

}
