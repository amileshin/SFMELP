package org.amileshin.sfmelp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionDB {
    private String database;
    private String host;
    private int port;
    private String username;
    private String password;
}
