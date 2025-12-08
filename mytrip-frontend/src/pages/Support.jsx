import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MessageSquare, Plus, Loader2 } from 'lucide-react';
import supportApi from '../api/supportApi';

const Support = () => {
  const navigate = useNavigate();
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [newSubject, setNewSubject] = useState('');

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const data = await supportApi.getMyTickets();
      setTickets(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error("Failed to load tickets");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTicket = async (e) => {
    e.preventDefault();
    try {
      await supportApi.createTicket({ subject: newSubject });
      setShowModal(false);
      setNewSubject('');
      fetchTickets(); // Refresh list
      alert("Ticket Created!");
    } catch (error) {
      alert("Failed to create ticket");
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'OPEN': return 'bg-green-100 text-green-700';
      case 'IN_PROGRESS': return 'bg-blue-100 text-blue-700';
      case 'RESOLVED': return 'bg-gray-100 text-gray-700';
      default: return 'bg-yellow-100 text-yellow-700';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4">
      <div className="max-w-4xl mx-auto">
        
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Help Center</h1>
          <button 
            onClick={() => setShowModal(true)}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center gap-2"
          >
            <Plus size={18} /> New Ticket
          </button>
        </div>

        {loading ? (
          <div className="text-center py-10"><Loader2 className="animate-spin mx-auto text-blue-600" /></div>
        ) : tickets.length === 0 ? (
          <div className="bg-white p-10 rounded-xl text-center shadow-sm text-gray-500">
            No support tickets yet. Need help? Create one!
          </div>
        ) : (
          <div className="space-y-4">
            {tickets.map((ticket) => (
              <div 
                key={ticket.id} 
                onClick={() => navigate(`/support/${ticket.id}`)}
                className="bg-white p-5 rounded-xl shadow-sm border border-gray-100 hover:shadow-md transition cursor-pointer flex justify-between items-center"
              >
                <div className="flex items-center gap-4">
                  <div className="bg-blue-50 p-3 rounded-full">
                    <MessageSquare className="h-6 w-6 text-blue-600" />
                  </div>
                  <div>
                    <h3 className="font-semibold text-gray-800">{ticket.subject}</h3>
                    <p className="text-xs text-gray-500">Created: {new Date(ticket.createdAt).toLocaleDateString()}</p>
                  </div>
                </div>
                <span className={`px-3 py-1 rounded-full text-xs font-bold ${getStatusColor(ticket.status)}`}>
                  {ticket.status}
                </span>
              </div>
            ))}
          </div>
        )}

        {/* Create Ticket Modal */}
        {showModal && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
            <div className="bg-white p-6 rounded-xl w-full max-w-md">
              <h2 className="text-xl font-bold mb-4">Create New Ticket</h2>
              <form onSubmit={handleCreateTicket}>
                <label className="block text-sm font-medium text-gray-600 mb-2">Subject / Issue</label>
                <input 
                  value={newSubject}
                  onChange={(e) => setNewSubject(e.target.value)}
                  className="w-full border p-2 rounded mb-4"
                  placeholder="e.g. Booking Refund Issue"
                  required
                />
                <div className="flex justify-end gap-2">
                  <button type="button" onClick={() => setShowModal(false)} className="px-4 py-2 text-gray-600">Cancel</button>
                  <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">Create</button>
                </div>
              </form>
            </div>
          </div>
        )}

      </div>
    </div>
  );
};

export default Support;