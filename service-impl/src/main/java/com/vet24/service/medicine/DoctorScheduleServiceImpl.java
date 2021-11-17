package com.vet24.service.medicine;

import com.vet24.dao.medicine.DoctorScheduleDao;
import com.vet24.models.medicine.DoctorSchedule;
import com.vet24.service.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorScheduleServiceImpl extends ReadWriteServiceImpl<Long, DoctorSchedule> implements DoctorScheduleService {

    @Autowired
    public DoctorScheduleServiceImpl(DoctorScheduleDao doctorScheduleDao) {
        super(doctorScheduleDao);
    }

    @Override
    public boolean isExistByDoctorIdAndWeekNumber(Long doctorId, Integer weekNumber) {
        return doctorScheduleDao.isExistByDoctorIdAndWeekNumber(doctorId, weekNumber);
    }
}

