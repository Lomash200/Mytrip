package com.lomash.mytrip.controller;

import com.lomash.mytrip.service.FileStorageService;
import com.lomash.mytrip.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/hotels")
@PreAuthorize("hasRole('ADMIN')")
public class HotelImageController {

    private final FileStorageService fileStorageService;
    private final HotelService hotelService;

    public HotelImageController(FileStorageService fileStorageService, HotelService hotelService) {
        this.fileStorageService = fileStorageService;
        this.hotelService = hotelService;
    }

    @PostMapping("/{hotelId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long hotelId,
            @RequestParam("image") MultipartFile file) {

        String url = fileStorageService.saveFile(file, "hotels/" + hotelId);

        hotelService.addHotelImage(hotelId, url);

        return ResponseEntity.ok(url);
    }
}
