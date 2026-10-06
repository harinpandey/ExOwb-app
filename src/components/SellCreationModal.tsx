import React, { useState } from 'react';
import { X, Camera, Plus, Sparkles, Building, AlertCircle, Repeat, CheckCircle } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ListingType, ProductCondition } from '../types';

export const SellCreationModal: React.FC = () => {
  const { isSellModalOpen, setIsSellModalOpen, addListing, selectedCampus, categories } = useExOwn();

  const [title, setTitle] = useState('');
  const [categoryId, setCategoryId] = useState('bikes-transport');
  const [listingType, setListingType] = useState<ListingType>('SELL');
  const [price, setPrice] = useState('');
  const [originalPrice, setOriginalPrice] = useState('');
  const [condition, setCondition] = useState<ProductCondition>('GOOD');
  const [location, setLocation] = useState(selectedCampus.zones[0] || 'Campus Center');
  const [description, setDescription] = useState('');
  const [isUrgent, setIsUrgent] = useState(false);
  const [isExchangeEligible, setIsExchangeEligible] = useState(false);
  const [exchangePreferences, setExchangePreferences] = useState('');
  const [imageUrl, setImageUrl] = useState('https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=800&q=80');
  const [successMsg, setSuccessMsg] = useState(false);

  if (!isSellModalOpen) return null;

  const samplePhotos = [
    { label: 'Watch/Gadget', url: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=800&q=80' },
    { label: 'Bicycle', url: 'https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=800&q=80' },
    { label: 'Textbook', url: 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=800&q=80' },
    { label: 'Hostel Chair', url: 'https://images.unsplash.com/photo-1580481077195-c3a821a58875?w=800&q=80' },
    { label: 'Appliance', url: 'https://images.unsplash.com/photo-1585515320310-259814833e62?w=800&q=80' },
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const priceNum = parseFloat(price);
    if (!title.trim() || isNaN(priceNum) || priceNum <= 0) return;

    const cat = categories.find((c) => c.id === categoryId) || categories[1];

    addListing({
      title: title.trim(),
      price: priceNum,
      originalPrice: originalPrice ? parseFloat(originalPrice) : null,
      listingType,
      condition,
      categoryId: cat.id,
      categoryName: cat.name,
      description: description.trim() || `Available at ${selectedCampus.code} for student handover.`,
      imageUrl,
      location: location.trim() || `${selectedCampus.code} Campus`,
      campusId: selectedCampus.id,
      campusCode: selectedCampus.code,
      campusCity: selectedCampus.city,
      isUrgent,
      isVerified: true,
      isExchangeEligible,
      exchangePreferences: isExchangeEligible ? exchangePreferences : '',
      rentalDurationUnit: listingType === 'RENT' ? 'month' : undefined,
    });

    setSuccessMsg(true);
    setTimeout(() => {
      setSuccessMsg(false);
      setIsSellModalOpen(false);
    }, 1200);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/85 backdrop-blur-xs animate-in fade-in duration-200">
      <div
        className="w-full max-w-lg bg-[#10141B] border border-[#1F2633] rounded-t-2xl sm:rounded-2xl max-h-[92vh] flex flex-col overflow-hidden shadow-2xl"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="p-4 border-b border-[#1F2633] flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-[#2A72E8]/20 flex items-center justify-center text-[#2A72E8]">
              <Plus className="w-5 h-5 stroke-[2.5]" />
            </div>
            <div>
              <h2 className="text-base font-bold text-white">Create Campus Listing</h2>
              <p className="text-xs text-[#94A3B8]">Post to {selectedCampus.name} peers</p>
            </div>
          </div>
          <button
            onClick={() => setIsSellModalOpen(false)}
            aria-label="Close"
            className="w-8 h-8 rounded-full bg-[#161C25] hover:bg-[#1F2633] flex items-center justify-center text-[#94A3B8] hover:text-white transition-colors"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {successMsg ? (
          <div className="p-12 text-center space-y-3">
            <CheckCircle className="w-14 h-14 text-[#10B981] mx-auto animate-bounce" />
            <h3 className="text-lg font-black text-white">Listing Published!</h3>
            <p className="text-xs text-[#94A3B8]">
              Your listing is now live on {selectedCampus.code} marketplace.
            </p>
          </div>
        ) : (
          <form onSubmit={handleSubmit} className="overflow-y-auto p-4 space-y-4 flex-1">
            {/* Camera / Photo preview */}
            <div>
              <label className="text-xs font-semibold text-white block mb-1.5 flex items-center justify-between">
                <span>Product Photo (Visual Hero)</span>
                <span className="text-[10px] text-[#2A72E8] flex items-center gap-1">
                  <Camera className="w-3 h-3" /> Real campus photos
                </span>
              </label>

              <div className="flex gap-3 items-center">
                <img
                  src={imageUrl}
                  alt="Preview"
                  className="w-20 h-20 rounded-xl object-cover border border-[#1F2633] shrink-0"
                />
                <div className="flex-1 space-y-1.5">
                  <input
                    type="url"
                    value={imageUrl}
                    onChange={(e) => setImageUrl(e.target.value)}
                    placeholder="Enter image URL..."
                    className="w-full bg-[#161C25] border border-[#1F2633] rounded-lg px-3 py-1.5 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
                  />
                  <div className="flex flex-wrap gap-1">
                    {samplePhotos.map((photo) => (
                      <button
                        key={photo.label}
                        type="button"
                        onClick={() => setImageUrl(photo.url)}
                        className="text-[10px] px-2 py-0.5 rounded bg-[#161C25] hover:bg-[#1F2633] text-[#94A3B8] border border-[#1F2633]"
                      >
                        {photo.label}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            </div>

            {/* Title */}
            <div>
              <label className="text-xs font-semibold text-white block mb-1">Title *</label>
              <input
                type="text"
                required
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="e.g. Hero Sprint Pro Cycle or Engineering Maths Vol 1"
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-sm text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
              />
            </div>

            {/* Listing Type & Category */}
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-white block mb-1">Deal Type</label>
                <select
                  value={listingType}
                  onChange={(e) => setListingType(e.target.value as ListingType)}
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white focus:outline-hidden focus:border-[#2A72E8]"
                >
                  <option value="SELL">For Sale</option>
                  <option value="RENT">Rental</option>
                  <option value="EXCHANGE">Exchange (Barter)</option>
                  <option value="BUY">Wanted</option>
                  <option value="GIG">Student Gig</option>
                </select>
              </div>

              <div>
                <label className="text-xs font-semibold text-white block mb-1">Category</label>
                <select
                  value={categoryId}
                  onChange={(e) => setCategoryId(e.target.value)}
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white focus:outline-hidden focus:border-[#2A72E8]"
                >
                  {categories.filter((c) => c.id !== 'all').map((c) => (
                    <option key={c.id} value={c.id}>
                      {c.name}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            {/* Price & Original Price */}
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-white block mb-1">Price (₹) *</label>
                <input
                  type="number"
                  required
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                  placeholder="e.g. 1500"
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-sm font-bold text-[#2A72E8] placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
                />
              </div>

              <div>
                <label className="text-xs font-semibold text-white block mb-1">Original Price (₹)</label>
                <input
                  type="number"
                  value={originalPrice}
                  onChange={(e) => setOriginalPrice(e.target.value)}
                  placeholder="e.g. 3000 (optional)"
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-sm text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
                />
              </div>
            </div>

            {/* Condition & Pickup Location */}
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-white block mb-1">Condition</label>
                <select
                  value={condition}
                  onChange={(e) => setCondition(e.target.value as ProductCondition)}
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white focus:outline-hidden focus:border-[#2A72E8]"
                >
                  <option value="LIKE_NEW">Like New</option>
                  <option value="GOOD">Good Condition</option>
                  <option value="FAIR">Fair / Usable</option>
                  <option value="NEW">Brand New</option>
                </select>
              </div>

              <div>
                <label className="text-xs font-semibold text-white block mb-1">Pickup Spot</label>
                <input
                  type="text"
                  value={location}
                  onChange={(e) => setLocation(e.target.value)}
                  placeholder="e.g. BH-4, Law Gate, SJT..."
                  className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
                />
              </div>
            </div>

            {/* Barter / Exchange Checkbox */}
            <div className="p-3 bg-[#161C25] border border-[#1F2633] rounded-xl space-y-2">
              <label className="flex items-center gap-2 cursor-pointer">
                <input
                  type="checkbox"
                  checked={isExchangeEligible || listingType === 'EXCHANGE'}
                  onChange={(e) => setIsExchangeEligible(e.target.checked)}
                  className="w-4 h-4 rounded text-[#10B981] bg-[#10141B] border-[#1F2633] focus:ring-0"
                />
                <span className="text-xs font-bold text-white flex items-center gap-1.5">
                  <Repeat className="w-3.5 h-3.5 text-[#10B981]" /> Open to Barter / Exchange
                </span>
              </label>

              {(isExchangeEligible || listingType === 'EXCHANGE') && (
                <input
                  type="text"
                  value={exchangePreferences}
                  onChange={(e) => setExchangePreferences(e.target.value)}
                  placeholder="What would you barter for? (e.g. Kettle, Mouse, Study Table)"
                  className="w-full bg-[#10141B] border border-[#1F2633] rounded-lg px-3 py-1.5 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#10B981]"
                />
              )}
            </div>

            {/* Urgent moving-out flag */}
            <div className="flex items-center justify-between p-3 bg-[#161C25] border border-[#1F2633] rounded-xl">
              <div className="flex items-center gap-2">
                <AlertCircle className="w-4 h-4 text-[#EF4444]" />
                <div>
                  <div className="text-xs font-bold text-white">Urgent / Moving-out Deal</div>
                  <div className="text-[10px] text-[#94A3B8]">Need quick handover before semester ends</div>
                </div>
              </div>
              <input
                type="checkbox"
                checked={isUrgent}
                onChange={(e) => setIsUrgent(e.target.checked)}
                className="w-4 h-4 rounded text-[#EF4444] bg-[#10141B] border-[#1F2633] focus:ring-0"
              />
            </div>

            {/* Description */}
            <div>
              <label className="text-xs font-semibold text-white block mb-1">Description</label>
              <textarea
                rows={3}
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="Give specs, reasons for selling, warranty, bill status..."
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl p-3 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
              />
            </div>

            <div className="pt-2">
              <button
                type="submit"
                className="w-full py-3 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-black text-sm rounded-xl transition-all shadow-lg shadow-[#2A72E8]/20 flex items-center justify-center gap-2"
              >
                Publish to {selectedCampus.code} Peers
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};
