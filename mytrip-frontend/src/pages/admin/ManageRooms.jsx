import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Plus, Trash2, ArrowLeft, Bed } from 'lucide-react';
import adminApi from '../../api/adminApi';

const ManageRooms = () => {
  const { hotelId } = useParams();
  const navigate = useNavigate();
  const [rooms, setRooms] = useState([]);
  const [showForm, setShowForm] = useState(false);
  
  const [formData, setFormData] = useState({
    roomType: 'Deluxe', pricePerNight: '', capacity: 2, availabilityCount: 5
  });

  useEffect(() => {
    fetchRooms();
  }, [hotelId]);

  const fetchRooms = async () => {
    try {
      const data = await adminApi.getRoomsByHotel(hotelId);
      setRooms(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error("Failed to fetch rooms");
    }
  };

  const handleAddRoom = async (e) => {
    e.preventDefault();
    try {
      await adminApi.addRoom(hotelId, formData);
      setShowForm(false);
      setFormData({ roomType: 'Deluxe', pricePerNight: '', capacity: 2, availabilityCount: 5 });
      fetchRooms();
      alert("Room Added!");
    } catch (error) {
      alert("Failed to add room");
    }
  };

  const handleDelete = async (roomId) => {
    if(!confirm("Delete this room?")) return;
    try {
      await adminApi.deleteRoom(hotelId, roomId);
      fetchRooms();
    } catch (error) {
      alert("Delete failed");
    }
  };

  return (
    <div>
      <button onClick={() => navigate('/admin/hotels')} className="flex items-center gap-2 text-gray-500 hover:text-gray-800 mb-4">
        <ArrowLeft size={18} /> Back to Hotels
      </button>

      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Manage Rooms (Hotel ID: {hotelId})</h1>
        <button 
          onClick={() => setShowForm(!showForm)} 
          className="bg-blue-600 text-white px-4 py-2 rounded-lg flex items-center gap-2 hover:bg-blue-700"
        >
          <Plus size={20} /> Add Room
        </button>
      </div>

      {/* Add Room Form */}
      {showForm && (
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-8">
          <form onSubmit={handleAddRoom} className="grid grid-cols-1 md:grid-cols-5 gap-4 items-end">
            <div>
              <label className="text-xs font-bold text-gray-500">Type</label>
              <select className="w-full border p-2 rounded mt-1" value={formData.roomType} onChange={e => setFormData({...formData, roomType: e.target.value})}>
                <option>Standard</option>
                <option>Deluxe</option>
                <option>Suite</option>
                <option>Luxury</option>
              </select>
            </div>
            <div>
              <label className="text-xs font-bold text-gray-500">Price (₹)</label>
              <input type="number" className="w-full border p-2 rounded mt-1" required 
                value={formData.pricePerNight} onChange={e => setFormData({...formData, pricePerNight: e.target.value})} />
            </div>
            <div>
              <label className="text-xs font-bold text-gray-500">Capacity</label>
              <input type="number" className="w-full border p-2 rounded mt-1" required 
                value={formData.capacity} onChange={e => setFormData({...formData, capacity: e.target.value})} />
            </div>
            <div>
              <label className="text-xs font-bold text-gray-500">Total Rooms</label>
              <input type="number" className="w-full border p-2 rounded mt-1" required 
                value={formData.availabilityCount} onChange={e => setFormData({...formData, availabilityCount: e.target.value})} />
            </div>
            <button type="submit" className="bg-green-600 text-white font-bold py-2.5 rounded hover:bg-green-700">Save</button>
          </form>
        </div>
      )}

      {/* Rooms List */}
      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-gray-50 text-gray-600 border-b">
            <tr>
              <th className="p-4">Room Type</th>
              <th className="p-4">Price / Night</th>
              <th className="p-4">Capacity</th>
              <th className="p-4">Total Available</th>
              <th className="p-4">Action</th>
            </tr>
          </thead>
          <tbody className="divide-y">
            {rooms.length > 0 ? rooms.map((room) => (
              <tr key={room.id} className="hover:bg-gray-50">
                <td className="p-4 font-bold flex items-center gap-2">
                  <Bed size={18} className="text-gray-400"/> {room.roomType}
                </td>
                <td className="p-4">₹{room.pricePerNight}</td>
                <td className="p-4">{room.capacity} Guests</td>
                <td className="p-4">{room.availabilityCount}</td>
                <td className="p-4">
                  <button onClick={() => handleDelete(room.id)} className="text-red-500 hover:bg-red-50 p-2 rounded-full">
                    <Trash2 size={18} />
                  </button>
                </td>
              </tr>
            )) : (
              <tr><td colSpan="5" className="p-8 text-center text-gray-500">No rooms added yet.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageRooms;