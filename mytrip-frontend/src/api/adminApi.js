import axiosClient from '../utils/axiosClient';

const adminApi = {
  // --- DASHBOARD ---
  getStats: async () => {
    const response = await axiosClient.get('/api/admin/dashboard/stats');
    return response.data;
  },

  // --- BOOKINGS ---
  getAllBookings: async () => {
    const response = await axiosClient.get('/api/admin/bookings'); // Check backend endpoint
    return response.data;
  },

  // --- FLIGHTS ---
  getAllFlights: async () => {
    const response = await axiosClient.get('/api/admin/flights');
    return response.data;
  },
  addFlight: async (data) => {
    const response = await axiosClient.post('/api/admin/flights', data);
    return response.data;
  },

  // --- HOTELS (NEW) ---
  getAllHotels: async () => {
    // GET /api/admin/hotels
    const response = await axiosClient.get('/api/admin/hotels');
    // Backend pagination return kar sakta hai (e.g. response.data.content)
    // Safety check: agar content field hai to wo return karo, nahi to direct data
    return response.data.content || response.data; 
  },

  addHotel: async (data) => {
    const response = await axiosClient.post('/api/admin/hotels', data);
    return response.data;
  },

  deleteHotel: async (id) => {
    const response = await axiosClient.delete(`/api/admin/hotels/${id}`);
    return response.data;
  },

  // --- ROOMS (NEW) ---
  getRoomsByHotel: async (hotelId) => {
    const response = await axiosClient.get(`/api/admin/hotels/${hotelId}/rooms`);
    return response.data;
  },

  addRoom: async (hotelId, data) => {
    const response = await axiosClient.post(`/api/admin/hotels/${hotelId}/rooms`, data);
    return response.data;
  },

  deleteRoom: async (hotelId, roomId) => {
    const response = await axiosClient.delete(`/api/admin/hotels/${hotelId}/rooms/${roomId}`);
    return response.data;
  },

  // --- COUPONS ---
  getAllCoupons: async () => {
    const response = await axiosClient.get('/api/admin/coupons');
    return response.data;
  },
  createCoupon: async (data) => {
    const response = await axiosClient.post('/api/admin/coupons', data);
    return response.data;
  },
  deleteCoupon: async (id) => {
    const response = await axiosClient.delete(`/api/admin/coupons/${id}`);
    return response.data;
  },
  // --- KYC ---
  getPendingKyc: async () => {
    const response = await axiosClient.get('/api/admin/kyc/pending');
    return response.data;
  },
  reviewKyc: async (id, status, remark) => {
    // status: "APPROVED" or "REJECTED"
    const response = await axiosClient.put(`/api/admin/kyc/${id}/review`, { status, remark });
    return response.data;
  },

  // --- REVIEWS ---
  getAllReviews: async () => {
    const response = await axiosClient.get('/api/admin/reviews');
    return response.data;
  },
  approveReview: async (id) => {
    const response = await axiosClient.post(`/api/admin/reviews/${id}/approve`);
    return response.data;
  },
  deleteReview: async (id) => {
    const response = await axiosClient.delete(`/api/admin/reviews/${id}`);
    return response.data;
  },

  // --- SUPPORT ---
  getAllTickets: async () => {
    const response = await axiosClient.get('/api/admin/support');
    return response.data;
  },
  replyTicket: async (id, message) => {
    const response = await axiosClient.post(`/api/admin/support/${id}/reply`, { message });
    return response.data;
  },
  updateTicketStatus: async (id, status) => {
    const response = await axiosClient.put(`/api/admin/support/${id}/status?status=${status}`);
    return response.data;
  }
};

export default adminApi;