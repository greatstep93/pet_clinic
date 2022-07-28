package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class EmbedFieldDto {

    @JsonProperty(namespace = "name")
    private String name;

    @JsonProperty(namespace = "value")
    private String value;

    @JsonProperty(namespace = "inline")
    private boolean inline;
}
