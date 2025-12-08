import React, { useEffect, useState } from 'react';
import { Plane, Calendar, CheckCircle, XCircle, FileText, Clock } from 'lucide-react';
import Navbar from '../components/common/Navbar';
import bookingApi from '../api/bookingApi';
import { Link } from 'react-router-dom';

const MyBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const data = await bookingApi.getMyBookings();
        // Check kar rahe hain ki data array hai ya nahi
        setBookings(Array.isArray(data) ? data : []);
      } catch (error) {
        console.error("Failed to fetch bookings:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

  // Status Badge Helper
  const getStatusBadge = (status) => {
    switch (status?.toUpperCase()) {
      case 'CONFIRMED':
        return <span className="flex items-center gap-1 text-green-600 bg-green-50 px-3 py-1 rounded-full text-sm font-medium"><CheckCircle size={14} /> Confirmed</span>;
      case 'CANCELLED':
        return <span className="flex items-center gap-1 text-red-600 bg-red-50 px-3 py-1 rounded-full text-sm font-medium"><XCircle size={14} /> Cancelled</span>;
      default:
        return <span className="flex items-center gap-1 text-yellow-600 bg-yellow-50 px-3 py-1 rounded-full text-sm font-medium"><Clock size={14} /> Pending</span>;
    }
  };

  return (
    <>
      <Navbar />
      <div className="bg-gray-50 min-h-screen py-8 px-4">
        <div className="max-w-4xl mx-auto">
          
          <h1 className="text-2xl font-bold text-gray-800 mb-6">My Bookings</h1>

          {/* Loading State */}
          {loading && <div className="text-center py-10 text-gray-500">Loading your trips...</div>}

          {/* Empty State */}
          {!loading && bookings.length === 0 && (
            <div className="bg-white p-10 rounded-xl shadow-sm text-center">
              <Plane className="h-16 w-16 text-gray-300 mx-auto mb-4" />
              <h3 className="text-lg font-semibold text-gray-700">No bookings yet</h3>
              <p className="text-gray-500 mb-6">Looks like you haven't planned a trip yet.</p>
              <Link to="/" className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700">
                Book a Flight
              </Link>
            </div>
          )}

          {/* Bookings List */}
          <div className="space-y-4">
            {bookings.map((booking) => (
              <div key={booking.id} className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition">
                <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
                  
                  {/* Flight Icon & ID */}
                  <div className="flex items-center gap-4">
                    <div className="bg-blue-100 p-3 rounded-full">
                      <Plane className="h-6 w-6 text-blue-600" />
                    </div>
                    <div>
                      <p className="text-xs text-gray-500 font-medium">Booking ID: #{booking.id}</p>
                      <h3 className="font-bold text-gray-800 text-lg">
                        {booking.departureCity || 'DEL'} ➝ {booking.arrivalCity || 'BOM'}
                      </h3>
                    </div>
                  </div>

                  {/* Date & Amount */}
                  <div className="flex flex-col md:items-end">
                    <div className="flex items-center gap-2 text-gray-600 mb-1">
                      <Calendar size={16} />
                      <span className="text-sm">{booking.flightDate || '12 Oct, 2024'}</span>
                    </div>
                    <p className="font-bold text-gray-900">₹{booking.totalAmount || '5,500'}</p>
                  </div>

                  {/* Status & Actions */}
                  <div className="flex items-center gap-4 w-full md:w-auto justify-between md:justify-end border-t md:border-t-0 pt-4 md:pt-0 mt-2 md:mt-0">
                    {getStatusBadge(booking.status)}
                    
                    {/* Invoice Button */}
                    <button className="flex items-center gap-1 text-blue-600 hover:text-blue-800 text-sm font-medium">
                      <FileText size={16} /> Invoice
                    </button>
                  </div>

                </div>
              </div>
            ))}
          </div>

        </div>
      </div>
    </>
  );
};

export default MyBookings;