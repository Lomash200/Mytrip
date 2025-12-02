import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import Header from './components/common/Header';
import Login from './pages/auth/Login';
import Signup from './pages/auth/Signup';
import AdminDashboard from './pages/admin/Dashboard';
import UserDashboard from './pages/user/Dashboard';
 import HotelSearch from './pages/user/HotelSearch';
 import FlightSearch from './pages/user/FlightSearch';
 import BookingHistory from './pages/user/BookingHistory';
import SupportTickets from './pages/user/SupportTickets';
import KycManagement from './pages/admin/KycManagement';

const ProtectedRoute: React.FC<{ children: React.ReactNode; admin?: boolean }> = ({ 
  children, 
  admin = false 
}) => {
  const { user, isAuthenticated } = useAuth();
  
  if (!isAuthenticated) return <Navigate to="/login" />;
  if (admin && user?.role !== 'ADMIN') return <Navigate to="/" />;
  
  return <>{children}</>;
};

function AppContent() {
  const { isAuthenticated } = useAuth();

  return (
    <>
      {isAuthenticated && <Header />}
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        
        {/* User Routes */}
        <Route path="/" element={
          <ProtectedRoute>
            <UserDashboard />
          </ProtectedRoute>
        } />
        
        {/* Temporarily comment out these routes */}
        <Route path="/search/hotels" element={
          <ProtectedRoute>
            <HotelSearch />
          </ProtectedRoute>
        } />
        <Route path="/search/flights" element={
          <ProtectedRoute>
            <FlightSearch />
          </ProtectedRoute>
        } />
        <Route path="/my-bookings" element={
          <ProtectedRoute>
            <BookingHistory />
          </ProtectedRoute>
        } />
        <Route path="/support" element={
          <ProtectedRoute>
            <SupportTickets />
          </ProtectedRoute>
        } /> 
        
        {/* Admin Routes */}
        <Route path="/admin" element={
          <ProtectedRoute admin>
            <AdminDashboard />
          </ProtectedRoute>
        } />
        
        { <Route path="/admin/kyc" element={
          <ProtectedRoute admin>
            <KycManagement />
          </ProtectedRoute>
        } /> }
      </Routes>
    </>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-50">
          <AppContent />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;