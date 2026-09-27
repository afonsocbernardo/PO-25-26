package bci.exceptions;

public class UserRegistrationFailedException extends Exception {
    
    private String _name;
    private String _email;

    public UserRegistrationFailedException(String name, String email) {
        _name = name;
        _email = email;
    }

    public String getName() {
        return _name;
    }

    public String getEmail() {
        return _email;
    }
}

