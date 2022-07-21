package com.vet24.discord.model.dto;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MessageDto {

    Long id;

    Long channel_id;

    Long guid_id;

    DiscordUserDto author;

    DiscordGuildMemberDto member;

    String content;

    DateTime timestamp;

    DateTime edited_timestamp;

    boolean tts;

    boolean mention_everyone;


}
