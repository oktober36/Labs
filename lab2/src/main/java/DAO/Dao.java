package DAO;

import mainClasses.Person;
import java.io.IOException;

public interface Dao {

    <T extends Person> void save(T person, String type) throws IOException;

    <T extends Person> void update(T person, String type) throws IOException;

    void delete(int id, String type);

    <T extends Person> T get(int id, String type) throws IOException;

    String determineClass(int id);
}