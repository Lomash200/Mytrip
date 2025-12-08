import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { MapPin, Star, Wifi, Tv, Wind, CheckCircle } from 'lucide-react';
import Navbar from '../components/common/Navbar';
import hotelApi from '../api/hotelApi';

const HotelDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [hotel, setHotel] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const hotelData = await hotelApi.getHotelById(id);
        setHotel(hotelData);
        
        const roomsData = await hotelApi.getHotelRooms(id);
        setRooms(Array.isArray(roomsData) ? roomsData : []);
      } catch (err) {
        console.error("Error:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  if (loading) return <div className="text-center py-20">Loading Hotel Details...</div>;
  if (!hotel) return <div className="text-center py-20">Hotel Not Found</div>;

  return (
    <>
      <Navbar />
      
      {/* Hotel Header Image */}
      <div className="h-[400px] w-full relative">
        <img 
          src={hotel.imageUrl || "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1920&q=80"} 
          alt={hotel.name} 
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-black/40 flex items-end">
          <div className="max-w-7xl mx-auto w-full px-4 pb-10 text-white">
            <h1 className="text-4xl font-bold mb-2">{hotel.name}</h1>
            <p className="flex items-center gap-2 text-lg opacity-90">
              <MapPin size={20} /> {hotel.address}, {hotel.city}
            </p>
          </div>
        </div>
      </div>

      <div className="bg-gray-50 py-10 px-4">
        <div className="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
          
          {/* Left: Info & Rooms */}
          <div className="md:col-span-2 space-y-8">
            
            {/* Description */}
            <div className="bg-white p-6 rounded-xl shadow-sm">
              <h2 className="text-xl font-bold mb-4">About this property</h2>
              <p className="text-gray-600 leading-relaxed">
                {hotel.description || "Experience luxury and comfort at its finest. Located in the heart of the city, we offer world-class amenities and breathtaking views."}
              </p>
              
              <div className="mt-6 flex gap-6">
                <div className="flex items-center gap-2 text-gray-600"><Wifi size={20} className="text-blue-500"/> Free Wifi</div>
                <div className="flex items-center gap-2 text-gray-600"><Wind size={20} className="text-blue-500"/> AC</div>
                <div className="flex items-center gap-2 text-gray-600"><Tv size={20} className="text-blue-500"/> TV</div>
              </div>
            </div>

            {/* Rooms List */}
            <div>
              <h2 className="text-xl font-bold mb-4">Available Rooms</h2>
              <div className="space-y-4">
                {rooms.length > 0 ? rooms.map((room) => (
                  <div key={room.id} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex flex-col md:flex-row gap-6">
                    <img 
                      src={room.imageUrl || "https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=500&q=80"} 
                      alt={room.roomType} 
                      className="w-full md:w-48 h-32 object-cover rounded-lg"
                    />
                    <div className="flex-1">
                      <div className="flex justify-between items-start">
                        <div>
                          <h3 className="text-lg font-bold text-gray-800">{room.roomType}</h3>
                          <ul className="mt-2 space-y-1">
                            <li className="text-sm text-gray-500 flex items-center gap-2"><CheckCircle size={14} className="text-green-500"/> King Size Bed</li>
                            <li className="text-sm text-gray-500 flex items-center gap-2"><CheckCircle size={14} className="text-green-500"/> City View</li>
                          </ul>
                        </div>
                        <div className="text-right">
                          <p className="text-2xl font-bold text-gray-900">₹{room.pricePerNight}</p>
                          <p className="text-xs text-gray-500">per night</p>
                        </div>
                      </div>
                      
                      <div className="mt-4 flex justify-end">
                        <button 
                          onClick={() => alert("Room Booking Flow is similar to Flights (Implement later)")}
                          className="bg-orange-500 text-white px-6 py-2 rounded-lg font-bold hover:bg-orange-600 transition"
                        >
                          Select Room
                        </button>
                      </div>
                    </div>
                  </div>
                )) : (
                  <div className="bg-white p-6 rounded text-center text-gray-500">No rooms available right now.</div>
                )}
              </div>
            </div>

          </div>

          {/* Right: Reviews / Map Placeholder */}
          <div className="md:col-span-1">
             <div className="bg-white p-6 rounded-xl shadow-sm sticky top-24">
               <h3 className="font-bold text-lg mb-4 flex items-center gap-2">
                 <Star className="text-yellow-400 fill-yellow-400" /> 4.5/5 Rating
               </h3>
               <p className="text-gray-500 text-sm mb-4">Based on 120+ user reviews.</p>
               {/* <button className="w-full border border-blue-600 text-blue-600 py-2 rounded font-semibold hover:bg-blue-50 transition">
                 Read Reviews
               </button> */}
               <button 
                  onClick={() => navigate(`/book/hotel/${room.id}?hotelId=${hotel.id}&price=${room.pricePerNight}&checkIn=2024-01-01&checkOut=2024-01-02`)}
                 className="bg-orange-500 text-white px-6 py-2 rounded-lg font-bold hover:bg-orange-600 transition"
>
                   Select Room
                    </button>
             </div>
          </div>

        </div>
      </div>
    </>
  );
};

export default HotelDetails;