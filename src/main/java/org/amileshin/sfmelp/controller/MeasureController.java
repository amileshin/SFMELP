package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.model.dto.MeasureInfo;
import org.amileshin.sfmelp.model.dto.ResultInfo;
import org.amileshin.sfmelp.service.MeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/measure")
public class MeasureController {
    private final MeasureService measureService;

    @PostMapping("/")
    public ResponseEntity<ResultInfo> measure(@RequestBody MeasureInfo measureInfo) {
        try {
            return ResponseEntity.ok(measureService.getMeasure(measureInfo));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
