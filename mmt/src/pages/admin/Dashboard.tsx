import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { 
  Users, Hotel, Plane, BookOpen, Star, Shield, TrendingUp, User 
} from 'lucide-react';
import { adminService } from '../../services/api';
import { useAuth } from '../../contexts/AuthContext';
import { DashboardStatsResponse } from '../../types';
import LoadingSpinner from '../../components/common/LoadingSpinner';

const AdminDashboard: React.FC = () => {
  const [stats, setStats] = useState<DashboardStatsResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { switchRole } = useAuth();

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const data = await adminService.getStats(7, 10, 5);
      setStats(data);
    } catch (error: any) {
      setError(error.response?.data?.message || 'Failed to load dashboard statistics');
      console.error('Failed to load stats:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSwitchToUser = () => {
    switchRole('USER');
  };

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex justify-center items-center h-64">
          <LoadingSpinner />
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Header with Switch Button */}
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
          <p className="text-gray-600">Manage your travel platform efficiently</p>
        </div>
        <button
          onClick={handleSwitchToUser}
          className="flex items-center px-4 py-2 text-blue-700 bg-blue-100 rounded-lg hover:bg-blue-200"
        >
          <User className="w-4 h-4 mr-2" />
          Switch to User View
        </button>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="bg-white p-6 rounded-xl shadow-md">
          <div className="flex items-center">
            <div className="p-3 bg-blue-100 rounded-lg">
              <BookOpen className="w-6 h-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Bookings</p>
              <p className="text-2xl font-bold text-gray-900">{stats?.totalBookings || 0}</p>
            </div>
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-md">
          <div className="flex items-center">
            <div className="p-3 bg-green-100 rounded-lg">
              <TrendingUp className="w-6 h-6 text-green-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Revenue</p>
              <p className="text-2xl font-bold text-gray-900">
                ₹{stats?.totalRevenue?.toLocaleString() || 0}
              </p>
            </div>
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-md">
          <div className="flex items-center">
            <div className="p-3 bg-purple-100 rounded-lg">
              <Users className="w-6 h-6 text-purple-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Active Users</p>
              <p className="text-2xl font-bold text-gray-900">{stats?.totalUsers || 0}</p>
            </div>
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-md">
          <div className="flex items-center">
            <div className="p-3 bg-yellow-100 rounded-lg">
              <Star className="w-6 h-6 text-yellow-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Pending Reviews</p>
              <p className="text-2xl font-bold text-gray-900">-</p>
            </div>
          </div>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-8">
        <Link to="/admin/hotels" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg transition-shadow">
          <Hotel className="w-8 h-8 text-blue-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Hotels</h3>
          <p className="text-sm text-gray-600">Manage hotels</p>
        </Link>

        <Link to="/admin/flights" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg transition-shadow">
          <Plane className="w-8 h-8 text-green-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Flights</h3>
          <p className="text-sm text-gray-600">Manage flights</p>
        </Link>

        <Link to="/admin/bookings" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg transition-shadow">
          <BookOpen className="w-8 h-8 text-purple-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Bookings</h3>
          <p className="text-sm text-gray-600">View all bookings</p>
        </Link>

        <Link to="/admin/kyc" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg transition-shadow">
          <Shield className="w-8 h-8 text-indigo-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">KYC</h3>
          <p className="text-sm text-gray-600">Verify users</p>
        </Link>
      </div>

      {/* Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Recent Bookings */}
        <div className="bg-white rounded-xl shadow-md p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Bookings</h3>
          <div className="space-y-4">
            {stats?.recentBookings?.slice(0, 5).map((booking) => (
              <div key={booking.bookingId} className="flex items-center justify-between py-2 border-b">
                <div>
                  <p className="font-medium text-gray-900">{booking.username}</p>
                  <p className="text-sm text-gray-600">{booking.title}</p>
                </div>
                <div className="text-right">
                  <p className="font-semibold text-gray-900">₹{booking.amount}</p>
                  <p className="text-sm text-gray-600">{booking.status}</p>
                </div>
              </div>
            ))}
            {(!stats?.recentBookings || stats.recentBookings.length === 0) && (
              <p className="text-gray-500 text-center py-4">No recent bookings</p>
            )}
          </div>
        </div>

        {/* Popular Hotels */}
        <div className="bg-white rounded-xl shadow-md p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Popular Hotels</h3>
          <div className="space-y-4">
            {stats?.popularHotels?.slice(0, 5).map((hotel) => (
              <div key={hotel.hotelId} className="flex items-center justify-between py-2 border-b">
                <div>
                  <p className="font-medium text-gray-900">{hotel.hotelName}</p>
                </div>
                <div className="text-right">
                  <p className="text-sm text-gray-600">{hotel.totalBookings} bookings</p>
                </div>
              </div>
            ))}
            {(!stats?.popularHotels || stats.popularHotels.length === 0) && (
              <p className="text-gray-500 text-center py-4">No popular hotels data</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;