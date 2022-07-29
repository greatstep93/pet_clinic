package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
public class EmbedDto {

    @JsonProperty
    private EmbedType type;

    @JsonProperty
    private String url;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonProperty
    private Integer color;

    @JsonProperty
    private String timestamp;

    @JsonProperty
    private EmbedFieldDto[] fields;

    @JsonProperty
    private EmbedImageDto image;

    @JsonProperty
    private EmbedThumbnailDto thumbnail;

    @JsonProperty
    private EmbedFooterDto footer;

}
