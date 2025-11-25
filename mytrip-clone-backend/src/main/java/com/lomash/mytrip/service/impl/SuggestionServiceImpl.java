package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.search.SuggestionResponse;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.Location;
import com.lomash.mytrip.repository.HotelRepository;
import com.lomash.mytrip.repository.LocationRepository;
import com.lomash.mytrip.service.SuggestionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final LocationRepository locationRepository;
    private final HotelRepository hotelRepository;

    public SuggestionServiceImpl(LocationRepository locationRepository,
                                 HotelRepository hotelRepository) {
        this.locationRepository = locationRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<SuggestionResponse> suggest(String query, int limit) {
        if (!StringUtils.hasText(query)) return List.of();

        String q = query.trim();
        int locLimit = Math.max(3, limit/2);
        int hotelLimit = limit - locLimit;

        PageRequest locPage = PageRequest.of(0, locLimit);
        PageRequest hotelPage = PageRequest.of(0, hotelLimit);

        // Prefer prefix matches first (better UX)
        List<Location> locs = locationRepository.findTop5ByNameStartingWithIgnoreCase(q, locPage);
        if (locs.size() < locLimit) {
            // fill from containing if not enough
            locationRepository.findTop5ByNameContainingIgnoreCase(q, locPage)
                    .stream()
                    .filter(l -> !locs.contains(l))
                    .limit(locLimit - locs.size())
                    .forEach(locs::add);
        }

        List<Hotel> hotels = hotelRepository.findTop5ByNameStartingWithIgnoreCase(q, hotelPage);
        if (hotels.size() < hotelLimit) {
            hotelRepository.findTop5ByNameContainingIgnoreCase(q, hotelPage)
                    .stream()
                    .filter(h -> !hotels.contains(h))
                    .limit(hotelLimit - hotels.size())
                    .forEach(hotels::add);
        }

        List<SuggestionResponse> out = new ArrayList<>();

        for (Location l : locs) {
            out.add(SuggestionResponse.builder()
                    .type("LOCATION")
                    .id(l.getId())
                    .title(l.getName() + (l.getCity()!=null ? ", " + l.getCity() : ""))
                    .subtitle((l.getState()!=null?l.getState() + " • ":"") + (l.getCountry()!=null?l.getCountry():""))
                    .build());
        }

        for (Hotel h : hotels) {
            out.add(SuggestionResponse.builder()
                    .type("HOTEL")
                    .id(h.getId())
                    .title(h.getName())
                    .subtitle(h.getLocation() != null ? h.getLocation().getName() : "")
                    .build());
        }

        // final trimming to requested limit
        if (out.size() > limit) return out.subList(0, limit);
        return out;
    }
}
