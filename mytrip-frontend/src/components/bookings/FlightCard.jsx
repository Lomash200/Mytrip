import React from 'react';
import { Plane, Clock, ArrowRight } from 'lucide-react';

const FlightCard = ({ flight, onBook }) => {
  // flight object backend se aayega
  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-4 hover:shadow-md transition">
      <div className="flex flex-col md:flex-row justify-between items-center gap-4">
        
        {/* Airline Info */}
        <div className="flex items-center gap-4 w-full md:w-1/4">
          <div className="bg-blue-50 p-3 rounded-full">
            <Plane className="h-6 w-6 text-blue-600" />
          </div>
          <div>
            <h3 className="font-bold text-gray-800">{flight.airlineName || "Airline"}</h3>
            <p className="text-xs text-gray-500">{flight.flightNumber}</p>
          </div>
        </div>

        {/* Timing & Route */}
        <div className="flex-1 flex justify-center items-center gap-6 w-full">
          <div className="text-center">
            <p className="text-xl font-bold text-gray-800">{flight.departureTime}</p>
            <p className="text-sm text-gray-500">{flight.departureCity}</p>
          </div>

          <div className="flex flex-col items-center">
            <p className="text-xs text-gray-400 mb-1">{flight.duration || "2h 30m"}</p>
            <div className="flex items-center gap-2">
              <div className="h-[1px] w-12 bg-gray-300"></div>
              <Plane className="h-4 w-4 text-gray-400 rotate-90" />
              <div className="h-[1px] w-12 bg-gray-300"></div>
            </div>
            <p className="text-xs text-green-600 font-medium mt-1">Non-stop</p>
          </div>

          <div className="text-center">
            <p className="text-xl font-bold text-gray-800">{flight.arrivalTime}</p>
            <p className="text-sm text-gray-500">{flight.arrivalCity}</p>
          </div>
        </div>

        {/* Price & Action */}
        <div className="w-full md:w-1/4 flex flex-col items-end border-l border-gray-100 pl-6">
          <p className="text-sm text-gray-500 mb-1">Price per adult</p>
          <h2 className="text-2xl font-bold text-gray-900 mb-3">₹{flight.price}</h2>
          <button 
            onClick={() => onBook(flight.id)}
            className="bg-orange-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-orange-600 transition w-full md:w-auto"
          >
            Book Now
          </button>
        </div>

      </div>
    </div>
  );
};

export default FlightCard;