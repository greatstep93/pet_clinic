package com.vet24.discord.models.dto.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vet24.discord.models.dto.discord.embed.EmbedDto;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonView;
import com.vet24.models.dto.OnCreate;
import com.vet24.models.dto.OnUpdate;
import com.vet24.models.util.View;

import java.util.ArrayList;

@Getter
@Setter
@EqualsAndHashCode

public class MessageDto {

    @JsonView(View.Get.class)
    private Long id;

    private Integer type;

    private String content;

    private Long channel_id;

    private UserDto author;

    private AttachmentDto[] attachment;

    private ArrayList<EmbedDto> embeds;

    private UserDto[] mentions;

    private Long[] mention_roles;

    private boolean pinned;

    private boolean mention_everyone;

    private boolean tts;

    private String timestamp;

    private String edited_timestamp;

    private Long webhook_id;


}
