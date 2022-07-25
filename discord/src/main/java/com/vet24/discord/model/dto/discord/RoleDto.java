package com.vet24.discord.model.dto.discord;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class RoleDto {

    private Long id;

    private String name;

    private Integer color;

    private boolean hoist;

    private String icon;

    private String unicode_emoji;

    private Integer position;

    private String permissions;

    private boolean managed;

    private boolean mentionable;

    private RoleTagsDto tags;


}
