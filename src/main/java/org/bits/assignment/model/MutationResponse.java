package org.bits.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bits.assignment.entity.CriticsReviews;
import org.springframework.http.HttpStatus;

import java.util.List;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MutationResponse {

    public HttpStatus status;
    public String respMsg;
    public String modifiedCount;
    public String upsertId;

}
