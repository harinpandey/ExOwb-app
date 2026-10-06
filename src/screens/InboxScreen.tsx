import React from 'react';
import { MessageSquare, Handshake, ChevronRight, CheckCheck, Clock } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { Conversation } from '../types';

export const InboxScreen: React.FC = () => {
  const { conversations, openConversation, selectedCampus } = useExOwn();

  return (
    <div className="pb-24 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-4">
      <div>
        <h1 className="text-lg font-black text-white flex items-center gap-2">
          Messages & Deal Rooms
        </h1>
        <p className="text-xs text-[#94A3B8]">
          Active chats with verified campus students across {selectedCampus.code}
        </p>
      </div>

      {conversations.length > 0 ? (
        <div className="space-y-2">
          {conversations.map((conv: Conversation) => (
            <div
              key={conv.id}
              onClick={() => openConversation(conv)}
              className="cursor-pointer p-3.5 bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] rounded-xl transition-all flex items-center justify-between gap-3 group"
            >
              <div className="flex items-center gap-3 min-w-0 flex-1">
                {/* Avatar */}
                <div className="w-11 h-11 rounded-full bg-[#2A72E8] flex items-center justify-center text-white font-bold text-base shrink-0 shadow-xs">
                  {conv.otherUserName.slice(0, 1)}
                </div>

                {/* Details */}
                <div className="min-w-0 flex-1">
                  <div className="flex items-center justify-between gap-1">
                    <div className="flex items-center gap-1.5 truncate">
                      <span className="font-bold text-sm text-white truncate">
                        {conv.otherUserName}
                      </span>
                      <span className="text-[10px] bg-[#161C25] text-[#10B981] border border-[#1F2633] px-1.5 py-0.2 rounded font-semibold shrink-0">
                        {conv.otherUserCampus}
                      </span>
                    </div>
                    <span className="text-[10px] text-[#64748B] shrink-0">{conv.lastTimestamp}</span>
                  </div>

                  {conv.listingTitle && (
                    <p className="text-xs text-[#2A72E8] font-medium truncate mt-0.5">
                      {conv.listingTitle} • ₹{conv.listingPrice?.toLocaleString('en-IN')}
                    </p>
                  )}

                  <p className="text-xs text-[#94A3B8] truncate mt-0.5">
                    {conv.lastMessage}
                  </p>
                </div>
              </div>

              {/* Item Thumbnail or Badge */}
              <div className="flex items-center gap-2 shrink-0">
                {conv.listingImage && (
                  <img
                    src={conv.listingImage}
                    alt=""
                    className="w-10 h-10 rounded-lg object-cover border border-[#1F2633]"
                  />
                )}
                {conv.unreadCount > 0 ? (
                  <span className="w-5 h-5 rounded-full bg-[#2A72E8] text-white text-[11px] font-bold flex items-center justify-center shadow-xs">
                    {conv.unreadCount}
                  </span>
                ) : (
                  <ChevronRight className="w-4 h-4 text-[#64748B] group-hover:text-white transition-colors" />
                )}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="p-12 text-center bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-2">
          <MessageSquare className="w-10 h-10 text-[#64748B] mx-auto" />
          <h3 className="text-sm font-bold text-white">No active chats</h3>
          <p className="text-xs text-[#94A3B8]">
            Tap on any listing to start negotiating with the seller!
          </p>
        </div>
      )}
    </div>
  );
};
