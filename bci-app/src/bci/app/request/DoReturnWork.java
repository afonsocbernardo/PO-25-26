package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {
    
    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);

        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }
    @Override
    protected final void execute() throws CommandException {
    
        try {
            int userId = integerField("userId");
            int workId = integerField("workId");


            _receiver.returnWork(userId, workId);


        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getId());
        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getId());
        } catch (bci.exceptions.WorkNotBorrowedByUserException e) {
            throw new WorkNotBorrowedByUserException(e.getWorkId(), e.getUserId());
        } catch (bci.exceptions.HasFineException e) {
            _display.popup(Message.showFine(e.getUserId(), e.getFine()));
            boolean wantsToPay = Form.confirm(Prompt.finePaymentChoice());
            _receiver.payFineReturn(wantsToPay, e.getUserId());

        }
    }

}
