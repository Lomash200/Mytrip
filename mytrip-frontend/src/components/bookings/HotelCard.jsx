import React from 'react';
import { MapPin, Star, Wifi, Coffee } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const HotelCard = ({ hotel }) => {
  const navigate = useNavigate();

  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden hover:shadow-lg transition flex flex-col md:flex-row h-full md:h-64">
      
      {/* Image Section */}
      <div className="md:w-1/3 relative">
        <img 
          src={hotel.imageUrl || "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80"} 
          alt={hotel.name} 
          className="h-full w-full object-cover"
        />
        <div className="absolute top-4 left-4 bg-white/90 backdrop-blur-sm px-2 py-1 rounded text-xs font-bold text-gray-800 flex items-center gap-1">
          <Star size={12} className="text-orange-400 fill-orange-400" />
          {hotel.rating || "4.5"}
        </div>
      </div>

      {/* Details Section */}
      <div className="p-6 flex-1 flex flex-col justify-between">
        <div>
          <h3 className="text-xl font-bold text-gray-800 mb-1">{hotel.name}</h3>
          <p className="text-gray-500 text-sm flex items-center gap-1 mb-4">
            <MapPin size={14} /> {hotel.city}, {hotel.address || "India"}
          </p>

          {/* Amenities (Hardcoded for visuals) */}
          <div className="flex gap-3 text-xs text-gray-500 mb-4">
            <span className="flex items-center gap-1 bg-gray-50 px-2 py-1 rounded"><Wifi size={12}/> Free Wifi</span>
            <span className="flex items-center gap-1 bg-gray-50 px-2 py-1 rounded"><Coffee size={12}/> Breakfast</span>
            <span className="bg-gray-50 px-2 py-1 rounded">+3 more</span>
          </div>
        </div>

        {/* Price & Action */}
        <div className="flex justify-between items-end border-t pt-4">
          <div>
            <p className="text-xs text-gray-400 line-through">₹{Math.round(hotel.pricePerNight * 1.2)}</p>
            <p className="text-2xl font-bold text-gray-900">₹{hotel.pricePerNight || 2000}</p>
            <p className="text-xs text-gray-500">per night + taxes</p>
          </div>
          <button 
            onClick={() => navigate(`/hotels/${hotel.id}`)}
            className="bg-blue-600 text-white px-6 py-2.5 rounded-lg font-semibold hover:bg-blue-700 transition"
          >
            View Details
          </button>
        </div>
      </div>
    </div>
  );
};

export default HotelCard;