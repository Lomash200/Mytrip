package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.ticket.*;
import com.lomash.mytrip.entity.SupportMessage;
import com.lomash.mytrip.entity.SupportTicket;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.TicketStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.SupportMessageRepository;
import com.lomash.mytrip.repository.SupportTicketRepository;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.SupportTicketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository ticketRepo;
    private final SupportMessageRepository messageRepo;
    private final UserRepository userRepository;

    public SupportTicketServiceImpl(SupportTicketRepository ticketRepo,
                                    SupportMessageRepository messageRepo,
                                    UserRepository userRepository) {
        this.ticketRepo = ticketRepo;
        this.messageRepo = messageRepo;
        this.userRepository = userRepository;
    }

    private User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new ApiException("User not found"));
    }

    // ---------------- User ----------------

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        SupportTicket ticket = SupportTicket.builder()
                .subject(request.getSubject())
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .user(currentUser())
                .build();

        ticketRepo.save(ticket);

        return map(ticket);
    }

    @Override
    public List<TicketResponse> getMyTickets() {
        return ticketRepo.findByUser(currentUser())
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<MessageResponse> getTicketMessages(Long ticketId) {
        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ApiException("Ticket not found"));

        return messageRepo.findByTicketOrderByTimeAsc(ticket)
                .stream()
                .map(msg -> MessageResponse.builder()
                        .id(msg.getId())
                        .message(msg.getMessage())
                        .fromAdmin(msg.isFromAdmin())
                        .time(msg.getTime().toString())
                        .build())
                .toList();
    }

    @Override
    public MessageResponse sendUserMessage(Long ticketId, MessageRequest request) {
        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ApiException("Ticket not found"));

        SupportMessage msg = SupportMessage.builder()
                .ticket(ticket)
                .message(request.getMessage())
                .fromAdmin(false)
                .time(LocalDateTime.now())
                .build();

        messageRepo.save(msg);

        return MessageResponse.builder()
                .id(msg.getId())
                .message(msg.getMessage())
                .fromAdmin(false)
                .time(msg.getTime().toString())
                .build();
    }

    // ---------------- Admin ----------------

    @Override
    public MessageResponse sendAdminMessage(Long ticketId, MessageRequest request) {
        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ApiException("Ticket not found"));

        SupportMessage msg = SupportMessage.builder()
                .ticket(ticket)
                .message(request.getMessage())
                .fromAdmin(true)
                .time(LocalDateTime.now())
                .build();

        messageRepo.save(msg);

        ticket.setStatus(TicketStatus.IN_PROGRESS);
        ticketRepo.save(ticket);

        return MessageResponse.builder()
                .id(msg.getId())
                .message(msg.getMessage())
                .fromAdmin(true)
                .time(msg.getTime().toString())
                .build();
    }

    @Override
    public TicketResponse updateStatus(Long ticketId, String status) {
        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ApiException("Ticket not found"));

        ticket.setStatus(TicketStatus.valueOf(status.toUpperCase()));
        ticketRepo.save(ticket);

        return map(ticket);
    }

    private TicketResponse map(SupportTicket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .subject(ticket.getSubject())
                .status(ticket.getStatus().name())
                .createdAt(ticket.getCreatedAt().toString())
                .build();
    }
}
