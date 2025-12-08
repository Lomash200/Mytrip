import React, { useState } from 'react';
import { useParams, useNavigate, useSearchParams } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { Building2, Calendar, User, CreditCard } from 'lucide-react';
import bookingApi from '../api/bookingApi';

const BookHotel = () => {
  const { roomId } = useParams();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [loading, setLoading] = useState(false);

  // URL parameters se data nikalo (jo HotelDetails se aayega)
  const hotelId = searchParams.get('hotelId');
  const price = searchParams.get('price');
  const checkIn = searchParams.get('checkIn') || '2024-01-01';
  const checkOut = searchParams.get('checkOut') || '2024-01-02';

  // Calculate Total
  const days = Math.max(1, (new Date(checkOut) - new Date(checkIn)) / (1000 * 60 * 60 * 24));
  const totalAmount = (parseInt(price) * days) + 500; // + Taxes

  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const bookingPayload = {
        hotelId: hotelId,
        roomId: roomId,
        checkInDate: checkIn,
        checkOutDate: checkOut,
        guests: data.guests,
        // Backend expects 'userId' handling via token usually
      };

      await bookingApi.createBooking(bookingPayload);
      alert("Hotel Booking Successful!");
      navigate('/my-bookings');
    } catch (error) {
      alert("Booking Failed: " + (error.response?.data?.message || "Unknown error"));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-gray-100 min-h-screen py-8 px-4">
      <div className="max-w-4xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
        
        {/* Left: Form */}
        <div className="md:col-span-2 bg-white p-6 rounded-xl shadow-sm">
          <h2 className="text-xl font-bold mb-6 flex items-center gap-2">
            <User className="text-blue-600"/> Guest Details
          </h2>
          
          <form id="hotel-form" onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="text-xs font-bold text-gray-500">First Name</label>
                <input {...register("firstName", {required: true})} className="w-full border p-2 rounded mt-1" />
              </div>
              <div>
                <label className="text-xs font-bold text-gray-500">Last Name</label>
                <input {...register("lastName", {required: true})} className="w-full border p-2 rounded mt-1" />
              </div>
            </div>
            
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="text-xs font-bold text-gray-500">Guests</label>
                <input type="number" {...register("guests", {required: true, min: 1})} className="w-full border p-2 rounded mt-1" defaultValue={2} />
              </div>
              <div>
                <label className="text-xs font-bold text-gray-500">Phone</label>
                <input {...register("phone", {required: true})} className="w-full border p-2 rounded mt-1" />
              </div>
            </div>
          </form>
        </div>

        {/* Right: Summary */}
        <div className="bg-white p-6 rounded-xl shadow-sm h-fit">
          <h3 className="font-bold text-gray-800 border-b pb-2 mb-4">Booking Summary</h3>
          
          <div className="space-y-3 text-sm text-gray-600">
            <div className="flex justify-between">
              <span>Check-In</span>
              <span className="font-medium text-gray-900">{checkIn}</span>
            </div>
            <div className="flex justify-between">
              <span>Check-Out</span>
              <span className="font-medium text-gray-900">{checkOut}</span>
            </div>
            <div className="flex justify-between">
              <span>Nights</span>
              <span className="font-medium text-gray-900">{days}</span>
            </div>
            <div className="flex justify-between">
              <span>Room Price</span>
              <span className="font-medium text-gray-900">₹{price} x {days}</span>
            </div>
            <div className="flex justify-between text-green-600">
              <span>Taxes & Fees</span>
              <span>₹500</span>
            </div>
            <div className="border-t pt-3 flex justify-between text-lg font-bold text-blue-600">
              <span>Total</span>
              <span>₹{totalAmount}</span>
            </div>
          </div>

          <button 
            form="hotel-form" 
            type="submit" 
            disabled={loading}
            className="w-full bg-orange-500 text-white font-bold py-3 rounded-lg mt-6 hover:bg-orange-600"
          >
            {loading ? 'Booking...' : 'Pay & Book'}
          </button>
        </div>

      </div>
    </div>
  );
};

export default BookHotel;