import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { CheckCircle, XCircle, Loader2, CreditCard } from 'lucide-react';
import Navbar from '../components/common/Navbar';

const Payments = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const orderId = searchParams.get('orderId') || 'ORD-12345'; // Backend se aana chahiye
  
  const [status, setStatus] = useState('PROCESSING'); // PROCESSING | SUCCESS | FAILED

  // Fake Payment Simulation (3 seconds loading)
  useEffect(() => {
    const timer = setTimeout(() => {
      setStatus('CONFIRM'); // User se puchho success ya fail
    }, 2000);
    return () => clearTimeout(timer);
  }, []);

  const handlePaymentResult = (result) => {
    if (result === 'SUCCESS') {
      setStatus('SUCCESS');
      // Yaha Backend API call honi chahiye: /api/payments/verify
      setTimeout(() => navigate('/my-bookings'), 2000);
    } else {
      setStatus('FAILED');
    }
  };

  return (
    <>
      <Navbar />
      <div className="min-h-screen bg-gray-50 flex flex-col items-center justify-center p-4">
        
        <div className="bg-white w-full max-w-md rounded-2xl shadow-xl overflow-hidden">
          
          {/* Header */}
          <div className="bg-gray-900 p-6 text-white text-center">
            <h2 className="text-xl font-bold flex items-center justify-center gap-2">
              <CreditCard /> Secure Payment Gateway
            </h2>
            <p className="text-gray-400 text-sm mt-1">Order ID: {orderId}</p>
          </div>

          <div className="p-8 text-center">
            
            {status === 'PROCESSING' && (
              <div className="py-10">
                <Loader2 className="h-16 w-16 text-blue-600 animate-spin mx-auto mb-4" />
                <h3 className="text-lg font-semibold text-gray-800">Processing Transaction...</h3>
                <p className="text-gray-500">Please do not close this window.</p>
              </div>
            )}

            {status === 'CONFIRM' && (
              <div className="py-6 space-y-4">
                <p className="text-gray-600 mb-4">
                  This is a <strong>Demo Payment Page</strong>. <br/> Choose an outcome to proceed:
                </p>
                <button 
                  onClick={() => handlePaymentResult('SUCCESS')}
                  className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-3 rounded-xl transition"
                >
                  Simulate Success ✅
                </button>
                <button 
                  onClick={() => handlePaymentResult('FAILED')}
                  className="w-full bg-red-500 hover:bg-red-600 text-white font-bold py-3 rounded-xl transition"
                >
                  Simulate Failure ❌
                </button>
              </div>
            )}

            {status === 'SUCCESS' && (
              <div className="py-10">
                <CheckCircle className="h-20 w-20 text-green-500 mx-auto mb-4 animate-bounce" />
                <h3 className="text-2xl font-bold text-gray-800">Payment Successful!</h3>
                <p className="text-gray-500">Redirecting to your ticket...</p>
              </div>
            )}

            {status === 'FAILED' && (
              <div className="py-10">
                <XCircle className="h-20 w-20 text-red-500 mx-auto mb-4" />
                <h3 className="text-2xl font-bold text-gray-800">Payment Failed</h3>
                <p className="text-gray-500 mb-6">Something went wrong with the transaction.</p>
                <button 
                  onClick={() => setStatus('CONFIRM')}
                  className="text-blue-600 font-semibold hover:underline"
                >
                  Try Again
                </button>
              </div>
            )}

          </div>
          
          <div className="bg-gray-50 p-4 text-center text-xs text-gray-400">
            Secured by MyTrip Clone Payments
          </div>
        </div>

      </div>
    </>
  );
};

export default Payments;