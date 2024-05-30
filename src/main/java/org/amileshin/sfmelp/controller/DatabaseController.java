package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.manager.ClickHouseManager;
import org.amileshin.sfmelp.model.dto.ConnectionDB;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/db")
public class DatabaseController {
    private final ClickHouseManager dbManager;

    @PostMapping("/")
    public ResponseEntity<Boolean> connectDB(@RequestBody ConnectionDB db) {
        try {
            dbManager.getConnectionToDatabase(db);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
