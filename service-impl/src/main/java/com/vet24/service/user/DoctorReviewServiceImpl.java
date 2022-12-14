package com.vet24.service.user;

import com.vet24.dao.user.DoctorReviewDao;
import com.vet24.models.dto.user.DoctorReviewDto;
import com.vet24.models.user.DoctorReview;
import com.vet24.models.user.Topic;
import com.vet24.service.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorReviewServiceImpl extends ReadWriteServiceImpl<Long, DoctorReview> implements DoctorReviewService {

    private final DoctorReviewDao doctorReviewDao;

    @Autowired
    public DoctorReviewServiceImpl(DoctorReviewDao doctorReviewDao) {
        super(doctorReviewDao);
        this.doctorReviewDao = doctorReviewDao;
    }

    @Override
    public DoctorReview getByDoctorAndClientId(long doctorId, long clientId) {
        return doctorReviewDao.getByDoctorAndClientId(doctorId, clientId);
    }

    @Override
    public List<DoctorReview> getAllReviewByDoctorId(long doctorId) {
        return doctorReviewDao.getAllReviewByDoctorId(doctorId);
    }
}
