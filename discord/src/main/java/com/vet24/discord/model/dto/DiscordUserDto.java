package com.vet24.discord.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class DiscordUserDto {

    Long id;

    String username;

    String discriminator;

    String avatar;

    boolean bot;

    boolean system;

    boolean mfa_enabled;

    String banner;

    Integer accent_color;

    String locale;

    boolean verified;

    String email;

    Integer flags;

    Integer premium_type;

    Integer public_flags;
}
