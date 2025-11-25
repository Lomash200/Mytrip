package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.room.RoomRequest;
import com.lomash.mytrip.dto.room.RoomResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.repository.RoomRepository;
import com.lomash.mytrip.service.RoomService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .maxGuests(room.getMaxGuests())
                .availabilityCount(room.getAvailabilityCount())
                .build();
    }

    @Override
    @Transactional
    public RoomResponse addRoom(Long hotelId, RoomRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ApiException("Hotel not found"));

        Room room = Room.builder()
                .roomType(request.getRoomType())
                .pricePerNight(request.getPricePerNight())
                .availabilityCount(request.getAvailabilityCount())
                .maxGuests(request.getMaxGuests())
                .hotel(hotel)
                .build();

        return mapToResponse(roomRepository.save(room));
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long roomId, RoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ApiException("Room not found"));

        if (request.getRoomType() != null) room.setRoomType(request.getRoomType());
        if (request.getPricePerNight() > 0) room.setPricePerNight(request.getPricePerNight());
        if (request.getMaxGuests() > 0) room.setMaxGuests(request.getMaxGuests());
        if (request.getAvailabilityCount() >= 0) room.setAvailabilityCount(request.getAvailabilityCount());

        return mapToResponse(roomRepository.save(room));
    }

    @Override
    @Transactional
    public void deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ApiException("Room not found");
        }
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<RoomResponse> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public void addRoomImage(Long roomId, String url) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ApiException("Room not found"));

        room.getImages().add(url);
        roomRepository.save(room);
    }



}
