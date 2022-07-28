package com.vet24.discord.models.dto.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class UserDto {

    @JsonProperty(namespace = "bot")
    private boolean bot;

    @JsonProperty(namespace = "id")
    private Long id;

    @JsonProperty(namespace = "username")
    private String username;

    @JsonProperty(namespace = "avatar")
    private String avatar;

    @JsonProperty(namespace = "discriminator")
    private String discriminator;

    @JsonProperty(namespace = "email")
    private String email;

}
