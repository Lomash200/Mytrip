import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import Navbar from '../components/common/Navbar';
import bookingApi from '../api/bookingApi';
import { Plane, User, CreditCard } from 'lucide-react';

const BookFlight = () => {
  const { flightId } = useParams();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [loading, setLoading] = useState(false);

  // Note: Real app me hum yaha useEffect se Flight Details fetch karenge
  // Abhi ke liye hum hardcode price maan lete hain testing ke liye
  const flightPrice = 5500; 
  const tax = 500;
  const totalAmount = flightPrice + tax;

  const onSubmit = async (data) => {
    setLoading(true);
    try {
      // Data ko backend format me convert karo
      const bookingPayload = {
        flightId: flightId,
        passengers: [
          {
            firstName: data.firstName,
            lastName: data.lastName,
            age: data.age,
            gender: data.gender
          }
        ],
        contactEmail: data.email,
        contactPhone: data.phone
      };

      console.log("Sending Booking Request:", bookingPayload);
      
      const response = await bookingApi.createBooking(bookingPayload);
      
      // Success! Payment page ya Success page par bhejo
      alert("Booking Created! Booking ID: " + (response.id || "123"));
      navigate('/my-bookings'); // Ya payment page par
      
    } catch (error) {
      console.error("Booking Failed:", error);
      alert("Booking failed! Check console.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Navbar />
      <div className="bg-gray-100 min-h-screen py-8 px-4">
        <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
          
          {/* LEFT SIDE: Passenger Form */}
          <div className="md:col-span-2 space-y-6">
            
            {/* Traveler Details Card */}
            <div className="bg-white p-6 rounded-xl shadow-sm">
              <h2 className="text-xl font-bold flex items-center gap-2 mb-4">
                <User className="text-blue-600" /> Traveler Details
              </h2>
              
              <form id="booking-form" onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="text-sm font-semibold text-gray-600">First Name</label>
                    <input {...register("firstName", { required: true })} className="w-full border p-2 rounded mt-1 outline-blue-500" placeholder="Enter first name" />
                    {errors.firstName && <span className="text-red-500 text-xs">Required</span>}
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-600">Last Name</label>
                    <input {...register("lastName", { required: true })} className="w-full border p-2 rounded mt-1 outline-blue-500" placeholder="Enter last name" />
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="text-sm font-semibold text-gray-600">Age</label>
                    <input type="number" {...register("age", { required: true })} className="w-full border p-2 rounded mt-1 outline-blue-500" placeholder="25" />
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-600">Gender</label>
                    <select {...register("gender")} className="w-full border p-2 rounded mt-1 outline-blue-500">
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                    </select>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="text-sm font-semibold text-gray-600">Email</label>
                    <input type="email" {...register("email", { required: true })} className="w-full border p-2 rounded mt-1 outline-blue-500" placeholder="ticket@example.com" />
                  </div>
                  <div>
                    <label className="text-sm font-semibold text-gray-600">Phone</label>
                    <input {...register("phone", { required: true })} className="w-full border p-2 rounded mt-1 outline-blue-500" placeholder="9876543210" />
                  </div>
                </div>
              </form>
            </div>

            {/* Payment Method Dummy */}
            <div className="bg-white p-6 rounded-xl shadow-sm opacity-70">
              <h2 className="text-xl font-bold flex items-center gap-2 mb-4">
                <CreditCard className="text-gray-600" /> Payment Method
              </h2>
              <p className="text-sm text-gray-500">Payment gateway will open after clicking "Proceed to Pay"</p>
            </div>
          </div>

          {/* RIGHT SIDE: Fare Summary */}
          <div className="md:col-span-1">
            <div className="bg-white p-6 rounded-xl shadow-lg sticky top-24">
              <h3 className="text-lg font-bold text-gray-800 mb-4 border-b pb-2">Fare Summary</h3>
              
              <div className="flex justify-between mb-2">
                <span className="text-gray-600">Base Fare</span>
                <span className="font-medium">₹{flightPrice}</span>
              </div>
              <div className="flex justify-between mb-2">
                <span className="text-gray-600">Taxes & Fees</span>
                <span className="font-medium">₹{tax}</span>
              </div>
              <div className="border-t my-4"></div>
              <div className="flex justify-between mb-6">
                <span className="text-xl font-bold text-gray-900">Total</span>
                <span className="text-xl font-bold text-blue-600">₹{totalAmount}</span>
              </div>

              <button 
                form="booking-form" 
                type="submit" 
                disabled={loading}
                className="w-full bg-orange-500 text-white font-bold py-3 rounded-lg hover:bg-orange-600 transition shadow-md"
              >
                {loading ? 'Processing...' : 'Proceed to Pay'}
              </button>
            </div>
          </div>

        </div>
      </div>
    </>
  );
};

export default BookFlight;