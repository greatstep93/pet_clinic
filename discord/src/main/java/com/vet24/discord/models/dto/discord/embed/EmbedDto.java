package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class EmbedDto {

    @JsonProperty(namespace = "type")
    private EmbedType type;

    @JsonProperty(namespace = "url",defaultValue = "https://")
    private String url;

    @JsonProperty(namespace = "title")
    private String title;

    @JsonProperty(namespace = "description")
    private String description;

    @JsonProperty(namespace = "color")
    private Integer color;

//    @JsonProperty(namespace = "timestamp")
//    private DateTime timestamp;

    @JsonProperty(namespace = "fields")
    private EmbedFieldDto[] fields;

    @JsonProperty(namespace = "image")
    private EmbedImageDto image;

    @JsonProperty(namespace = "thumbnail")
    private EmbedThumbnailDto thumbnail;

    @JsonProperty(namespace = "footer")
    private EmbedFooterDto footer;

}
