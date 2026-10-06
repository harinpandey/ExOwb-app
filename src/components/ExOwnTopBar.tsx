import React from 'react';
import { Search, MessageSquare, ShieldCheck } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { CampusSelector } from './CampusSelector';

export const ExOwnTopBar: React.FC = () => {
  const { navigateTo, conversations, setIsTrustPassportOpen, currentUser } = useExOwn();

  const totalUnread = conversations.reduce((acc, c) => acc + (c.unreadCount || 0), 0);

  return (
    <header className="sticky top-0 z-40 w-full bg-[#10141B]/95 backdrop-blur-md border-b border-[#1F2633] px-3 sm:px-4 py-2.5">
      <div className="max-w-4xl mx-auto flex items-center justify-between gap-2">
        {/* Logo and Brand */}
        <div
          onClick={() => navigateTo('HOME')}
          className="flex items-center gap-2 cursor-pointer select-none"
        >
          <div className="w-8 h-8 rounded-full bg-[#2A72E8] flex items-center justify-center text-white font-black text-xs shadow-sm">
            XO
          </div>
          <div>
            <div className="flex items-center gap-1">
              <span className="font-black text-lg tracking-tight text-white">ExOwn</span>
              <span className="text-[10px] bg-[#10B981]/20 text-[#10B981] px-1.5 py-0.2 rounded font-bold">CAMPUS</span>
            </div>
          </div>
        </div>

        {/* Center: Campus Selector */}
        <div className="flex items-center gap-2">
          <CampusSelector />
        </div>

        {/* Right Actions: Search, Trust, Inbox */}
        <div className="flex items-center gap-1 sm:gap-2">
          <button
            onClick={() => navigateTo('EXPLORE')}
            aria-label="Search campus listings"
            className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <Search className="w-4 h-4" />
          </button>

          <button
            onClick={() => setIsTrustPassportOpen(true)}
            aria-label="View ExOwn Trust Passport"
            className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] flex items-center justify-center text-[#10B981] hover:text-white transition-colors relative"
            title="ExOwn Trust Passport"
          >
            <ShieldCheck className="w-4 h-4" />
            <span className="absolute -top-1 -right-1 text-[9px] font-black bg-[#10B981] text-black px-1 rounded-full">
              {currentUser.trustScore}
            </span>
          </button>

          <button
            onClick={() => navigateTo('INBOX')}
            aria-label="Open inbox"
            className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors relative"
          >
            <MessageSquare className="w-4 h-4" />
            {totalUnread > 0 && (
              <span className="absolute -top-1 -right-1 w-4 h-4 rounded-full bg-[#2A72E8] text-white font-bold text-[10px] flex items-center justify-center shadow-xs">
                {totalUnread}
              </span>
            )}
          </button>
        </div>
      </div>
    </header>
  );
};
