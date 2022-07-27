package com.vet24.discord.models.dto.discord;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@EqualsAndHashCode
@Data
public class GuildMemberDto {

    private UserDto user;

    private String nick;

    private String avatar;

    private Set<RoleDto> roles;

    private DateTime joined_at;

    private DateTime premium_since;

    private boolean deaf;

    private boolean mute;

    private boolean pending;

    private String permissions;

    private DateTime communication_disabled_until;
}


