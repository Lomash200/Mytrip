import React, { useEffect, useState } from 'react';
import { Check, X, FileText, ExternalLink } from 'lucide-react';
import adminApi from '../../api/adminApi';

const AdminKyc = () => {
  const [docs, setDocs] = useState([]);

  useEffect(() => {
    loadDocs();
  }, []);

  const loadDocs = async () => {
    try {
      const data = await adminApi.getPendingKyc();
      setDocs(data || []);
    } catch (e) { console.error(e); }
  };

  const handleReview = async (id, status) => {
    const remark = prompt(`Enter remark for ${status}:`, status === 'APPROVED' ? 'Verified' : 'Invalid Document');
    if (!remark) return;
    try {
      await adminApi.reviewKyc(id, status, remark);
      loadDocs(); // Refresh
    } catch (e) { alert("Action failed"); }
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Pending KYC Requests</h1>
      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left">
          <thead className="bg-gray-50 text-gray-600 border-b">
            <tr>
              <th className="p-4">ID</th>
              <th className="p-4">Doc Type</th>
              <th className="p-4">Doc Number</th>
              <th className="p-4">File</th>
              <th className="p-4">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y">
            {docs.map((doc) => (
              <tr key={doc.id} className="hover:bg-gray-50">
                <td className="p-4">#{doc.id}</td>
                <td className="p-4"><span className="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-bold">{doc.documentType}</span></td>
                <td className="p-4 font-mono">{doc.documentNumber}</td>
                <td className="p-4">
                  <a href={`http://localhost:8080${doc.fileUrl}`} target="_blank" rel="noreferrer" className="text-blue-600 flex items-center gap-1 hover:underline">
                    <FileText size={16} /> View <ExternalLink size={12}/>
                  </a>
                </td>
                <td className="p-4 flex gap-2">
                  <button onClick={() => handleReview(doc.id, 'APPROVED')} className="bg-green-100 text-green-700 p-2 rounded hover:bg-green-200"><Check size={18}/></button>
                  <button onClick={() => handleReview(doc.id, 'REJECTED')} className="bg-red-100 text-red-700 p-2 rounded hover:bg-red-200"><X size={18}/></button>
                </td>
              </tr>
            ))}
            {docs.length === 0 && <tr><td colSpan="5" className="p-8 text-center text-gray-500">No pending requests</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminKyc;