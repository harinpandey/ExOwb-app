import React from 'react';
import { MapPin, ChevronDown } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';

export const CampusSelector: React.FC = () => {
  const { selectedCampus, openCampusSwitcher } = useExOwn();

  return (
    <button
      onClick={openCampusSwitcher}
      aria-label={`Change campus, currently selected: ${selectedCampus.code} (${selectedCampus.city})`}
      className="flex items-center gap-1.5 px-2.5 py-1.5 rounded-full bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-white transition-colors text-xs font-semibold"
    >
      <MapPin className="w-3.5 h-3.5 text-[#2A72E8]" />
      <span className="truncate max-w-[110px] sm:max-w-[150px]">
        {selectedCampus.code} · {selectedCampus.city}
      </span>
      <ChevronDown className="w-3 h-3 text-[#94A3B8]" />
    </button>
  );
};
