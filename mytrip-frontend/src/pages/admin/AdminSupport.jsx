import React, { useEffect, useState } from 'react';
import { MessageSquare, Send } from 'lucide-react';
import adminApi from '../../api/adminApi';
import supportApi from '../../api/supportApi'; // Reusing user api for fetching messages

const AdminSupport = () => {
  const [tickets, setTickets] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [messages, setMessages] = useState([]);
  const [reply, setReply] = useState('');

  useEffect(() => {
    loadTickets();
  }, []);

  const loadTickets = async () => {
    try {
      const data = await adminApi.getAllTickets();
      setTickets(data || []);
    } catch (e) { console.error(e); }
  };

  const openTicket = async (ticket) => {
    setSelectedTicket(ticket);
    const msgs = await supportApi.getMessages(ticket.id);
    setMessages(msgs);
  };

  const sendReply = async (e) => {
    e.preventDefault();
    if (!reply) return;
    await adminApi.replyTicket(selectedTicket.id, reply);
    setReply('');
    openTicket(selectedTicket); // Refresh chat
  };

  return (
    <div className="flex h-[calc(100vh-100px)] gap-6">
      
      {/* List */}
      <div className="w-1/3 bg-white rounded-xl shadow-sm border overflow-y-auto">
        <div className="p-4 border-b font-bold text-gray-700">All Tickets</div>
        {tickets.map(t => (
          <div key={t.id} onClick={() => openTicket(t)} className={`p-4 border-b cursor-pointer hover:bg-blue-50 ${selectedTicket?.id === t.id ? 'bg-blue-50 border-l-4 border-blue-500' : ''}`}>
            <h4 className="font-semibold text-gray-800">{t.subject}</h4>
            <div className="flex justify-between mt-1 text-xs text-gray-500">
              <span>#{t.id}</span>
              <span className={`px-2 py-0.5 rounded ${t.status==='OPEN'?'bg-green-100 text-green-700':'bg-gray-100'}`}>{t.status}</span>
            </div>
          </div>
        ))}
      </div>

      {/* Chat Box */}
      <div className="flex-1 bg-white rounded-xl shadow-sm border flex flex-col">
        {selectedTicket ? (
          <>
            <div className="p-4 border-b flex justify-between bg-gray-50">
              <h3 className="font-bold">#{selectedTicket.id} - {selectedTicket.subject}</h3>
              <button onClick={() => adminApi.updateTicketStatus(selectedTicket.id, 'RESOLVED')} className="text-xs bg-gray-200 px-2 py-1 rounded hover:bg-gray-300">Mark Resolved</button>
            </div>
            
            <div className="flex-1 overflow-y-auto p-4 space-y-4">
              {messages.map(m => (
                <div key={m.id} className={`flex ${m.fromAdmin ? 'justify-end' : 'justify-start'}`}>
                  <div className={`p-3 rounded-lg max-w-md text-sm ${m.fromAdmin ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-800'}`}>
                    {m.message}
                  </div>
                </div>
              ))}
            </div>

            <form onSubmit={sendReply} className="p-4 border-t flex gap-2">
              <input value={reply} onChange={e=>setReply(e.target.value)} className="flex-1 border p-2 rounded" placeholder="Reply..." />
              <button type="submit" className="bg-blue-600 text-white p-2 rounded"><Send size={20}/></button>
            </form>
          </>
        ) : (
          <div className="flex-1 flex items-center justify-center text-gray-400">Select a ticket</div>
        )}
      </div>
    </div>
  );
};

export default AdminSupport;