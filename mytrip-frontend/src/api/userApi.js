import axiosClient from '../utils/axiosClient';

const userApi = {
  // --- Profile ---
  updateProfile: async (userData) => {
    // PUT /api/users/me
    const response = await axiosClient.put('/api/users/me', userData);
    return response.data;
  },

  // --- Wishlist ---
  getWishlist: async () => {
    // GET /api/wishlist
    const response = await axiosClient.get('/api/wishlist');
    return response.data;
  },

  removeFromWishlist: async (type, targetId) => {
    // DELETE /api/wishlist/{type}/{targetId}
    const response = await axiosClient.delete(`/api/wishlist/${type}/${targetId}`);
    return response.data;
  },

  // --- KYC ---
  getKycStatus: async () => {
    // GET /api/kyc/status
    const response = await axiosClient.get('/api/kyc/status');
    return response.data;
  },

  uploadKyc: async (formData) => {
    // POST /api/kyc/upload (Multipart File)
    const response = await axiosClient.post('/api/kyc/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
  }
};

export default userApi;