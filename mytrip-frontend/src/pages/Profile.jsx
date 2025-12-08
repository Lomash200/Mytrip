import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { User, Heart, ShieldCheck, Save, Trash2, UploadCloud, Loader2 } from 'lucide-react';
// Navbar import hata diya gaya hai kyunki wo ab UserLayout.jsx me hai
import authApi from '../api/authApi';
import userApi from '../api/userApi';

const Profile = () => {
  const [activeTab, setActiveTab] = useState('info'); // info | wishlist | kyc
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Data States
  const [wishlist, setWishlist] = useState([]);
  const [kycData, setKycData] = useState([]);

  // Forms
  const { register, handleSubmit, reset } = useForm();

  // Initial Data Fetch
  useEffect(() => {
    fetchUserData();
  }, []);

  const fetchUserData = async () => {
    try {
      setLoading(true);
      // 1. Get User Details
      const userData = await authApi.getCurrentUser();
      setUser(userData);
      reset(userData); // Form me data bhar do

      // 2. Get Wishlist
      const wishlistData = await userApi.getWishlist();
      setWishlist(wishlistData || []);

      // 3. Get KYC
      const kycStatus = await userApi.getKycStatus();
      setKycData(Array.isArray(kycStatus.data) ? kycStatus.data : []); // Backend response structure check kar lena

    } catch (error) {
      console.error("Failed to load profile", error);
    } finally {
      setLoading(false);
    }
  };

  // Update Profile Logic
  const onUpdateProfile = async (data) => {
    try {
      await userApi.updateProfile(data);
      alert("Profile Updated Successfully!");
    } catch (error) {
      alert("Failed to update profile");
    }
  };

  // Remove Wishlist Logic
  const onRemoveWishlist = async (type, id) => {
    try {
      await userApi.removeFromWishlist(type, id);
      setWishlist(prev => prev.filter(item => item.targetId !== id));
    } catch (error) {
      console.error(error);
    }
  };

  // Upload KYC Logic
  const onKycUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      await userApi.uploadKyc(formData);
      alert("Document Uploaded! Waiting for Admin Approval.");
      fetchUserData(); // Refresh list
    } catch (error) {
      alert("Upload Failed");
    }
  };

  if (loading) return <div className="text-center py-20">Loading Profile...</div>;

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4">
      <div className="max-w-5xl mx-auto grid grid-cols-1 md:grid-cols-4 gap-6">
        
        {/* Sidebar Navigation */}
        <div className="bg-white p-4 rounded-xl shadow-sm h-fit">
          <div className="text-center mb-6 border-b pb-4">
            <div className="w-16 h-16 bg-blue-100 text-blue-600 rounded-full flex items-center justify-center mx-auto mb-2 text-2xl font-bold">
              {user?.firstName?.charAt(0)}
            </div>
            <h2 className="font-bold text-gray-800">{user?.firstName} {user?.lastName}</h2>
            <p className="text-xs text-gray-500">{user?.email}</p>
          </div>
          
          <nav className="space-y-2">
            <button 
              onClick={() => setActiveTab('info')}
              className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${activeTab === 'info' ? 'bg-blue-50 text-blue-600 font-medium' : 'text-gray-600 hover:bg-gray-50'}`}
            >
              <User size={18} /> Personal Info
            </button>
            <button 
              onClick={() => setActiveTab('wishlist')}
              className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${activeTab === 'wishlist' ? 'bg-blue-50 text-blue-600 font-medium' : 'text-gray-600 hover:bg-gray-50'}`}
            >
              <Heart size={18} /> My Wishlist
            </button>
            <button 
              onClick={() => setActiveTab('kyc')}
              className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${activeTab === 'kyc' ? 'bg-blue-50 text-blue-600 font-medium' : 'text-gray-600 hover:bg-gray-50'}`}
            >
              <ShieldCheck size={18} /> KYC Documents
            </button>
          </nav>
        </div>

        {/* Content Area */}
        <div className="md:col-span-3 bg-white p-8 rounded-xl shadow-sm">
          
          {/* TAB: Personal Info */}
          {activeTab === 'info' && (
            <div>
              <h2 className="text-xl font-bold text-gray-800 mb-6">Edit Profile</h2>
              <form onSubmit={handleSubmit(onUpdateProfile)} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-gray-600 mb-1">First Name</label>
                  <input {...register("firstName")} className="w-full border p-2 rounded focus:ring-2 focus:ring-blue-500 outline-none" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-600 mb-1">Last Name</label>
                  <input {...register("lastName")} className="w-full border p-2 rounded focus:ring-2 focus:ring-blue-500 outline-none" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-600 mb-1">Email (Cannot Change)</label>
                  <input {...register("email")} disabled className="w-full border p-2 rounded bg-gray-100 text-gray-500 cursor-not-allowed" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-600 mb-1">Phone</label>
                  <input {...register("phone")} className="w-full border p-2 rounded focus:ring-2 focus:ring-blue-500 outline-none" />
                </div>
                <div className="md:col-span-2">
                  <button type="submit" className="bg-blue-600 text-white px-6 py-2.5 rounded-lg hover:bg-blue-700 flex items-center gap-2">
                    <Save size={18} /> Save Changes
                  </button>
                </div>
              </form>
            </div>
          )}

          {/* TAB: Wishlist */}
          {activeTab === 'wishlist' && (
            <div>
              <h2 className="text-xl font-bold text-gray-800 mb-6">My Saved Items</h2>
              {wishlist.length === 0 ? (
                <div className="text-center text-gray-400 py-10">Your wishlist is empty.</div>
              ) : (
                <div className="space-y-4">
                  {wishlist.map((item) => (
                    <div key={item.id} className="flex justify-between items-center border p-4 rounded-lg">
                      <div>
                        <span className="text-xs font-bold text-blue-600 bg-blue-50 px-2 py-1 rounded uppercase">{item.type}</span>
                        <p className="font-medium text-gray-800 mt-1">ID: #{item.targetId}</p>
                      </div>
                      <button 
                        onClick={() => onRemoveWishlist(item.type, item.targetId)}
                        className="text-red-500 hover:bg-red-50 p-2 rounded-full transition"
                      >
                        <Trash2 size={18} />
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {/* TAB: KYC */}
          {activeTab === 'kyc' && (
            <div>
              <h2 className="text-xl font-bold text-gray-800 mb-6">KYC Verification</h2>
              
              {/* Upload Section */}
              <div className="border-2 border-dashed border-gray-300 rounded-xl p-8 text-center mb-8 hover:bg-gray-50 transition relative">
                <input type="file" onChange={onKycUpload} className="absolute inset-0 w-full h-full opacity-0 cursor-pointer" />
                <UploadCloud className="h-10 w-10 text-gray-400 mx-auto mb-2" />
                <p className="text-gray-600 font-medium">Click to upload Aadhaar / Passport</p>
                <p className="text-xs text-gray-400">Supported formats: JPG, PNG, PDF</p>
              </div>

              {/* Status List */}
              <h3 className="font-bold text-gray-700 mb-4">Uploaded Documents</h3>
              <div className="space-y-3">
                {kycData.map((doc) => (
                  <div key={doc.id} className="flex justify-between items-center bg-gray-50 p-4 rounded-lg">
                    <div>
                      <p className="font-medium text-gray-800">{doc.documentType || "Document"}</p>
                      <p className="text-xs text-gray-500">Uploaded on: {doc.uploadedAt}</p>
                    </div>
                    <div>
                      {doc.status === 'APPROVED' && <span className="bg-green-100 text-green-700 px-3 py-1 rounded-full text-xs font-bold">Verified</span>}
                      {doc.status === 'PENDING' && <span className="bg-yellow-100 text-yellow-700 px-3 py-1 rounded-full text-xs font-bold">Pending</span>}
                      {doc.status === 'REJECTED' && <span className="bg-red-100 text-red-700 px-3 py-1 rounded-full text-xs font-bold">Rejected</span>}
                    </div>
                  </div>
                ))}
                {kycData.length === 0 && <p className="text-gray-400 text-sm">No documents uploaded yet.</p>}
              </div>
            </div>
          )}

        </div>
      </div>
    </div>
  );
};

export default Profile;