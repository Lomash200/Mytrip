package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.room.RoomRequest;
import com.lomash.mytrip.dto.room.RoomResponse;
import com.lomash.mytrip.service.RoomService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/hotels/{hotelId}/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoomController {

    private final RoomService roomService;

    public AdminRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponse> addRoom(
            @PathVariable Long hotelId,
            @RequestBody RoomRequest request
    ) {
        return ResponseEntity.ok(roomService.addRoom(hotelId, request));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long roomId,
            @RequestBody RoomRequest request
    ) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, request));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted");
    }

    @GetMapping
    public List<RoomResponse> getRooms(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotel(hotelId);
    }
}
