import axiosClient from '../utils/axiosClient';

const supportApi = {
  // Create Ticket
  createTicket: async (ticketData) => {
    // POST /api/support/create
    const response = await axiosClient.post('/api/support/create', ticketData);
    return response.data;
  },

  // Get My Tickets
  getMyTickets: async () => {
    // GET /api/support/my-tickets
    const response = await axiosClient.get('/api/support/my-tickets');
    return response.data;
  },

  // Get Messages of a Ticket
  getMessages: async (ticketId) => {
    // GET /api/support/{ticketId}/messages
    const response = await axiosClient.get(`/api/support/${ticketId}/messages`);
    return response.data;
  },

  // Send Message
  sendMessage: async (ticketId, messageData) => {
    // POST /api/support/{ticketId}/message
    const response = await axiosClient.post(`/api/support/${ticketId}/message`, messageData);
    return response.data;
  }
};

export default supportApi;