import React, { useEffect, useState } from 'react';
import { Users, ShoppingCart, DollarSign, Activity, TrendingUp } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';
import adminApi from '../../api/adminApi';

const Dashboard = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        // Backend: getStats(days, recentLimit, popularLimit)
        const response = await adminApi.getStats();
        setData(response);
      } catch (error) {
        console.error("Error fetching admin stats:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  if (loading) return <div className="p-10 text-center">Loading Dashboard...</div>;
  if (!data) return <div className="p-10 text-center text-red-500">Failed to load data</div>;

  const StatCard = ({ title, value, icon: Icon, color, subValue }) => (
    <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center gap-4">
      <div className={`p-4 rounded-full ${color}`}>
        <Icon className="h-6 w-6 text-white" />
      </div>
      <div>
        <p className="text-gray-500 text-sm">{title}</p>
        <h3 className="text-2xl font-bold text-gray-800">{value}</h3>
        {subValue && <p className="text-xs text-gray-400 mt-1">{subValue}</p>}
      </div>
    </div>
  );

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Dashboard Overview</h1>
      
      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <StatCard 
            title="Total Revenue" 
            value={`₹${data.totalRevenue.toLocaleString()}`} 
            icon={DollarSign} 
            color="bg-green-600" 
        />
        <StatCard 
            title="Total Bookings" 
            value={data.totalBookings} 
            icon={ShoppingCart} 
            color="bg-blue-600"
            subValue={`${data.pendingBookings} Pending`} 
        />
        <StatCard 
            title="Active Users" 
            value={data.totalUsers} 
            icon={Users} 
            color="bg-purple-600" 
        />
        <StatCard 
            title="Hotels & Rooms" 
            value={data.totalHotels} 
            icon={Activity} 
            color="bg-orange-500" 
            subValue={`${data.totalRooms} Rooms Available`}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Revenue Chart */}
        <div className="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h2 className="text-lg font-bold text-gray-800 mb-4 flex items-center gap-2">
            <TrendingUp size={20} className="text-green-600"/> Revenue (Last 7 Days)
          </h2>
          <div className="h-72">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={data.dailyRevenue}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis dataKey="date" tick={{fontSize: 12}} />
                <YAxis tick={{fontSize: 12}} />
                <Tooltip />
                <Bar dataKey="revenue" fill="#4F46E5" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Popular Hotels */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <h2 className="text-lg font-bold text-gray-800 mb-4">Popular Hotels</h2>
          <div className="space-y-4">
            {data.popularHotels.map((hotel) => (
              <div key={hotel.hotelId} className="flex justify-between items-center border-b pb-3 last:border-0">
                <div>
                  <p className="font-semibold text-gray-700">{hotel.hotelName}</p>
                  <p className="text-xs text-gray-500">{hotel.totalBookings} Bookings</p>
                </div>
                <div className="bg-blue-50 text-blue-600 px-3 py-1 rounded-full text-xs font-bold">
                  #{hotel.hotelId}
                </div>
              </div>
            ))}
            {data.popularHotels.length === 0 && <p className="text-gray-400">No data yet.</p>}
          </div>
        </div>

      </div>

      {/* Recent Bookings Table */}
      <div className="mt-8 bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <h2 className="text-lg font-bold text-gray-800 mb-4">Recent Bookings</h2>
        <div className="overflow-x-auto">
          <table className="w-full text-left">
            <thead className="bg-gray-50 text-gray-600">
              <tr>
                <th className="p-3">ID</th>
                <th className="p-3">User</th>
                <th className="p-3">Item</th>
                <th className="p-3">Amount</th>
                <th className="p-3">Status</th>
                <th className="p-3">Date</th>
              </tr>
            </thead>
            <tbody className="divide-y">
              {data.recentBookings.map((b) => (
                <tr key={b.bookingId} className="hover:bg-gray-50">
                  <td className="p-3 font-mono text-xs">{b.bookingId}</td>
                  <td className="p-3">{b.username}</td>
                  <td className="p-3">{b.title}</td>
                  <td className="p-3 font-bold">₹{b.amount}</td>
                  <td className="p-3">
                    <span className={`px-2 py-1 rounded text-xs ${
                        b.status === 'CONFIRMED' ? 'bg-green-100 text-green-700' : 
                        b.status === 'CANCELLED' ? 'bg-red-100 text-red-700' : 'bg-yellow-100 text-yellow-700'
                    }`}>
                      {b.status}
                    </span>
                  </td>
                  <td className="p-3 text-sm text-gray-500">{new Date(b.createdAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

    </div>
  );
};

export default Dashboard;