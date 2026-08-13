package com.ict.asset.assetservice.controller;

import com.ict.asset.assetservice.model.Asset;
import com.ict.asset.assetservice.repository.AssetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetRepository assetRepository;

    public AssetController(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset savedAsset = assetRepository.save(asset);
        return new ResponseEntity<>(savedAsset, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        return assetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset assetDetails) {
        return assetRepository.findById(id)
                .map(existingAsset -> {
                    existingAsset.setName(assetDetails.getName());
                    existingAsset.setSerialNumber(assetDetails.getSerialNumber());
                    existingAsset.setCategory(assetDetails.getCategory());
                    existingAsset.setBrand(assetDetails.getBrand());
                    existingAsset.setStatus(assetDetails.getStatus());
                    existingAsset.setPurchaseDate(assetDetails.getPurchaseDate());
                    existingAsset.setCost(assetDetails.getCost());
                    Asset updatedAsset = assetRepository.save(existingAsset);
                    return ResponseEntity.ok(updatedAsset);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        return assetRepository.findById(id)
                .map(asset -> {
                    assetRepository.delete(asset);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
