package bci;

import bci.exceptions.*;
import bci.request.Request;
import bci.rules.*;
import bci.user.ActiveStatus;
import bci.user.AvailabilityNotification;
import bci.user.Notification;
import bci.user.SuspendedStatus;
import bci.user.User;
import bci.work.Book;
import bci.work.Category;
import bci.work.Dvd;
import bci.work.Work;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;




/** Class that represents the library as a whole. */
class Library implements Serializable {

    /** Serial number for serialization. */
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private String _filename = "";

    private boolean changed = false;
    private int _presentDate = 1;
    private int userId = 1;
    private int workId = 1;

    /** User Registry. */
    private Map<Integer, User> _userRegistry = new TreeMap<Integer, User>();

    /** Work Registry. */
    private Map<Integer, Work> _workRegistry = new TreeMap<Integer, Work>();

    private Map<String, Creator> _creatorRegistry = new TreeMap<String, Creator>();

    /** Rule Registry. */
    private List<Rule> _ruleRegistry = new ArrayList<>();
    


    public Library() {

      _ruleRegistry.add(new DuplicateRequestRule());
      _ruleRegistry.add(new NotSuspendedRule());
      _ruleRegistry.add(new NotAvailableRule());
      _ruleRegistry.add(new MaxRequestLimitRule());
      _ruleRegistry.add(new NotReferenceRule());
      _ruleRegistry.add(new MaxRequestPrice());
    }



    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException 
     * @throws IOException
     * @throws InvalidEntryException if there is an invalid entry
     */

