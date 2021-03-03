package fr.axzial.safetyalterts.repository;

import fr.axzial.safetyalterts.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer>, JpaSpecificationExecutor<MedicalRecord> {
    List<MedicalRecord> findAllByLastNameInAndFirstNameIn(Collection<String> collect, Collection<String> collect1);
}
