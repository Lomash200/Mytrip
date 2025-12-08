import React, { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import FlightCard from '../components/bookings/FlightCard';
import searchApi from '../api/searchApi';
import { Loader2 } from 'lucide-react';
// ❌ Navbar import hata diya

const Flights = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // URL se data nikalo
  const from = searchParams.get('from');
  const to = searchParams.get('to');
  const date = searchParams.get('date');

  useEffect(() => {
    const fetchFlights = async () => {
      setLoading(true);
      setError(null);
      try {
        // Backend API ko data bhejo
        const data = await searchApi.searchFlights({
          departureCity: from,
          arrivalCity: to,
          departureDate: date
        });
        
        // Agar data array hai to set karo, nahi to empty array
        setFlights(Array.isArray(data) ? data : []);
      } catch (err) {
        console.error("Error fetching flights:", err);
        setError("Failed to load flights. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    if (from && to) {
      fetchFlights();
    }
  }, [from, to, date]);

  const handleBook = (flightId) => {
    navigate(`/book/flight/${flightId}`);
  };

  return (
    <>
      {/* ❌ Navbar hata diya, ab UserLayout se aayega */}
      
      <div className="bg-gray-50 min-h-screen py-8">
        <div className="max-w-5xl mx-auto px-4">
          
          {/* Header */}
          <div className="mb-6">
            <h1 className="text-2xl font-bold text-gray-800">
              Flights from <span className="text-blue-600">{from}</span> to <span className="text-blue-600">{to}</span>
            </h1>
            <p className="text-gray-500">{date} • {flights.length} flights found</p>
          </div>

          {/* Loading State */}
          {loading && (
            <div className="flex flex-col items-center justify-center h-64">
              <Loader2 className="h-10 w-10 text-blue-600 animate-spin mb-4" />
              <p className="text-gray-500">Searching best flights for you...</p>
            </div>
          )}

          {/* Error State */}
          {error && (
            <div className="bg-red-50 text-red-600 p-4 rounded-lg text-center border border-red-200">
              {error}
            </div>
          )}

          {/* Empty State */}
          {!loading && !error && flights.length === 0 && (
            <div className="bg-white p-8 rounded-xl shadow-sm text-center">
              <img src="https://cdn-icons-png.flaticon.com/512/6134/6134065.png" alt="No Flights" className="h-32 w-32 mx-auto mb-4 opacity-50" />
              <h3 className="text-xl font-semibold text-gray-700">No flights found</h3>
              <p className="text-gray-500 mt-2">Try changing the dates or route.</p>
            </div>
          )}

          {/* Flights List */}
          <div className="space-y-4">
            {flights.map((flight) => (
              <FlightCard key={flight.id} flight={flight} onBook={handleBook} />
            ))}
          </div>

        </div>
      </div>
    </>
  );
};

export default Flights;