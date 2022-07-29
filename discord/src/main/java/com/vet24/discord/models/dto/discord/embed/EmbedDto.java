package com.vet24.discord.models.dto.discord.embed;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Getter
@Setter
public class EmbedDto {

    private EmbedType type;

    private String url;

    private String title;

    private String description;

    private Integer color;

    private String timestamp;

    private EmbedFieldDto[] fields;

    private EmbedImageDto image;

    private EmbedThumbnailDto thumbnail;

    private EmbedFooterDto footer;

}
