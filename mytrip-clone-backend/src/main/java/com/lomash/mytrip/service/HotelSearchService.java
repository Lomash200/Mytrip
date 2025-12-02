package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.search.HotelSearchRequest;
import com.lomash.mytrip.dto.search.HotelSearchResponse;

import java.util.List;

public interface HotelSearchService {
    List<HotelSearchResponse> searchHotels(HotelSearchRequest request);
}
