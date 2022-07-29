package com.vet24.discord.models.dto.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.vet24.models.util.View;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class AttachmentDto {

    @JsonView(View.Get.class)
    private Long id;


    private String filename;


    private String description;


    private String content_type;

    @JsonView(View.Get.class)
    private Integer size;


    private String url;


    private String proxy_url;

    @JsonView(View.Get.class)
    private Integer height;

    @JsonView(View.Get.class)
    private Integer width;


    private boolean ephemeral;

}
