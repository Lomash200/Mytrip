import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import HotelCard from '../components/bookings/HotelCard';
import searchApi from '../api/searchApi';
import { Loader2, Filter } from 'lucide-react';
// ❌ Navbar import hata diya

const Hotels = () => {
  const [searchParams] = useSearchParams();
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);

  const city = searchParams.get('city') || 'Goa';

  useEffect(() => {
    const fetchHotels = async () => {
      setLoading(true);
      try {
        const data = await searchApi.searchHotels({ city: city });
        setHotels(Array.isArray(data) ? data : []);
      } catch (error) {
        console.error("Error fetching hotels:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchHotels();
  }, [city]);

  return (
    <>
      {/* ❌ Navbar hata diya */}
      
      <div className="bg-gray-50 min-h-screen py-8 px-4">
        <div className="max-w-6xl mx-auto flex gap-8">
          
          {/* Filters Sidebar */}
          <div className="hidden md:block w-1/4 bg-white p-6 rounded-xl shadow-sm h-fit sticky top-24">
            <div className="flex justify-between items-center mb-6">
              <h3 className="font-bold text-gray-800">Filters</h3>
              <Filter size={18} className="text-gray-400" />
            </div>
            
            <div className="space-y-6">
              <div>
                <h4 className="font-semibold text-sm mb-3">Price Range</h4>
                <input type="range" className="w-full accent-blue-600" />
                <div className="flex justify-between text-xs text-gray-500 mt-1">
                  <span>₹1000</span>
                  <span>₹20000+</span>
                </div>
              </div>
              
              <div>
                <h4 className="font-semibold text-sm mb-3">Star Rating</h4>
                {[5, 4, 3].map(star => (
                  <label key={star} className="flex items-center gap-2 mb-2 cursor-pointer">
                    <input type="checkbox" className="rounded text-blue-600 focus:ring-blue-500" />
                    <span className="text-sm text-gray-600">{star} Stars</span>
                  </label>
                ))}
              </div>
            </div>
          </div>

          {/* Hotels List */}
          <div className="flex-1">
            <h1 className="text-2xl font-bold text-gray-800 mb-2">Hotels in {city}</h1>
            <p className="text-gray-500 mb-6">{hotels.length} properties found</p>

            {loading ? (
              <div className="flex justify-center py-20">
                <Loader2 className="h-10 w-10 text-blue-600 animate-spin" />
              </div>
            ) : hotels.length > 0 ? (
              <div className="space-y-6">
                {hotels.map((hotel) => (
                  <HotelCard key={hotel.id} hotel={hotel} />
                ))}
              </div>
            ) : (
              <div className="bg-white p-10 rounded-xl text-center shadow-sm">
                <h3 className="text-lg font-bold">No hotels found in {city}</h3>
                <p className="text-gray-500">Try searching for 'Mumbai', 'Delhi' or 'Goa'</p>
              </div>
            )}
          </div>

        </div>
      </div>
    </>
  );
};

export default Hotels;