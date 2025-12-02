import React from 'react';
import { MessageSquare } from 'lucide-react';

const SupportTickets: React.FC = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Support Tickets</h1>
        <p className="text-gray-600">Get help with your bookings and account</p>
      </div>

      <div className="text-center py-12">
        <MessageSquare className="w-16 h-16 text-gray-400 mx-auto mb-4" />
        <h2 className="text-xl font-semibold text-gray-900 mb-2">No Support Tickets</h2>
        <p className="text-gray-600">You haven't created any support tickets yet</p>
      </div>
    </div>
  );
};

export default SupportTickets;