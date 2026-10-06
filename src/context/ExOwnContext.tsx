import React, { createContext, useContext, useState, useMemo, useEffect } from 'react';
import {
  Campus,
  ListingCategory,
  ListingType,
  ProductCondition,
  ProductListing,
  StudentUser,
  HousingListing,
  RoommateListing,
  CampusServiceItem,
  Conversation,
  ChatMessage,
  AppScreen,
  ServicesTab,
} from '../types';
import {
  campusesData,
  categoriesData,
  initialListingsData,
  initialCurrentUser,
  initialHousingData,
  initialRoommatesData,
  initialCampusServicesData,
  initialConversationsData,
  initialChatMessagesData,
} from '../data/mockData';

interface ExOwnContextType {
  currentScreen: AppScreen;
  navigateTo: (screen: AppScreen) => void;
  navigateBack: () => void;
  canNavigateBack: boolean;

  // Auth
  isAuthenticated: boolean;
  currentUser: StudentUser;
  signInStudent: (name: string, email: string, university: string, campus: string, hostel: string) => void;
  signOutStudent: () => void;

  // Campus
  campuses: Campus[];
  selectedCampus: Campus;
  selectCampus: (campus: Campus) => void;
  selectCampusById: (campusId: string) => void;
  campusScopeOnly: boolean;
  setCampusScopeOnly: (scopeOnly: boolean) => void;
  toggleCampusScope: () => void;
  showCampusSwitcher: boolean;
  openCampusSwitcher: () => void;
  closeCampusSwitcher: () => void;

  // Categories & Data
  categories: ListingCategory[];
  allListings: ProductListing[];
  filteredListings: ProductListing[];
  savedListings: ProductListing[];
  myListings: ProductListing[];
  savedListingIds: Set<string>;
  toggleSave: (id: string) => void;
  addListing: (newListing: Omit<ProductListing, 'id' | 'createdAt' | 'sellerId' | 'sellerName' | 'sellerAvatar' | 'sellerRating' | 'sellerDepartment'>) => void;
  markListingSold: (id: string) => void;
  deleteListing: (id: string) => void;

  // Search & Filter
  searchQuery: string;
  setSearchQuery: (query: string) => void;
  selectedCategoryId: string;
  setSelectedCategoryId: (catId: string) => void;
  selectedListingType: ListingType | null;
  setSelectedListingType: (type: ListingType | null) => void;
  selectedCondition: ProductCondition | null;
  setSelectedCondition: (cond: ProductCondition | null) => void;
  filterOnlyUrgent: boolean;
  toggleFilterOnlyUrgent: () => void;
  filterOnlyExchange: boolean;
  toggleFilterOnlyExchange: () => void;
  sortBy: string;
  setSortBy: (sort: string) => void;
  resetFilters: () => void;

  // Detail views
  selectedProduct: ProductListing | null;
  viewProductDetails: (product: ProductListing) => void;
  selectedHousing: HousingListing | null;
  viewHousingDetails: (housing: HousingListing) => void;

  // Services tab
  servicesTab: ServicesTab;
  setServicesTab: (tab: ServicesTab) => void;
  housingListings: HousingListing[];
  roommateListings: RoommateListing[];
  campusServices: CampusServiceItem[];

  // Chat & Deal Room
  conversations: Conversation[];
  activeConversation: Conversation | null;
  activeMessages: ChatMessage[];
  openConversation: (conv: Conversation) => void;
  startChatForProduct: (product: ProductListing) => void;
  sendMessage: (text: string, isOffer?: boolean, offerAmount?: number) => void;
  updateDealStatus: (
    convId: string,
    status: 'NEGOTIATING' | 'OFFER_MADE' | 'OFFER_ACCEPTED' | 'PICKUP_SCHEDULED' | 'COMPLETED',
    details?: { price?: number; pickup?: string; time?: string }
  ) => void;

