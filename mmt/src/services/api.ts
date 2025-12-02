import axios from 'axios';
import { 
  LoginRequest, LoginResponse, RegisterRequest, UserDto, UpdateUserRequest,
  DashboardStatsResponse, HotelRequest, HotelResponse, RoomRequest, RoomResponse,
  FlightRequest, FlightResponse, BookingRequest, BookingResponse, 
  FlightBookingRequest, FlightBookingResponse, HotelSearchRequest, HotelSearchResponse,
  FlightSearchRequest, FlightSearchResponse, SuggestionResponse, CouponRequest, CouponResponse,
  ReviewRequest, ReviewResponse, KycUploadRequest, KycResponse, KycDecisionRequest,
  PaymentRequest, PaymentResponse, PaymentVerifyRequest, RefundRequest, RefundResponse,
  TicketRequest, TicketResponse, MessageRequest, MessageResponse, WishlistResponse
} from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Request interceptor to add auth token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth Services
export const authService = {
  login: (credentials: LoginRequest): Promise<LoginResponse> => 
    api.post('/auth/login', credentials).then(res => res.data),

  signup: (userData: RegisterRequest): Promise<void> => 
    api.post('/auth/signup', userData),
};

// User Services
export const userService = {
  getProfile: (): Promise<UserDto> => 
    api.get('/users/me').then(res => res.data),

  updateProfile: (data: UpdateUserRequest): Promise<UserDto> => 
    api.put('/users/me', data).then(res => res.data),

  getUserById: (id: number): Promise<UserDto> => 
    api.get(`/users/${id}`).then(res => res.data),
};

// Admin Services
export const adminService = {
  getStats: (days: number = 7, recentLimit: number = 10, popularLimit: number = 5): Promise<DashboardStatsResponse> =>
    api.get(`/admin/dashboard/stats?days=${days}&recentLimit=${recentLimit}&popularLimit=${popularLimit}`)
      .then(res => res.data),

  getAllBookings: (): Promise<BookingResponse[]> =>
    api.get('/admin/bookings').then(res => res.data),

  getAllFlightBookings: (): Promise<FlightBookingResponse[]> =>
    api.get('/admin/flights/bookings').then(res => res.data),

  getAllReviews: (): Promise<ReviewResponse[]> =>
    api.get('/admin/reviews').then(res => res.data),

  approveReview: (id: number, approve: boolean = true): Promise<string> =>
    api.post(`/admin/reviews/${id}/approve?approve=${approve}`).then(res => res.data),

  deleteReview: (id: number): Promise<string> =>
    api.delete(`/admin/reviews/${id}`).then(res => res.data),
};

// Hotel Services
export const hotelService = {
  create: (data: HotelRequest): Promise<HotelResponse> =>
    api.post('/admin/hotels', data).then(res => res.data),

  update: (id: number, data: HotelRequest): Promise<HotelResponse> =>
    api.put(`/admin/hotels/${id}`, data).then(res => res.data),

  delete: (id: number): Promise<void> =>
    api.delete(`/admin/hotels/${id}`).then(res => res.data),

  getById: (id: number): Promise<HotelResponse> =>
    api.get(`/admin/hotels/${id}`).then(res => res.data),

  getAll: (page: number = 0, size: number = 10): Promise<any> =>
    api.get(`/admin/hotels?page=${page}&size=${size}`).then(res => res.data),

  // Image upload
  uploadImage: (hotelId: number, image: File): Promise<string> => {
    const formData = new FormData();
    formData.append('image', image);
    return api.post(`/admin/hotels/${hotelId}/images`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    }).then(res => res.data);
  },
};

// Room Services
export const roomService = {
  add: (hotelId: number, data: RoomRequest): Promise<RoomResponse> =>
    api.post(`/admin/hotels/${hotelId}/rooms`, data).then(res => res.data),

  update: (roomId: number, data: RoomRequest): Promise<RoomResponse> =>
    api.put(`/admin/hotels//rooms/${roomId}`, data).then(res => res.data),

  delete: (roomId: number): Promise<void> =>
    api.delete(`/admin/hotels//rooms/${roomId}`).then(res => res.data),

  getByHotel: (hotelId: number): Promise<RoomResponse[]> =>
    api.get(`/admin/hotels/${hotelId}/rooms`).then(res => res.data),

  // Image upload
  uploadImage: (roomId: number, image: File): Promise<string> => {
    const formData = new FormData();
    formData.append('image', image);
    return api.post(`/admin/rooms/${roomId}/images`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    }).then(res => res.data);
  },
};

// Flight Services
export const flightService = {
  create: (data: FlightRequest): Promise<FlightResponse> =>
    api.post('/admin/flights', data).then(res => res.data),

  update: (id: number, data: FlightRequest): Promise<FlightResponse> =>
    api.put(`/admin/flights/${id}`, data).then(res => res.data),

  delete: (id: number): Promise<string> =>
    api.delete(`/admin/flights/${id}`).then(res => res.data),

  getAll: (): Promise<FlightResponse[]> =>
    api.get('/admin/flights').then(res => res.data),
};

// Booking Services
export const bookingService = {
  create: (data: BookingRequest): Promise<BookingResponse> =>
    api.post('/bookings', data).then(res => res.data),

  cancel: (id: number): Promise<BookingResponse> =>
    api.put(`/bookings/${id}/cancel`).then(res => res.data),

  getMyBookings: (): Promise<BookingResponse[]> =>
    api.get('/bookings').then(res => res.data),
};

