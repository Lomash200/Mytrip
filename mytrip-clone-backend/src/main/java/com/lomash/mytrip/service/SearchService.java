package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<HotelSearchResponse> searchHotels(HotelSearchRequest request);
}
