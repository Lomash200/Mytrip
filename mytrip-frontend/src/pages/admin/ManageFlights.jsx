import React, { useState } from 'react';
import { Plus, Plane, Trash2 } from 'lucide-react';
import adminApi from '../../api/adminApi';

const ManageFlights = () => {
  // Form State
  const [formData, setFormData] = useState({
    airlineName: '', flightNumber: '', departureCity: '', arrivalCity: '',
    departureTime: '', arrivalTime: '', price: '', totalSeats: 60
  });

  // Dummy List (Baad me API se replace kar denge)
  const [flights, setFlights] = useState([
    { id: 1, airlineName: 'IndiGo', flightNumber: '6E-505', departureCity: 'DEL', arrivalCity: 'BOM', price: 5500 }
  ]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Adding Flight:", formData);
      // API Call (Abhi comment hai)
      // await adminApi.addFlight(formData);
      
      // UI Update (Fake)
      setFlights([...flights, { ...formData, id: Date.now() }]);
      alert("Flight Added Successfully (UI Only)");
      
      // Reset Form
      setFormData({
        airlineName: '', flightNumber: '', departureCity: '', arrivalCity: '',
        departureTime: '', arrivalTime: '', price: '', totalSeats: 60
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Manage Flights</h1>

      {/* Add Flight Form */}
      <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-8">
        <h2 className="text-lg font-semibold mb-4 flex items-center gap-2">
          <Plus className="h-5 w-5 text-blue-600" /> Add New Flight
        </h2>
        <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <input name="airlineName" value={formData.airlineName} onChange={handleChange} placeholder="Airline (e.g. IndiGo)" className="border p-2 rounded" required />
          <input name="flightNumber" value={formData.flightNumber} onChange={handleChange} placeholder="Flight No (e.g. 6E-202)" className="border p-2 rounded" required />
          <input name="departureCity" value={formData.departureCity} onChange={handleChange} placeholder="From (e.g. DEL)" className="border p-2 rounded" required />
          <input name="arrivalCity" value={formData.arrivalCity} onChange={handleChange} placeholder="To (e.g. BOM)" className="border p-2 rounded" required />
          
          <input type="time" name="departureTime" value={formData.departureTime} onChange={handleChange} className="border p-2 rounded" required />
          <input type="time" name="arrivalTime" value={formData.arrivalTime} onChange={handleChange} className="border p-2 rounded" required />
          <input type="number" name="price" value={formData.price} onChange={handleChange} placeholder="Price (₹)" className="border p-2 rounded" required />
          <input type="number" name="totalSeats" value={formData.totalSeats} onChange={handleChange} placeholder="Seats" className="border p-2 rounded" required />
          
          <button type="submit" className="bg-blue-600 text-white font-bold py-2 rounded hover:bg-blue-700 md:col-span-4 mt-2">
            Save Flight
          </button>
        </form>
      </div>

      {/* Flights List Table */}
      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-gray-50 text-gray-600 border-b">
            <tr>
              <th className="p-4">Airline</th>
              <th className="p-4">Route</th>
              <th className="p-4">Price</th>
              <th className="p-4">Action</th>
            </tr>
          </thead>
          <tbody className="divide-y">
            {flights.map((flight) => (
              <tr key={flight.id} className="hover:bg-gray-50">
                <td className="p-4">
                  <div className="font-bold">{flight.airlineName}</div>
                  <div className="text-xs text-gray-500">{flight.flightNumber}</div>
                </td>
                <td className="p-4 font-medium">{flight.departureCity} ➝ {flight.arrivalCity}</td>
                <td className="p-4">₹{flight.price}</td>
                <td className="p-4">
                  <button className="text-red-500 hover:text-red-700 p-2 rounded-full hover:bg-red-50">
                    <Trash2 size={18} />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageFlights;