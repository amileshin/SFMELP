package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.amileshin.sfmelp.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;

    @PostMapping("/getTestConnect")
    public ResponseEntity<Void> getTestConnect(@RequestBody ConnectInfoDTO info) {
        return mainService.getTestConnect(info)? ResponseEntity.ok().build(): ResponseEntity.badRequest().build();
    }

    @PostMapping("/getTables")
    public ResponseEntity<List<String>> getAllTablesFromDatabase(@RequestBody ConnectInfoDTO info) {
        List<String> tables = mainService.getAllTablesFromDatabase(info);
        if (tables != null) {
            return ResponseEntity.ok().body(mainService.getAllTablesFromDatabase(info));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/healthy")
    public ResponseEntity<Void> isHealthy() {
        return ResponseEntity.ok().build();
    }
}
