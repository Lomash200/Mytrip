import React from 'react';
import { Link } from 'react-router-dom';
import { Plane, Facebook, Twitter, Instagram, Mail, Phone, Shield } from 'lucide-react';

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-gray-300 pt-16 pb-8">
      <div className="max-w-7xl mx-auto px-4">
        
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-12">
          
          {/* Brand */}
          <div>
            <div className="flex items-center gap-2 mb-4">
              <div className="bg-blue-600 p-1.5 rounded-lg">
                <Plane className="h-5 w-5 text-white" />
              </div>
              <span className="text-xl font-bold text-white tracking-tighter">MyTrip</span>
            </div>
            <p className="text-sm text-gray-400">
              Your trusted partner for hassle-free travel booking. Flights, Hotels, and more at the best prices.
            </p>
          </div>

          {/* Quick Links */}
          <div>
            <h4 className="text-white font-bold mb-4">Company</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/" className="hover:text-blue-500 transition">About Us</Link></li>
              <li><Link to="/" className="hover:text-blue-500 transition">Careers</Link></li>
              <li><Link to="/" className="hover:text-blue-500 transition">Blog</Link></li>
            </ul>
          </div>

          {/* Support */}
          <div>
            <h4 className="text-white font-bold mb-4">Support</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/support" className="hover:text-blue-500 transition">Help Center</Link></li>
              <li><Link to="/" className="hover:text-blue-500 transition">Terms of Service</Link></li>
              <li><Link to="/" className="hover:text-blue-500 transition">Privacy Policy</Link></li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h4 className="text-white font-bold mb-4">Contact</h4>
            <div className="space-y-3 text-sm">
              <p className="flex items-center gap-2"><Mail size={16} /> support@mytrip.com</p>
              <p className="flex items-center gap-2"><Phone size={16} /> +91 98765 43210</p>
              <div className="flex gap-4 mt-4">
                <Facebook className="hover:text-blue-500 cursor-pointer" size={20} />
                <Twitter className="hover:text-blue-400 cursor-pointer" size={20} />
                <Instagram className="hover:text-pink-500 cursor-pointer" size={20} />
              </div>
            </div>
          </div>
        </div>

        {/* Bottom Bar with Admin Link */}
        <div className="border-t border-gray-800 pt-8 flex flex-col md:flex-row justify-between items-center text-sm text-gray-500">
          <p>© {new Date().getFullYear()} MyTrip Clone. All rights reserved.</p>
          
          {/* 👇 Admin Link Yahan Hai */}
          <div className="mt-4 md:mt-0">
            <Link to="/admin/login" className="flex items-center gap-1 hover:text-white transition">
              <Shield size={14} /> Admin Access
            </Link>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;