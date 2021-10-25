package com.vet24.service.user;

import com.vet24.dao.user.ClientDao;
import com.vet24.models.user.Client;
import com.vet24.service.ReadWriteServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl extends ReadWriteServiceImpl<Long, Client> implements ClientService {

    private final ClientDao clientDao;
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientDao clientDao, PasswordEncoder passwordEncoder) {
        super(clientDao);
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Client getCurentClientEasy() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDao.getClientByEmail(client.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientByEmail(String email) {
        return clientDao.getClientByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getCurrentClientWithPets() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDao.getClientWithPetsByEmail(client.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public Client getCurrentClientWithReactions() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDao.getClientWithReactionsByEmail(client.getUsername());
    }

    @Override
    @Transactional()
    public void persist(Client client) {
        String password = passwordEncoder.encode(client.getPassword());
        client.setPassword(password);
        clientDao.persist(client);
    }
}
