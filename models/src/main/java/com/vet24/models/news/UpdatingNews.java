package com.vet24.models.news;

import com.vet24.models.enums.NewsType;
import lombok.EqualsAndHashCode;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("UPDATING")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UpdatingNews extends News {

    private NewsType type;
    public UpdatingNews() {
        super();
        this.type = NewsType.UPDATING;
    }

    public UpdatingNews(String content, boolean isImportant, LocalDateTime endTime) {
        super(content, isImportant, endTime);
        this.type = NewsType.UPDATING;
    }
}

