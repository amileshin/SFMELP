package org.amileshin.sfmelp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitController {
    @GetMapping("/healthy")
    public ResponseEntity<Void> isHealthy() {
        return ResponseEntity.ok().build();
    }

}