// Flight Booking Services
export const flightBookingService = {
  create: (data: FlightBookingRequest): Promise<FlightBookingResponse> =>
    api.post('/flights/bookings', data).then(res => res.data),

  confirm: (pnr: string): Promise<FlightBookingResponse> =>
    api.post(`/flights/bookings/confirm/${pnr}`).then(res => res.data),

  cancel: (pnr: string): Promise<FlightBookingResponse> =>
    api.put(`/flights/bookings/cancel/${pnr}`).then(res => res.data),

  getMyBookings: (): Promise<FlightBookingResponse[]> =>
    api.get('/flights/bookings').then(res => res.data),
};

// Search Services
export const searchService = {
  searchHotels: (data: HotelSearchRequest): Promise<any> =>
    api.post('/search/hotels', data).then(res => res.data),

  searchFlights: (data: FlightSearchRequest): Promise<FlightSearchResponse[]> =>
    api.post('/search/flights', data).then(res => res.data),

  getSuggestions: (query: string, limit: number = 8): Promise<SuggestionResponse[]> =>
    api.get(`/search/suggestions?query=${query}&limit=${limit}`).then(res => res.data),
};

// Coupon Services
export const couponService = {
  create: (data: CouponRequest): Promise<CouponResponse> =>
    api.post('/admin/coupons', data).then(res => res.data),

  update: (id: number, data: CouponRequest): Promise<CouponResponse> =>
    api.put(`/admin/coupons/${id}`, data).then(res => res.data),

  delete: (id: number): Promise<void> =>
    api.delete(`/admin/coupons/${id}`).then(res => res.data),

  apply: (code: string, amount: number): Promise<CouponResponse> =>
    api.get(`/coupon/apply?code=${code}&amount=${amount}`).then(res => res.data),
};

// Review Services
export const reviewService = {
  add: (data: ReviewRequest): Promise<ReviewResponse> =>
    api.post('/reviews', data).then(res => res.data),

  update: (id: number, data: ReviewRequest): Promise<ReviewResponse> =>
    api.put(`/reviews/${id}`, data).then(res => res.data),

  delete: (id: number): Promise<void> =>
    api.delete(`/reviews/${id}`).then(res => res.data),

  getReviews: (targetType: string, targetId: number, onlyApproved: boolean = true): Promise<ReviewResponse[]> =>
    api.get(`/reviews/target/${targetType}/${targetId}?onlyApproved=${onlyApproved}`).then(res => res.data),

  getMyReviews: (): Promise<ReviewResponse[]> =>
    api.get('/reviews/me').then(res => res.data),

  getAverageRating: (targetType: string, targetId: number): Promise<number> =>
    api.get(`/reviews/target/${targetType}/${targetId}/avg`).then(res => res.data),
};

// KYC Services
export const kycService = {
  upload: (data: FormData): Promise<KycResponse> =>
    api.post('/kyc/upload', data, {
      headers: { 'Content-Type': 'multipart/form-data' }
    }).then(res => res.data),

  getMyDocs: (): Promise<KycResponse[]> =>
    api.get('/kyc/my').then(res => res.data),

  getPending: (): Promise<KycResponse[]> =>
    api.get('/admin/kyc/pending').then(res => res.data),

  review: (id: number, decision: KycDecisionRequest): Promise<KycResponse> =>
    api.put(`/admin/kyc/${id}/review`, decision).then(res => res.data),
};

// Payment Services
export const paymentService = {
  createOrder: (data: PaymentRequest): Promise<PaymentResponse> =>
    api.post('/payments/create-order', data).then(res => res.data),

  verify: (data: PaymentVerifyRequest): Promise<string> =>
    api.post('/payments/verify', data).then(res => res.data),
};

// Refund Services
export const refundService = {
  initiate: (data: RefundRequest): Promise<RefundResponse> =>
    api.post('/refund', data).then(res => res.data),
};

// Support Ticket Services
export const supportTicketService = {
  create: (data: TicketRequest): Promise<TicketResponse> =>
    api.post('/support/create', data).then(res => res.data),

  getMyTickets: (): Promise<TicketResponse[]> =>
    api.get('/support/my-tickets').then(res => res.data),

  getMessages: (ticketId: number): Promise<MessageResponse[]> =>
    api.get(`/support/${ticketId}/messages`).then(res => res.data),

  sendMessage: (ticketId: number, data: MessageRequest): Promise<MessageResponse> =>
    api.post(`/support/${ticketId}/message`, data).then(res => res.data),

  // Admin functions
  reply: (ticketId: number, data: MessageRequest): Promise<MessageResponse> =>
    api.post(`/admin/support/${ticketId}/reply`, data).then(res => res.data),

  updateStatus: (ticketId: number, status: string): Promise<TicketResponse> =>
    api.put(`/admin/support/${ticketId}/status?status=${status}`).then(res => res.data),
};

// Wishlist Services
export const wishlistService = {
  add: (type: string, targetId: number): Promise<WishlistResponse> =>
    api.post(`/wishlist/${type}/${targetId}`).then(res => res.data),

  remove: (type: string, targetId: number): Promise<void> =>
    api.delete(`/wishlist/${type}/${targetId}`).then(res => res.data),

  getMyWishlist: (): Promise<WishlistResponse[]> =>
    api.get('/wishlist').then(res => res.data),
};

// Invoice Services
export const invoiceService = {
  getHotelInvoice: (bookingId: number): Promise<Blob> =>
    api.get(`/invoice/hotel/${bookingId}`, { responseType: 'blob' }).then(res => res.data),

  getFlightTicket: (flightBookingId: number): Promise<Blob> =>
    api.get(`/invoice/flight/${flightBookingId}`, { responseType: 'blob' }).then(res => res.data),
};

export default api;