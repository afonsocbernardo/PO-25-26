package bci.work;


import bci.Creator;
import java.io.Serializable;
import java.util.List;

public abstract class Work implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _id;
    private int _copies;
    private int _available;
    private String _title;
    private int _price;
    private Category _category;

    /**
     * Constructor
     * @param id
     * @param copies
     * @param title
     * @param price
     * @param category
     */
    public Work(int id, int copies, String title, int price, Category category){
        _id = id;
        _copies = copies;
        _available = copies;
        _title = title;
        _price = price;
        _category = category;
    }

    public int getId(){
        return _id;
    }

    public int getCopies(){
        return _copies;
}

    public String getTitle(){
        return _title;
    }

    public int getPrice(){
        return _price;
    }

    public Category getCategory(){
        return _category;
    }

    public int getAvailable(){
        return _available;
    }

    public void setId(int id){
        _id = id;
    }

    public void setCopies(int copies){
        _copies = copies;
    }

    public void setTitle(String title){
        _title = title;
    }

    public void setPrice(int price){
        _price = price;
    }

    public void setCategory(Category category){
        _category = category;
    }

    public void setAvailable(int available) {
        _available = available;
    }

    public boolean isAvailable(){
        return _available > 0;
    }
    
    public abstract String getType();

    public abstract String getAdditionalInfo();

    public abstract List<Creator> getCreator();

    public abstract boolean creatorSearch(String term);

    public boolean titleSearch(String term){
        return _title.toLowerCase().contains(term.toLowerCase());
    }

    public boolean hasTerm(String term){
        return titleSearch(term) || creatorSearch(term);
    }





    /**
     * String representation of the work
     */
    @Override
    public String toString(){
        return _id + " - " + _available + " de " + _copies + " - " + getType() + " - " 
             + _title + " - " + _price + " - "  + _category + " - " + getAdditionalInfo();
    }


}
