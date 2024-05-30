package org.amileshin.sfmelp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeasureInfo {
    private ConnectionDB db;
    private String table;
    private String file;
}
