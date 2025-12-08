package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.ticket.*;

import java.util.List;

public interface SupportTicketService {

    TicketResponse createTicket(TicketRequest request);

    List<TicketResponse> getMyTickets();

    List<MessageResponse> getTicketMessages(Long ticketId);
    List<TicketResponse> getAllTickets();

    MessageResponse sendUserMessage(Long ticketId, MessageRequest request);

    MessageResponse sendAdminMessage(Long ticketId, MessageRequest request);

    TicketResponse updateStatus(Long ticketId, String status);
}
