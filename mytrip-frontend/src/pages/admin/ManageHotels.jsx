import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Plus, Trash2, MapPin, Building2, BedDouble } from 'lucide-react';
import adminApi from '../../api/adminApi';

const ManageHotels = () => {
  const navigate = useNavigate();
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);
  
  // Form State
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '', city: '', address: '', description: '', rating: 4.5
  });

  useEffect(() => {
    fetchHotels();
  }, []);

  const fetchHotels = async () => {
    try {
      const data = await adminApi.getAllHotels();
      setHotels(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error("Failed to load hotels");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if(!window.confirm("Are you sure? All rooms will be deleted too.")) return;
    try {
      await adminApi.deleteHotel(id);
      setHotels(hotels.filter(h => h.id !== id));
    } catch (error) {
      alert("Failed to delete hotel");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await adminApi.addHotel(formData);
      setShowForm(false);
      setFormData({ name: '', city: '', address: '', description: '', rating: 4.5 });
      fetchHotels();
      alert("Hotel Added Successfully!");
    } catch (error) {
      alert("Failed to add hotel");
    }
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Manage Hotels</h1>
        <button 
          onClick={() => setShowForm(!showForm)} 
          className="bg-blue-600 text-white px-4 py-2 rounded-lg flex items-center gap-2 hover:bg-blue-700"
        >
          <Plus size={20} /> Add Hotel
        </button>
      </div>

      {/* Add Hotel Form */}
      {showForm && (
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-8">
          <h2 className="font-bold mb-4">New Hotel Details</h2>
          <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input placeholder="Hotel Name" className="border p-2 rounded" required 
              value={formData.name} onChange={e => setFormData({...formData, name: e.target.value})} />
            <input placeholder="City" className="border p-2 rounded" required 
              value={formData.city} onChange={e => setFormData({...formData, city: e.target.value})} />
            <input placeholder="Address" className="border p-2 rounded" required 
              value={formData.address} onChange={e => setFormData({...formData, address: e.target.value})} />
            <input type="number" step="0.1" placeholder="Rating (e.g. 4.5)" className="border p-2 rounded" 
              value={formData.rating} onChange={e => setFormData({...formData, rating: e.target.value})} />
            <textarea placeholder="Description" className="border p-2 rounded md:col-span-2" required 
              value={formData.description} onChange={e => setFormData({...formData, description: e.target.value})} />
            
            <button type="submit" className="bg-green-600 text-white py-2 rounded font-bold hover:bg-green-700">Save Hotel</button>
          </form>
        </div>
      )}

      {/* Hotels List */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {hotels.map((hotel) => (
          <div key={hotel.id} className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden group">
            <div className="h-32 bg-gray-200 relative">
              <img src={hotel.imageUrl || "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=500&q=60"} alt="Hotel" className="w-full h-full object-cover" />
              <div className="absolute top-2 right-2 bg-white px-2 py-1 rounded text-xs font-bold shadow">
                ⭐ {hotel.rating}
              </div>
            </div>
            <div className="p-4">
              <h3 className="font-bold text-lg text-gray-800">{hotel.name}</h3>
              <p className="text-gray-500 text-sm flex items-center gap-1 mb-4">
                <MapPin size={14} /> {hotel.city}
              </p>
              
              <div className="flex gap-2">
                <button 
                  onClick={() => navigate(`/admin/hotels/${hotel.id}/rooms`)}
                  className="flex-1 bg-blue-50 text-blue-600 py-2 rounded-lg text-sm font-semibold flex items-center justify-center gap-2 hover:bg-blue-100"
                >
                  <BedDouble size={16} /> Manage Rooms
                </button>
                <button 
                  onClick={() => handleDelete(hotel.id)}
                  className="bg-red-50 text-red-600 p-2 rounded-lg hover:bg-red-100"
                >
                  <Trash2 size={18} />
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ManageHotels;