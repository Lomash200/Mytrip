package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.room.RoomRequest;
import com.lomash.mytrip.dto.room.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse addRoom(Long hotelId, RoomRequest request);

    RoomResponse updateRoom(Long roomId, RoomRequest request);

    void deleteRoom(Long roomId);

    List<RoomResponse> getRoomsByHotel(Long hotelId);

    void addRoomImage(Long roomId, String url);
}

