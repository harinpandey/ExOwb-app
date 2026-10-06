import React from 'react';
import { useExOwn } from './context/ExOwnContext';
import { ExOwnTopBar } from './components/ExOwnTopBar';
import { ExOwnBottomBar } from './components/ExOwnBottomBar';
import { CampusSwitcherModal } from './components/CampusSwitcherModal';
import { TrustPassportModal } from './components/TrustPassportModal';
import { DealRoomModal } from './components/DealRoomModal';
import { SellCreationModal } from './components/SellCreationModal';

// Screens
import { HomeScreen } from './screens/HomeScreen';
import { ExploreScreen } from './screens/ExploreScreen';
import { ServicesScreen } from './screens/ServicesScreen';
import { InboxScreen } from './screens/InboxScreen';
import { ChatScreen } from './screens/ChatScreen';
import { ProfileScreen } from './screens/ProfileScreen';
import { CampusHubScreen } from './screens/CampusHubScreen';
import { ProductDetailScreen } from './screens/ProductDetailScreen';
import { HousingDetailScreen } from './screens/HousingDetailScreen';
import { AuthScreen } from './screens/AuthScreen';

export const AppContent: React.FC = () => {
  const { currentScreen, isAuthenticated } = useExOwn();

  if (!isAuthenticated) {
    return <AuthScreen />;
  }

  const renderScreen = () => {
    switch (currentScreen) {
      case 'HOME':
        return <HomeScreen />;
      case 'EXPLORE':
        return <ExploreScreen />;
      case 'SERVICES':
        return <ServicesScreen />;
      case 'INBOX':
        return <InboxScreen />;
      case 'CHAT':
        return <ChatScreen />;
      case 'PROFILE':
        return <ProfileScreen />;
      case 'CAMPUS_HUB':
        return <CampusHubScreen />;
      case 'PRODUCT_DETAIL':
        return <ProductDetailScreen />;
      case 'HOUSING_DETAIL':
        return <HousingDetailScreen />;
      default:
        return <HomeScreen />;
    }
  };

  const showTopBar = currentScreen !== 'CHAT' && currentScreen !== 'PRODUCT_DETAIL' && currentScreen !== 'HOUSING_DETAIL';
  const showBottomBar = currentScreen !== 'CHAT' && currentScreen !== 'PRODUCT_DETAIL' && currentScreen !== 'HOUSING_DETAIL';

  return (
    <div className="min-h-screen bg-[#07090D] text-white flex flex-col font-sans">
      {showTopBar && <ExOwnTopBar />}

      <main className="flex-1 w-full max-w-4xl mx-auto">
        {renderScreen()}
      </main>

      {showBottomBar && <ExOwnBottomBar />}

      {/* Global Modals */}
      <CampusSwitcherModal />
      <TrustPassportModal />
      <DealRoomModal />
      <SellCreationModal />
    </div>
  );
};
