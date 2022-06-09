package com.vet24.models.dto.news;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.vet24.models.enums.NewsType;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
public class UpdatingNewsDto extends AbstractNewNewsDto {

    @JsonCreator
    public UpdatingNewsDto(NewsType type, String content, boolean isImportant, LocalDateTime endTime) {
        super(NewsType.UPDATING, content, isImportant, endTime);
    }
}
