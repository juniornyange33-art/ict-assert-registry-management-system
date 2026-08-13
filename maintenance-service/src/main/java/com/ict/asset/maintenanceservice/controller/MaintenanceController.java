package com.ict.asset.maintenanceservice.controller;

import com.ict.asset.maintenanceservice.model.MaintenanceLog;
import com.ict.asset.maintenanceservice.repository.MaintenanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance")
public class MaintenanceController {

    private final MaintenanceRepository maintenanceRepository;

    public MaintenanceController(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @PostMapping
    public ResponseEntity<MaintenanceLog> createLog(@RequestBody MaintenanceLog log) {
        MaintenanceLog savedLog = maintenanceRepository.save(log);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceLog>> getAllLogs() {
        return ResponseEntity.ok(maintenanceRepository.findAll());
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<MaintenanceLog>> getLogsByAssetId(@PathVariable Long assetId) {
        return ResponseEntity.ok(maintenanceRepository.findByAssetId(assetId));
    }
}
