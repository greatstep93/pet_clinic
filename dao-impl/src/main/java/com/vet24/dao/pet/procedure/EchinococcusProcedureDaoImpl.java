package com.vet24.dao.pet.procedure;

import com.vet24.dao.ReadWriteDaoImpl;
import com.vet24.models.pet.procedure.Deworming;
import org.springframework.stereotype.Repository;

@Repository
public class EchinococcusProcedureDaoImpl extends ReadWriteDaoImpl<Long, Deworming> implements  EchinococcusProcedureDao {
}
