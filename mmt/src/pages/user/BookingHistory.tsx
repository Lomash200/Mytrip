import React from 'react';
import { Calendar, MapPin, Users } from 'lucide-react';

const BookingHistory: React.FC = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">My Bookings</h1>
        <p className="text-gray-600">Manage and view your travel bookings</p>
      </div>

      <div className="text-center py-12">
        <Calendar className="w-16 h-16 text-gray-400 mx-auto mb-4" />
        <h2 className="text-xl font-semibold text-gray-900 mb-2">No Bookings Yet</h2>
        <p className="text-gray-600">You haven't made any bookings yet. Start planning your trip!</p>
      </div>
    </div>
  );
};

export default BookingHistory;