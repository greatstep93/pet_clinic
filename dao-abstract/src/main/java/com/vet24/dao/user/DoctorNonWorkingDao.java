package com.vet24.dao.user;

import com.vet24.dao.ReadWriteDao;
import com.vet24.models.user.Doctor;
import com.vet24.models.user.DoctorNonWorking;

import java.time.LocalDate;
import java.util.List;

public interface DoctorNonWorkingDao extends ReadWriteDao<Long, DoctorNonWorking> {

    boolean isExistByDoctorIdAndDate(Doctor doctor, LocalDate date);
    List<DoctorNonWorking> getDoctorNonWorkingAfterDate(LocalDate date);
}
