package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.hotel.HotelRequest;
import com.lomash.mytrip.dto.hotel.HotelResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Room;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.repository.RoomRepository;
import com.lomash.mytrip.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelServiceImpl(HotelRepository hotelRepository,
                            RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public HotelResponse createHotel(HotelRequest request) {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .city(request.getCity())
                .rating(request.getRating())
                .build();

        hotel = hotelRepository.save(hotel);
        return toResponse(hotel);
    }

    @Override
    @Transactional
    public HotelResponse updateHotel(Long id, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ApiException("Hotel not found"));

        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setRating(request.getRating());

        hotelRepository.save(hotel);
        return toResponse(hotel);
    }

    @Override
    @Transactional
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ApiException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ApiException("Hotel not found"));
        return toResponse(hotel);
    }

    @Override
    public Page<HotelResponse> getAllHotels(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);

        List<HotelResponse> dtoList = hotels.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(dtoList, pageable, hotels.getTotalElements());
    }

    @Override
    public void addHotelImage(Long hotelId, String imageUrl) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ApiException("Hotel not found"));
        hotel.setImageUrl(imageUrl);
        hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> searchHotels(String city, String name, Double minPrice, Double maxPrice) {
        if (city != null && !city.isBlank()) {
            return hotelRepository.findByCityIgnoreCase(city);
        } else if (name != null && !name.isBlank()) {
            return hotelRepository.findByNameContainingIgnoreCase(name);
        } else {
            return hotelRepository.findAll();
        }
    }

    @Override
    public Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).orElse(null);
    }

    @Override
    public List<Room> getRoomsForHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        // Simplified - In production, check bookings
        return true;
    }

    private HotelResponse toResponse(Hotel hotel) {
        if (hotel == null) return null;

        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress())
                .locationName(hotel.getCity())
                .rating(hotel.getRating())
                .build();
    }
}