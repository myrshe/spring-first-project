package org.jetbrains.semwork_2sem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JokeResponseDto {
    private boolean error;
    private String category;
    private String type;
    private String joke;
    private boolean safe;

    public String getJoke() {
        return joke;
    }

}
