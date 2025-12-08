import React, { useEffect, useState, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { Send, User, ShieldAlert } from 'lucide-react';
import supportApi from '../api/supportApi';

const TicketChat = () => {
  const { ticketId } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const bottomRef = useRef(null);

  useEffect(() => {
    fetchMessages();
    // Optional: Auto refresh every 5 sec to check admin reply
    const interval = setInterval(fetchMessages, 5000);
    return () => clearInterval(interval);
  }, [ticketId]);

  const fetchMessages = async () => {
    try {
      const data = await supportApi.getMessages(ticketId);
      setMessages(data || []);
      // Scroll to bottom
      setTimeout(() => bottomRef.current?.scrollIntoView({ behavior: 'smooth' }), 100);
    } catch (error) {
      console.error("Failed to load messages");
    }
  };

  const handleSend = async (e) => {
    e.preventDefault();
    if (!newMessage.trim()) return;

    try {
      await supportApi.sendMessage(ticketId, { message: newMessage });
      setNewMessage('');
      fetchMessages();
    } catch (error) {
      alert("Failed to send message");
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Header */}
      <div className="bg-white shadow-sm p-4 sticky top-0 z-10">
        <div className="max-w-3xl mx-auto">
          <h2 className="text-lg font-bold text-gray-800">Support Chat #{ticketId}</h2>
        </div>
      </div>

      {/* Chat Area */}
      <div className="flex-1 overflow-y-auto p-4">
        <div className="max-w-3xl mx-auto space-y-4">
          {messages.map((msg) => (
            <div key={msg.id} className={`flex ${msg.fromAdmin ? 'justify-start' : 'justify-end'}`}>
              <div className={`flex items-start gap-2 max-w-[80%] ${msg.fromAdmin ? 'flex-row' : 'flex-row-reverse'}`}>
                
                {/* Avatar */}
                <div className={`w-8 h-8 rounded-full flex items-center justify-center text-white text-xs ${msg.fromAdmin ? 'bg-red-500' : 'bg-blue-500'}`}>
                  {msg.fromAdmin ? <ShieldAlert size={14} /> : <User size={14} />}
                </div>

                {/* Message Bubble */}
                <div className={`p-3 rounded-xl shadow-sm text-sm ${
                  msg.fromAdmin 
                    ? 'bg-white text-gray-800 rounded-tl-none' 
                    : 'bg-blue-600 text-white rounded-tr-none'
                }`}>
                  <p>{msg.message}</p>
                  <p className={`text-[10px] mt-1 text-right ${msg.fromAdmin ? 'text-gray-400' : 'text-blue-200'}`}>
                    {new Date(msg.time).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                  </p>
                </div>

              </div>
            </div>
          ))}
          <div ref={bottomRef}></div>
        </div>
      </div>

      {/* Input Area */}
      <div className="bg-white p-4 border-t sticky bottom-0">
        <div className="max-w-3xl mx-auto">
          <form onSubmit={handleSend} className="flex gap-2">
            <input 
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              className="flex-1 border p-3 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500 bg-gray-50"
              placeholder="Type your message..."
            />
            <button type="submit" className="bg-blue-600 text-white p-3 rounded-full hover:bg-blue-700 transition">
              <Send size={20} />
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default TicketChat;