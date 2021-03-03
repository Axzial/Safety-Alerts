package fr.axzial.safetyalterts.repository;

import fr.axzial.safetyalterts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
    List<Person> findAllByAddressIn(Collection<String> addresses);

    List<Person> findAllByAddress(String address);

    List<Person> findAllByCity(String city);
}
