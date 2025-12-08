import React, { useEffect, useState } from 'react';
import { Eye, CheckCircle, XCircle, Clock } from 'lucide-react';
import adminApi from '../../api/adminApi';

const AdminBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // API Call Simulate kar rahe hain
    const fetchBookings = async () => {
      try {
        const data = await adminApi.getAllBookings();
        setBookings(Array.isArray(data) ? data : []);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchBookings();
  }, []);

  const getStatusBadge = (status) => {
    switch (status) {
      case 'CONFIRMED': return <span className="text-green-600 bg-green-100 px-2 py-1 rounded-full text-xs">Confirmed</span>;
      case 'CANCELLED': return <span className="text-red-600 bg-red-100 px-2 py-1 rounded-full text-xs">Cancelled</span>;
      default: return <span className="text-yellow-600 bg-yellow-100 px-2 py-1 rounded-full text-xs">Pending</span>;
    }
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">All Bookings</h1>

      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-x-auto">
        <table className="w-full text-left min-w-[600px]">
          <thead className="bg-gray-50 text-gray-600 border-b">
            <tr>
              <th className="p-4">Booking ID</th>
              <th className="p-4">User</th>
              <th className="p-4">Flight</th>
              <th className="p-4">Date</th>
              <th className="p-4">Amount</th>
              <th className="p-4">Status</th>
              <th className="p-4">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y">
            {bookings.length > 0 ? bookings.map((booking) => (
              <tr key={booking.id} className="hover:bg-gray-50">
                <td className="p-4 font-mono text-blue-600">#{booking.id}</td>
                <td className="p-4">
                  <div className="font-medium">{booking.contactEmail || "User"}</div>
                </td>
                <td className="p-4 font-medium">{booking.departureCity} ➝ {booking.arrivalCity}</td>
                <td className="p-4 text-sm text-gray-500">{booking.flightDate}</td>
                <td className="p-4 font-bold">₹{booking.totalAmount}</td>
                <td className="p-4">{getStatusBadge(booking.status)}</td>
                <td className="p-4">
                  <button className="text-gray-500 hover:text-blue-600">
                    <Eye size={20} />
                  </button>
                </td>
              </tr>
            )) : (
              <tr>
                <td colSpan="7" className="p-8 text-center text-gray-500">No bookings found</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminBookings;