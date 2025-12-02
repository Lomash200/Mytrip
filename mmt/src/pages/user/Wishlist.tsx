import React, { useState, useEffect } from 'react';
import { Heart, MapPin, Star, IndianRupee, Trash2, Calendar } from 'lucide-react';
import { wishlistService } from '../../services/api';

interface WishlistItem {
  id: number;
  type: 'HOTEL' | 'FLIGHT' | 'DESTINATION';
  itemId: number;
  name: string;
  location?: string;
  price?: number;
  rating?: number;
  image: string;
  addedAt: string;
}

const Wishlist: React.FC = () => {
  const [wishlist, setWishlist] = useState<WishlistItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('ALL');

  useEffect(() => {
    loadWishlist();
  }, []);

  const loadWishlist = async () => {
    try {
      // Mock data for demonstration
      const mockWishlist: WishlistItem[] = [
        {
          id: 1,
          type: 'HOTEL',
          itemId: 101,
          name: 'Grand Plaza Hotel',
          location: 'New York, USA',
          price: 12000,
          rating: 4.5,
          image: '/api/placeholder/300/200',
          addedAt: '2024-11-20'
        },
        {
          id: 2,
          type: 'HOTEL',
          itemId: 102,
          name: 'Beach Resort & Spa',
          location: 'Miami, USA',
          price: 18000,
          rating: 4.8,
          image: '/api/placeholder/300/200',
          addedAt: '2024-11-18'
        },
        {
          id: 3,
          type: 'FLIGHT',
          itemId: 201,
          name: 'New York to Paris',
          location: 'Direct Flight',
          price: 45000,
          image: '/api/placeholder/300/200',
          addedAt: '2024-11-15'
        }
      ];
      setWishlist(mockWishlist);
    } catch (error) {
      console.error('Failed to load wishlist:', error);
    } finally {
      setLoading(false);
    }
  };

  const removeFromWishlist = async (type: string, targetId: number) => {
    try {
      await wishlistService.remove(type, targetId);
      setWishlist(prev => prev.filter(item => !(item.type === type && item.itemId === targetId)));
    } catch (error) {
      console.error('Failed to remove from wishlist:', error);
    }
  };

  const filteredWishlist = filter === 'ALL' 
    ? wishlist 
    : wishlist.filter(item => item.type === filter);

  const getTypeColor = (type: string) => {
    switch (type) {
      case 'HOTEL': return 'bg-blue-100 text-blue-800';
      case 'FLIGHT': return 'bg-green-100 text-green-800';
      case 'DESTINATION': return 'bg-purple-100 text-purple-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex justify-center items-center h-64">
          <div className="text-lg">Loading your wishlist...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">My Wishlist</h1>
        <p className="text-gray-600">Your saved hotels, flights, and destinations</p>
      </div>

      {/* Filter Tabs */}
      <div className="bg-white rounded-xl shadow-sm p-4 mb-6">
        <div className="flex space-x-4 overflow-x-auto">
          {['ALL', 'HOTEL', 'FLIGHT', 'DESTINATION'].map((type) => (
            <button
              key={type}
              onClick={() => setFilter(type)}
              className={`px-4 py-2 rounded-lg font-medium whitespace-nowrap ${
                filter === type
                  ? 'bg-blue-600 text-white'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              {type === 'ALL' ? 'All Items' : type.charAt(0) + type.slice(1).toLowerCase() + 's'}
              {filter === type && (
                <span className="ml-2 bg-blue-500 text-white px-2 py-1 text-xs rounded-full">
                  {type === 'ALL' ? wishlist.length : wishlist.filter(item => item.type === type).length}
                </span>
              )}
            </button>
          ))}
        </div>
      </div>

      {/* Wishlist Items */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredWishlist.length === 0 ? (
          <div className="col-span-3 bg-white rounded-xl shadow-md p-8 text-center">
            <Heart className="w-16 h-16 text-gray-400 mx-auto mb-4" />
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Your wishlist is empty</h3>
            <p className="text-gray-600 mb-4">
              {filter === 'ALL' 
                ? "You haven't added any items to your wishlist yet."
                : `No ${filter.toLowerCase()}s in your wishlist.`
              }
            </p>
            <button className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700">
              Explore Properties
            </button>
          </div>
        ) : (
          filteredWishlist.map((item) => (
            <div key={item.id} className="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow">
              <div className="relative">
                <img 
                  src={item.image} 
                  alt={item.name}
                  className="w-full h-48 object-cover"
                />
                <button
                  onClick={() => removeFromWishlist(item.type, item.itemId)}
                  className="absolute top-3 right-3 p-2 bg-white rounded-full shadow-md hover:bg-red-50 hover:text-red-600 transition-colors"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
                <div className="absolute top-3 left-3">
                  <span className={`px-2 py-1 rounded-full text-xs font-medium ${getTypeColor(item.type)}`}>
                    {item.type}
                  </span>
                </div>
              </div>

              <div className="p-4">
                <h3 className="font-semibold text-lg text-gray-900 mb-2">{item.name}</h3>
                
                {item.location && (
                  <div className="flex items-center text-gray-600 mb-2">
                    <MapPin className="w-4 h-4 mr-1" />
                    <span className="text-sm">{item.location}</span>
                  </div>
                )}

                {item.rating && (
                  <div className="flex items-center mb-3">
                    <Star className="w-4 h-4 text-yellow-400 fill-current" />
                    <span className="ml-1 text-sm font-medium">{item.rating}</span>
                  </div>
                )}

                <div className="flex justify-between items-center mb-3">
                  {item.price && (
                    <div className="flex items-center text-gray-900">
                      <IndianRupee className="w-4 h-4 mr-1" />
                      <span className="text-xl font-bold">{item.price}</span>
                      <span className="text-gray-600 text-sm ml-1">
                        {item.type === 'HOTEL' ? '/ night' : ''}
                      </span>
                    </div>
                  )}
                </div>

                <div className="flex justify-between items-center pt-3 border-t border-gray-200">
                  <div className="flex items-center text-gray-500 text-sm">
                    <Calendar className="w-4 h-4 mr-1" />
                    Added {new Date(item.addedAt).toLocaleDateString()}
                  </div>
                  <button className="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700">
                    {item.type === 'HOTEL' ? 'Book Now' : 'View Details'}
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Wishlist;