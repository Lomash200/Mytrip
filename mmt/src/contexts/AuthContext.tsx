import React, { createContext, useContext, useState, useEffect } from 'react';
import { authService, userService } from '../services/api';
import { User, LoginRequest, RegisterRequest, UserDto } from '../types';

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (username: string, password: string) => Promise<void>;
  signup: (userData: RegisterRequest) => Promise<void>;
  logout: () => void;
  loading: boolean;
  switchRole: (role: 'USER' | 'ADMIN') => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const savedUser = localStorage.getItem('user');
    
    if (token && savedUser) {
      try {
        const userData: User = JSON.parse(savedUser);
        setUser(userData);
        
        // Verify token is still valid by fetching user profile
        userService.getProfile().then(profile => {
          const updatedUser: User = {
            ...profile,
            role: userData.role // Preserve the role
          };
          setUser(updatedUser);
          localStorage.setItem('user', JSON.stringify(updatedUser));
        }).catch(() => {
          // Token is invalid, logout
          logout();
        });
      } catch (error) {
        logout();
      }
    }
    setLoading(false);
  }, []);

  const login = async (username: string, password: string) => {
    setLoading(true);
    try {
      const loginRequest: LoginRequest = { username, password };
      const response = await authService.login(loginRequest);
      
      localStorage.setItem('token', response.token);
      
      // Fetch user profile to get complete user data
      const userProfile = await userService.getProfile();
      
      // Determine role based on backend response or other logic
      // For now, we'll use a simple check - you might want to adjust this
      const userData: User = {
        ...userProfile,
        role: userProfile.username.includes('admin') ? 'ADMIN' : 'USER'
      };
      
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
    } catch (error) {
      console.error('Login failed:', error);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const signup = async (userData: RegisterRequest) => {
    setLoading(true);
    try {
      await authService.signup(userData);
      // After successful signup, automatically login
      await login(userData.username, userData.password);
    } catch (error) {
      console.error('Signup failed:', error);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const switchRole = (role: 'USER' | 'ADMIN') => {
    if (user) {
      const updatedUser = { ...user, role };
      setUser(updatedUser);
      localStorage.setItem('user', JSON.stringify(updatedUser));
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{
      user,
      isAuthenticated: !!user,
      login,
      signup,
      logout,
      loading,
      switchRole
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};