import React from 'react';
import {
  ArrowLeft,
  MapPin,
  CheckCircle2,
  Phone,
  ShieldCheck,
  Wifi,
  Sparkles,
  Home,
  IndianRupee,
  Share2,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';

export const HousingDetailScreen: React.FC = () => {
  const { selectedHousing, navigateBack } = useExOwn();

  if (!selectedHousing) return null;

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
        <span className="text-xs font-bold text-white truncate max-w-[200px]">
          {selectedHousing.type} Accommodation
        </span>
        <button
          onClick={() => {
            navigator.clipboard?.writeText(window.location.href);
            alert('Copied housing link!');
          }}
          aria-label="Share housing"
          className="w-9 h-9 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white"
        >
          <Share2 className="w-4 h-4" />
        </button>
      </div>

      {/* Image Banner */}
      <div className="relative aspect-16/9 w-full bg-[#161C25] rounded-2xl overflow-hidden border border-[#1F2633] shadow-lg">
        <img
          src={selectedHousing.imageUrl}
          alt={selectedHousing.title}
          className="w-full h-full object-cover"
        />
        <div className="absolute top-3 left-3 bg-[#10141B]/85 text-white text-xs font-bold px-2.5 py-1 rounded-lg backdrop-blur-xs">
          {selectedHousing.type} • {selectedHousing.occupancy}
        </div>
        <div className="absolute bottom-3 right-3 bg-[#10B981] text-black text-xs font-black px-2.5 py-1 rounded-lg">
          {selectedHousing.distanceToCampus}
        </div>
      </div>

      {/* Title & Pricing */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
        <h1 className="text-base sm:text-lg font-bold text-white">{selectedHousing.title}</h1>

        <div className="flex items-baseline gap-3 pt-1">
          <div>
            <span className="text-2xl font-black text-[#2A72E8]">
              ₹{selectedHousing.rentPrice.toLocaleString('en-IN')}
            </span>
            <span className="text-xs text-[#94A3B8]"> /month</span>
          </div>

          <div className="text-xs text-[#94A3B8] border-l border-[#1F2633] pl-3">
            Security Deposit: <strong className="text-white">₹{selectedHousing.deposit.toLocaleString('en-IN')}</strong>
          </div>
        </div>

        <div className="flex items-center gap-1.5 text-xs text-[#94A3B8] pt-2 border-t border-[#1F2633]">
          <MapPin className="w-4 h-4 text-[#2A72E8] shrink-0" />
          <span>{selectedHousing.address}</span>
        </div>
      </div>

      {/* Amenities Grid */}
      <div className="p-4 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
        <h2 className="text-xs font-bold text-white uppercase tracking-wider">
          Included Amenities
        </h2>
        <div className="grid grid-cols-2 gap-2">
          {selectedHousing.amenities.map((am) => (
            <div
              key={am}
              className="p-2.5 rounded-xl bg-[#161C25] border border-[#1F2633] text-xs text-white flex items-center gap-2"
            >
              <CheckCircle2 className="w-4 h-4 text-[#10B981] shrink-0" />
              <span>{am}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Verification & Trust */}
      <div className="p-3.5 bg-[#10141B] border border-[#10B981]/30 rounded-xl flex items-center gap-3">
        <ShieldCheck className="w-6 h-6 text-[#10B981] shrink-0" />
        <div>
          <div className="text-xs font-bold text-white">Campus Verified PG</div>
          <div className="text-[11px] text-[#94A3B8]">
            Inspected for student safety, electricity backup, and university proximity.
          </div>
        </div>
      </div>

      {/* Sticky Call Bottom Bar */}
      <div className="fixed bottom-0 left-0 right-0 z-40 bg-[#10141B] border-t border-[#1F2633] p-3">
        <div className="max-w-3xl mx-auto flex items-center gap-3">
          <a
            href={`tel:${selectedHousing.contactPhone}`}
            className="flex-1 py-3 px-4 rounded-xl bg-[#2A72E8] hover:bg-[#2563EB] text-xs font-black text-white transition-colors flex items-center justify-center gap-2 shadow-lg shadow-[#2A72E8]/25"
          >
            <Phone className="w-4 h-4" />
            Call Owner ({selectedHousing.contactPhone})
          </a>
        </div>
      </div>
    </div>
  );
};
