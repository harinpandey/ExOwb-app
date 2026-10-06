import React, { useState } from 'react';
import { X, Search, Check, Building2, Users, PackageCheck } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { Campus } from '../types';

export const CampusSwitcherModal: React.FC = () => {
  const { showCampusSwitcher, closeCampusSwitcher, campuses, selectedCampus, selectCampus } = useExOwn();
  const [search, setSearch] = useState('');

  if (!showCampusSwitcher) return null;

  const filteredCampuses = campuses.filter(
    (c) =>
      c.name.toLowerCase().includes(search.toLowerCase()) ||
      c.code.toLowerCase().includes(search.toLowerCase()) ||
      c.city.toLowerCase().includes(search.toLowerCase()) ||
      c.state.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/80 backdrop-blur-xs animate-in fade-in duration-200">
      <div
        className="w-full max-w-lg bg-[#10141B] border border-[#1F2633] rounded-t-2xl sm:rounded-2xl max-h-[85vh] flex flex-col overflow-hidden shadow-2xl"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="p-4 border-b border-[#1F2633] flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-[#2A72E8]/20 flex items-center justify-center text-[#2A72E8]">
              <Building2 className="w-4 h-4" />
            </div>
            <div>
              <h2 className="text-base font-bold text-white">Select Your University Campus</h2>
              <p className="text-xs text-[#94A3B8]">Browse listings and deals exclusive to your peers</p>
            </div>
          </div>
          <button
            onClick={closeCampusSwitcher}
            aria-label="Close modal"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Search */}
        <div className="p-3 border-b border-[#1F2633]">
          <div className="relative">
            <Search className="w-4 h-4 text-[#64748B] absolute left-3 top-1/2 -translate-y-1/2" />
            <input
              type="text"
              placeholder="Search by campus, city, or code (e.g. LPU, Vellore)..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl pl-9 pr-4 py-2 text-sm text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
            />
          </div>
        </div>

        {/* Campus List */}
        <div className="overflow-y-auto p-3 space-y-2 flex-1">
          {filteredCampuses.map((campus: Campus) => {
            const isSelected = selectedCampus.id === campus.id;
            return (
              <button
                key={campus.id}
                onClick={() => selectCampus(campus)}
                className={`w-full text-left p-3.5 rounded-xl border transition-all flex items-start justify-between gap-3 ${
                  isSelected
                    ? 'bg-[#2A72E8]/10 border-[#2A72E8] shadow-xs'
                    : 'bg-[#161C25] border-[#1F2633] hover:border-[#2A72E8]/40 hover:bg-[#1A222E]'
                }`}
              >
                <div className="space-y-1">
                  <div className="flex items-center gap-2">
                    <span className="font-bold text-sm text-white">{campus.name}</span>
                    <span className="bg-[#10141B] border border-[#1F2633] text-[#2A72E8] text-[10px] font-black px-1.5 py-0.5 rounded">
                      {campus.code}
                    </span>
                  </div>
                  <p className="text-xs text-[#94A3B8]">
                    {campus.city}, {campus.state} · <span className="text-[#10B981]">{campus.verificationDomain}</span>
                  </p>
                  <div className="flex items-center gap-3 pt-1 text-[11px] text-[#64748B]">
                    <span className="flex items-center gap-1">
                      <Users className="w-3 h-3 text-[#2A72E8]" />
                      {campus.studentCount.toLocaleString('en-IN')} students
                    </span>
                    <span>•</span>
                    <span className="flex items-center gap-1">
                      <PackageCheck className="w-3 h-3 text-[#10B981]" />
                      {campus.activeListingsCount} active listings
                    </span>
                  </div>
                </div>

                {isSelected && (
                  <div className="w-6 h-6 rounded-full bg-[#2A72E8] flex items-center justify-center text-white shrink-0 mt-1">
                    <Check className="w-3.5 h-3.5" />
                  </div>
                )}
              </button>
            );
          })}

          {filteredCampuses.length === 0 && (
            <div className="text-center py-8 text-[#94A3B8] text-sm">
              No university campus matches "{search}".
            </div>
          )}
        </div>

        {/* Footer */}
        <div className="p-3 bg-[#161C25] border-t border-[#1F2633] text-center text-xs text-[#94A3B8]">
          Only peers with verified university email addresses can make purchases and handovers.
        </div>
      </div>
    </div>
  );
};
