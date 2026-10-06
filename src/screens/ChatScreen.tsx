import React, { useState } from 'react';
import {
  ArrowLeft,
  Send,
  Handshake,
  Tag,
  ShieldCheck,
  CheckCircle,
  MapPin,
  Clock,
  Sparkles,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { DealRoomModal } from '../components/DealRoomModal';

export const ChatScreen: React.FC = () => {
  const {
    activeConversation,
    activeMessages,
    sendMessage,
    navigateBack,
    setIsDealRoomOpen,
    selectedCampus,
  } = useExOwn();

  const [input, setInput] = useState('');
  const [offerValue, setOfferValue] = useState(
    activeConversation?.listingPrice ? Math.round(activeConversation.listingPrice * 0.9).toString() : '3000'
  );
  const [showOfferInput, setShowOfferInput] = useState(false);

  if (!activeConversation) return null;

  const handleSend = (e: React.FormEvent) => {
    e.preventDefault();
    if (!input.trim()) return;
    sendMessage(input.trim());
    setInput('');
  };

  const handleSendOffer = () => {
    const val = parseFloat(offerValue);
    if (!val || val <= 0) return;
    sendMessage(`Offer: ₹${val.toLocaleString('en-IN')}`, true, val);
    setShowOfferInput(false);
  };

  const quickReplies = [
    'Is this still available?',
    'Can we meet at hostel reception?',
    'Is price negotiable?',
    'Can I test drive / inspect it today?',
  ];

  return (
    <div className="pb-24 pt-1 max-w-3xl mx-auto px-3 sm:px-4 flex flex-col min-h-[calc(100vh-120px)]">
      {/* Top Header */}
      <div className="flex items-center justify-between pb-3 border-b border-[#1F2633] gap-2">
        <div className="flex items-center gap-2.5">
          <button
            onClick={navigateBack}
            aria-label="Back to inbox"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <ArrowLeft className="w-4 h-4" />
          </button>
          <div className="w-9 h-9 rounded-full bg-[#2A72E8] flex items-center justify-center text-white font-bold text-sm">
            {activeConversation.otherUserName.slice(0, 1)}
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <span className="font-bold text-sm text-white">
                {activeConversation.otherUserName}
              </span>
              <span className="text-[10px] bg-[#10B981]/15 text-[#10B981] px-1.5 rounded font-bold">
                {activeConversation.otherUserCampus}
              </span>
            </div>
            <p className="text-[11px] text-[#64748B]">Active campus peer</p>
          </div>
        </div>

        {/* Open Deal Room Button */}
        <button
          onClick={() => setIsDealRoomOpen(true)}
          className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-[#2A72E8] hover:bg-[#2563EB] text-white text-xs font-bold transition-colors shadow-xs"
        >
          <Handshake className="w-4 h-4" />
          Deal Room
        </button>
      </div>

      {/* Listing Context Banner */}
      {activeConversation.listingTitle && (
        <div className="my-3 p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center justify-between gap-3 shadow-xs">
          <div className="flex items-center gap-2.5 min-w-0">
            {activeConversation.listingImage && (
              <img
                src={activeConversation.listingImage}
                alt=""
                className="w-12 h-12 rounded-lg object-cover border border-[#1F2633] shrink-0"
              />
            )}
            <div className="min-w-0">
              <h3 className="text-xs font-bold text-white truncate">
                {activeConversation.listingTitle}
              </h3>
              <p className="text-xs text-[#2A72E8] font-black mt-0.5">
                ₹{activeConversation.listingPrice?.toLocaleString('en-IN')}
              </p>
            </div>
          </div>

          <button
            onClick={() => setShowOfferInput(!showOfferInput)}
            className="shrink-0 text-xs px-2.5 py-1.5 bg-[#161C25] hover:bg-[#1F2633] text-[#10B981] border border-[#10B981]/30 font-bold rounded-lg transition-colors flex items-center gap-1"
          >
            <Tag className="w-3.5 h-3.5" />
            Make Offer
          </button>
        </div>
      )}

      {/* Inline Offer Bar */}
      {showOfferInput && (
        <div className="mb-3 p-3 bg-[#161C25] border border-[#2A72E8] rounded-xl flex items-center gap-2 animate-in fade-in">
          <span className="text-xs text-[#94A3B8] font-medium">Peer Offer: ₹</span>
          <input
            type="number"
            value={offerValue}
            onChange={(e) => setOfferValue(e.target.value)}
            className="flex-1 bg-[#10141B] border border-[#1F2633] rounded px-2 py-1 text-xs text-white font-bold"
          />
          <button
            onClick={handleSendOffer}
            className="px-3 py-1 bg-[#2A72E8] text-white text-xs font-bold rounded hover:bg-[#2563EB]"
          >
            Submit
          </button>
          <button
            onClick={() => setShowOfferInput(false)}
            className="text-xs text-[#64748B] hover:text-white"
          >
            Cancel
          </button>
        </div>
      )}

      {/* Messages Feed */}
      <div className="flex-1 space-y-2.5 overflow-y-auto py-2">
        {activeMessages.map((msg) => (
          <div
            key={msg.id}
            className={`flex flex-col ${msg.isMine ? 'items-end' : 'items-start'}`}
          >
            <div
              className={`max-w-[80%] rounded-2xl px-3.5 py-2 text-xs leading-relaxed ${
                msg.isMine
                  ? 'bg-[#2A72E8] text-white rounded-br-xs'
                  : 'bg-[#161C25] text-white border border-[#1F2633] rounded-bl-xs'
              }`}
            >
              {msg.isOffer && (
                <div className="mb-1 pb-1 border-b border-white/20 flex items-center gap-1 font-black text-[11px] text-[#F59E0B]">
                  <Tag className="w-3 h-3" /> Offer: ₹{msg.offerAmount?.toLocaleString('en-IN')}
                </div>
              )}
              {msg.text}
            </div>
            <span className="text-[10px] text-[#64748B] mt-0.5 px-1">
              {msg.timestamp}
            </span>
          </div>
        ))}
      </div>

      {/* Quick Reply Chips */}
      <div className="flex gap-1.5 overflow-x-auto py-2 scrollbar-none">
        {quickReplies.map((qr) => (
          <button
            key={qr}
            onClick={() => sendMessage(qr)}
            className="text-[11px] px-2.5 py-1 rounded-full bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] text-[#94A3B8] hover:text-white whitespace-nowrap transition-colors"
          >
            {qr}
          </button>
        ))}
      </div>

      {/* Input row */}
      <form onSubmit={handleSend} className="flex items-center gap-2 pt-2">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder={`Message ${activeConversation.otherUserName}...`}
          className="flex-1 bg-[#10141B] border border-[#1F2633] rounded-xl px-3.5 py-2.5 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
        />
        <button
          type="submit"
          aria-label="Send message"
          className="w-10 h-10 rounded-xl bg-[#2A72E8] hover:bg-[#2563EB] text-white flex items-center justify-center transition-colors shrink-0"
        >
          <Send className="w-4 h-4" />
        </button>
      </form>
    </div>
  );
};
