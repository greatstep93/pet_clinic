package com.vet24.discord.model.dto.discord;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class MessageDto {

    private Long id;

    private Long channel_id;

    private Long guid_id;

    private UserDto author;

    private GuildMemberDto member;

    private String content;

    private DateTime timestamp;

    private DateTime edited_timestamp;

    private boolean tts;

    private boolean mention_everyone;

    private List<UserDto> mentions;

    private List<RoleDto> mention_roles;

    private List<AttachmentDto> attachment;

    private List<EmbedDto> embeds;

    private List<ReactionDto> reactions;

    private String nonce;

    private boolean pinned;

    private Long webhook_id;

    private Integer type;

    private MessageActivityDto activity;

    private ApplicationDto application;

    private Long application_id;

    private MessageReferenceDto message_reference;

    private Integer flags;

    private MessageDto referenced_message;

    private MessageInteractionDto interaction;

    private ChannelDto thread;

    private List<MessageComponentsDto> components;

    private MessageStickerItemDto sticker_items;

    private List<StickerDto> stickers;

}
