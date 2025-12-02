// Auth Types
export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phone: string;
}

// User Types
export interface UserDto {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone: string;
  enabled: boolean;
}

export interface UpdateUserRequest {
  firstName?: string;
  lastName?: string;
  phone?: string;
  email?: string;
}

// Admin Dashboard Types
export interface DailyRevenueDto {
  date: string;
  revenue: number;
}

export interface PopularHotelDto {
  hotelId: number;
  hotelName: string;
  totalBookings: number;
}

export interface RecentBookingDto {
  bookingId: number;
  username: string;
  title: string;
  status: string;
  amount: number;
  createdAt: string;
}

export interface DashboardStatsResponse {
  totalUsers: number;
  totalHotels: number;
  totalRooms: number;
  totalBookings: number;
  totalRevenue: number;
  pendingBookings: number;
  confirmedBookings: number;
  cancelledBookings: number;
  recentBookings: RecentBookingDto[];
  popularHotels: PopularHotelDto[];
  dailyRevenue: DailyRevenueDto[];
}

// Hotel Types
export interface HotelRequest {
  name: string;
  description: string;
  locationId: number;
  address: string;
  rating?: number;
}

export interface HotelResponse {
  id: number;
  name: string;
  description: string;
  address: string;
  locationName: string;
  rating: number;
}

// Room Types
export interface RoomRequest {
  roomType: string;
  pricePerNight: number;
  maxGuests: number;
  availabilityCount: number;
}

export interface RoomResponse {
  id: number;
  roomType: string;
  pricePerNight: number;
  maxGuests: number;
  availabilityCount: number;
}

// Flight Types
export interface FlightRequest {
  airline: string;
  flightNumber: string;
  originId: number;
  destinationId: number;
  departureTime: string;
  arrivalTime: string;
  price: number;
  seatsAvailable: number;
}

export interface FlightResponse {
  id: number;
  airline: string;
  flightNumber: string;
  origin: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  price: number;
  seatsAvailable: number;
}

// Booking Types
export interface BookingRequest {
  hotelId: number;
  roomId: number;
  checkInDate: string;
  checkOutDate: string;
  guests: number;
}

export interface BookingResponse {
  id: number;
  referenceCode: string;
  status: string;
  totalAmount: number;
  hotelName: string;
  roomType: string;
  checkInDate: string;
  checkOutDate: string;
}

// Flight Booking Types
export interface FlightBookingRequest {
  flightId: number;
  passengers: number;
  travelDate: string;
}

export interface FlightBookingResponse {
  id: number;
  pnr: string;
  status: string;
  totalAmount: number;
  airline: string;
  flightNumber: string;
  origin: string;
  destination: string;
  departureTime: string;
  passengers: number;
  travelDate: string;
}

// Search Types
export interface HotelSearchRequest {
  locationId?: number;
  checkIn?: string;
  checkOut?: string;
  guests?: number;
  page?: number;
  size?: number;
}

export interface HotelSearchResponse {
  id: number;
  name: string;
  locationName: string;
  rating: number;
  priceFrom: number;
}

export interface FlightSearchRequest {
  originId?: number;
  destinationId?: number;
  date?: string;
  minPrice?: number;
  maxPrice?: number;
  sortBy?: string;
  sortOrder?: string;
}

export interface FlightSearchResponse {
  id: number;
  airline: string;
  flightNumber: string;
  origin: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  price: number;
  seatsAvailable: number;
}

export interface SuggestionResponse {
  type: string;
  id?: number;
  title: string;
  subtitle?: string;
}

// Coupon Types
export interface CouponRequest {
  code: string;
  discount: number;
  minAmount: number;
  expiryDate: string;
}

export interface CouponResponse {
  id: number;
  code: string;
  discount: number;
  minAmount: number;
  expiryDate: string;
  active: boolean;
}

// Review Types
export interface ReviewRequest {
  targetType: string;
  targetId: number;
  rating: number;
  title: string;
  comment: string;
}

export interface ReviewResponse {
  id: number;
  targetType: string;
  targetId: number;
  userId: number;
  username: string;
  rating: number;
  title: string;
  comment: string;
  approved: boolean;
  createdAt: string;
}

// KYC Types
export interface KycUploadRequest {
  documentType: string;
  documentNumber: string;
  file: File;
}

export interface KycResponse {
  id: number;
  documentType: string;
  documentNumber: string;
  status: string;
  fileUrl: string;
  adminRemark?: string;
  uploadedAt: string;
  reviewedAt?: string;
}

export interface KycDecisionRequest {
  status: string;
  remark?: string;
}

// Payment Types
export interface PaymentRequest {
  bookingId: number;
  amount: number;
}

export interface PaymentResponse {
  orderId: string;
  amount: number;
  currency: string;
}

export interface PaymentVerifyRequest {
  razorpayPaymentId: string;
  razorpayOrderId: string;
  razorpaySignature: string;
  bookingId: number;
}

// Refund Types
export interface RefundRequest {
  bookingId: number;
  reason: string;
}

export interface RefundResponse {
  refundId: string;
  amount: number;
  status: string;
  message: string;
}

// Support Ticket Types
export interface TicketRequest {
  subject: string;
}

export interface TicketResponse {
  id: number;
  subject: string;
  status: string;
  createdAt: string;
}

export interface MessageRequest {
  message: string;
}

export interface MessageResponse {
  id: number;
  message: string;
  fromAdmin: boolean;
  time: string;
}

// Wishlist Types
export interface WishlistResponse {
  id: number;
  type: string;
  targetId: number;
}

// User Interface for Auth Context
export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: 'ADMIN' | 'USER';
}