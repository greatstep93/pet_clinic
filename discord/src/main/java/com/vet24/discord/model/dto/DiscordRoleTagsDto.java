package com.vet24.discord.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class DiscordRoleTagsDto {

    Long bot_id;

    Long integration_id;

    boolean premium_subscriber;
}
