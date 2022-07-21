package com.vet24.discord.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class DiscordRoleDto {

    Long id;

    String name;

    Integer color;

    boolean hoist;

    String icon;

    String unicode_emoji;

    Integer position;

    String permissions;

    boolean managed;

    boolean mentionable;

    DiscordRoleTagsDto tags;


}
