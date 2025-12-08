import React, { useState, useEffect } from 'react';
import { Plus, Tag, Trash2, Scissors } from 'lucide-react';
import adminApi from '../../api/adminApi';

const ManageCoupons = () => {
  const [coupons, setCoupons] = useState([
    { id: 1, code: 'WELCOME50', discountPercentage: 10, maxDiscount: 500, expiryDate: '2025-12-31' }
  ]);
  
  const [formData, setFormData] = useState({
    code: '', discountPercentage: '', maxDiscount: '', expiryDate: ''
  });

  // Real API integration (Commented out for now)
  /*
  useEffect(() => {
    const fetchCoupons = async () => {
      try {
        const data = await adminApi.getAllCoupons();
        setCoupons(data);
      } catch (e) console.error(e);
    };
    fetchCoupons();
  }, []);
  */

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Creating Coupon:", formData);
    // await adminApi.createCoupon(formData);
    
    setCoupons([...coupons, { ...formData, id: Date.now() }]);
    setFormData({ code: '', discountPercentage: '', maxDiscount: '', expiryDate: '' });
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Manage Coupons</h1>

      {/* Create Coupon Form */}
      <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-8">
        <h2 className="text-lg font-semibold mb-4 flex items-center gap-2">
          <Scissors className="h-5 w-5 text-green-600" /> Create New Coupon
        </h2>
        <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-5 gap-4 items-end">
          
          <div className="md:col-span-1">
            <label className="text-xs font-bold text-gray-500 uppercase">Code</label>
            <input 
              value={formData.code} 
              onChange={(e) => setFormData({...formData, code: e.target.value.toUpperCase()})} 
              placeholder="e.g. SUMMER20" 
              className="w-full border p-2 rounded mt-1 font-mono uppercase" 
              required 
            />
          </div>

          <div className="md:col-span-1">
            <label className="text-xs font-bold text-gray-500 uppercase">Discount (%)</label>
            <input 
              type="number"
              value={formData.discountPercentage} 
              onChange={(e) => setFormData({...formData, discountPercentage: e.target.value})} 
              placeholder="10" 
              className="w-full border p-2 rounded mt-1" 
              required 
            />
          </div>

          <div className="md:col-span-1">
            <label className="text-xs font-bold text-gray-500 uppercase">Max Amount (₹)</label>
            <input 
              type="number"
              value={formData.maxDiscount} 
              onChange={(e) => setFormData({...formData, maxDiscount: e.target.value})} 
              placeholder="500" 
              className="w-full border p-2 rounded mt-1" 
              required 
            />
          </div>

          <div className="md:col-span-1">
            <label className="text-xs font-bold text-gray-500 uppercase">Expiry Date</label>
            <input 
              type="date"
              value={formData.expiryDate} 
              onChange={(e) => setFormData({...formData, expiryDate: e.target.value})} 
              className="w-full border p-2 rounded mt-1" 
              required 
            />
          </div>
          
          <button type="submit" className="bg-green-600 text-white font-bold py-2.5 rounded hover:bg-green-700 flex justify-center items-center gap-2">
            <Plus size={18} /> Add
          </button>
        </form>
      </div>

      {/* Coupons List */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {coupons.map((coupon) => (
          <div key={coupon.id} className="bg-white p-5 rounded-xl border border-dashed border-gray-300 hover:border-blue-400 transition relative group">
            <div className="flex justify-between items-start">
              <div>
                <h3 className="text-xl font-bold text-gray-800 font-mono tracking-wider">{coupon.code}</h3>
                <p className="text-green-600 font-bold">{coupon.discountPercentage}% OFF <span className="text-gray-400 text-xs font-normal">(Up to ₹{coupon.maxDiscount})</span></p>
              </div>
              <div className="bg-gray-100 p-2 rounded-full">
                <Tag size={20} className="text-gray-500" />
              </div>
            </div>
            
            <div className="mt-4 pt-4 border-t flex justify-between items-center">
              <span className="text-xs text-gray-500">Expires: {coupon.expiryDate}</span>
              <button 
                onClick={() => console.log("Delete", coupon.id)}
                className="text-red-400 hover:text-red-600 opacity-0 group-hover:opacity-100 transition"
              >
                <Trash2 size={18} />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ManageCoupons;