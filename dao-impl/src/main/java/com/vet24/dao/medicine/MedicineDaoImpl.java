package com.vet24.dao.medicine;

import com.vet24.models.medicine.Medicine;
import com.vet24.models.user.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MedicineDaoImpl implements MedicineDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Medicine getMedicineById(Long id) {
        return entityManager.find(Medicine.class, id);
    }

    @Override
    public List<Medicine> getAllMedicine() {
        return entityManager
                .createQuery("SELECT m FROM Medicine m", Medicine.class)
                .getResultList();
    }

    @Override
    public void addMedicine(Medicine medicine) {
        entityManager.persist(medicine);
    }

    @Override
    public void editMedicine(Medicine medicine) {
        entityManager.merge(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        entityManager.remove(getMedicineById(id));
    }

    @Override
    public List<Medicine> searchFull(String manufactureName, String name, String searchtext) {
        return entityManager.createNativeQuery("SELECT * from medicine WHERE "
                + "manufacture_name LIKE :manufactureName "
                + "AND name LIKE :name "
                + "AND setweight(to_tsvector(name), 'A') || setweight(to_tsvector(description), 'B')  @@ to_tsquery(:searchtext)", Medicine.class)
                .setParameter("manufactureName", "%" + manufactureName + "%")
                .setParameter("name", "%" + name + "%")
                .setParameter("searchtext", searchtext)
                .getResultList();
    }

    @Override
    public List<Medicine> search(String manufactureName, String name) {
        return entityManager.createNativeQuery("SELECT * from medicine WHERE "
                + "manufacture_name LIKE :manufactureName "
                + "AND name LIKE :name "
                , Medicine.class)
                .setParameter("manufactureName", "%" + manufactureName + "%")
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }


}
