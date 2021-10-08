package DAO;

import mainClasses.Person;
import java.util.HashMap;
import java.util.Map;

public class CachedPeopleDAO implements Dao {
    private Map persons = new HashMap();

    @Override
    public <T extends Person> void save(T person, String type) {
        if (!persons.containsKey(person.getId() + type))    persons.put(person.getId() + type, person);
    }

    @Override
    public <T extends Person> void update(T person, String type) {
        if (persons.containsKey(person.getId() + type))    persons.put(person.getId() + type, person);
    }

    @Override
    public void delete(int id, String type) {
        persons.remove(id + type);
    }

    @Override
    public <T extends Person> T get(int id, String type) {
        return (T) persons.get(id + type);
    }

    @Override
    public String determineClass(int id) {
        if (persons.containsKey(id + "T")) return "T";
        if (persons.containsKey(id + "S")) return "S";
        return null;
    }
}
