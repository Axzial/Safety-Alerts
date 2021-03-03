package fr.axzial.safetyalterts.repository;

import fr.axzial.safetyalterts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Integer>, JpaSpecificationExecutor {

    List<FireStation> findAllByStationIn(Collection<String> stations);

    Optional<FireStation> findOneByAddress(String address);

    List<FireStation> findAllByStation(String station);
}
