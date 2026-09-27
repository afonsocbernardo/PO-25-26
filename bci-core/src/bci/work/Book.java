package bci.work;

import bci.Creator;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends Work {
    
    private List<Creator> _authors;
    private String _isbn;
    
    /**
     * Constructor
     * @param id
     * @param copies
     * @param title
     * @param price
     * @param category
     * @param isbn
     * @param authors
     */
    public Book(int id, int copies, String title, int price, Category category, String isbn, List<Creator> authors) {
        super(id, copies, title, price, category);
        _isbn = isbn;
        _authors = authors;
    }

    public String getIsbn(){
        return _isbn;
    }

    public List<Creator> getAuthors(){
        return _authors;
    }

    public void setIsbn(String isbn){
        _isbn = isbn;
    }

    public void setAuthors(List<Creator> authors){
        _authors = authors;
    }

    @Override
    public boolean creatorSearch(String term) {
        return _authors.stream().anyMatch(author -> author.getName().toLowerCase().contains(term.toLowerCase()));

    }
    @Override
    public String getType() {
        return "Livro";
    }

    /**
     * Get the creators of the work
     * @return list of creators
     */
    @Override
    public List<Creator> getCreator() {
        return _authors;
    }
    /**
     * Get additional info about the work
     * @return string with additional info
     */
    @Override
    public String getAdditionalInfo() {
        String authors = _authors.stream()
                                 .map(Creator :: getName)
                                 .collect(Collectors.joining("; "));
        return authors + " - " + _isbn;
    }
}
