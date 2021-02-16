package fr.axzial.safetyalterts.repository;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Integer> {
}
