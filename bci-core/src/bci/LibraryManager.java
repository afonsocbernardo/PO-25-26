package bci;

import bci.exceptions.*;
import java.io.*;
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {

    /** The object doing all the actual work. */
    private Library _library = new Library(/**_defaultRules**/);


    public boolean isChanged() {
      return _library.isChanged();
    }

    public int displayDate() {
      return _library.displayDate();
    }

    public void advanceDate(int days) {
      _library.advanceDate(days);
    }

    public int registerUser(String name, String email) throws UserRegistrationFailedException {
      return _library.registerUser(name, email);
    }

    public String showUser(int id) throws NoSuchUserException {
      return _library.showUser(id);
    }



    public List<String> showAllUsers() {
      return _library.showAllUsers();
    }


    public String showWork(int id) throws NoSuchWorkException {
      return _library.showWork(id);
    }

  
    public List<String> showAllWorks() {
      return _library.showAllWorks();
    }

    public List<String> showAllReferenceWorks() {
      return _library.showAllReferenceWorks();
    }

    public List<String> showUnavailableWorks() {
      return _library.showUnavaibleWorks();
    }

    public List<String> showAllNoFineUsers() {
      return _library.showAllNoFineUsers();
    }

    public List<String> showWorksByCreator(String creatorId) throws NoSuchCreatorException {
      return _library.showWorksByCreator(creatorId);
    }

    public List<String> search(String term){
      return _library.search(term);
    }

    public void updateInventory(int id, int amount) throws NotEnoughInventoryException, NoSuchWorkException {
      _library.updateInventory(id, amount);
    }

    public int requestWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException,
                                                           RuleFailedException, NotAvailableRuleException {
      return _library.requestWork(userId, workId);
    }

    public void registerAvailabilityNotification(int userId, int workId, boolean wantsNotification) {
      _library.registerAvailabilityNotification(userId, workId, wantsNotification);
    }


    public List<String> showUserNotifications(int userId) throws NoSuchUserException {
      return _library.showUserNotifications(userId);

    }

    public void returnWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException, 
                                                          WorkNotBorrowedByUserException, HasFineException {
      _library.returnWork(userId, workId);
    }

    public void payFine(int userId) throws NoSuchUserException, UserIsActiveException {
      _library.payFine(userId);
    }

    public void payFineReturn(boolean wantsToPay, int userId) {
      _library.payFineReturn(wantsToPay, userId);
    }




    /**
     * Save the current library to the associated file.
     * @throws MissingFileAssociationException if there is no associated file
     * @throws IOException if some other problem happens
     */
    public void save() throws MissingFileAssociationException, IOException {
        if (_library.getFilename() == null || _library.getFilename().equals("")) {
          throw new MissingFileAssociationException();
        }

        if (_library.isChanged()) {
          try (
              ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_library.getFilename())))){
                oos.writeObject(_library);
              }

          _library.clear();
        }
    }

    /**
     * Save the current library to a given file and associates that file
     * with the library.
     * @param filename name of the file where to save the library
     * @throws MissingFileAssociationException never happens
     * @throws IOException if some problem happens while saving
     */
    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }

    /**
     * Load a library from a given file and associates that file
     * with the library.
     * @param filename name of the file from where to load the library
     * @throws UnavailableFileException if some problem happens while loading
     */
    public void load(String filename) throws UnavailableFileException {
        try (
          ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){

          _library = (Library) ois.readObject();
          _library.setFilename(filename);
          _library.clear();
          } catch (IOException e){
            throw new UnavailableFileException(filename);
          } catch (ClassNotFoundException e){
            throw new UnavailableFileException(filename);
          }
          
    }

    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
      } catch (IOException | UnrecognizedEntryException | InvalidEntryException e) {
        throw new ImportFileException(filename, e);
      }
    }


}
