package org.amileshin.sfmelp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectInfoDTO {
    private String database;
    private String host;
    private int port;

}
