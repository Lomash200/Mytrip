import React, { useEffect, useState } from 'react';
import { Check, Trash2, Star } from 'lucide-react';
import adminApi from '../../api/adminApi';

const AdminReviews = () => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    adminApi.getAllReviews().then(setReviews);
  }, []);

  const handleAction = async (id, action) => {
    if (action === 'approve') await adminApi.approveReview(id);
    else await adminApi.deleteReview(id);
    setReviews(prev => prev.filter(r => r.id !== id)); // Remove from list for UI update
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Manage Reviews</h1>
      <div className="grid gap-4">
        {reviews.map(r => (
          <div key={r.id} className="bg-white p-4 rounded-lg shadow-sm border flex justify-between items-start">
            <div>
              <h4 className="font-bold text-gray-800">{r.title} <span className="text-xs text-gray-500 font-normal">by {r.username}</span></h4>
              <div className="flex text-yellow-400 my-1">{[...Array(r.rating)].map((_,i)=><Star key={i} size={12} fill="currentColor"/>)}</div>
              <p className="text-gray-600 text-sm">{r.comment}</p>
            </div>
            <div className="flex gap-2">
              {!r.approved && (
                <button onClick={() => handleAction(r.id, 'approve')} className="p-2 bg-green-100 text-green-600 rounded hover:bg-green-200"><Check size={16}/></button>
              )}
              <button onClick={() => handleAction(r.id, 'delete')} className="p-2 bg-red-100 text-red-600 rounded hover:bg-red-200"><Trash2 size={16}/></button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminReviews;