package com.vet24.discord.models.dto.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class AttachmentDto {

    @JsonProperty(namespace = "id")
    private Long id;

    @JsonProperty(namespace = "filename")
    private String filename;

    @JsonProperty(namespace = "description")
    private String description;

    @JsonProperty(namespace = "content_type")
    private String content_type;

    @JsonProperty(namespace = "size")
    private Integer size;

    @JsonProperty(namespace = "url")
    private String url;

    @JsonProperty(namespace = "proxy_url")
    private String proxy_url;

    @JsonProperty(namespace = "height")
    private Integer height;

    @JsonProperty(namespace = "width")
    private Integer width;

    @JsonProperty(namespace = "ephemeral")
    private boolean ephemeral;

}
