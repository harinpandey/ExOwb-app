import React from 'react';
import {
  ArrowLeft,
  Building2,
  Users,
  PackageCheck,
  MapPin,
  Flame,
  CheckCircle2,
  Tag,
  ArrowRight,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ProductCard } from '../components/ProductCard';

export const CampusHubScreen: React.FC = () => {
  const {
    selectedCampus,
    openCampusSwitcher,
    allListings,
    viewProductDetails,
    toggleSave,
    navigateBack,
    navigateTo,
    setSelectedCategoryId,
  } = useExOwn();

  const campusListings = allListings.filter(
    (item) => item.campusId.toLowerCase() === selectedCampus.id.toLowerCase()
  );

  return (
    <div className="pb-24 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-4">
      {/* Top Bar */}
      <div className="flex items-center justify-between pb-2 border-b border-[#1F2633]">
        <div className="flex items-center gap-2">
          <button
            onClick={navigateBack}
            aria-label="Back"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <ArrowLeft className="w-4 h-4" />
          </button>
          <div>
            <h1 className="text-base font-black text-white flex items-center gap-1.5">
              {selectedCampus.code} Campus Hub
            </h1>
            <p className="text-[11px] text-[#94A3B8]">{selectedCampus.city}, {selectedCampus.state}</p>
          </div>
        </div>

        <button
          onClick={openCampusSwitcher}
          className="text-xs px-3 py-1.5 bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-[#2A72E8] font-bold rounded-lg transition-colors"
        >
          Change Campus
        </button>
      </div>

      {/* University Hero Card */}
      <div className="p-4 bg-gradient-to-br from-[#10141B] via-[#161C25] to-[#10141B] border border-[#1F2633] rounded-2xl space-y-3 shadow-lg">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <div className="w-10 h-10 rounded-xl bg-[#2A72E8]/20 flex items-center justify-center text-[#2A72E8]">
              <Building2 className="w-5 h-5" />
            </div>
            <div>
              <h2 className="text-sm font-black text-white">{selectedCampus.name}</h2>
              <span className="text-xs text-[#10B981] font-semibold flex items-center gap-1">
                <CheckCircle2 className="w-3.5 h-3.5" /> Verified Domain: {selectedCampus.verificationDomain}
              </span>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-2 gap-2 pt-2 border-t border-[#1F2633]">
          <div className="flex items-center gap-2 text-xs text-[#94A3B8]">
            <Users className="w-4 h-4 text-[#2A72E8]" />
            <span>
              <strong className="text-white">{selectedCampus.studentCount.toLocaleString('en-IN')}</strong> Active Students
            </span>
          </div>
          <div className="flex items-center gap-2 text-xs text-[#94A3B8]">
            <PackageCheck className="w-4 h-4 text-[#10B981]" />
            <span>
              <strong className="text-white">{campusListings.length}</strong> Local Listings
            </span>
          </div>
        </div>
      </div>

      {/* Move-out Clearance Season Banner */}
      {selectedCampus.moveOutActive && (
        <div className="p-3.5 bg-radial from-[#EF4444]/20 to-transparent border border-[#EF4444]/40 rounded-xl flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <Flame className="w-5 h-5 text-[#EF4444] animate-pulse" />
            <div>
              <div className="text-xs font-black text-white">Semester Move-Out Clearance Active!</div>
              <div className="text-[11px] text-[#94A3B8]">
                Senior batch clearance deals on cycles, coolers, and furniture
              </div>
            </div>
          </div>
          <button
            onClick={() => navigateTo('EXPLORE')}
            className="text-xs px-3 py-1 bg-[#EF4444] hover:bg-[#DC2626] text-white font-black rounded-lg transition-colors"
          >
            Explore
          </button>
        </div>
      )}

      {/* Hostel & Campus Delivery Zones */}
      <div className="space-y-2">
        <h3 className="text-xs font-bold text-white uppercase tracking-wider flex items-center gap-1.5">
          <MapPin className="w-3.5 h-3.5 text-[#2A72E8]" />
          Hostel & Pickup Zones
        </h3>
        <div className="flex flex-wrap gap-2">
          {selectedCampus.zones.map((zone) => (
            <div
              key={zone}
              className="text-xs px-3 py-1.5 rounded-xl bg-[#10141B] border border-[#1F2633] text-white font-medium flex items-center gap-1.5"
            >
              <span className="w-2 h-2 rounded-full bg-[#10B981]" />
              {zone}
            </div>
          ))}
        </div>
      </div>

      {/* Popular Campus Collections */}
      <div className="space-y-2">
        <h3 className="text-xs font-bold text-white uppercase tracking-wider flex items-center gap-1.5">
          <Tag className="w-3.5 h-3.5 text-[#F59E0B]" />
          Campus Collections
        </h3>
        <div className="flex flex-wrap gap-2">
          {selectedCampus.popularCollections.map((col) => (
            <button
              key={col}
              onClick={() => navigateTo('EXPLORE')}
              className="text-xs px-3 py-1.5 rounded-xl bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-[#94A3B8] hover:text-white transition-colors"
            >
              {col}
            </button>
          ))}
        </div>
      </div>

      {/* All scoped listings for this campus */}
      <div className="space-y-2 pt-2">
        <div className="flex items-center justify-between">
          <h3 className="text-xs font-bold text-white uppercase tracking-wider">
            Available at {selectedCampus.code} ({campusListings.length})
          </h3>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-3 gap-2.5 sm:gap-3">
          {campusListings.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
              onClick={() => viewProductDetails(product)}
              onSaveToggle={() => toggleSave(product.id)}
            />
          ))}
        </div>
      </div>
    </div>
  );
};
