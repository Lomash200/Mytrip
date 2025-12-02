-- V2__add_hotels_rooms.sql
-- Add hotels and rooms tables if they don't exist

-- First, create hotels table if it doesn't exist
CREATE TABLE IF NOT EXISTS hotel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    address VARCHAR(500),
    description TEXT,
    rating DOUBLE PRECISION DEFAULT 0.0,
    star_category INT DEFAULT 3,
    image_url VARCHAR(500)
);

-- Create hotel_amenities table for amenities list
CREATE TABLE IF NOT EXISTS hotel_amenities (
    hotel_id BIGINT NOT NULL,
    amenities VARCHAR(100),
    FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE
);

-- Create rooms table if it doesn't exist
CREATE TABLE IF NOT EXISTS room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(50),
    room_type VARCHAR(50),
    capacity INT DEFAULT 2,
    max_guests INT DEFAULT 2,
    price_per_night DOUBLE PRECISION,
    description TEXT,
    availability_count INT DEFAULT 1,
    image_url VARCHAR(500),
    hotel_id BIGINT,
    FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE
);

-- Create room_images table for multiple images
CREATE TABLE IF NOT EXISTS room_images (
    room_id BIGINT NOT NULL,
    images VARCHAR(500),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Insert sample hotels
INSERT INTO hotel (name, city, address, description, rating, star_category, image_url) VALUES
('Taj Palace', 'Mumbai', 'Colaba, Mumbai', 'Luxury hotel with sea view', 4.8, 5, '/uploads/hotels/taj.jpg'),
('The Oberoi', 'Delhi', 'Dr. Zakir Hussain Marg, Delhi', '5-star luxury hotel', 4.7, 5, '/uploads/hotels/oberoi.jpg'),
('ITC Grand Chola', 'Chennai', 'Guindy, Chennai', 'Business luxury hotel', 4.6, 5, '/uploads/hotels/itc.jpg'),
('The Leela Palace', 'Bangalore', 'Old Airport Road, Bangalore', 'Palace style luxury', 4.5, 5, '/uploads/hotels/leela.jpg'),
('Hyatt Regency', 'Delhi', 'Bhikaji Cama Place, Delhi', 'Business hotel', 4.4, 5, '/uploads/hotels/hyatt.jpg');

-- Insert sample rooms
INSERT INTO room (room_number, room_type, capacity, max_guests, price_per_night, description, availability_count, hotel_id) VALUES
-- Taj Palace rooms
('101', 'DELUXE', 2, 2, 8000.00, 'Sea view room with balcony', 5, 1),
('102', 'SUITE', 4, 4, 15000.00, 'Luxury suite with jacuzzi', 3, 1),
('103', 'STANDARD', 2, 2, 5000.00, 'Standard room with amenities', 10, 1),

-- Oberoi rooms
('201', 'DELUXE', 2, 2, 9000.00, 'City view deluxe room', 6, 2),
('202', 'PRESIDENTIAL', 6, 6, 30000.00, 'Presidential suite', 1, 2),

-- ITC Grand Chola rooms
('301', 'EXECUTIVE', 2, 2, 7000.00, 'Executive room for business', 8, 3),
('302', 'SUITE', 3, 3, 12000.00, 'Family suite', 4, 3),

-- Leela Palace rooms
('401', 'ROYAL', 2, 2, 10000.00, 'Royal treatment room', 5, 4),
('402', 'DELUXE', 2, 2, 7500.00, 'Deluxe room with garden view', 7, 4),

-- Hyatt Regency rooms
('501', 'STANDARD', 2, 2, 6000.00, 'Standard business room', 12, 5),
('502', 'EXECUTIVE', 2, 2, 8500.00, 'Executive lounge access', 6, 5);

-- Insert sample amenities
INSERT INTO hotel_amenities (hotel_id, amenities) VALUES
(1, 'Free WiFi'), (1, 'Swimming Pool'), (1, 'Spa'), (1, 'Gym'),
(2, 'Free WiFi'), (2, 'Restaurant'), (2, 'Bar'), (2, 'Airport Shuttle'),
(3, 'Free WiFi'), (3, 'Conference Room'), (3, 'Business Center'), (3, 'Gym'),
(4, 'Free WiFi'), (4, 'Swimming Pool'), (4, 'Spa'), (4, 'Fine Dining'),
(5, 'Free WiFi'), (5, 'Gym'), (5, 'Restaurant'), (5, 'Parking');