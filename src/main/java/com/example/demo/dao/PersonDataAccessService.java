package com.example.demo.dao;

import com.example.demo.model.person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements personDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, person person) {
        final String sql = "insert into person (id, name) values (?, ?)";
        return jdbcTemplate.update(sql, id, person.getName());
    }

    @Override
    public List<person> selectAllPeople() {
        final String sql = "SELECT id, name FROM person";
        return jdbcTemplate.query(sql, ((resultSet,i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new person(id, name);
        }));
    }


    @Override
    public Optional<person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name FROM person WHERE id = ?";
        person Person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                ((resultSet,i) -> {
                    UUID personId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new person(personId, name);
                })
        );
        return Optional.ofNullable(Person);
    }

    @Override
    public int deleterPersonById(UUID id) {
        String sql = "" +
                "DELETE FROM person " +
                "WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updatePersonById(UUID id, person person) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, person.getName(), id);
    }
}
