package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.UserRegistrationFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        addStringField("name", Prompt.userName());
        addStringField("email", Prompt.userEMail());
    }

    @Override
    protected final void execute() throws CommandException {

        try {
            String name = stringField("name");
            String email = stringField("email");

            int userId = _receiver.registerUser(name, email);
            _display.popup(Message.registrationSuccessful(userId));

        } catch (bci.exceptions.UserRegistrationFailedException e) {
            throw new UserRegistrationFailedException(e.getName(), e.getEmail());
        }

    }

}
