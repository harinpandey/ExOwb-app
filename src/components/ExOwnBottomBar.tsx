import React from 'react';
import { Home, Compass, MessageSquare, User, Plus } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { AppScreen } from '../types';

export const ExOwnBottomBar: React.FC = () => {
  const { currentScreen, navigateTo, conversations, setIsSellModalOpen } = useExOwn();

  const totalUnread = conversations.reduce((acc, c) => acc + (c.unreadCount || 0), 0);

  const navItems: { label: string; screen: AppScreen; icon: typeof Home; badge?: number }[] = [
    { label: 'Home', screen: 'HOME', icon: Home },
    { label: 'Explore', screen: 'EXPLORE', icon: Compass },
    { label: 'Inbox', screen: 'INBOX', icon: MessageSquare, badge: totalUnread },
    { label: 'Me', screen: 'PROFILE', icon: User },
  ];

  return (
    <div className="fixed bottom-0 left-0 right-0 z-40 bg-[#10141B] border-t border-[#1F2633] pb-safe">
      <div className="max-w-md mx-auto relative flex items-center justify-around h-16 px-2">
        {/* Left items */}
        {navItems.slice(0, 2).map((item) => {
          const isActive = currentScreen === item.screen;
          const Icon = item.icon;
          return (
            <button
              key={item.screen}
              onClick={() => navigateTo(item.screen)}
              aria-label={item.label}
              className={`flex-1 flex flex-col items-center justify-center h-full min-w-[48px] min-h-[48px] transition-colors relative ${
                isActive ? 'text-[#2A72E8]' : 'text-[#94A3B8] hover:text-white'
              }`}
            >
              <Icon className={`w-5 h-5 ${isActive ? 'stroke-[2.5]' : 'stroke-2'}`} />
              <span className={`text-[11px] mt-1 ${isActive ? 'font-bold' : 'font-medium'}`}>
                {item.label}
              </span>
            </button>
          );
        })}

        {/* Center Floating Action Button: SELL */}
        <div className="relative -top-3 flex items-center justify-center px-1">
          <button
            onClick={() => setIsSellModalOpen(true)}
            aria-label="Post a listing on campus"
            className="w-13 h-13 rounded-full bg-[#2A72E8] hover:bg-[#2563EB] active:scale-95 text-white flex items-center justify-center shadow-lg shadow-[#2A72E8]/35 border-4 border-[#07090D] transition-transform"
          >
            <Plus className="w-6 h-6 stroke-[3]" />
          </button>
        </div>

        {/* Right items */}
        {navItems.slice(2).map((item) => {
          const isActive = currentScreen === item.screen;
          const Icon = item.icon;
          return (
            <button
              key={item.screen}
              onClick={() => navigateTo(item.screen)}
              aria-label={item.label}
              className={`flex-1 flex flex-col items-center justify-center h-full min-w-[48px] min-h-[48px] transition-colors relative ${
                isActive ? 'text-[#2A72E8]' : 'text-[#94A3B8] hover:text-white'
              }`}
            >
              <div className="relative">
                <Icon className={`w-5 h-5 ${isActive ? 'stroke-[2.5]' : 'stroke-2'}`} />
                {item.badge && item.badge > 0 ? (
                  <span className="absolute -top-1.5 -right-2 bg-[#2A72E8] text-white text-[10px] font-bold rounded-full w-4 h-4 flex items-center justify-center shadow-xs">
                    {item.badge}
                  </span>
                ) : null}
              </div>
              <span className={`text-[11px] mt-1 ${isActive ? 'font-bold' : 'font-medium'}`}>
                {item.label}
              </span>
            </button>
          );
        })}
      </div>
    </div>
  );
};
