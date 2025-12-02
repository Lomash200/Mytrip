import React from 'react';
import { Shield, User, Check, X } from 'lucide-react';

const KycManagement: React.FC = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">KYC Document Verification</h1>
        <p className="text-gray-600">Review and verify user identity documents</p>
      </div>

      <div className="text-center py-12">
        <Shield className="w-16 h-16 text-gray-400 mx-auto mb-4" />
        <h2 className="text-xl font-semibold text-gray-900 mb-2">No Pending KYC Documents</h2>
        <p className="text-gray-600">All KYC documents have been reviewed</p>
      </div>
    </div>
  );
};

export default KycManagement;