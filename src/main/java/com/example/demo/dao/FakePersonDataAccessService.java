package com.example.demo.dao;

import com.example.demo.model.person;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Repository("fakeDao")
public class FakePersonDataAccessService implements personDao {

    private static List<person> DB = new ArrayList<>();


    @Override
    public int insertPerson(UUID id, person person) {
        DB.add(new person(id,person.getName()));
        return 1;
    }

    @Override
    public List<person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deleterPersonById(UUID id) {
        Optional<person> personMaybe = selectPersonById(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }


    @Override
    public int updatePersonById(UUID id, person Person) {
        return selectPersonById(id)
                .map(p -> {
                    int indexPersonToUpdate = DB.indexOf(p);
                    if(indexPersonToUpdate >= 0){
                        DB.set(indexPersonToUpdate,new person(id,Person.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }


}
