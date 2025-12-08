import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { UserPlus, Mail, User, Lock, Phone, AtSign } from 'lucide-react';
import authApi from '../api/authApi';
import Navbar from '../components/common/Navbar'; // UserLayout use kar rahe ho to ye hata dena

const Signup = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    setError('');
    setLoading(true);
    try {
      // Backend ko 'username' aur 'email' dono alag-alag chahiye
      const payload = {
        username: data.username,
        email: data.email,
        password: data.password,
        firstName: data.firstName,
        lastName: data.lastName,
        phone: data.phone
      };

      console.log("Sending Payload:", payload); // Console check karna

      await authApi.signup(payload);
      
      alert("Registration Successful! Please Login.");
      navigate('/login');
    } catch (err) {
      console.error("Signup Error:", err);
      // Backend se exact error message nikalo
      const msg = err.response?.data?.message || err.response?.data?.errors?.username || err.response?.data?.errors?.email || 'Registration failed.';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4">
      <div className="max-w-md w-full bg-white rounded-xl shadow-lg p-8">
        
        <div className="text-center mb-8">
          <div className="bg-green-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
            <UserPlus className="h-8 w-8 text-green-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900">Create Account</h2>
          <p className="text-gray-500 mt-2">Join us to book your next trip</p>
        </div>

        {error && <div className="bg-red-50 text-red-600 p-3 rounded mb-4 text-sm text-center border border-red-200">{error}</div>}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          
          {/* 1. USERNAME FIELD (Unique ID) */}
          <div>
            <label className="text-xs font-bold text-gray-500 uppercase">Username (Unique)</label>
            <div className="flex items-center border rounded mt-1 px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500 bg-white">
              <AtSign className="h-4 w-4 text-gray-400 mr-2" />
              <input 
                {...register("username", { required: "Username is required", minLength: { value: 3, message: "Min 3 chars" } })} 
                className="w-full outline-none" 
                placeholder="lomash123" 
              />
            </div>
            {errors.username && <span className="text-red-500 text-xs">{errors.username.message}</span>}
          </div>

          {/* 2. EMAIL FIELD */}
          <div>
            <label className="text-xs font-bold text-gray-500 uppercase">Email</label>
            <div className="flex items-center border rounded mt-1 px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500 bg-white">
              <Mail className="h-4 w-4 text-gray-400 mr-2" />
              <input 
                {...register("email", { 
                  required: "Email is required",
                  pattern: { value: /^\S+@\S+$/i, message: "Invalid email" }
                })} 
                className="w-full outline-none" 
                placeholder="lomash@gmail.com" 
              />
            </div>
            {errors.email && <span className="text-red-500 text-xs">{errors.email.message}</span>}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="text-xs font-bold text-gray-500 uppercase">First Name</label>
              <input {...register("firstName")} className="w-full border p-2 rounded mt-1 focus:ring-2 focus:ring-blue-500 outline-none" placeholder="Lomash" />
            </div>
            <div>
              <label className="text-xs font-bold text-gray-500 uppercase">Last Name</label>
              <input {...register("lastName")} className="w-full border p-2 rounded mt-1 focus:ring-2 focus:ring-blue-500 outline-none" placeholder="Badole" />
            </div>
          </div>

          {/* Phone */}
          <div>
            <label className="text-xs font-bold text-gray-500 uppercase">Phone</label>
            <div className="flex items-center border rounded mt-1 px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500 bg-white">
              <Phone className="h-4 w-4 text-gray-400 mr-2" />
              <input {...register("phone")} className="w-full outline-none" placeholder="9876543210" />
            </div>
          </div>

          {/* Password */}
          <div>
            <label className="text-xs font-bold text-gray-500 uppercase">Password</label>
            <div className="flex items-center border rounded mt-1 px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500 bg-white">
              <Lock className="h-4 w-4 text-gray-400 mr-2" />
              <input type="password" {...register("password", { required: "Password is required", minLength: { value: 6, message: "Min 6 chars" } })} className="w-full outline-none" placeholder="******" />
            </div>
            {errors.password && <span className="text-red-500 text-xs">{errors.password.message}</span>}
          </div>

          <button type="submit" disabled={loading} className="w-full bg-green-600 text-white font-bold py-3 rounded-lg hover:bg-green-700 transition">
            {loading ? 'Creating Account...' : 'Sign Up'}
          </button>
        </form>

        <p className="mt-6 text-center text-sm text-gray-600">
          Already have an account? <Link to="/login" className="font-semibold text-blue-600">Log In</Link>
        </p>
      </div>
    </div>
  );
};

export default Signup;