package com.lomash.mytrip.dto.search;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestionResponse {
    private String type;   // "LOCATION" | "HOTEL" | "FLIGHT"
    private Long id;       // id of target (optional for location can be null)
    private String title;  // what to show in UI (e.g. "Delhi, India" / "Taj Palace")
    private String subtitle; // optional small text (e.g. "Hotel • Connaught Place")
}
