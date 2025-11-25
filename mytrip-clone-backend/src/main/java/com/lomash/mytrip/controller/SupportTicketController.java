package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.ticket.*;
import com.lomash.mytrip.service.SupportTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportTicketController {

    private final SupportTicketService service;

    public SupportTicketController(SupportTicketService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> create(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(service.createTicket(request));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketResponse>> myTickets() {
        return ResponseEntity.ok(service.getMyTickets());
    }

    @GetMapping("/{ticketId}/messages")
    public ResponseEntity<List<MessageResponse>> messages(@PathVariable Long ticketId) {
        return ResponseEntity.ok(service.getTicketMessages(ticketId));
    }

    @PostMapping("/{ticketId}/message")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long ticketId,
            @RequestBody MessageRequest request) {
        return ResponseEntity.ok(service.sendUserMessage(ticketId, request));
    }
}
