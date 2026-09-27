package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int userId = integerField("userId");
            int workId = integerField("workId");

            int day = _receiver.requestWork(userId, workId);
            _display.popup(Message.workReturnDay(workId, day));

        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(e.getId());
        } catch (bci.exceptions.NoSuchWorkException e) {
            throw new NoSuchWorkException(e.getId());
        } catch (bci.exceptions.NotAvailableRuleException e) {
            boolean wantsNotification = Form.confirm(Prompt.returnNotificationPreference());
            _receiver.registerAvailabilityNotification(e.getUserId(), e.getWorkId(), wantsNotification);
        } catch (bci.exceptions.RuleFailedException e) {
            throw new BorrowingRuleFailedException(e.getUserId(), e.getWorkId(), e.getRuleId());
        }
    }

}
