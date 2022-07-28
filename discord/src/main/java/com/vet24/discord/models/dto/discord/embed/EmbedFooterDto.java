package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class EmbedFooterDto {

    @JsonProperty(namespace = "text")
    private String text;

    @JsonProperty(namespace = "icon_url",defaultValue = "https://")
    private String icon_url;

    @JsonProperty(namespace = "proxy_icon_url",defaultValue = "https://")
    private String proxy_icon_url;

}
