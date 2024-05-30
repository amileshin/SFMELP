package org.amileshin.sfmelp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultInfo {
    private double compressionTime;
    private double decompressionTime;
    private double ratio;
}
