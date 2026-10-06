export type ListingType = 'BUY' | 'SELL' | 'RENT' | 'EXCHANGE' | 'HOUSING' | 'GIG';

export type ProductCondition = 'NEW' | 'LIKE_NEW' | 'GOOD' | 'FAIR';

export interface Campus {
  id: string;
  code: string;
  name: string;
  city: string;
  state: string;
  studentCount: number;
  activeListingsCount: number;
  verificationDomain: string;
  zones: string[];
  popularCollections: string[];
  moveOutActive: boolean;
}

export interface ListingCategory {
  id: string;
  name: string;
  iconName: string;
  count: number;
}

export interface ProductListing {
  id: string;
  title: string;
  price: number;
  originalPrice?: number | null;
  listingType: ListingType;
  condition: ProductCondition;
  categoryId: string;
  categoryName: string;
  description: string;
  imageUrl: string;
  location: string;
  campusId: string;
  campusCode: string;
  campusCity: string;
  isUrgent?: boolean;
  isVerified?: boolean;
  sellerId: string;
  sellerName: string;
  sellerAvatar?: string | null;
  sellerRating?: number;
  sellerDepartment?: string;
  isExchangeEligible?: boolean;
  exchangePreferences?: string;
  rentalDurationUnit?: string;
  isSaved?: boolean;
  isSold?: boolean;
  isNegotiable?: boolean;
  createdAt?: string;
}

export interface StudentUser {
  id: string;
  name: string;
  email: string;
  university: string;
  campus: string;
  campusId: string;
  hostel: string;
  isVerified: boolean;
  activeListings: number;
  soldListings: number;
  totalSavedInr: number;
  trustScore: number;
  department: string;
  year: string;
  avatarUrl?: string | null;
}

export interface HousingListing {
  id: string;
  title: string;
  rentPrice: number;
  deposit: number;
  type: string;
  occupancy: string;
  distanceToCampus: string;
  address: string;
  campusId: string;
  amenities: string[];
  imageUrl: string;
  contactPhone: string;
  isVerified: boolean;
}

export interface RoommateListing {
  id: string;
  studentName: string;
  gender: string;
  course: string;
  budgetPerMonth: number;
  preferredLocation: string;
  campusId: string;
  habits: string[];
  bio: string;
}

export interface CampusServiceItem {
  id: string;
  title: string;
  category: string;
  providerName: string;
  campusId: string;
  rating: number;
  priceStarting: number;
  description: string;
  deliveryTime: string;
  tags: string[];
}

export interface ChatMessage {
  id: string;
  conversationId: string;
  senderId: string;
  text: string;
  timestamp: string;
  isMine: boolean;
  isOffer?: boolean;
  offerAmount?: number | null;
}

export interface Conversation {
  id: string;
  otherUserName: string;
  otherUserAvatar?: string | null;
  otherUserCampus: string;
  lastMessage: string;
  lastTimestamp: string;
  unreadCount: number;
  listingId?: string | null;
  listingTitle?: string | null;
  listingPrice?: number | null;
  listingImage?: string | null;
  // Deal Room state machine
  dealStatus?: 'NEGOTIATING' | 'OFFER_MADE' | 'OFFER_ACCEPTED' | 'PICKUP_SCHEDULED' | 'COMPLETED';
  agreedPrice?: number;
  pickupLocation?: string;
  pickupTime?: string;
}

export type AppScreen =
  | 'HOME'
  | 'EXPLORE'
  | 'SELL'
  | 'SERVICES'
  | 'INBOX'
  | 'PROFILE'
  | 'CAMPUS_HUB'
  | 'PRODUCT_DETAIL'
  | 'HOUSING_DETAIL'
  | 'CHAT';

export type ServicesTab = 'SERVICES' | 'HOUSING' | 'ROOMMATES';
