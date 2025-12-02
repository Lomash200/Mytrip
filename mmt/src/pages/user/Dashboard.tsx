import React from 'react';
import { Link } from 'react-router-dom';
import { Hotel, Plane, BookOpen, User } from 'lucide-react';

const UserDashboard: React.FC = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Welcome to MyTrip!</h1>
        <p className="text-gray-600 mb-6">Discover amazing places and book your next adventure</p>
        
        <div className="flex space-x-4 mb-6">
          <Link to="/search/hotels" className="px-6 py-3 bg-blue-600 text-white rounded-lg font-semibold flex items-center hover:bg-blue-700">
            <Hotel className="w-5 h-5 mr-2" />
            Find Hotels
          </Link>
          <Link to="/search/flights" className="px-6 py-3 bg-green-600 text-white rounded-lg font-semibold flex items-center hover:bg-green-700">
            <Plane className="w-5 h-5 mr-2" />
            Find Flights
          </Link>
        </div>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
        <Link to="/search/hotels" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg">
          <Hotel className="w-8 h-8 text-blue-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Hotels</h3>
          <p className="text-sm text-gray-600">Book your stay</p>
        </Link>

        <Link to="/search/flights" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg">
          <Plane className="w-8 h-8 text-green-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Flights</h3>
          <p className="text-sm text-gray-600">Find best deals</p>
        </Link>

        <Link to="/my-bookings" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg">
          <BookOpen className="w-8 h-8 text-purple-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">My Bookings</h3>
          <p className="text-sm text-gray-600">Manage trips</p>
        </Link>

        <Link to="/profile" className="bg-white p-6 rounded-xl shadow-md text-center hover:shadow-lg">
          <User className="w-8 h-8 text-orange-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Profile</h3>
          <p className="text-sm text-gray-600">Account settings</p>
        </Link>
      </div>
    </div>
  );
};

export default UserDashboard;