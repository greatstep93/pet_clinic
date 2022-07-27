package com.vet24.discord.models.dto.discord;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class RoleTagsDto {

    private Long bot_id;

    private Long integration_id;

    private boolean premium_subscriber;
}
