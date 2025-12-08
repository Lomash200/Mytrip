import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { Plane, Building2, Calendar, MapPin, Search } from 'lucide-react';
// ❌ Navbar import hata diya gaya hai

const Home = () => {
  const [activeTab, setActiveTab] = useState('flights');
  const navigate = useNavigate();
  
  // React Hook Form setup
  const { register, handleSubmit } = useForm();

  const onSubmit = (data) => {
    console.log("Searching for:", data);
    
    // Logic: Search data ko URL me daal kar agle page par bhej do
    if (activeTab === 'flights') {
      const queryParams = new URLSearchParams(data).toString();
      navigate(`/flights?${queryParams}`);
    } else {
      const queryParams = new URLSearchParams(data).toString();
      navigate(`/hotels?${queryParams}`);
    }
  };

  return (
    <>
      {/* ❌ Navbar component yaha se hata diya gaya hai */}
      
      {/* Hero Section */}
      <div className="relative bg-blue-600 h-[500px]">
        <div className="absolute inset-0 bg-gradient-to-r from-blue-600 to-blue-400 opacity-90"></div>
        <div className="absolute inset-0 opacity-20 bg-[url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?q=80&w=2074&auto=format&fit=crop')] bg-cover bg-center"></div>

        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-20 flex flex-col items-center">
          
          <h1 className="text-4xl md:text-5xl font-extrabold text-white text-center mb-6 drop-shadow-md">
            Find Your Next Adventure
          </h1>
          <p className="text-blue-100 text-lg mb-10 text-center max-w-2xl">
            Discover the best flights and hotels at the lowest prices.
          </p>

          {/* Search Card */}
          <div className="bg-white p-6 rounded-2xl shadow-2xl w-full max-w-4xl">
            
            {/* Tabs */}
            <div className="flex border-b border-gray-200 mb-6">
              <button
                onClick={() => setActiveTab('flights')}
                className={`flex items-center gap-2 px-6 py-3 font-semibold transition-colors ${
                  activeTab === 'flights' ? 'text-blue-600 border-b-2 border-blue-600' : 'text-gray-500 hover:text-blue-500'
                }`}
              >
                <Plane className="h-5 w-5" /> Flights
              </button>
              <button
                onClick={() => setActiveTab('hotels')}
                className={`flex items-center gap-2 px-6 py-3 font-semibold transition-colors ${
                  activeTab === 'hotels' ? 'text-blue-600 border-b-2 border-blue-600' : 'text-gray-500 hover:text-blue-500'
                }`}
              >
                <Building2 className="h-5 w-5" /> Hotels
              </button>
            </div>

            {/* Form Starts Here */}
            <form onSubmit={handleSubmit(onSubmit)} className="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
              
              {activeTab === 'flights' ? (
                <>
                  <div className="md:col-span-1">
                    <label className="block text-xs font-bold text-gray-500 uppercase mb-1">From</label>
                    <div className="flex items-center border rounded-lg p-3 bg-gray-50">
                      <MapPin className="h-5 w-5 text-gray-400 mr-2" />
                      <input {...register("from", {required: true})} placeholder="DEL" className="bg-transparent outline-none w-full text-gray-700 font-medium" />
                    </div>
                  </div>
                  <div className="md:col-span-1">
                    <label className="block text-xs font-bold text-gray-500 uppercase mb-1">To</label>
                    <div className="flex items-center border rounded-lg p-3 bg-gray-50">
                      <MapPin className="h-5 w-5 text-gray-400 mr-2" />
                      <input {...register("to", {required: true})} placeholder="BOM" className="bg-transparent outline-none w-full text-gray-700 font-medium" />
                    </div>
                  </div>
                  <div className="md:col-span-1">
                    <label className="block text-xs font-bold text-gray-500 uppercase mb-1">Date</label>
                    <div className="flex items-center border rounded-lg p-3 bg-gray-50">
                      <Calendar className="h-5 w-5 text-gray-400 mr-2" />
                      <input type="date" {...register("date", {required: true})} className="bg-transparent outline-none w-full text-gray-700 font-medium" />
                    </div>
                  </div>
                </>
              ) : (
                <>
                  <div className="md:col-span-2">
                    <label className="block text-xs font-bold text-gray-500 uppercase mb-1">City</label>
                    <div className="flex items-center border rounded-lg p-3 bg-gray-50">
                      <Building2 className="h-5 w-5 text-gray-400 mr-2" />
                      <input {...register("city", {required: true})} placeholder="Goa" className="bg-transparent outline-none w-full text-gray-700 font-medium" />
                    </div>
                  </div>
                  <div className="md:col-span-1">
                    <label className="block text-xs font-bold text-gray-500 uppercase mb-1">Check-in</label>
                    <div className="flex items-center border rounded-lg p-3 bg-gray-50">
                      <Calendar className="h-5 w-5 text-gray-400 mr-2" />
                      <input type="date" {...register("checkIn", {required: true})} className="bg-transparent outline-none w-full text-gray-700 font-medium" />
                    </div>
                  </div>
                </>
              )}

              {/* Submit Button */}
              <div className="md:col-span-1">
                <button type="submit" className="w-full bg-blue-600 text-white font-bold py-3.5 rounded-lg hover:bg-blue-700 shadow-lg flex justify-center items-center gap-2">
                  <Search className="h-5 w-5" />
                  Search
                </button>
              </div>
            </form>

          </div>
        </div>
      </div>
      
      {/* Featured Section */}
      <div className="max-w-7xl mx-auto px-4 py-16">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">Popular Destinations</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition cursor-pointer">
            <img src="https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=500&auto=format&fit=crop" alt="Goa" className="h-48 w-full object-cover" />
            <div className="p-4">
              <h3 className="font-bold text-lg text-gray-800">Goa, India</h3>
              <p className="text-gray-500 text-sm">Flights from ₹3,500</p>
            </div>
          </div>
          <div className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition cursor-pointer">
            <img src="https://images.unsplash.com/photo-1524492412937-b28074a5d7da?w=500&auto=format&fit=crop" alt="Agra" className="h-48 w-full object-cover" />
            <div className="p-4">
              <h3 className="font-bold text-lg text-gray-800">Agra, India</h3>
              <p className="text-gray-500 text-sm">Hotels from ₹1,200</p>
            </div>
          </div>
          <div className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition cursor-pointer">
            <img src="https://images.unsplash.com/photo-1587474262715-9aa4c19702b6?w=500&auto=format&fit=crop" alt="Mumbai" className="h-48 w-full object-cover" />
            <div className="p-4">
              <h3 className="font-bold text-lg text-gray-800">Mumbai, Maharashtra</h3>
              <p className="text-gray-500 text-sm">Trending now</p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;