package bci.exceptions;

import java.io.Serial;

public class InvalidEntryException extends Exception{

    @Serial

    private static final long serialVersionUID = 202507171003L;

    
    private final String[] _entryFields;

    public InvalidEntryException(String[] entryFields){

        _entryFields = entryFields;
    }

    public InvalidEntryException(String[] entryFields, Exception cause){
        super(cause);
        _entryFields = entryFields;
    }

    public String[] getEntrySpecification(){
        return _entryFields;
    }

    
}
