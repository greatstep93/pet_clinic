package com.vet24.discord.models.dto.discord;

import com.vet24.discord.models.dto.discord.embed.EmbedDto;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode

public class MessageDto {

    private Long id;

    private Integer type;

    private String content;

    private Long channel_id;

    private UserDto author;

    private AttachmentDto[] attachment;

    private EmbedDto[] embeds;

    private UserDto[] mentions;

    private Long[] mention_roles;

    private boolean pinned;

    private boolean mention_everyone;

    private boolean tts;

    private String timestamp;

    private String edited_timestamp;

    private Long webhook_id;


}
