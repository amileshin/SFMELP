package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.amileshin.sfmelp.service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/database")
public class DatabaseController {
    private final DatabaseService databaseService;

    @PostMapping("/testConnect")
    public ResponseEntity<Void> getTestConnect(@RequestBody ConnectInfoDTO info) {
        return databaseService.getTestConnect(info)? ResponseEntity.ok().build(): ResponseEntity.badRequest().build();
    }

    @PostMapping("/getTables")
    public ResponseEntity<List<String>> getAllTablesFromDatabase(@RequestBody ConnectInfoDTO info) {
        List<String> tables = databaseService.getAllTablesFromDatabase(info);
        if (tables != null) {
            return ResponseEntity.ok().body(databaseService.getAllTablesFromDatabase(info));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
