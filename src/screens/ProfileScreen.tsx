import React, { useState } from 'react';
import {
  ShieldCheck,
  CheckCircle2,
  Trash2,
  CheckCircle,
  Heart,
  Package,
  Building2,
  LogOut,
  MapPin,
  Leaf,
  IndianRupee,
  Share2,
  ChevronRight,
  Moon,
  Sun,
  Plus,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ProductCard } from '../components/ProductCard';

export const ProfileScreen: React.FC = () => {
  const {
    currentUser,
    myListings,
    savedListings,
    markListingSold,
    deleteListing,
    viewProductDetails,
    toggleSave,
    setIsTrustPassportOpen,
    openCampusSwitcher,
    selectedCampus,
    signOutStudent,
    setIsSellModalOpen,
    isDarkTheme,
    setIsDarkTheme,
  } = useExOwn();

  const [activeTab, setActiveTab] = useState<'MY_LISTINGS' | 'SAVED'>('MY_LISTINGS');

  return (
    <div className="pb-28 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-4">
      {/* 1. Student Identity Card */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
        <div className="flex items-start justify-between gap-3">
          <div className="flex items-center gap-3">
            <div className="w-13 h-13 rounded-2xl bg-[#2A72E8] flex items-center justify-center text-white text-xl font-black shadow-md">
              {currentUser.name.slice(0, 1)}
            </div>
            <div>
              <h1 className="text-base font-black text-white flex items-center gap-1.5">
                {currentUser.name}
                <CheckCircle2 className="w-4 h-4 text-[#10B981] fill-[#10B981]/20" />
              </h1>
              <p className="text-xs text-[#94A3B8]">
                {currentUser.department} • {currentUser.year}
              </p>
              <p className="text-xs text-[#64748B] flex items-center gap-1 mt-0.5">
                <MapPin className="w-3.5 h-3.5 text-[#2A72E8]" /> {currentUser.hostel}
              </p>
            </div>
          </div>

          <button
            onClick={() => setIsTrustPassportOpen(true)}
            className="flex flex-col items-center bg-[#161C25] hover:bg-[#1F2633] border border-[#10B981]/30 p-2 rounded-xl transition-colors"
          >
            <span className="text-sm font-black text-[#10B981]">{currentUser.trustScore}/100</span>
            <span className="text-[9px] uppercase tracking-wider text-[#94A3B8] font-bold">Trust Index</span>
          </button>
        </div>

        {/* Action button: View Trust Passport */}
        <button
          onClick={() => setIsTrustPassportOpen(true)}
          className="w-full py-2.5 px-3 rounded-xl bg-gradient-to-r from-[#10B981]/20 to-[#2A72E8]/20 border border-[#10B981]/40 hover:border-[#10B981] text-xs font-bold text-white flex items-center justify-between transition-colors"
        >
          <span className="flex items-center gap-2">
            <ShieldCheck className="w-4 h-4 text-[#10B981]" />
            Official ExOwn Trust Passport
          </span>
          <span className="text-[11px] text-[#10B981] flex items-center gap-1 font-semibold">
            View Credentials <ChevronRight className="w-3.5 h-3.5" />
          </span>
        </button>
      </div>

      {/* 2. Campus Circular Economy Impact */}
      <div className="grid grid-cols-2 gap-2.5">
        <div className="p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center gap-3">
          <div className="w-9 h-9 rounded-lg bg-[#10B981]/15 text-[#10B981] flex items-center justify-center shrink-0">
            <IndianRupee className="w-5 h-5" />
          </div>
          <div>
            <div className="text-sm font-black text-white">
              ₹{currentUser.totalSavedInr.toLocaleString('en-IN')}
            </div>
            <div className="text-[10px] text-[#94A3B8]">Saved via Campus Reuse</div>
          </div>
        </div>

        <div className="p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center gap-3">
          <div className="w-9 h-9 rounded-lg bg-[#2A72E8]/15 text-[#2A72E8] flex items-center justify-center shrink-0">
            <Leaf className="w-5 h-5" />
          </div>
          <div>
            <div className="text-sm font-black text-white">28.4 kg</div>
            <div className="text-[10px] text-[#94A3B8]">CO₂ Landfill Offset</div>
          </div>
        </div>
      </div>

      {/* 3. University Affiliation Setting */}
      <div className="p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center justify-between">
        <div className="flex items-center gap-2.5">
          <Building2 className="w-4 h-4 text-[#2A72E8]" />
          <div>
            <div className="text-xs font-bold text-white">Active Campus Affiliation</div>
            <div className="text-[10px] text-[#94A3B8]">
              {selectedCampus.name} ({selectedCampus.code})
            </div>
          </div>
        </div>
        <button
          onClick={openCampusSwitcher}
          className="text-xs font-bold text-[#2A72E8] hover:underline"
        >
          Change
        </button>
      </div>

      {/* 4. Tab Header: My Listings vs Saved */}
      <div className="grid grid-cols-2 gap-2 p-1 bg-[#10141B] border border-[#1F2633] rounded-xl">
        <button
          onClick={() => setActiveTab('MY_LISTINGS')}
          className={`py-2 text-xs font-bold rounded-lg transition-all flex items-center justify-center gap-1.5 ${
            activeTab === 'MY_LISTINGS'
              ? 'bg-[#2A72E8] text-white'
              : 'text-[#94A3B8] hover:text-white'
          }`}
        >
          <Package className="w-3.5 h-3.5" />
          My Listings ({myListings.length})
        </button>

        <button
          onClick={() => setActiveTab('SAVED')}
          className={`py-2 text-xs font-bold rounded-lg transition-all flex items-center justify-center gap-1.5 ${
            activeTab === 'SAVED'
              ? 'bg-[#2A72E8] text-white'
              : 'text-[#94A3B8] hover:text-white'
          }`}
        >
          <Heart className="w-3.5 h-3.5" />
          Saved Wishlist ({savedListings.length})
        </button>
      </div>

      {/* 5. Tab Content */}
      {activeTab === 'MY_LISTINGS' && (
        <div className="space-y-3">
          {myListings.length > 0 ? (
            <div className="space-y-3">
              {myListings.map((item) => (
                <div
                  key={item.id}
                  className="p-3 bg-[#10141B] border border-[#1F2633] rounded-xl flex items-center justify-between gap-3"
                >
                  <div
                    onClick={() => viewProductDetails(item)}
                    className="flex items-center gap-3 min-w-0 cursor-pointer flex-1"
                  >
                    <img
                      src={item.imageUrl}
                      alt=""
                      className="w-14 h-14 rounded-lg object-cover border border-[#1F2633] shrink-0"
                    />
                    <div className="min-w-0">
                      <h4 className="text-xs font-bold text-white truncate">{item.title}</h4>
                      <p className="text-xs text-[#2A72E8] font-black mt-0.5">
                        ₹{item.price.toLocaleString('en-IN')}
                      </p>
                      <p className="text-[10px] text-[#94A3B8] mt-0.5">
                        Status: {item.isSold ? <span className="text-[#EF4444] font-bold">SOLD</span> : <span className="text-[#10B981] font-bold">ACTIVE</span>}
                      </p>
                    </div>
                  </div>

                  <div className="flex items-center gap-2 shrink-0">
                    {!item.isSold && (
                      <button
                        onClick={() => markListingSold(item.id)}
                        className="px-2.5 py-1.5 bg-[#10B981]/20 hover:bg-[#10B981] text-[#10B981] hover:text-black text-xs font-bold rounded-lg transition-colors"
                        title="Mark item as sold"
                      >
                        <CheckCircle className="w-3.5 h-3.5 inline mr-1" />
                        Mark Sold
                      </button>
                    )}
                    <button
                      onClick={() => deleteListing(item.id)}
                      aria-label="Delete listing"
                      className="p-1.5 rounded-lg bg-[#161C25] hover:bg-[#EF4444]/20 text-[#64748B] hover:text-[#EF4444] transition-colors"
                      title="Delete listing"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="p-8 text-center bg-[#10141B] border border-[#1F2633] rounded-xl space-y-2">
              <Package className="w-8 h-8 text-[#64748B] mx-auto" />
              <p className="text-xs font-bold text-white">No active listings</p>
              <p className="text-[11px] text-[#94A3B8]">
                Declutter your hostel room and earn cash from peers!
              </p>
              <button
                onClick={() => setIsSellModalOpen(true)}
                className="mt-2 px-3 py-1.5 bg-[#2A72E8] text-white text-xs font-bold rounded-lg"
              >
                Post an Item
              </button>
            </div>
          )}
        </div>
      )}

      {activeTab === 'SAVED' && (
        <div className="space-y-3">
          {savedListings.length > 0 ? (
            <div className="grid grid-cols-2 sm:grid-cols-3 gap-2.5 sm:gap-3">
              {savedListings.map((product) => (
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
              <Heart className="w-8 h-8 text-[#64748B] mx-auto" />
              <p className="text-xs font-bold text-white">Your wishlist is empty</p>
              <p className="text-[11px] text-[#94A3B8]">
                Tap the heart on any campus listing to keep track of it here.
              </p>
            </div>
          )}
        </div>
      )}

      {/* 6. Sign Out Option */}
      <div className="pt-2">
        <button
          onClick={signOutStudent}
          className="w-full py-2.5 bg-[#161C25] hover:bg-[#EF4444]/15 border border-[#1F2633] hover:border-[#EF4444]/40 text-[#EF4444] text-xs font-bold rounded-xl transition-colors flex items-center justify-center gap-1.5"
        >
          <LogOut className="w-4 h-4" />
          Sign Out ({currentUser.email})
        </button>
      </div>
    </div>
  );
};
