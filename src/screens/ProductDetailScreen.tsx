import React from 'react';
import {
  ArrowLeft,
  Heart,
  MapPin,
  Clock,
  ShieldCheck,
  Star,
  CheckCircle2,
  Tag,
  Repeat,
  MessageSquare,
  Handshake,
  AlertCircle,
  Building,
  Share2,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { VerificationBadge } from '../components/VerificationBadge';

export const ProductDetailScreen: React.FC = () => {
  const {
    selectedProduct,
    navigateBack,
    toggleSave,
    startChatForProduct,
    setIsDealRoomOpen,
    setIsTrustPassportOpen,
    selectedCampus,
  } = useExOwn();

  if (!selectedProduct) return null;

  const isSaved = selectedProduct.isSaved;

  const handleShare = () => {
    if (navigator.share) {
      navigator.share({
        title: selectedProduct.title,
        text: `Check out ${selectedProduct.title} on ExOwn (${selectedProduct.campusCode})`,
        url: window.location.href,
      }).catch(() => {});
    } else {
      navigator.clipboard.writeText(window.location.href);
      alert('Link copied to clipboard!');
    }
  };

  return (
    <div className="pb-28 pt-1 max-w-3xl mx-auto px-3 sm:px-4 space-y-4">
      {/* Top Bar */}
      <div className="flex items-center justify-between pb-2 border-b border-[#1F2633]">
        <button
          onClick={navigateBack}
          aria-label="Back"
          className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
        >
          <ArrowLeft className="w-4 h-4" />
        </button>
        <span className="text-xs font-bold text-[#94A3B8] truncate max-w-[200px]">
          {selectedProduct.categoryName}
        </span>
        <div className="flex items-center gap-2">
          <button
            onClick={handleShare}
            aria-label="Share listing"
            className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <Share2 className="w-4 h-4" />
          </button>
          <button
            onClick={() => toggleSave(selectedProduct.id)}
            aria-label={isSaved ? "Remove from saved items" : "Save item"}
            className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center transition-colors"
          >
            <Heart
              className={`w-4 h-4 ${
                isSaved ? 'fill-[#EF4444] text-[#EF4444]' : 'text-white'
              }`}
            />
          </button>
        </div>
      </div>

      {/* Hero Image Container */}
      <div className="relative aspect-4/3 w-full bg-[#161C25] rounded-2xl overflow-hidden border border-[#1F2633] shadow-lg">
        <img
          src={selectedProduct.imageUrl}
          alt={selectedProduct.title}
          className="w-full h-full object-cover"
        />

        {/* Badges */}
        <div className="absolute top-3 left-3 flex items-center gap-1.5 flex-wrap">
          {selectedProduct.isUrgent && (
            <span className="bg-[#EF4444] text-white text-[10px] font-black px-2.5 py-0.5 rounded-full uppercase tracking-wider shadow-md">
              Urgent Moving Out
            </span>
          )}
          <span className="bg-black/75 text-white text-[10px] font-bold px-2.5 py-0.5 rounded-full uppercase backdrop-blur-xs">
            {selectedProduct.listingType}
          </span>
        </div>

        <div className="absolute bottom-3 left-3">
          <span className="bg-black/80 text-white text-xs font-semibold px-2.5 py-1 rounded-lg backdrop-blur-xs">
            {selectedProduct.condition}
          </span>
        </div>
      </div>

      {/* Title & Price Card */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
        <div className="flex items-start justify-between gap-3">
          <h1 className="text-base sm:text-lg font-bold text-white leading-snug">
            {selectedProduct.title}
          </h1>
        </div>

        <div className="flex items-baseline gap-2 pt-1">
          <span className="text-2xl font-black text-[#2A72E8]">
            ₹{selectedProduct.price.toLocaleString('en-IN')}
          </span>
          {selectedProduct.rentalDurationUnit && selectedProduct.listingType === 'RENT' && (
            <span className="text-sm font-semibold text-[#F59E0B]">
              /{selectedProduct.rentalDurationUnit}
            </span>
          )}
          {selectedProduct.originalPrice && selectedProduct.originalPrice > selectedProduct.price && (
            <span className="text-sm text-[#64748B] line-through font-normal">
              ₹{selectedProduct.originalPrice.toLocaleString('en-IN')}
            </span>
          )}
          {selectedProduct.originalPrice && selectedProduct.originalPrice > selectedProduct.price && (
            <span className="text-xs font-bold text-[#10B981] bg-[#10B981]/15 px-2 py-0.5 rounded">
              {Math.round(((selectedProduct.originalPrice - selectedProduct.price) / selectedProduct.originalPrice) * 100)}% OFF
            </span>
          )}
        </div>

        <div className="flex flex-wrap items-center gap-3 pt-2 border-t border-[#1F2633] text-xs text-[#94A3B8]">
          <span className="flex items-center gap-1">
            <MapPin className="w-3.5 h-3.5 text-[#2A72E8]" />
            {selectedProduct.location} ({selectedProduct.campusCode})
          </span>
          <span>•</span>
          <span className="flex items-center gap-1">
            <Clock className="w-3.5 h-3.5 text-[#64748B]" />
            {selectedProduct.createdAt || 'Recent'}
          </span>
          <span>•</span>
          <VerificationBadge label="Verified Student Listing" />
        </div>
      </div>

      {/* Barter / Exchange Section (If eligible) */}
      {(selectedProduct.isExchangeEligible || selectedProduct.listingType === 'EXCHANGE') && (
        <div className="p-3.5 bg-gradient-to-r from-[#10B981]/15 to-transparent border border-[#10B981]/30 rounded-xl space-y-1.5">
          <div className="flex items-center gap-2 text-xs font-bold text-[#10B981]">
            <Repeat className="w-4 h-4" />
            Exchange & Barter Terms
          </div>
          <p className="text-xs text-white">
            {selectedProduct.exchangePreferences || 'Open to fair student barter propositions + cash adjustment.'}
          </p>
        </div>
      )}

      {/* Description */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-2">
        <h2 className="text-xs font-bold text-white uppercase tracking-wider">
          Product Details & Description
        </h2>
        <p className="text-xs sm:text-sm text-[#94A3B8] leading-relaxed whitespace-pre-line">
          {selectedProduct.description}
        </p>
      </div>

      {/* Seller Trust Passport Card */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-11 h-11 rounded-full bg-[#2A72E8] flex items-center justify-center text-white font-black text-base shadow-xs">
              {selectedProduct.sellerName.slice(0, 1)}
            </div>
            <div>
              <h3 className="text-sm font-bold text-white flex items-center gap-1.5">
                {selectedProduct.sellerName}
                <CheckCircle2 className="w-4 h-4 text-[#10B981] fill-[#10B981]/20" />
              </h3>
              <p className="text-xs text-[#94A3B8]">
                {selectedProduct.sellerDepartment || 'Student'} · {selectedProduct.campusCode}
              </p>
            </div>
          </div>

          <div className="flex items-center gap-1 text-xs font-bold text-[#F59E0B] bg-[#161C25] px-2 py-1 rounded-lg border border-[#1F2633]">
            <Star className="w-3.5 h-3.5 fill-[#F59E0B]" />
            {selectedProduct.sellerRating || 4.9}
          </div>
        </div>

        <button
          onClick={() => setIsTrustPassportOpen(true)}
          className="w-full py-2 bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-xs font-bold text-[#10B981] rounded-xl flex items-center justify-center gap-1.5 transition-colors"
        >
          <ShieldCheck className="w-4 h-4" /> View Seller Trust Passport
        </button>
      </div>

      {/* Safety Notice */}
      <div className="p-3 bg-[#161C25] border border-[#1F2633] rounded-xl flex items-start gap-2.5 text-xs text-[#94A3B8]">
        <AlertCircle className="w-4 h-4 text-[#F59E0B] shrink-0 mt-0.5" />
        <span>
          Campus Safety Rule: Handover and test all items at safe campus locations (e.g. Uni Mall, Hostel gate, Central Library). Never pay advance tokens to unknown numbers.
        </span>
      </div>

      {/* Sticky Bottom Action Bar */}
      <div className="fixed bottom-0 left-0 right-0 z-40 bg-[#10141B] border-t border-[#1F2633] p-3">
        <div className="max-w-3xl mx-auto flex items-center gap-3">
          <button
            onClick={() => {
              startChatForProduct(selectedProduct);
              setIsDealRoomOpen(true);
            }}
            className="flex-1 py-3 px-4 rounded-xl bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-xs font-bold text-white transition-colors flex items-center justify-center gap-2"
          >
            <Handshake className="w-4 h-4 text-[#2A72E8]" />
            Make Offer / Deal Room
          </button>

          <button
            onClick={() => startChatForProduct(selectedProduct)}
            className="flex-1 py-3 px-4 rounded-xl bg-[#2A72E8] hover:bg-[#2563EB] text-xs font-black text-white transition-colors flex items-center justify-center gap-2 shadow-lg shadow-[#2A72E8]/25"
          >
            <MessageSquare className="w-4 h-4" />
            Chat with Student
          </button>
        </div>
      </div>
    </div>
  );
};
