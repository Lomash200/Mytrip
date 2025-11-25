package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.search.SuggestionResponse;
import com.lomash.mytrip.service.SuggestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/suggestions")
    public List<SuggestionResponse> suggestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "8") int limit) {

        return suggestionService.suggest(query, limit);
    }
}
