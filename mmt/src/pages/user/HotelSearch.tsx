import React, { useState } from 'react';
import { Search, MapPin, Star } from 'lucide-react';

const HotelSearch: React.FC = () => {
  const [filters, setFilters] = useState({
    location: '',
    checkIn: '',
    checkOut: '',
    guests: 1
  });

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Search Header */}
      <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
        <h1 className="text-2xl font-bold text-gray-900 mb-6">Find Your Perfect Stay</h1>
        
        <div className="grid grid-cols-1 md:grid-cols-5 gap-4 mb-4">
          <input
            type="text"
            placeholder="Where are you going?"
            value={filters.location}
            onChange={(e) => setFilters({...filters, location: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="date"
            value={filters.checkIn}
            onChange={(e) => setFilters({...filters, checkIn: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="date"
            value={filters.checkOut}
            onChange={(e) => setFilters({...filters, checkOut: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <select
            value={filters.guests}
            onChange={(e) => setFilters({...filters, guests: parseInt(e.target.value)})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          >
            {[1,2,3,4,5,6].map(num => (
              <option key={num} value={num}>{num} Guest{num > 1 ? 's' : ''}</option>
            ))}
          </select>
          <button className="bg-blue-600 text-white px-6 py-3 rounded-lg font-semibold flex items-center justify-center hover:bg-blue-700">
            <Search className="w-5 h-5 mr-2" />
            Search
          </button>
        </div>
      </div>

      {/* Results Placeholder */}
      <div className="text-center py-12">
        <Search className="w-16 h-16 text-gray-400 mx-auto mb-4" />
        <h2 className="text-xl font-semibold text-gray-900 mb-2">Search for Hotels</h2>
        <p className="text-gray-600">Enter your destination and dates to find available hotels</p>
      </div>
    </div>
  );
};

export default HotelSearch;