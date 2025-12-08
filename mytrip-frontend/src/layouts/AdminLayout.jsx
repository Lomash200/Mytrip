import React from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { LayoutDashboard, Users, Plane, Building2, Ticket, LogOut } from 'lucide-react';

const AdminLayout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/login');
  };

  return (
    <div className="flex h-screen bg-gray-100">
      
      {/* Sidebar */}
      <aside className="w-64 bg-gray-900 text-white flex flex-col">
        <div className="h-16 flex items-center justify-center border-b border-gray-800">
          <h1 className="text-2xl font-bold text-blue-500">MyTrip Admin</h1>
        </div>

        <nav className="flex-1 px-4 py-6 space-y-2">
          <Link to="/admin/dashboard" className="flex items-center gap-3 px-4 py-3 bg-gray-800 rounded-lg text-blue-400">
            <LayoutDashboard size={20} /> Dashboard
          </Link>
          <Link to="/admin/bookings" className="flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-lg text-gray-300 transition">
            <Ticket size={20} /> Bookings
          </Link>
          <Link to="/admin/users" className="flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-lg text-gray-300 transition">
            <Users size={20} /> Users
          </Link>
          <Link to="/admin/flights" className="flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-lg text-gray-300 transition">
            <Plane size={20} /> Manage Flights
          </Link>
          <Link to="/admin/hotels" className="flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-lg text-gray-300 transition">
            <Building2 size={20} /> Manage Hotels
          </Link>
          <Link to="/admin/coupons" className="flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-lg text-gray-300 transition">
  <Tag size={20} /> Manage Coupons {/* Tag icon import kar lena lucide-react se */}
</Link>
          <Link to="/admin/kyc" className="..."> <Shield size={20} /> KYC Requests </Link>
          <Link to="/admin/support" className="..."> <MessageSquare size={20} /> Support </Link>
          <Link to="/admin/reviews" className="..."> <Star size={20} /> Reviews </Link>
        </nav>

        <div className="p-4 border-t border-gray-800">
          <button 
            onClick={handleLogout}
            className="flex items-center gap-3 text-red-400 hover:text-red-300 w-full px-4 py-2"
          >
            <LogOut size={20} /> Logout
          </button>
        </div>
      </aside>

      {/* Main Content Area */}
      <main className="flex-1 overflow-y-auto">
        {/* Header */}
        <header className="h-16 bg-white shadow-sm flex items-center justify-between px-8">
          <h2 className="text-xl font-semibold text-gray-800">Overview</h2>
          <div className="flex items-center gap-4">
            <span className="text-sm text-gray-600">Admin User</span>
            <div className="h-8 w-8 bg-blue-500 rounded-full flex items-center justify-center text-white font-bold">
              A
            </div>
          </div>
        </header>

        {/* Dynamic Page Content */}
        <div className="p-8">
          <Outlet /> {/* Yaha Dashboard/Users/Flights pages render honge */}
        </div>
      </main>
    </div>
  );
};

export default AdminLayout;