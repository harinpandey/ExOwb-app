import React, { useState } from 'react';
import { X, CheckCircle, MapPin, Clock, ArrowRight, ShieldCheck, Handshake, AlertCircle } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';

export const DealRoomModal: React.FC = () => {
  const { isDealRoomOpen, setIsDealRoomOpen, activeConversation, updateDealStatus, selectedCampus } = useExOwn();
  const [offerInput, setOfferInput] = useState(activeConversation?.listingPrice?.toString() || '');
  const [pickupInput, setPickupInput] = useState(activeConversation?.pickupLocation || `${selectedCampus.zones[0] || 'Hostel Lobby'}`);
  const [timeInput, setTimeInput] = useState('Today at 5:30 PM');

  if (!isDealRoomOpen || !activeConversation) return null;

  const currentStatus = activeConversation.dealStatus || 'NEGOTIATING';

  const steps = [
    { id: 'OFFER_MADE', label: 'Offer Sent', done: ['OFFER_MADE', 'OFFER_ACCEPTED', 'PICKUP_SCHEDULED', 'COMPLETED'].includes(currentStatus) },
    { id: 'OFFER_ACCEPTED', label: 'Accepted', done: ['OFFER_ACCEPTED', 'PICKUP_SCHEDULED', 'COMPLETED'].includes(currentStatus) },
    { id: 'PICKUP_SCHEDULED', label: 'Pickup Agreed', done: ['PICKUP_SCHEDULED', 'COMPLETED'].includes(currentStatus) },
    { id: 'COMPLETED', label: 'Handover Done', done: currentStatus === 'COMPLETED' },
  ];

  const handleMakeOffer = () => {
    const amount = parseFloat(offerInput);
    if (!amount || amount <= 0) return;
    updateDealStatus(activeConversation.id, 'OFFER_MADE', { price: amount });
  };

  const handleAcceptOffer = () => {
    updateDealStatus(activeConversation.id, 'OFFER_ACCEPTED', { price: activeConversation.agreedPrice || parseFloat(offerInput) });
  };

  const handleSchedulePickup = () => {
    if (!pickupInput.trim()) return;
    updateDealStatus(activeConversation.id, 'PICKUP_SCHEDULED', {
      pickup: pickupInput,
      time: timeInput,
    });
  };

  const handleCompleteHandover = () => {
    updateDealStatus(activeConversation.id, 'COMPLETED');
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-xs animate-in fade-in duration-200">
      <div
        className="w-full max-w-md bg-[#10141B] border border-[#1F2633] rounded-2xl overflow-hidden shadow-2xl flex flex-col max-h-[90vh]"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="p-4 border-b border-[#1F2633] flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-[#2A72E8]/20 flex items-center justify-center text-[#2A72E8]">
              <Handshake className="w-4 h-4" />
            </div>
            <div>
              <h2 className="text-base font-bold text-white">Campus Deal Room</h2>
              <p className="text-xs text-[#94A3B8]">Safe peer handover state machine</p>
            </div>
          </div>
          <button
            onClick={() => setIsDealRoomOpen(false)}
            aria-label="Close Deal Room"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Item Summary Card */}
        <div className="p-3 mx-4 mt-4 bg-[#161C25] border border-[#1F2633] rounded-xl flex items-center gap-3">
          {activeConversation.listingImage ? (
            <img
              src={activeConversation.listingImage}
              alt=""
              className="w-14 h-14 rounded-lg object-cover shrink-0"
            />
          ) : (
            <div className="w-14 h-14 rounded-lg bg-[#1F2633] shrink-0" />
          )}
          <div className="flex-1 min-w-0">
            <h3 className="text-xs font-bold text-white truncate">
              {activeConversation.listingTitle || 'Campus Item'}
            </h3>
            <p className="text-[11px] text-[#94A3B8] mt-0.5">
              Listed Price: <span className="text-[#2A72E8] font-bold">₹{activeConversation.listingPrice?.toLocaleString('en-IN')}</span>
            </p>
            <p className="text-[11px] text-[#10B981] font-semibold mt-0.5">
              Seller: {activeConversation.otherUserName} ({activeConversation.otherUserCampus})
            </p>
          </div>
        </div>

        {/* State Machine Progress Bar */}
        <div className="px-4 py-3">
          <div className="flex items-center justify-between">
            {steps.map((step, idx) => (
              <React.Fragment key={step.id}>
                <div className="flex flex-col items-center">
                  <div
                    className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold transition-colors ${
                      step.done
                        ? 'bg-[#10B981] text-black shadow-xs'
                        : 'bg-[#161C25] text-[#64748B] border border-[#1F2633]'
                    }`}
                  >
                    {step.done ? <CheckCircle className="w-4 h-4" /> : idx + 1}
                  </div>
                  <span
                    className={`text-[10px] mt-1 font-semibold ${
                      step.done ? 'text-white' : 'text-[#64748B]'
                    }`}
                  >
                    {step.label}
                  </span>
                </div>
                {idx < steps.length - 1 && (
                  <div
                    className={`flex-1 h-0.5 mx-1.5 transition-colors ${
                      steps[idx + 1].done ? 'bg-[#10B981]' : 'bg-[#1F2633]'
                    }`}
                  />
                )}
              </React.Fragment>
            ))}
          </div>
        </div>

        {/* Interactive Step Forms */}
        <div className="p-4 overflow-y-auto space-y-4">
          {currentStatus === 'NEGOTIATING' && (
            <div className="space-y-3 bg-[#161C25] p-3.5 rounded-xl border border-[#1F2633]">
              <label className="text-xs font-semibold text-white block">
                Submit Peer Offer Amount (₹)
              </label>
              <div className="flex gap-2">
                <input
                  type="number"
                  value={offerInput}
                  onChange={(e) => setOfferInput(e.target.value)}
                  className="flex-1 bg-[#10141B] border border-[#1F2633] rounded-lg px-3 py-2 text-white font-bold text-sm focus:outline-hidden focus:border-[#2A72E8]"
                  placeholder="e.g. 3500"
                />
                <button
                  onClick={handleMakeOffer}
                  className="px-4 py-2 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-bold text-xs rounded-lg transition-colors flex items-center gap-1"
                >
                  Send Offer <ArrowRight className="w-3.5 h-3.5" />
                </button>
              </div>
              <p className="text-[11px] text-[#94A3B8]">
                Offers are binding campus agreements. Once accepted, arrange a hostel or campus spot.
              </p>
            </div>
          )}

          {currentStatus === 'OFFER_MADE' && (
            <div className="space-y-3 bg-[#161C25] p-3.5 rounded-xl border border-[#1F2633]">
              <div className="flex items-center justify-between">
                <span className="text-xs text-[#94A3B8]">Active Agreed Offer:</span>
                <span className="text-base font-black text-[#2A72E8]">
                  ₹{activeConversation.agreedPrice?.toLocaleString('en-IN')}
                </span>
              </div>
              <button
                onClick={handleAcceptOffer}
                className="w-full py-2.5 bg-[#10B981] hover:bg-[#059669] text-black font-black text-xs rounded-lg transition-colors flex items-center justify-center gap-1.5"
              >
                <CheckCircle className="w-4 h-4" /> Confirm & Accept Offer
              </button>
            </div>
          )}

          {currentStatus === 'OFFER_ACCEPTED' && (
            <div className="space-y-3 bg-[#161C25] p-3.5 rounded-xl border border-[#1F2633]">
              <div className="text-xs font-bold text-white">Select Designated Campus Handover Spot</div>
              <div>
                <label className="text-[11px] text-[#94A3B8] block mb-1">Campus Location</label>
                <input
                  type="text"
                  value={pickupInput}
                  onChange={(e) => setPickupInput(e.target.value)}
                  className="w-full bg-[#10141B] border border-[#1F2633] rounded-lg px-3 py-2 text-white text-xs focus:outline-hidden focus:border-[#2A72E8]"
                  placeholder="e.g. LPU Main Gate, Uni-Mall, or BH-4 Lobby"
                />
              </div>

              {selectedCampus.zones.length > 0 && (
                <div className="flex flex-wrap gap-1.5 pt-1">
                  {selectedCampus.zones.slice(0, 3).map((zone) => (
                    <button
                      key={zone}
                      onClick={() => setPickupInput(zone)}
                      className="text-[10px] px-2 py-1 bg-[#10141B] hover:bg-[#1F2633] border border-[#1F2633] text-[#94A3B8] rounded"
                    >
                      + {zone}
                    </button>
                  ))}
                </div>
              )}

              <div>
                <label className="text-[11px] text-[#94A3B8] block mb-1">Agreed Time</label>
                <input
                  type="text"
                  value={timeInput}
                  onChange={(e) => setTimeInput(e.target.value)}
                  className="w-full bg-[#10141B] border border-[#1F2633] rounded-lg px-3 py-2 text-white text-xs focus:outline-hidden focus:border-[#2A72E8]"
                  placeholder="e.g. Today at 5:00 PM"
                />
              </div>

              <button
                onClick={handleSchedulePickup}
                className="w-full py-2.5 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-bold text-xs rounded-lg transition-colors flex items-center justify-center gap-1.5"
              >
                <MapPin className="w-4 h-4" /> Lock Campus Pickup Details
              </button>
            </div>
          )}

          {currentStatus === 'PICKUP_SCHEDULED' && (
            <div className="space-y-3 bg-[#161C25] p-3.5 rounded-xl border border-[#10B981]/40">
              <div className="flex items-center gap-2 text-[#10B981] text-xs font-bold">
                <CheckCircle className="w-4 h-4" />
                Pickup Scheduled at {activeConversation.pickupLocation}
              </div>
              <div className="text-xs text-[#94A3B8] space-y-1">
                <p className="flex items-center gap-1.5">
                  <Clock className="w-3.5 h-3.5 text-[#2A72E8]" /> {activeConversation.pickupTime || 'Today'}
                </p>
                <p className="flex items-center gap-1.5">
                  <MapPin className="w-3.5 h-3.5 text-[#10B981]" /> {activeConversation.pickupLocation}
                </p>
              </div>

              <div className="p-2.5 rounded-lg bg-[#10141B] border border-[#1F2633] text-[11px] text-[#F59E0B] flex items-start gap-2">
                <AlertCircle className="w-4 h-4 shrink-0 mt-0.5" />
                <span>Always inspect the physical item before making cash or UPI handover.</span>
              </div>

              <button
                onClick={handleCompleteHandover}
                className="w-full py-2.5 bg-[#10B981] hover:bg-[#059669] text-black font-black text-xs rounded-lg transition-colors flex items-center justify-center gap-1.5"
              >
                <ShieldCheck className="w-4 h-4" /> Complete Handover & Update Trust Passport
              </button>
            </div>
          )}

          {currentStatus === 'COMPLETED' && (
            <div className="text-center p-4 bg-[#10B981]/15 border border-[#10B981]/40 rounded-xl space-y-2">
              <CheckCircle className="w-10 h-10 text-[#10B981] mx-auto" />
              <h4 className="text-sm font-black text-white">Deal Successfully Completed!</h4>
              <p className="text-xs text-[#94A3B8]">
                Handover recorded at {activeConversation.pickupLocation}. Both students earn Trust Passport credit!
              </p>
            </div>
          )}
        </div>

        {/* Footer */}
        <div className="p-3 bg-[#161C25] border-t border-[#1F2633] text-center text-[11px] text-[#64748B]">
          Zero middlemen · Zero commission · Student-to-student trusted protocol
        </div>
      </div>
    </div>
  );
};
