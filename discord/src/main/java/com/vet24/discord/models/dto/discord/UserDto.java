package com.vet24.discord.models.dto.discord;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class UserDto {

    private boolean bot;

    private Long id;

    private String username;

    private String avatar;

    private String discriminator;

    private String email;

}
