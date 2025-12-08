import axiosClient from '../utils/axiosClient';

const searchApi = {
  // Flight Search
  searchFlights: async (searchParams) => {
    // searchParams example: { from: 'DEL', to: 'BOM', date: '2023-12-25' }
    // Postman collection ke hisab se ye POST request hai
    const response = await axiosClient.post('/api/search/flights', searchParams);
    return response.data;
  },

  // Hotel Search
  searchHotels: async (searchParams) => {
    // searchParams example: { city: 'Goa', checkIn: '2023-12-25' }
    const response = await axiosClient.post('/api/search/hotels', searchParams);
    return response.data;
  }
};

export default searchApi;