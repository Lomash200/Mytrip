package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.ticket.MessageRequest;
import com.lomash.mytrip.dto.ticket.MessageResponse;
import com.lomash.mytrip.dto.ticket.TicketResponse;
import com.lomash.mytrip.service.SupportTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/support")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSupportController {

    private final SupportTicketService service;

    public AdminSupportController(SupportTicketService service) {
        this.service = service;
    }

    @PostMapping("/{ticketId}/reply")
    public ResponseEntity<MessageResponse> reply(
            @PathVariable Long ticketId,
            @RequestBody MessageRequest request) {
        return ResponseEntity.ok(service.sendAdminMessage(ticketId, request));
    }

    @PutMapping("/{ticketId}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Long ticketId,
            @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(ticketId, status));
    }
}
