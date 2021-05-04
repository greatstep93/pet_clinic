package com.vet24.models.mappers;

import com.vet24.models.dtos.CatDto;
import com.vet24.models.dtos.ClientDto;
import com.vet24.models.dtos.DogDto;
import com.vet24.models.dtos.PetDto;
import com.vet24.models.pet.Dog;
import com.vet24.models.pet.Pet;
import com.vet24.models.user.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

    @Mapping(source = "login", target = "username")
    ClientDto clientToClientDto(Client client);

//    @Mapping(source = "petType", target = "type")
//    PetDto petToPetDto(Pet pet);

// abstract
//    @Mapping(source = "type", target = "petType")
//    Pet PetDtoToPet(PetDto petDto);

    Dog DogDtoToDog(DogDto dogDto);

    DogDto DogToDogDto(Dog dog);

//    Cat CatDtoToCat(CatDto catDto);
}
