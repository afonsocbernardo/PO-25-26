package bci.work;

import bci.Creator;
import java.util.List;

public class Dvd extends Work{

    private String _igac;
    private Creator _director;

    /**
     * Constructor
     * @param id
     * @param copies
     * @param title
     * @param price
     * @param category
     * @param igac
     * @param director
     */
    public Dvd(int id, int copies, String title, int price, Category category, String igac, Creator director) {
        super(id, copies, title, price, category);
        _igac = igac;
        _director = director;
    }


    @Override
    public boolean creatorSearch(String term) {
        return _director.getName().toLowerCase().contains(term.toLowerCase());
    }

    @Override
    public String getType() {
        return "DVD";
    }

    /**
     * Get additional info about the work
     * @return
     */
    @Override
    public String getAdditionalInfo() {
        return _director.getName() + " - " + _igac;
    }

    /**
     * Get the creators of the work
     * @return list of creators
     */
    @Override
    public List<Creator> getCreator() {
        return List.of(_director);
    }
}

