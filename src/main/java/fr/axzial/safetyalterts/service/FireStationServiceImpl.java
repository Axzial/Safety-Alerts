package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FireStationServiceImpl implements FireStationService {

    FireStationRepository fireStationRepository;
    PersonRepository personRepository;
    EntityManager entityManager;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<FireStation> getFireStationByIds(List<String> stations) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FireStation> cq = cb.createQuery(FireStation.class);
        Root<FireStation> root = cq.from(FireStation.class);
        cq.select(root).where(cb.and(root.get("stations").in(stations)));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<FireStation> getFireStationByAddress(String address){
        FireStation fireStation = new FireStation();
        fireStation.setAddress(address);
        return fireStationRepository.findOne(Example.of(fireStation));
    }

    @Override
    public Optional<FireStation> getFireStationByStation(String station) {
        FireStation fireStation = new FireStation();
        fireStation.setStation(station);
        return fireStationRepository.findOne(Example.of(fireStation));
    }
}
