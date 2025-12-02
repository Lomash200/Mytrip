import React, { useState } from 'react';
import { Upload, CheckCircle, XCircle, FileText, Shield } from 'lucide-react';
import { kycService } from '../../services/api';

const KycUpload = ({ onUploadComplete }) => {
  const [uploading, setUploading] = useState(false);
  const [formData, setFormData] = useState({
    documentType: 'AADHAAR',
    documentNumber: '',
    frontImage: null,
    backImage: null,
  });

  const documentTypes = [
    { value: 'AADHAAR', label: 'Aadhaar Card' },
    { value: 'PASSPORT', label: 'Passport' },
    { value: 'DRIVING_LICENSE', label: 'Driving License' },
    { value: 'PAN_CARD', label: 'PAN Card' },
  ];

  const handleFileChange = (side, file) => {
    if (file.size > 5 * 1024 * 1024) {
      alert('File size must be less than 5MB');
      return;
    }
    
    if (!file.type.startsWith('image/')) {
      alert('Please upload an image file');
      return;
    }

    setFormData(prev => ({
      ...prev,
      [side === 'front' ? 'frontImage' : 'backImage']: file,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.frontImage) {
      alert('Please upload the front side of the document');
      return;
    }

    setUploading(true);
    try {
      const submitData = new FormData();
      submitData.append('documentType', formData.documentType);
      submitData.append('documentNumber', formData.documentNumber);
      submitData.append('frontImage', formData.frontImage);

      if (formData.backImage) {
        submitData.append('backImage', formData.backImage);
      }

      await kycService.upload(submitData);
      alert('KYC document uploaded successfully! It will be reviewed within 24 hours.');
      onUploadComplete();
    } catch (error) {
      console.error('KYC upload failed:', error);
      alert('Failed to upload document. Please try again.');
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto bg-white rounded-xl shadow-md p-6">
      <div className="flex items-center mb-6">
        <Shield className="w-8 h-8 text-blue-600 mr-3" />
        <div>
          <h2 className="text-2xl font-bold text-gray-900">KYC Verification</h2>
          <p className="text-gray-600">Verify your identity to access all features</p>
        </div>
      </div>

      <form onSubmit={handleSubmit} className="space-y-6">
        
        {/* Document Type */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Document Type
          </label>

          <select
            value={formData.documentType}
            onChange={(e) =>
              setFormData(prev => ({ ...prev, documentType: e.target.value }))
            }
            className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
            required
          >
            {documentTypes.map(type => (
              <option key={type.value} value={type.value}>
                {type.label}
              </option>
            ))}
          </select>
        </div>

        {/* Document Number */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Document Number
          </label>
          <input
            type="text"
            value={formData.documentNumber}
            onChange={(e) =>
              setFormData(prev => ({ ...prev, documentNumber: e.target.value }))
            }
            className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
            placeholder="Enter document number"
            required
          />
        </div>

        {/* Front Image Upload */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Front Side of Document *
          </label>

          <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center">
            {formData.frontImage ? (
              <div className="text-green-600">
                <CheckCircle className="w-8 h-8 mx-auto mb-2" />
                <p>{formData.frontImage.name}</p>
                <button
                  type="button"
                  onClick={() =>
                    setFormData(prev => ({ ...prev, frontImage: null }))
                  }
                  className="text-red-600 text-sm mt-2 hover:text-red-800"
                >
                  Remove
                </button>
              </div>
            ) : (
              <div>
                <Upload className="w-8 h-8 mx-auto text-gray-400" />
                <p className="text-sm text-gray-600">Click to upload or drag & drop</p>
                <input
                  type="file"
                  className="hidden"
                  id="front-upload"
                  accept="image/*"
                  onChange={(e) =>
                    e.target.files?.[0] && handleFileChange('front', e.target.files[0])
                  }
                />
                <label
                  htmlFor="front-upload"
                  className="inline-block mt-3 px-4 py-2 bg-blue-600 text-white rounded-lg cursor-pointer hover:bg-blue-700"
                >
                  Choose File
                </label>
              </div>
            )}
          </div>
        </div>

        {/* Back Image Upload */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Back Side of Document (Optional)
          </label>

          <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center">
            {formData.backImage ? (
              <div className="text-green-600">
                <CheckCircle className="w-8 h-8 mx-auto mb-2" />
                <p>{formData.backImage.name}</p>
                <button
                  type="button"
                  onClick={() =>
                    setFormData(prev => ({ ...prev, backImage: null }))
                  }
                  className="text-red-600 text-sm mt-2 hover:text-red-800"
                >
                  Remove
                </button>
              </div>
            ) : (
              <div>
                <Upload className="w-8 h-8 mx-auto text-gray-400" />
                <p className="text-sm text-gray-600">Click to upload or drag & drop</p>

                <input
                  type="file"
                  className="hidden"
                  id="back-upload"
                  accept="image/*"
                  onChange={(e) =>
                    e.target.files?.[0] && handleFileChange('back', e.target.files[0])
                  }
                />

                <label
                  htmlFor="back-upload"
                  className="inline-block mt-3 px-4 py-2 bg-gray-600 text-white rounded-lg cursor-pointer hover:bg-gray-700"
                >
                  Choose File
                </label>
              </div>
            )}
          </div>
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          disabled={uploading || !formData.frontImage}
          className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {uploading ? 'Uploading...' : 'Submit KYC Document'}
        </button>
      </form>

      {/* Security Notice */}
      <div className="mt-6 p-4 bg-blue-50 rounded-lg">
        <div className="flex items-start">
          <Shield className="w-5 h-5 text-blue-600 mr-3" />
          <div>
            <h4 className="font-medium text-blue-900 mb-1">Your data is secure</h4>
            <p className="text-blue-800 text-sm">
              We use bank-level encryption to protect your documents.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default KycUpload;
