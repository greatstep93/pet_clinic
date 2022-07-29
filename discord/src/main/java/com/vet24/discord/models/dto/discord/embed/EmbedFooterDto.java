package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class EmbedFooterDto {

    @JsonProperty
    private String text;

    @JsonProperty
    private String icon_url;

    @JsonProperty
    private String proxy_icon_url;

}
