package com.vet24.discord.models.dto.discord.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class EmbedFieldDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String value;

    @JsonProperty
    private boolean inline;
}
