import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Plane, Building2, UserCircle, LogOut, Ticket, User, Menu } from 'lucide-react';

const Navbar = () => {
  const navigate = useNavigate();
  // Token check karke pata lagayenge ki user login hai ya nahi
  const isLoggedIn = !!localStorage.getItem('token');

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/login');
    window.location.reload(); // Refresh taaki navbar update ho jaye
  };

  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2">
            <div className="bg-blue-600 p-2 rounded-lg">
              <Plane className="h-6 w-6 text-white" />
            </div>
            <span className="text-2xl font-bold text-blue-600 tracking-tighter">MyTrip</span>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex space-x-8 items-center">
            <Link to="/flights" className="flex items-center gap-1 text-gray-600 hover:text-blue-600 font-medium transition">
              <Plane className="h-4 w-4" /> Flights
            </Link>
            <Link to="/hotels" className="flex items-center gap-1 text-gray-600 hover:text-blue-600 font-medium transition">
              <Building2 className="h-4 w-4" /> Hotels
            </Link>
            
            {/* Logged In User Links */}
            {isLoggedIn && (
              <>
                <Link to="/my-bookings" className="flex items-center gap-1 text-gray-600 hover:text-blue-600 font-medium">
                  <Ticket className="h-4 w-4" /> My Bookings
                </Link>
                
                {/* ✅ New Profile Link added here */}
                <Link to="/profile" className="flex items-center gap-1 text-gray-600 hover:text-blue-600 font-medium mr-4">
                  <User className="h-4 w-4" /> Profile
                </Link>
              </>
            )}

            {/* Login / Logout Button */}
            {isLoggedIn ? (
              <button 
                onClick={handleLogout}
                className="flex items-center gap-2 bg-red-50 text-red-600 px-5 py-2 rounded-full hover:bg-red-100 transition border border-red-200"
              >
                <LogOut className="h-5 w-5" />
                Logout
              </button>
            ) : (
              <Link to="/login" className="flex items-center gap-2 bg-blue-600 text-white px-5 py-2 rounded-full hover:bg-blue-700 transition shadow-lg shadow-blue-200">
                <UserCircle className="h-5 w-5" />
                Login / Signup
              </Link>
            )}
          </div>

          {/* Mobile Menu Icon */}
          <div className="md:hidden flex items-center">
            <button className="text-gray-600 hover:text-blue-600 focus:outline-none">
              <Menu className="h-6 w-6" />
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;