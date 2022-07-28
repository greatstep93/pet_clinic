package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class EmbedThumbnailDto {

    @JsonProperty(namespace = "url")
    private String url;

    @JsonProperty(namespace = "proxy_url")
    private String proxy_url;

    @JsonProperty(namespace = "width")
    private Integer width;

    @JsonProperty(namespace = "height")
    private Integer height;
}
