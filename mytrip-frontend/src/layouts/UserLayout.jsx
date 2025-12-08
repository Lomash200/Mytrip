import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../components/common/Navbar';
import Footer from '../components/common/Footer';

const UserLayout = () => {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Navbar Hamesha upar rahega */}
      <Navbar />
      
      {/* Outlet ka matlab hai: Jo bhi page khulega wo yaha beech me aayega */}
      <main className="flex-1">
        <Outlet />
      </main>

      {/* Footer Hamesha neeche rahega */}
      <Footer />
    </div>
  );
};

export default UserLayout;