import React from 'react';
import { X, ShieldCheck, CheckCircle2, Award, Sparkles, Leaf, IndianRupee, MapPin } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';

export const TrustPassportModal: React.FC = () => {
  const { isTrustPassportOpen, setIsTrustPassportOpen, currentUser, selectedCampus } = useExOwn();

  if (!isTrustPassportOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-xs animate-in fade-in duration-200">
      <div
        className="w-full max-w-md bg-[#10141B] border border-[#1F2633] rounded-2xl overflow-hidden shadow-2xl flex flex-col max-h-[90vh]"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="p-4 border-b border-[#1F2633] flex items-center justify-between bg-radial from-[#2A72E8]/10 to-transparent">
          <div className="flex items-center gap-2.5">
            <div className="w-9 h-9 rounded-xl bg-[#10B981]/20 border border-[#10B981]/40 flex items-center justify-center text-[#10B981]">
              <ShieldCheck className="w-5 h-5" />
            </div>
            <div>
              <h2 className="text-base font-black text-white flex items-center gap-1.5">
                ExOwn Trust Passport
                <span className="text-[10px] font-bold bg-[#10B981]/20 text-[#10B981] px-1.5 py-0.5 rounded">
                  AUTHENTIC
                </span>
              </h2>
              <p className="text-xs text-[#94A3B8]">Campus-verified peer commerce record</p>
            </div>
          </div>
          <button
            onClick={() => setIsTrustPassportOpen(false)}
            aria-label="Close Trust Passport"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Content */}
        <div className="overflow-y-auto p-4 space-y-4">
          {/* Student Identity Card */}
          <div className="p-4 rounded-xl bg-[#161C25] border border-[#1F2633] space-y-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="w-12 h-12 rounded-full bg-[#2A72E8] flex items-center justify-center text-white text-lg font-black">
                  {currentUser.name.slice(0, 1)}
                </div>
                <div>
                  <h3 className="text-sm font-bold text-white flex items-center gap-1.5">
                    {currentUser.name}
                    <CheckCircle2 className="w-4 h-4 text-[#10B981] fill-[#10B981]/20" />
                  </h3>
                  <p className="text-xs text-[#94A3B8]">{currentUser.department} • {currentUser.year}</p>
                  <p className="text-[11px] text-[#10B981] flex items-center gap-1 mt-0.5">
                    <MapPin className="w-3 h-3" /> {currentUser.hostel}
                  </p>
                </div>
              </div>
              <div className="text-right">
                <div className="text-xl font-black text-[#10B981]">{currentUser.trustScore}/100</div>
                <div className="text-[10px] text-[#64748B] uppercase tracking-wider font-semibold">Trust Index</div>
              </div>
            </div>

            <div className="pt-2 border-t border-[#1F2633] flex items-center justify-between text-xs text-[#94A3B8]">
              <span>Affiliation: {currentUser.university}</span>
              <span className="text-[#10B981] font-semibold">{currentUser.email}</span>
            </div>
          </div>

          {/* Verification Criteria */}
          <div className="space-y-2">
            <div className="text-xs font-bold text-[#94A3B8] uppercase tracking-wider px-1">
              Verified Trust Attributes
            </div>

            <div className="space-y-1.5">
              <div className="p-2.5 rounded-lg bg-[#161C25]/80 border border-[#1F2633] flex items-center justify-between text-xs">
                <div className="flex items-center gap-2 text-white">
                  <CheckCircle2 className="w-4 h-4 text-[#10B981]" />
                  <span>College Domain Verification ({selectedCampus.verificationDomain})</span>
                </div>
                <span className="text-[#10B981] font-bold">Confirmed</span>
              </div>

              <div className="p-2.5 rounded-lg bg-[#161C25]/80 border border-[#1F2633] flex items-center justify-between text-xs">
                <div className="flex items-center gap-2 text-white">
                  <Award className="w-4 h-4 text-[#2A72E8]" />
                  <span>Completed Campus Handovers</span>
                </div>
                <span className="text-white font-bold">{currentUser.soldListings} Deals</span>
              </div>

              <div className="p-2.5 rounded-lg bg-[#161C25]/80 border border-[#1F2633] flex items-center justify-between text-xs">
                <div className="flex items-center gap-2 text-white">
                  <ShieldCheck className="w-4 h-4 text-[#10B981]" />
                  <span>Dispute & Violation Flags</span>
                </div>
                <span className="text-[#10B981] font-bold">0 Clean</span>
              </div>

              <div className="p-2.5 rounded-lg bg-[#161C25]/80 border border-[#1F2633] flex items-center justify-between text-xs">
                <div className="flex items-center gap-2 text-white">
                  <Sparkles className="w-4 h-4 text-[#F59E0B]" />
                  <span>Peer Rating</span>
                </div>
                <span className="text-[#F59E0B] font-bold">4.9 / 5.0 (18 reviews)</span>
              </div>
            </div>
          </div>

          {/* Eco & Financial Circular Impact */}
          <div className="p-3.5 rounded-xl bg-gradient-to-r from-[#10B981]/15 to-[#2A72E8]/15 border border-[#10B981]/30">
            <div className="text-xs font-bold text-[#10B981] mb-2 flex items-center gap-1.5">
              <Leaf className="w-4 h-4" />
              Circular Campus Impact
            </div>
            <div className="grid grid-cols-2 gap-2 text-center">
              <div className="p-2 rounded-lg bg-[#10141B]/80 border border-[#1F2633]">
                <div className="text-base font-black text-white flex items-center justify-center gap-0.5">
                  <IndianRupee className="w-3.5 h-3.5 text-[#10B981]" />
                  {currentUser.totalSavedInr.toLocaleString('en-IN')}
                </div>
                <div className="text-[10px] text-[#94A3B8]">Saved by Campus Re-commerce</div>
              </div>
              <div className="p-2 rounded-lg bg-[#10141B]/80 border border-[#1F2633]">
                <div className="text-base font-black text-[#10B981]">28.4 kg</div>
                <div className="text-[10px] text-[#94A3B8]">CO₂ Landfill Offset</div>
              </div>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="p-3 bg-[#161C25] border-t border-[#1F2633] text-center">
          <p className="text-[11px] text-[#94A3B8]">
            ExOwn never manufactures artificial reviews or vanity metrics. All transactions are local to {selectedCampus.name}.
          </p>
        </div>
      </div>
    </div>
  );
};
