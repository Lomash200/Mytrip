package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.search.SuggestionResponse;

import java.util.List;

public interface SuggestionService {
    List<SuggestionResponse> suggest(String query, int limit);
}
