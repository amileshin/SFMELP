package org.amileshin.sfmelp.controller;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.manager.FileManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/file")
public class FileController {
    private final FileManager fileManager;

    @GetMapping("/testfiles")
    public ResponseEntity<List<String>> testFiles() {
        return ResponseEntity.ok(fileManager.getFiles());
    }

    @GetMapping("/exist/{filename}")
    public ResponseEntity<Boolean> exist(@PathVariable String filename) {
        try {
            fileManager.getFile(filename);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/size/{filename}")
    public ResponseEntity<Long> fileSize(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(fileManager.getFileSize(filename));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/logs/{filename}")
    public ResponseEntity<List<String>> logsFromFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(fileManager.getStringFromFile(filename));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
