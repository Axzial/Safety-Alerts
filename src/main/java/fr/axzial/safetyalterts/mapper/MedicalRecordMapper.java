package fr.axzial.safetyalterts.mapper;

import fr.axzial.safetyalterts.dto.ChildMedicalRecordDto;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.util.TimeUtils;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class MedicalRecordMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ChildMedicalRecordDto mapChildMedicalRecordDto(MedicalRecord medicalRecord){
        ChildMedicalRecordDto childMedicalRecordDto = modelMapper.map(medicalRecord, ChildMedicalRecordDto.class);
        childMedicalRecordDto.setAge(TimeUtils.getAgeFromBirthday(medicalRecord.getBirth()));
        return childMedicalRecordDto;
    }

}