  // Modals
  isTrustPassportOpen: boolean;
  setIsTrustPassportOpen: (open: boolean) => void;
  isSellModalOpen: boolean;
  setIsSellModalOpen: (open: boolean) => void;
  isDealRoomOpen: boolean;
  setIsDealRoomOpen: (open: boolean) => void;
  isDarkTheme: boolean;
  setIsDarkTheme: (dark: boolean) => void;
}

const ExOwnContext = createContext<ExOwnContextType | undefined>(undefined);

export const ExOwnProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [screenStack, setScreenStack] = useState<AppScreen[]>(['HOME']);
  const currentScreen = screenStack[screenStack.length - 1];

  const navigateTo = (screen: AppScreen) => {
    if (screen === currentScreen) return;
    setScreenStack((prev) => [...prev, screen]);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const navigateBack = () => {
    if (screenStack.length > 1) {
      setScreenStack((prev) => prev.slice(0, prev.length - 1));
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  // Auth State
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(true);
  const [currentUser, setCurrentUser] = useState<StudentUser>(initialCurrentUser);

  const signInStudent = (
    name: string,
    email: string,
    university: string,
    campus: string,
    hostel: string
  ) => {
    const campusObj = campusesData.find((c) =>
      c.name.toLowerCase().includes(university.toLowerCase())
    ) || campusesData[0];

    const newUser: StudentUser = {
      id: `user-${Date.now()}`,
      name: name.trim() || 'Campus Student',
      email: email.trim(),
      university: university.trim() || campusObj.name,
      campus: campus.trim() || `${campusObj.code} • ${campusObj.city}`,
      campusId: campusObj.id,
      hostel: hostel.trim() || 'Hostel Block A',
      isVerified: true,
      activeListings: 1,
      soldListings: 0,
      totalSavedInr: 2500,
      trustScore: 95,
      department: 'Computer Science',
      year: '2nd Year',
      avatarUrl: null,
    };
    setCurrentUser(newUser);
    setSelectedCampus(campusObj);
    setIsAuthenticated(true);
    navigateTo('HOME');
  };

  const signOutStudent = () => {
    setIsAuthenticated(false);
    navigateTo('HOME');
  };

  // Campus Management
  const [campuses] = useState<Campus[]>(campusesData);
  const [selectedCampus, setSelectedCampus] = useState<Campus>(campusesData[0]);
  const [campusScopeOnly, setCampusScopeOnly] = useState<boolean>(true);
  const [showCampusSwitcher, setShowCampusSwitcher] = useState<boolean>(false);

  const selectCampus = (campus: Campus) => {
    setSelectedCampus(campus);
    setShowCampusSwitcher(false);
  };

  const selectCampusById = (campusId: string) => {
    const found = campuses.find((c) => c.id.toLowerCase() === campusId.toLowerCase());
    if (found) {
      setSelectedCampus(found);
      setShowCampusSwitcher(false);
    }
  };

  const toggleCampusScope = () => {
    setCampusScopeOnly((prev) => !prev);
  };

  const openCampusSwitcher = () => setShowCampusSwitcher(true);
  const closeCampusSwitcher = () => setShowCampusSwitcher(false);

  // Listings & Persistence
  const [allListings, setAllListings] = useState<ProductListing[]>(initialListingsData);
  const [savedListingIds, setSavedListingIds] = useState<Set<string>>(new Set(['item-2']));

  const toggleSave = (id: string) => {
    setSavedListingIds((prev) => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  };

  const addListing = (
    newListingData: Omit<
      ProductListing,
      'id' | 'createdAt' | 'sellerId' | 'sellerName' | 'sellerAvatar' | 'sellerRating' | 'sellerDepartment'
    >
  ) => {
    const fullListing: ProductListing = {
      ...newListingData,
      id: `item-${Date.now()}`,
      sellerId: currentUser.id,
      sellerName: currentUser.name,
      sellerAvatar: currentUser.avatarUrl,
      sellerRating: 5.0,
      sellerDepartment: currentUser.department,
      isVerified: true,
      createdAt: 'Just now',
      isSold: false,
    };
    setAllListings((prev) => [fullListing, ...prev]);
    setCurrentUser((prev) => ({
      ...prev,
      activeListings: prev.activeListings + 1,
    }));
  };

  const markListingSold = (id: string) => {
    setAllListings((prev) =>
      prev.map((item) => (item.id === id ? { ...item, isSold: true } : item))
    );
    setCurrentUser((prev) => ({
      ...prev,
      soldListings: prev.soldListings + 1,
      activeListings: Math.max(0, prev.activeListings - 1),
    }));
  };

  const deleteListing = (id: string) => {
    setAllListings((prev) => prev.filter((item) => item.id !== id));
  };

  // Search & Filters
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategoryId, setSelectedCategoryId] = useState('all');
  const [selectedListingType, setSelectedListingType] = useState<ListingType | null>(null);
  const [selectedCondition, setSelectedCondition] = useState<ProductCondition | null>(null);
  const [filterOnlyUrgent, setFilterOnlyUrgent] = useState(false);
  const [filterOnlyExchange, setFilterOnlyExchange] = useState(false);
  const [sortBy, setSortBy] = useState('newest');

  const toggleFilterOnlyUrgent = () => setFilterOnlyUrgent((prev) => !prev);
  const toggleFilterOnlyExchange = () => setFilterOnlyExchange((prev) => !prev);

  const resetFilters = () => {
    setSearchQuery('');
    setSelectedCategoryId('all');
    setSelectedListingType(null);
    setSelectedCondition(null);
    setFilterOnlyUrgent(false);
    setFilterOnlyExchange(false);
    setSortBy('newest');
  };

  // Filtered Listings
  const filteredListings = useMemo(() => {
    let list = allListings;

    if (campusScopeOnly) {
      list = list.filter((item) => item.campusId.toLowerCase() === selectedCampus.id.toLowerCase());
    }

    if (selectedCategoryId !== 'all') {
      list = list.filter((item) => item.categoryId === selectedCategoryId);
    }

    if (searchQuery.trim()) {
      const q = searchQuery.toLowerCase().trim();
      list = list.filter(
        (item) =>
          item.title.toLowerCase().includes(q) ||
          item.description.toLowerCase().includes(q) ||
          item.categoryName.toLowerCase().includes(q) ||
          item.location.toLowerCase().includes(q)
      );
    }

    if (selectedListingType) {
      list = list.filter((item) => item.listingType === selectedListingType);
    }

    if (selectedCondition) {
      list = list.filter((item) => item.condition === selectedCondition);
    }

    if (filterOnlyUrgent) {
      list = list.filter((item) => item.isUrgent);
    }

    if (filterOnlyExchange) {
      list = list.filter((item) => item.isExchangeEligible);
    }

    if (sortBy === 'price_low') {
      list = [...list].sort((a, b) => a.price - b.price);
    } else if (sortBy === 'price_high') {
      list = [...list].sort((a, b) => b.price - a.price);
    }

    return list.map((item) => ({
      ...item,
      isSaved: savedListingIds.has(item.id),
    }));
  }, [
    allListings,
    selectedCampus,
    campusScopeOnly,
    selectedCategoryId,
    searchQuery,
    selectedListingType,
    selectedCondition,
    filterOnlyUrgent,
    filterOnlyExchange,
    sortBy,
    savedListingIds,
  ]);

  const savedListings = useMemo(() => {
    return allListings
      .filter((item) => savedListingIds.has(item.id))
      .map((item) => ({ ...item, isSaved: true }));
  }, [allListings, savedListingIds]);

  const myListings = useMemo(() => {
    return allListings.filter((item) => item.sellerId === currentUser.id);
  }, [allListings, currentUser.id]);

  // Selected Detail views
  const [selectedProduct, setSelectedProduct] = useState<ProductListing | null>(null);
  const [selectedHousing, setSelectedHousing] = useState<HousingListing | null>(null);

  const viewProductDetails = (product: ProductListing) => {
    setSelectedProduct(product);
    navigateTo('PRODUCT_DETAIL');
  };

  const viewHousingDetails = (housing: HousingListing) => {
    setSelectedHousing(housing);
    navigateTo('HOUSING_DETAIL');
  };

  // Services Tab
  const [servicesTab, setServicesTab] = useState<ServicesTab>('SERVICES');
  const [housingListings] = useState<HousingListing[]>(initialHousingData);
  const [roommateListings] = useState<RoommateListing[]>(initialRoommatesData);
  const [campusServices] = useState<CampusServiceItem[]>(initialCampusServicesData);

  // Chat & Conversations
  const [conversations, setConversations] = useState<Conversation[]>(initialConversationsData);
  const [chatMessages, setChatMessages] = useState<Record<string, ChatMessage[]>>(initialChatMessagesData);
  const [activeConversation, setActiveConversation] = useState<Conversation | null>(null);

  const activeMessages = useMemo(() => {
    if (!activeConversation) return [];
    return chatMessages[activeConversation.id] || [];
  }, [activeConversation, chatMessages]);

  const openConversation = (conv: Conversation) => {
    setActiveConversation(conv);
    navigateTo('CHAT');
  };

  const startChatForProduct = (product: ProductListing) => {
    const existing = conversations.find((c) => c.listingId === product.id);
    if (existing) {
      openConversation(existing);
      return;
    }

    const newConv: Conversation = {
      id: `conv-${Date.now()}`,
      otherUserName: product.sellerName,
      otherUserCampus: product.campusCode,
      lastMessage: `Started chat about ${product.title}`,
      lastTimestamp: 'Just now',
      unreadCount: 0,
      listingId: product.id,
      listingTitle: product.title,
      listingPrice: product.price,
      listingImage: product.imageUrl,
      dealStatus: 'NEGOTIATING',
      agreedPrice: product.price,
      pickupLocation: product.location,
    };

    const initialMsg: ChatMessage = {
      id: `m-${Date.now()}`,
      conversationId: newConv.id,
      senderId: currentUser.id,
      text: `Hi ${product.sellerName}, is this available on campus?`,
      timestamp: 'Just now',
      isMine: true,
    };

    setConversations((prev) => [newConv, ...prev]);
    setChatMessages((prev) => ({
      ...prev,
      [newConv.id]: [initialMsg],
    }));

    setActiveConversation(newConv);
    navigateTo('CHAT');
  };

  const sendMessage = (text: string, isOffer = false, offerAmount?: number) => {
    if (!activeConversation) return;

    const newMsg: ChatMessage = {
      id: `m-${Date.now()}`,
      conversationId: activeConversation.id,
      senderId: currentUser.id,
      text,
      timestamp: 'Just now',
      isMine: true,
      isOffer,
      offerAmount,
    };

    setChatMessages((prev) => ({
      ...prev,
      [activeConversation.id]: [...(prev[activeConversation.id] || []), newMsg],
    }));

    setConversations((prev) =>
      prev.map((c) =>
        c.id === activeConversation.id
          ? {
              ...c,
              lastMessage: isOffer ? `Offer made: ₹${offerAmount}` : text,
              lastTimestamp: 'Just now',
              dealStatus: isOffer ? 'OFFER_MADE' : c.dealStatus,
              agreedPrice: isOffer ? offerAmount : c.agreedPrice,
            }
          : c
      )
    );

    // Auto simulated seller response after 1.5 seconds if an offer is sent
    if (isOffer && offerAmount) {
      setTimeout(() => {
        const sellerMsg: ChatMessage = {
          id: `m-${Date.now() + 1}`,
          conversationId: activeConversation.id,
          senderId: 'seller',
          text: `Deal! I accept ₹${offerAmount.toLocaleString('en-IN')}. Can we meet at ${activeConversation.pickupLocation || 'Hostel Reception'}?`,
          timestamp: 'Just now',
          isMine: false,
        };

        setChatMessages((prev) => ({
          ...prev,
          [activeConversation.id]: [...(prev[activeConversation.id] || []), sellerMsg],
        }));

        setConversations((prev) =>
          prev.map((c) =>
            c.id === activeConversation.id
              ? {
                  ...c,
                  dealStatus: 'OFFER_ACCEPTED',
                  agreedPrice: offerAmount,
                  lastMessage: `Accepted offer: ₹${offerAmount}`,
                  lastTimestamp: 'Just now',
                }
              : c
          )
        );
      }, 1200);
    }
  };

  const updateDealStatus = (
    convId: string,
    status: 'NEGOTIATING' | 'OFFER_MADE' | 'OFFER_ACCEPTED' | 'PICKUP_SCHEDULED' | 'COMPLETED',
    details?: { price?: number; pickup?: string; time?: string }
  ) => {
    setConversations((prev) =>
      prev.map((c) => {
        if (c.id === convId) {
          return {
            ...c,
            dealStatus: status,
            agreedPrice: details?.price ?? c.agreedPrice,
            pickupLocation: details?.pickup ?? c.pickupLocation,
            pickupTime: details?.time ?? c.pickupTime,
          };
        }
        return c;
      })
    );
  };

  // Modals
  const [isTrustPassportOpen, setIsTrustPassportOpen] = useState(false);
  const [isSellModalOpen, setIsSellModalOpen] = useState(false);
  const [isDealRoomOpen, setIsDealRoomOpen] = useState(false);
  const [isDarkTheme, setIsDarkTheme] = useState(true);

  return (
    <ExOwnContext.Provider
      value={{
        currentScreen,
        navigateTo,
        navigateBack,
        canNavigateBack: screenStack.length > 1,
        isAuthenticated,
        currentUser,
        signInStudent,
        signOutStudent,
        campuses,
        selectedCampus,
        selectCampus,
        selectCampusById,
        campusScopeOnly,
        setCampusScopeOnly,
        toggleCampusScope,
        showCampusSwitcher,
        openCampusSwitcher,
        closeCampusSwitcher,
        categories: categoriesData,
        allListings,
        filteredListings,
        savedListings,
        myListings,
        savedListingIds,
        toggleSave,
        addListing,
        markListingSold,
        deleteListing,
        searchQuery,
        setSearchQuery,
        selectedCategoryId,
        setSelectedCategoryId,
        selectedListingType,
        setSelectedListingType,
        selectedCondition,
        setSelectedCondition,
        filterOnlyUrgent,
        toggleFilterOnlyUrgent,
        filterOnlyExchange,
        toggleFilterOnlyExchange,
        sortBy,
        setSortBy,
        resetFilters,
        selectedProduct,
        viewProductDetails,
        selectedHousing,
        viewHousingDetails,
        servicesTab,
        setServicesTab,
        housingListings,
        roommateListings,
        campusServices,
        conversations,
        activeConversation,
        activeMessages,
        openConversation,
        startChatForProduct,
        sendMessage,
        updateDealStatus,
        isTrustPassportOpen,
        setIsTrustPassportOpen,
        isSellModalOpen,
        setIsSellModalOpen,
        isDealRoomOpen,
        setIsDealRoomOpen,
        isDarkTheme,
        setIsDarkTheme,
      }}
    >
      {children}
    </ExOwnContext.Provider>
  );
};

export const useExOwn = () => {
  const context = useContext(ExOwnContext);
  if (!context) {
    throw new Error('useExOwn must be used within an ExOwnProvider');
  }
  return context;
};
