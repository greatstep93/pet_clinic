package com.vet24.discord.models.dto.discord;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class WebhookDto {
    private Long id;
    private Integer type;
    private Long guid_id;
    private Long channel_id;
    private UserDto user;
    private String name;
    private String avatar;
    private String token;
    private Long application_id;
    private GuildDto source_guild;
    private ChannelDto source_channel;
    private String url;
}
