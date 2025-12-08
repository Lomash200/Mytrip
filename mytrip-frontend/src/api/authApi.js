import axiosClient from '../utils/axiosClient';

const authApi = {
  login: async (credentials) => {
    const response = await axiosClient.post('/api/auth/login', credentials);
    return response.data;
  },
  signup: async (data) => {
    const response = await axiosClient.post('/api/auth/signup', data);
    return response.data;
  },
  getCurrentUser: async () => {
    const response = await axiosClient.get('/api/users/me');
    return response.data;
  }
};

export default authApi;