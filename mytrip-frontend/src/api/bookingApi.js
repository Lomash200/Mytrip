import axiosClient from '../utils/axiosClient';

const bookingApi = {
  // Flight ki details lana
  getFlightDetails: async (flightId) => {
    // Note: Agar ye endpoint fail ho to hum search result se data pass kar sakte hain
    const response = await axiosClient.get(`/api/search/flights/${flightId}`); 
    return response.data;
  },

  // Booking Create karna
  createBooking: async (bookingData) => {
    const response = await axiosClient.post('/api/bookings/create', bookingData);
    return response.data;
  }, // <--- Yaha Comma (,) zaruri hai

  // My Bookings lana
  getMyBookings: async () => {
    const response = await axiosClient.get('/api/bookings/me');
    return response.data;
  }, // <--- Yaha bhi Comma

  // Booking Cancel karna
  cancelBooking: async (bookingId) => {
    const response = await axiosClient.put(`/api/flights/bookings/cancel/${bookingId}`);
    return response.data;
  }
};

export default bookingApi;