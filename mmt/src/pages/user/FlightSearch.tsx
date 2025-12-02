import React, { useState } from 'react';
import { Search, Plane, MapPin } from 'lucide-react';

const FlightSearch: React.FC = () => {
  const [filters, setFilters] = useState({
    from: '',
    to: '',
    departure: '',
    return: '',
    passengers: 1,
    class: 'ECONOMY'
  });

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Search Header */}
      <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
        <h1 className="text-2xl font-bold text-gray-900 mb-6">Find Your Flight</h1>
        
        <div className="grid grid-cols-1 md:grid-cols-6 gap-4 mb-4">
          <input
            type="text"
            placeholder="From"
            value={filters.from}
            onChange={(e) => setFilters({...filters, from: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="text"
            placeholder="To"
            value={filters.to}
            onChange={(e) => setFilters({...filters, to: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="date"
            value={filters.departure}
            onChange={(e) => setFilters({...filters, departure: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="date"
            value={filters.return}
            onChange={(e) => setFilters({...filters, return: e.target.value})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          />
          <select
            value={filters.passengers}
            onChange={(e) => setFilters({...filters, passengers: parseInt(e.target.value)})}
            className="px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          >
            {[1,2,3,4,5,6].map(num => (
              <option key={num} value={num}>{num} Passenger{num > 1 ? 's' : ''}</option>
            ))}
          </select>
          <button className="bg-green-600 text-white px-6 py-3 rounded-lg font-semibold flex items-center justify-center hover:bg-green-700">
            <Search className="w-5 h-5 mr-2" />
            Search
          </button>
        </div>
      </div>

      {/* Results Placeholder */}
      <div className="text-center py-12">
        <Plane className="w-16 h-16 text-gray-400 mx-auto mb-4" />
        <h2 className="text-xl font-semibold text-gray-900 mb-2">Search for Flights</h2>
        <p className="text-gray-600">Enter your departure and destination to find available flights</p>
      </div>
    </div>
  );
};

export default FlightSearch;