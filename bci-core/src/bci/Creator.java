package bci;


import bci.work.Work;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Creator implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private String _name;

    private List<Work> _works = new ArrayList<>();


    public Creator(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }   

    public List<Work> getWorks() {
        return _works;
    }

    public void addWork(Work work) {
        if (! _works.contains(work)) {
            _works.add(work);
        }
    }

    public void removeWork(Work work) {
        _works.remove(work);
    }
}
