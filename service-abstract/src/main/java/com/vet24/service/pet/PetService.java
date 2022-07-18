package com.vet24.service.pet;

import com.vet24.models.pet.Pet;
import com.vet24.service.ReadWriteService;

public interface PetService extends ReadWriteService<Long, Pet> {
    boolean isPetBelongToClient(Long petId, Long clientId);
    boolean isExistByPetIdAndClientId(Long petId, Long clientId);
}
