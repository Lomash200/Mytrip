import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

// Layouts
import UserLayout from './layouts/UserLayout';
import AdminLayout from './layouts/AdminLayout';

// Public & Auth Pages
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Payments from './pages/Payments';

// User Feature Pages
import Flights from './pages/Flights';
import Hotels from './pages/Hotels';
import HotelDetails from './pages/HotelDetails';
import BookFlight from './pages/BookFlight';
import BookHotel from './pages/BookHotel';
import MyBookings from './pages/MyBookings';
import Profile from './pages/Profile';
import Support from './pages/Support';
import TicketChat from './pages/TicketChat';

// Admin Pages
import Dashboard from './pages/admin/Dashboard';
import ManageFlights from './pages/admin/ManageFlights';
import ManageHotels from './pages/admin/ManageHotels';
import ManageRooms from './pages/admin/ManageRooms';
import AdminBookings from './pages/admin/AdminBookings';
import ManageCoupons from './pages/admin/ManageCoupons';
import AdminKyc from './pages/admin/AdminKyc';
import AdminSupport from './pages/admin/AdminSupport';
import AdminReviews from './pages/admin/AdminReviews';

function App() {
  return (
    <Routes>
      
      {/* 1. AUTH ROUTES (No Navbar/Footer) */}
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/payment" element={<Payments />} />

      {/* 2. USER ROUTES (With Navbar & Footer) */}
      <Route element={<UserLayout />}>
        <Route path="/" element={<Home />} />
        
        {/* Core Features */}
        <Route path="/flights" element={<Flights />} />
        <Route path="/hotels" element={<Hotels />} />
        <Route path="/hotels/:id" element={<HotelDetails />} />
        
        {/* Booking Flows */}
        <Route path="/book/flight/:flightId" element={<BookFlight />} />
        <Route path="/book/hotel/:roomId" element={<BookHotel />} />
        
        {/* User Dashboard */}
        <Route path="/my-bookings" element={<MyBookings />} />
        <Route path="/profile" element={<Profile />} />
        
        {/* Support System */}
        <Route path="/support" element={<Support />} />
        <Route path="/support/:ticketId" element={<TicketChat />} />
      </Route>

      {/* 3. ADMIN ROUTES (With Sidebar) */}
      <Route path="/admin" element={<AdminLayout />}>
        <Route index element={<Navigate to="dashboard" />} />
        
        <Route path="dashboard" element={<Dashboard />} />
        
        {/* Managements */}
        <Route path="flights" element={<ManageFlights />} />
        <Route path="hotels" element={<ManageHotels />} />
        <Route path="hotels/:hotelId/rooms" element={<ManageRooms />} />
        <Route path="bookings" element={<AdminBookings />} />
        <Route path="coupons" element={<ManageCoupons />} />
        
        {/* Advanced Features */}
        <Route path="kyc" element={<AdminKyc />} />
        <Route path="support" element={<AdminSupport />} />
        <Route path="reviews" element={<AdminReviews />} />
      </Route>

      {/* 4. 404 Not Found */}
      <Route path="*" element={<div className="text-center py-20 text-2xl">404 - Page Not Found</div>} />

    </Routes>
  );
}

export default App;