    void importFile(String filename) throws UnrecognizedEntryException, IOException, InvalidEntryException {
    
        try (
          BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null){
              importFromFields(line.split(":"));
            }
          }

    }

    /**
     * Import an entry given its fields, the first of which indicates the type of.
     * @param fields
     * @throws UnrecognizedEntryException
     * @throws InvalidEntryException if there is an invalid entry
     */
    public void importFromFields(String[] fields) throws UnrecognizedEntryException, InvalidEntryException {
      switch (fields[0]){
        case "USER" -> importUser(fields);
        case "DVD" -> importDvd(fields);
        case "BOOK" -> importBook(fields);
        default -> throw new UnrecognizedEntryException(fields[0]);
      }
      
    }
    
    /**
     * Import a user given its fields.
     * @param fields
     * @throws InvalidEntryException if there is an invalid entry
     */
    public void importUser(String... fields) throws InvalidEntryException {
      if (fields.length != 3) {
        throw new InvalidEntryException(fields);
      }
      try {
        registerUser(fields[1], fields[2]);
      } catch(UserRegistrationFailedException e) {
        throw new InvalidEntryException(fields);
      }
      change();
    }

    /**
     * Import a book given its fields.
     * @param fields
     * @throws InvalidEntryException if there is an invalid entry
     */
    public void importBook(String... fields) throws InvalidEntryException {
      if (fields.length != 7) {
        throw new InvalidEntryException(fields);
      }

      String title = fields[1];
      
      String[] names = fields[2].split(",");
      List<Creator> authors = new ArrayList<>();

      for (String name : names) {

        String clearName  = name.trim();
        Creator creator = _creatorRegistry.computeIfAbsent(clearName, Creator::new);

        authors.add(creator);
      }

      int price = Integer.parseInt(fields[3]);
      Category category = Category.valueOf(fields[4]);
      String isbn = fields[5];
      int copies = Integer.parseInt(fields[6]);

      Book book = new Book(workId, copies, title, price, category, isbn, authors);

      for (Creator creator: authors) {
        creator.addWork(book);
      }

      _workRegistry.put(workId, book);
      workId++;
      change();
    }

    /**
     * Import a DVD given its fields.
     * @param fields
     * @throws InvalidEntryException if there is an invalid entry
     */
    public void importDvd(String... fields) throws InvalidEntryException {
      if (fields.length != 7){
        throw new InvalidEntryException(fields);
      }
      
      String title = fields[1];
      String directorName = fields[2];
      int price = Integer.parseInt(fields[3]);
      Category category = Category.valueOf(fields[4]);
      String igac = fields[5];
      int copies = Integer.parseInt(fields[6]);
      

      Creator director = _creatorRegistry.computeIfAbsent(directorName.trim(), Creator::new);
      Dvd dvd = new Dvd(workId, copies, title, price, category, igac, director);
      director.addWork(dvd);
      _workRegistry.put(workId, dvd);
      workId++;
      change();
    }


    public String getFilename() {
      return _filename;
    } 

    public void setFilename(String filename) {
      _filename = filename;
    }



    /** Check if the library has changed since the last call to clear.
     *  @return true if the library has changed
     */
    public boolean isChanged() {
      return changed;
    }

    /** Indicate that the library has changed. */
    public void change() {
      changed = true;
    }

    /** Indicate that the library has not changed. */
    public void clear() {
      changed = false;
    }

    /** Returns the current date.
     *  @return the current date
     */
    public int displayDate() {
      return _presentDate;
    }

    /** Advances the current date by the given number of days.
     *  @param days number of days to advance
     */
    public void advanceDate(int days) {
      if (days > 0) {
        _presentDate += days;
        for (User user : _userRegistry.values()) {
          updateStatus(user, _presentDate);
        }
        change();
      }

    }

    /**
     * Registers a new user in the library.
     * @param name  name of the user
     * @param email email of the user
     * @return the id of the new user
     * @throws UserRegistrationFailedException if the name or the email is empty
     */
    public int registerUser(String name, String email) throws UserRegistrationFailedException {
      User user = new User(name, userId, email);
      _userRegistry.put(userId, user);

      if (name.isEmpty() || email.isEmpty()) {
            throw new UserRegistrationFailedException(name, email);
      }

      change();
      return userId++;

    }

    /**
     * Shows the information of a user given its id.
     * @param id the id of the user
     * @return the string representation of the user or null if the user does not exist
     * @throws NoSuchUserException if there are no user
     */
    public String showUser(int id) throws NoSuchUserException {
      User u = _userRegistry.get(id);
      if (u == null) {
        throw new NoSuchUserException(id);
      }

      return u.toString();
    }

    /**
     * Shows the information of all users in the library.
     * @return a list of string representations of all users
     */
    public List<String> showAllUsers() {
      List<String> users = _userRegistry.values().stream()
            .sorted(Comparator.comparing(User::getName)
            .thenComparingInt(User::getId))
            .map(User::toString)
            .collect(Collectors.toList());
      users.add(String.valueOf(_userRegistry.size()));
      return users;
    }


    public List<String> showAllNoFineUsers() {
      List<String> users = _userRegistry.values().stream()
            .filter(user -> !user.getStatus().isSuspended())
            .sorted(Comparator.comparing(User::getName)
            .thenComparingInt(User::getId))
            .map(User::toString)
            .collect(Collectors.toList());
      return users;
    }

    /**
     * Shows the information of a work given its id.
     * @param id the id of the work
     * @return the string representation of the work or null if the work does not exist
     * @throws NoSuchWorkException if there are no works
     */
    public String showWork(int id) throws NoSuchWorkException{
      Work w = _workRegistry.get(id);
      
      if (w == null) {
        throw new NoSuchWorkException(id);
      }

      return w.toString();
    }

    /**
     * Shows the information of all works in the library.
     * @return a list of string representations of all works
     */
    public List<String> showAllWorks() {
      return _workRegistry.values().stream()
             .sorted(Comparator.comparingInt(Work::getId))
             .map(Work::toString)
             .collect(Collectors.toList());
    }

    public List<String> showAllReferenceWorks() {
      return _workRegistry.values().stream()
             .filter(work -> work.getCategory() == Category.REFERENCE)
             .sorted(Comparator.comparingInt(Work::getId))
             .map(Work::toString)
             .collect(Collectors.toList());
    }

        public List<String> showUnavaibleWorks() {
      return _workRegistry.values().stream()
             .filter(work -> work.getAvailable() == 0)
             .sorted(Comparator.comparingInt(Work::getId))
             .map(Work::toString)
             .collect(Collectors.toList());
    }

    /**
     * Shows the information of all works by a given creator.
     * @param creatorId the name of the creator
     * @return a list of string representations of all works by the given creator
     * @throws NoSuchCreatorException if there are no works by the given creator
     */
    public List<String> showWorksByCreator(String creatorId) throws NoSuchCreatorException {
      List<String> works = _workRegistry.values().stream()
          .filter(work -> work.getCreator().stream()
              .anyMatch(creator -> creator.getName().equalsIgnoreCase(creatorId)))
          .filter(work -> work.getCopies() > 0)
          .sorted(Comparator.comparing(work -> work.getTitle().toLowerCase()))
          .map(Work::toString)
          .collect(Collectors.toList());


      if (works.isEmpty()) {
        throw new NoSuchCreatorException(creatorId);
      }
      
      return works;
  }


    public List<String> search(String term) {
      return _workRegistry.values().stream()
        .filter(work -> work.hasTerm(term))
        .sorted(Comparator.comparingInt(Work::getId))
        .map(Work::toString)
        .collect(Collectors.toList());

    }


    public void updateInventory(int id, int amount) throws NotEnoughInventoryException, NoSuchWorkException {
      Work w = _workRegistry.get(id);

      if (w == null) {
        throw new NoSuchWorkException(id);
      }

      int availableUpdated = w.getAvailable() + amount;
      int copiesUpdated = w.getCopies() + amount;

      if (availableUpdated < 0) {
        throw new NotEnoughInventoryException(id);
      }

      w.setAvailable(availableUpdated);
      w.setCopies(copiesUpdated);
      change();

      if (copiesUpdated == 0) {
        removeWork(id);
      }
    }
      
    public int requestWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException,
                                                           RuleFailedException, NotAvailableRuleException {
       
      User user = _userRegistry.get(userId);

      if (user == null) {
        throw new NoSuchUserException(userId);
      }

      Work work = _workRegistry.get(workId);

      if (work == null) {
        throw new NoSuchWorkException(workId);
      }



      for (Rule rule : _ruleRegistry) {
        try {
            rule.check(work, user);

        } catch (RuleFailedException e) {
            if (e.getRuleId() == 3) {
              throw new NotAvailableRuleException(userId, workId);
            }
            throw new RuleFailedException(userId, workId, e.getRuleId());
          }
      }

      int deadline = _presentDate + user.getBehaviour().getDeadline(work.getCopies());
      Request request = new Request(user, work, _presentDate, deadline);
      user.addRequest(request);
      work.setAvailable(work.getAvailable() - 1);

      change();
      return deadline;
    }

    public void registerAvailabilityNotification(int userId, int workId, boolean wantsNotification) {
      User user = _userRegistry.get(userId);
      Work work = _workRegistry.get(workId);

      if (user != null && work != null) {
        user.interestAvailabilityNotification(work, wantsNotification);
        change();
      }
    }

    public void notifyAvailabilityInterested(Work work) {
      for (User user: _userRegistry.values()) {
        if (user.hasAvailabilityInterest(work)) {
          user.addNotification(new AvailabilityNotification(work));
        }
      }
    }

    public List<String> showUserNotifications(int userId) throws  NoSuchUserException {
      User user =  _userRegistry.get(userId);

      if (user == null) {
        throw new NoSuchUserException(userId);
      }

      change();

      return user.showAndRemoveNotification().stream()
              .map(Notification::toString)
              .collect(Collectors.toList());
    }


    public void returnWork(int userId, int workId) throws NoSuchUserException, NoSuchWorkException,
                                                          WorkNotBorrowedByUserException, HasFineException {
      User user = _userRegistry.get(userId);
      Work work = _workRegistry.get(workId);

      if (user == null) {
        throw new NoSuchUserException(userId);
      }

      if(work == null) {
        throw new NoSuchWorkException(workId);
      }

      Request request = user.getActiveRequest(work);
      if (request == null) {
        throw new WorkNotBorrowedByUserException(workId, userId);
      }


      request.setReturnDate(_presentDate);

      user.updateBehaviour(_presentDate);

      work.setAvailable(work.getAvailable() + 1);
    

      if (work.getAvailable() == 1) {
        notifyAvailabilityInterested(work);
      }
   

      change();

      if(request.wasReturnedLate()) {

        int daysLate = request.getDaysAfter(_presentDate);
        int fine = daysLate * 5;
        user.setFine(user.getFine() + fine);
        user.setStatus(new SuspendedStatus());
        throw new HasFineException(userId, user.getFine());
      }
    }


    public void payFineReturn(boolean wantsToPay, int userId) {

      User user = _userRegistry.get(userId);
      user.userPayFineReturn(wantsToPay, _presentDate);
      change();
    }


    public void payFine(int userId) throws NoSuchUserException, UserIsActiveException {

      User user = _userRegistry.get(userId);

      if (user == null) {
        throw new NoSuchUserException(userId);
      }

      if (!user.getStatus().isSuspended()) {
        throw new UserIsActiveException(userId);
      }

      user.setFine(0);

      updateStatus(user, _presentDate);

      change();

    }

    public void updateStatus(User user, int presentDate) {

      boolean hasLateRequests = user.getRequests().stream()
                                  .anyMatch(request -> request.isLate(presentDate));
      if (hasLateRequests || user.getFine() > 0) {
        user.setStatus(new SuspendedStatus());
      }
      else {
        user.setStatus(new ActiveStatus());
      }
    }
   

    public void removeWorkFromCreators(Work work) {
    List<String> creatorsToRemove = new ArrayList<>();

    for (Creator creator : _creatorRegistry.values()) {
        creator.removeWork(work);

        if (creator.getWorks().isEmpty()) {
            creatorsToRemove.add(creator.getName());
        }
    }

    for (String name : creatorsToRemove) {
        _creatorRegistry.remove(name);
    }

  }

  public void removeWork(int workId) {

    Work work = _workRegistry.remove(workId);


    removeWorkFromCreators(work);

    for (User user : _userRegistry.values()) {
        user.removeAvailability(work);
    }

    change();
    
  }


    
}
