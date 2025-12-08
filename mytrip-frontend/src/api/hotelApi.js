import axiosClient from '../utils/axiosClient';

const hotelApi = {
  // Single Hotel Details
  getHotelById: async (id) => {
    // GET /api/hotels/{id}
    const response = await axiosClient.get(`/api/hotels/${id}`);
    return response.data;
  },

  // Rooms of a specific Hotel
  getHotelRooms: async (hotelId) => {
    // GET /api/hotels/{hotelId}/rooms
    const response = await axiosClient.get(`/api/hotels/${hotelId}/rooms`);
    return response.data;
  }
};

export default hotelApi;