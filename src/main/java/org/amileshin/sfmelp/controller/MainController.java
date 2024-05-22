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

    @PostMapping("/getConnect")
    public ResponseEntity<List<String>> getConnect(@RequestBody ConnectInfoDTO info) {
        return ResponseEntity.ok().body(mainService.getConnect(info));
    }

    @GetMapping("/healthy")
    public ResponseEntity<Void> isHealthy() {
        return ResponseEntity.ok().build();
    }
}
