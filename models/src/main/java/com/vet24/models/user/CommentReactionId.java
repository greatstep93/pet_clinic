package com.vet24.models.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CommentReactionId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(nullable= false,name="comment_id")

    private Long commentId;

    @EqualsAndHashCode.Include
    @Column(nullable= false,name="client_id")
    private Long clientId;

}
