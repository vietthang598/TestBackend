package com.example.demo.service;

import com.example.demo.model.person;
import com.example.demo.dao.personDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final personDao PersonDao;

    @Autowired
    public PersonService(@Qualifier("postgres") personDao personDao) {
        this.PersonDao = personDao;
    }

    public int addPerson(person person) {
        return PersonDao.insertPerson(person);
    }

    public List<person> getAllPeople() {
        return PersonDao.selectAllPeople();
    }

    public Optional<person> getPersonById(UUID id) {
        return PersonDao.selectPersonById(id);
    }

    public int deletePerson(UUID id) {
        return PersonDao.deleterPersonById(id);
    }

    public int updatePerson( UUID id,person newPerson) {
        return PersonDao.updatePersonById(id,newPerson);
    }

}
