import React from 'react';
import { Calendar, MapPin, Users, IndianRupee } from 'lucide-react';
import { formatCurrency, formatDate } from '../../utils/helpers';

interface BookingCardProps {
  booking: {
    id: number;
    hotelName: string;
    location: string;
    checkIn: string;
    checkOut: string;
    guests: number;
    totalAmount: number;
    status: string;
  };
}

const BookingCard: React.FC<BookingCardProps> = ({ booking }) => {
  const getStatusColor = (status: string) => {
    switch (status) {
      case 'CONFIRMED': return 'bg-green-100 text-green-800';
      case 'PENDING': return 'bg-yellow-100 text-yellow-800';
      case 'CANCELLED': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-lg font-semibold text-gray-900">{booking.hotelName}</h3>
        <span className={`px-2 py-1 rounded-full text-xs font-medium ${getStatusColor(booking.status)}`}>
          {booking.status}
        </span>
      </div>

      <div className="flex items-center text-gray-600 mb-3">
        <MapPin className="w-4 h-4 mr-1" />
        <span className="text-sm">{booking.location}</span>
      </div>

      <div className="grid grid-cols-2 gap-4 mb-4">
        <div className="flex items-center text-gray-600">
          <Calendar className="w-4 h-4 mr-2" />
          <div>
            <p className="text-xs text-gray-500">Check-in</p>
            <p className="text-sm font-medium">{formatDate(booking.checkIn)}</p>
          </div>
        </div>
        <div className="flex items-center text-gray-600">
          <Calendar className="w-4 h-4 mr-2" />
          <div>
            <p className="text-xs text-gray-500">Check-out</p>
            <p className="text-sm font-medium">{formatDate(booking.checkOut)}</p>
          </div>
        </div>
      </div>

      <div className="flex justify-between items-center">
        <div className="flex items-center text-gray-600">
          <Users className="w-4 h-4 mr-1" />
          <span className="text-sm">{booking.guests} guests</span>
        </div>
        <div className="flex items-center text-gray-900">
          <IndianRupee className="w-4 h-4 mr-1" />
          <span className="font-semibold">{formatCurrency(booking.totalAmount)}</span>
        </div>
      </div>
    </div>
  );
};

export default BookingCard;