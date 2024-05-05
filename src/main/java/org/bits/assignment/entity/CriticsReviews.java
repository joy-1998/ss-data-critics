package org.bits.assignment.entity;


import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriticsReviews {
    private String critic_name;
    private String critics_review;
    private String ratings;
}
