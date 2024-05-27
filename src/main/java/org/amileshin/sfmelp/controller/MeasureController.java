package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.service.MeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/measure")
public class MeasureController {
    private final MeasureService measureService;

    @GetMapping("/testFileSize")
    public ResponseEntity<Long> testFileSize() {
        return ResponseEntity.ok(measureService.getFileSize());
    }
}
