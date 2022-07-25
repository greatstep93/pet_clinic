package com.vet24.discord.model.dto.discord;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class UserDto {

    private Long id;

    private String username;

    private String discriminator;

    private String avatar;

    private boolean bot;

    private boolean system;

    private boolean mfa_enabled;

    private String banner;

    private Integer accent_color;

    private String locale;

    private boolean verified;

    private String email;

    private Integer flags;

    private Integer premium_type;

    private Integer public_flags;
}
