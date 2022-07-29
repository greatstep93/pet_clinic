package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class EmbedThumbnailDto {

    @JsonProperty
    private String url;

    @JsonProperty
    private String proxy_url;

    @JsonProperty
    private Integer width;

    @JsonProperty
    private Integer height;
}
