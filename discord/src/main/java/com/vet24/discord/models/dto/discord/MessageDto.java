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

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode

public class MessageDto {

//    @JsonView(View.Get.class)
    private Long id;
//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private Integer type;

//    @NotBlank(groups = {OnCreate.class}, message = "Поле content не должно быть null")
//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private String content;

//    @JsonView(View.Get.class)
    private Long channel_id;

//    @JsonView(View.Get.class)
    private UserDto author;

//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private AttachmentDto[] attachment;

//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private EmbedDto[] embeds;

//    @JsonView(View.Get.class)
    private UserDto[] mentions;

//    @JsonView(View.Get.class)
    private Long[] mention_roles;

//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private boolean pinned;

//    @JsonView(View.Get.class)
    private boolean mention_everyone;

//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private boolean tts;

//    @JsonView({View.Post.class, View.Put.class, View.Get.class})
    private String timestamp;

//    @JsonView(View.Get.class)
    private String edited_timestamp;

//    @JsonView(View.Get.class)
    private Long webhook_id;


}
