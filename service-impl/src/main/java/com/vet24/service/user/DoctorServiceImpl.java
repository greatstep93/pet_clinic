package com.vet24.service.user;


import com.vet24.dao.user.DoctorDao;
import com.vet24.models.user.Doctor;
import com.vet24.models.user.Role;
import com.vet24.service.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DoctorServiceImpl extends ReadWriteServiceImpl<Long, Doctor> implements DoctorService{

    private final DoctorDao doctorDao;


    @Autowired
    public DoctorServiceImpl(DoctorDao  doctorDao) {
        super(doctorDao);
        this.doctorDao = doctorDao;
    }

    @Override
    public Doctor getCurrentDoctor() {
        return doctorDao.getByKey(40L);
    }
}
