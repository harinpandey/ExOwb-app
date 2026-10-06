import React from 'react';
import { Heart, MapPin, Repeat, Clock } from 'lucide-react';
import { ProductListing } from '../types';
import { VerificationBadge } from './VerificationBadge';

interface ProductCardProps {
  product: ProductListing;
  onClick: () => void;
  onSaveToggle: (e: React.MouseEvent) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({
  product,
  onClick,
  onSaveToggle,
}) => {
  const getTypeBadge = () => {
    switch (product.listingType) {
      case 'RENT':
        return (
          <span className="bg-[#F59E0B]/90 text-black text-[10px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider backdrop-blur-xs">
            Rental
          </span>
        );
      case 'EXCHANGE':
        return (
          <span className="bg-[#10B981]/90 text-white text-[10px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider backdrop-blur-xs flex items-center gap-1">
            <Repeat className="w-2.5 h-2.5" /> Exchange
          </span>
        );
      case 'BUY':
        return (
          <span className="bg-[#2A72E8]/90 text-white text-[10px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider backdrop-blur-xs">
            Wanted
          </span>
        );
      default:
        return (
          <span className="bg-[#10141B]/80 text-[#94A3B8] border border-[#1F2633] text-[10px] font-semibold px-2 py-0.5 rounded-full uppercase tracking-wider backdrop-blur-xs">
            For Sale
          </span>
        );
    }
  };

  const getConditionLabel = () => {
    switch (product.condition) {
      case 'NEW':
        return 'Brand New';
      case 'LIKE_NEW':
        return 'Like New';
      case 'GOOD':
        return 'Good';
      case 'FAIR':
        return 'Usable';
      default:
        return product.condition;
    }
  };

  return (
    <div
      onClick={onClick}
      className="group relative flex flex-col bg-[#10141B] border border-[#1F2633] hover:border-[#2A72E8]/60 transition-all duration-200 rounded-xl overflow-hidden cursor-pointer shadow-sm hover:shadow-md"
    >
      {/* 4:3 Image Container */}
      <div className="relative aspect-4/3 w-full bg-[#161C25] overflow-hidden">
        <img
          src={product.imageUrl}
          alt={product.title}
          className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
          loading="lazy"
        />

        {/* Top Badges */}
        <div className="absolute top-2.5 left-2.5 flex items-center gap-1.5 flex-wrap z-10">
          {product.isUrgent && (
            <span className="bg-[#EF4444] text-white text-[10px] font-black px-2 py-0.5 rounded-full uppercase tracking-wider shadow-sm animate-pulse">
              Urgent
            </span>
          )}
          {getTypeBadge()}
        </div>

        {/* Favorite Wishlist Button */}
        <button
          onClick={(e) => {
            e.stopPropagation();
            onSaveToggle(e);
          }}
          aria-label={product.isSaved ? "Remove from saved items" : "Save item"}
          className="absolute top-2.5 right-2.5 w-8 h-8 rounded-full bg-black/60 hover:bg-black/80 backdrop-blur-xs flex items-center justify-center transition-colors z-10"
        >
          <Heart
            className={`w-4 h-4 transition-colors ${
              product.isSaved ? 'fill-[#EF4444] text-[#EF4444]' : 'text-white'
            }`}
          />
        </button>

        {/* Condition Tag Bottom Left */}
        <div className="absolute bottom-2 left-2.5 z-10">
          <span className="bg-black/75 text-white text-[10px] font-semibold px-1.5 py-0.5 rounded-sm backdrop-blur-xs tracking-wide">
            {getConditionLabel()}
          </span>
        </div>

        {product.isSold && (
          <div className="absolute inset-0 bg-black/70 flex items-center justify-center z-20">
            <span className="bg-[#EF4444] text-white font-black text-xs uppercase px-3 py-1 rounded tracking-widest rotate-[-12deg] shadow-lg">
              Sold Out
            </span>
          </div>
        )}
      </div>

      {/* Card Body */}
      <div className="p-3 flex flex-col flex-1 justify-between gap-2">
        <div>
          {/* Title - max 2 lines */}
          <h3 className="text-white text-[13px] font-semibold leading-snug line-clamp-2 min-h-[36px]">
            {product.title}
          </h3>
        </div>

        <div>
          {/* Price & Verification Row */}
          <div className="flex items-center justify-between gap-1 mt-1">
            <div className="flex items-baseline gap-1.5">
              <span className="text-[#2A72E8] font-black text-[17px] leading-none">
                ₹{product.price.toLocaleString('en-IN')}
              </span>
              {product.rentalDurationUnit && product.listingType === 'RENT' && (
                <span className="text-[#F59E0B] text-[11px] font-medium">
                  /{product.rentalDurationUnit}
                </span>
              )}
              {product.originalPrice && product.originalPrice > product.price && (
                <span className="text-[#64748B] text-[11px] line-through font-normal">
                  ₹{product.originalPrice.toLocaleString('en-IN')}
                </span>
              )}
            </div>

            {product.isVerified && <VerificationBadge label="Verified" size="sm" />}
          </div>

          {/* Location & Relative Time Row */}
          <div className="flex items-center justify-between text-[11px] text-[#94A3B8] mt-2 pt-2 border-t border-[#1F2633]/60">
            <div className="flex items-center gap-1 truncate max-w-[65%]">
              <MapPin className="w-3 h-3 text-[#64748B] shrink-0" />
              <span className="truncate">{product.location}</span>
            </div>
            <div className="flex items-center gap-1 text-[#64748B] shrink-0">
              <Clock className="w-3 h-3" />
              <span>{product.createdAt || 'Recent'}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
