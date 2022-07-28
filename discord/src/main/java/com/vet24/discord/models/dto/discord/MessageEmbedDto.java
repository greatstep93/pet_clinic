package com.vet24.discord.models.dto.discord;

import com.vet24.discord.models.dto.discord.embed.EmbedDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

public class MessageEmbedDto extends MessageDto{
    private EmbedDto[] embeds;
}
