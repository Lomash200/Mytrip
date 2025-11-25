package com.lomash.mytrip.controller;

import com.lomash.mytrip.service.FileStorageService;
import com.lomash.mytrip.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class RoomImageController {

    private final FileStorageService fileStorageService;
    private final RoomService roomService;

    public RoomImageController(FileStorageService fileStorageService, RoomService roomService) {
        this.fileStorageService = fileStorageService;
        this.roomService = roomService;
    }

    @PostMapping("/{roomId}/images")
    public ResponseEntity<?> uploadRoomImage(
            @PathVariable Long roomId,
            @RequestParam("image") MultipartFile file) {

        String url = fileStorageService.saveFile(file, "rooms/" + roomId);

        roomService.addRoomImage(roomId, url);

        return ResponseEntity.ok(url);
    }
}
