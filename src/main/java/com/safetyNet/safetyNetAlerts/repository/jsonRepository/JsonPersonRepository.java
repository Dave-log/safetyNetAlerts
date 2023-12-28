package com.safetyNet.safetyNetAlerts.repository.jsonRepository;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JsonPersonRepository implements PersonRepository {
    private static final Logger logger = LogManager.getLogger(JsonPersonRepository.class);

    private final String jsonFilePath = "src/main/resources/data.json";
    private final JsonReader jsonReader;
    private final List<Person> listPerson;

    public JsonPersonRepository() {
        this.jsonReader = new JsonReader();
        listPerson = getAllPersons();
    }

    @Override
    public List<Person> getAllPersons() {
        try {
           return jsonReader.readListFromFile(jsonFilePath, "persons", Person.class);
        } catch (Exception e) {
            logger.error("Error while reading json file", e);
            return null;
        }
    }

    @Override
    public Person getPerson(String firstName, String lastName) {
        for (Person person : listPerson) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> getPersonFromAddress(String address, String zip, String city) {
        List<Person> listSearchedPerson = new ArrayList<>();
        for (Person person : listPerson) {
            if (person.getAddress().equals(address) && person.getZip().equals(zip) && person.getCity().equals(city)) {
                listSearchedPerson.add(person);
            }
        }
        if (!listSearchedPerson.isEmpty()) {
            return listSearchedPerson;
        } else {
            return null;
        }
    }

    @Override
    public void addPerson(Person person) {
        listPerson.add(person);
    }

    @Override
    public Person updatePerson(Person person) {
        Person personToUpdate = getPerson(person.getFirstName(), person.getLastName());
        if (personToUpdate != null) {
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());
            return personToUpdate;
        }
        return null;
    }

    @Override
    public Person deletePerson(Person person) {
        Person personToDelete = getPerson(person.getFirstName(), person.getLastName());
        if (personToDelete != null) {
            listPerson.remove(personToDelete);
            return personToDelete;
        }
        return null;
    }
}
