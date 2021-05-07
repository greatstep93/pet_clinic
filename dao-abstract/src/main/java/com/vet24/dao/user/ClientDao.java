package com.vet24.dao.user;


import com.vet24.dao.ReadWriteDao;
import com.vet24.models.user.Client;


public interface ClientDao extends ReadWriteDao<Long, Client> {

    Client getClientByLogin(String login);
}
