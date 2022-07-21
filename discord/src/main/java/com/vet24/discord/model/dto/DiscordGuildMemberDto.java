package com.vet24.discord.model.dto;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@EqualsAndHashCode
@Data
public class DiscordGuildMemberDto {

    DiscordUserDto user;

    String nick;

    String avatar;

    Set<DiscordRoleDto> roles;

    DateTime joined_at;

    DateTime premium_since;

    boolean deaf;

    boolean mute;

    boolean pending;

    String permissions;

    DateTime communication_disabled_until;
}


