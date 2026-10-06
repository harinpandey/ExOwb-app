import React from 'react';
import {
  Search,
  ShoppingCart,
  PlusCircle,
  Clock,
  Repeat,
  Flame,
  Building2,
  ChevronRight,
  ShieldCheck,
  Sparkles,
  SlidersHorizontal,
  Home,
  CheckCircle2,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ProductCard } from '../components/ProductCard';
import { ProductListing } from '../types';

export const HomeScreen: React.FC = () => {
  const {
    selectedCampus,
    categories,
    selectedCategoryId,
    setSelectedCategoryId,
    filteredListings,
    allListings,
    viewProductDetails,
    toggleSave,
    navigateTo,
    setIsSellModalOpen,
    setSelectedListingType,
    setIsTrustPassportOpen,
    campusScopeOnly,
    toggleCampusScope,
    currentUser,
  } = useExOwn();

  const movingOutDeals = allListings.filter(
    (item) => item.isUrgent && item.campusId.toLowerCase() === selectedCampus.id.toLowerCase()
  );

  const handleQuickAction = (action: 'BUY' | 'SELL' | 'RENT' | 'EXCHANGE') => {
    if (action === 'SELL') {
      setIsSellModalOpen(true);
    } else {
      setSelectedListingType(action);
      navigateTo('EXPLORE');
    }
  };

  return (
    <div className="pb-24 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-4">
      {/* 1. Personalized Greeting & Campus Context */}
      <div className="flex items-center justify-between pt-1">
        <div>
          <h1 className="text-sm font-semibold text-[#94A3B8]">
            Good day, <span className="text-white font-bold">{currentUser.name.split(' ')[0]}</span>
          </h1>
          <p className="text-xs text-[#64748B] flex items-center gap-1">
            Active around <span className="text-[#2A72E8] font-semibold">{selectedCampus.code} · {selectedCampus.city}</span>
          </p>
        </div>
        <button
          onClick={() => navigateTo('CAMPUS_HUB')}
          className="text-xs font-bold text-[#2A72E8] hover:text-[#60A5FA] bg-[#161C25] border border-[#1F2633] px-3 py-1.5 rounded-full flex items-center gap-1 transition-colors"
        >
          <Building2 className="w-3.5 h-3.5" />
          Campus Hub
          <ChevronRight className="w-3 h-3" />
        </button>
      </div>

      {/* 2. Natural Language Search Box */}
      <div
        onClick={() => navigateTo('EXPLORE')}
        className="cursor-pointer bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] rounded-xl px-3.5 py-3 flex items-center gap-2.5 transition-colors shadow-xs"
      >
        <Search className="w-4 h-4 text-[#2A72E8]" />
        <span className="text-xs text-[#94A3B8] truncate flex-1">
          Search cycles, engineering books, monitors at {selectedCampus.code}...
        </span>
        <span className="text-[10px] bg-[#161C25] text-[#94A3B8] border border-[#1F2633] px-2 py-0.5 rounded font-mono">
          ⌘K
        </span>
      </div>

      {/* 3. Quick Action Chips: Buy, Sell, Rent, Exchange */}
      <div className="grid grid-cols-4 gap-2">
        <button
          onClick={() => handleQuickAction('BUY')}
          className="flex flex-col items-center justify-center p-2.5 rounded-xl bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] transition-all group"
        >
          <div className="w-8 h-8 rounded-lg bg-[#2A72E8]/15 text-[#2A72E8] flex items-center justify-center mb-1 group-hover:scale-105 transition-transform">
            <ShoppingCart className="w-4 h-4" />
          </div>
          <span className="text-[11px] font-bold text-white">Buy</span>
          <span className="text-[9px] text-[#64748B]">Peer items</span>
        </button>

        <button
          onClick={() => handleQuickAction('SELL')}
          className="flex flex-col items-center justify-center p-2.5 rounded-xl bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] transition-all group"
        >
          <div className="w-8 h-8 rounded-lg bg-[#10B981]/15 text-[#10B981] flex items-center justify-center mb-1 group-hover:scale-105 transition-transform">
            <PlusCircle className="w-4 h-4" />
          </div>
          <span className="text-[11px] font-bold text-white">Sell</span>
          <span className="text-[9px] text-[#64748B]">0% commission</span>
        </button>

        <button
          onClick={() => handleQuickAction('RENT')}
          className="flex flex-col items-center justify-center p-2.5 rounded-xl bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] transition-all group"
        >
          <div className="w-8 h-8 rounded-lg bg-[#F59E0B]/15 text-[#F59E0B] flex items-center justify-center mb-1 group-hover:scale-105 transition-transform">
            <Clock className="w-4 h-4" />
          </div>
          <span className="text-[11px] font-bold text-white">Rent</span>
          <span className="text-[9px] text-[#64748B]">Per semester</span>
        </button>

        <button
          onClick={() => handleQuickAction('EXCHANGE')}
          className="flex flex-col items-center justify-center p-2.5 rounded-xl bg-[#10141B] hover:bg-[#161C25] border border-[#1F2633] transition-all group"
        >
          <div className="w-8 h-8 rounded-lg bg-[#8B5CF6]/15 text-[#8B5CF6] flex items-center justify-center mb-1 group-hover:scale-105 transition-transform">
            <Repeat className="w-4 h-4" />
          </div>
          <span className="text-[11px] font-bold text-white">Exchange</span>
          <span className="text-[9px] text-[#64748B]">Student barter</span>
        </button>
      </div>

      {/* 4. Moving-Out Urgent Deals Banner (If available) */}
      {movingOutDeals.length > 0 && (
        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-1.5 text-xs font-black text-white uppercase tracking-wider">
              <Flame className="w-4 h-4 text-[#EF4444]" />
              Moving-Out Clearance
              <span className="text-[10px] font-normal lowercase bg-[#EF4444]/20 text-[#EF4444] px-1.5 rounded">
                urgent handovers
              </span>
            </div>
            <button
              onClick={() => {
                navigateTo('EXPLORE');
              }}
              className="text-[11px] font-bold text-[#2A72E8] hover:underline"
            >
              See all
            </button>
          </div>

          <div className="flex gap-3 overflow-x-auto pb-1 scrollbar-none snap-x">
            {movingOutDeals.map((item) => (
              <div key={item.id} className="min-w-[210px] max-w-[220px] shrink-0 snap-start">
                <ProductCard
                  product={item}
                  onClick={() => viewProductDetails(item)}
                  onSaveToggle={() => toggleSave(item.id)}
                />
              </div>
            ))}
          </div>
        </div>
      )}

      {/* 5. Campus Services & Housing Quick Strip */}
      <div className="p-3 bg-gradient-to-r from-[#10141B] to-[#161C25] border border-[#1F2633] rounded-xl flex items-center justify-between">
        <div className="flex items-center gap-2.5">
          <div className="w-8 h-8 rounded-lg bg-[#2A72E8]/20 flex items-center justify-center text-[#2A72E8]">
            <Home className="w-4 h-4" />
          </div>
          <div>
            <div className="text-xs font-bold text-white">Student Housing & Services</div>
            <div className="text-[10px] text-[#94A3B8]">PGs, roommate matching & bike repairs</div>
          </div>
        </div>
        <button
          onClick={() => navigateTo('SERVICES')}
          className="text-xs font-bold text-white bg-[#2A72E8] hover:bg-[#2563EB] px-3 py-1.5 rounded-lg transition-colors"
        >
          View Hub
        </button>
      </div>

      {/* 6. Category Filter Chips */}
      <div className="space-y-2">
        <div className="flex items-center justify-between">
          <div className="text-xs font-bold text-white uppercase tracking-wider">
            Categories
          </div>
          <button
            onClick={toggleCampusScope}
            className="flex items-center gap-1.5 text-[11px] text-[#94A3B8] hover:text-white"
          >
            <span>{campusScopeOnly ? `${selectedCampus.code} Only` : 'All Campuses'}</span>
            <span
              className={`w-7 h-4 rounded-full transition-colors relative flex items-center px-0.5 ${
                campusScopeOnly ? 'bg-[#2A72E8]' : 'bg-[#1F2633]'
              }`}
            >
              <span
                className={`w-3 h-3 rounded-full bg-white transition-transform ${
                  campusScopeOnly ? 'translate-x-3' : 'translate-x-0'
                }`}
              />
            </span>
          </button>
        </div>

        <div className="flex gap-1.5 overflow-x-auto pb-1 scrollbar-none">
          {categories.map((cat) => {
            const isSelected = selectedCategoryId === cat.id;
            return (
              <button
                key={cat.id}
                onClick={() => setSelectedCategoryId(cat.id)}
                className={`text-xs px-3 py-1.5 rounded-lg border whitespace-nowrap font-medium transition-all ${
                  isSelected
                    ? 'bg-[#2A72E8] text-white border-[#2A72E8] font-bold'
                    : 'bg-[#10141B] text-[#94A3B8] border-[#1F2633] hover:border-[#2A72E8]/40 hover:text-white'
                }`}
              >
                {cat.name}
              </button>
            );
          })}
        </div>
      </div>

      {/* 7. Product Listing Feed */}
      <div className="space-y-2">
        <div className="flex items-center justify-between">
          <span className="text-xs font-bold text-[#94A3B8]">
            {filteredListings.length} {filteredListings.length === 1 ? 'item' : 'items'} available near {selectedCampus.code}
          </span>
          <button
            onClick={() => navigateTo('EXPLORE')}
            className="text-xs font-bold text-[#2A72E8] hover:underline flex items-center gap-1"
          >
            <SlidersHorizontal className="w-3 h-3" /> Filters
          </button>
        </div>

        {filteredListings.length > 0 ? (
          <div className="grid grid-cols-2 sm:grid-cols-3 gap-2.5 sm:gap-3">
            {filteredListings.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                onClick={() => viewProductDetails(product)}
                onSaveToggle={() => toggleSave(product.id)}
              />
            ))}
          </div>
        ) : (
          <div className="p-8 text-center bg-[#10141B] border border-[#1F2633] rounded-xl space-y-2">
            <p className="text-sm font-semibold text-white">No listings in this category yet</p>
            <p className="text-xs text-[#94A3B8]">
              Be the first student to post an item in this category at {selectedCampus.name}!
            </p>
            <button
              onClick={() => setIsSellModalOpen(true)}
              className="mt-2 px-4 py-2 bg-[#2A72E8] hover:bg-[#2563EB] text-white text-xs font-bold rounded-lg transition-colors"
            >
              Post an Item
            </button>
          </div>
        )}
      </div>

      {/* 8. Trust Banner at bottom */}
      <div
        onClick={() => setIsTrustPassportOpen(true)}
        className="cursor-pointer p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center justify-between hover:border-[#10B981]/50 transition-colors"
      >
        <div className="flex items-center gap-2.5">
          <ShieldCheck className="w-5 h-5 text-[#10B981]" />
          <div>
            <div className="text-xs font-bold text-white flex items-center gap-1.5">
              Protected by ExOwn Trust Layer
            </div>
            <div className="text-[10px] text-[#94A3B8]">
              College ID verified · In-person campus handovers · Zero advance risk
            </div>
          </div>
        </div>
        <span className="text-xs text-[#10B981] font-bold">Details</span>
      </div>
    </div>
  );
};
