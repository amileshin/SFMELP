package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.manager.FileManager;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.amileshin.sfmelp.service.MeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/measure")
public class MeasureController {
    private final MeasureService measureService;

    @GetMapping("/testFileSize")
    public ResponseEntity<Long> testFileSize() {
        return ResponseEntity.ok(measureService.getFileSize());
    }

    @GetMapping("/getLogs")
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = measureService.getLogsFromFile();
        if (logs != null) {
            return ResponseEntity.ok(logs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/loadLogs/{table}")
    public ResponseEntity<Void> loadLogs(@PathVariable String table, @RequestBody ConnectInfoDTO info) {
        try {
            measureService.loadLogsToDatabase(info, table);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
