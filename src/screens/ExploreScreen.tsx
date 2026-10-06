import React from 'react';
import {
  Search,
  X,
  Filter,
  ArrowUpDown,
  Flame,
  Repeat,
  SlidersHorizontal,
  RotateCcw,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ProductCard } from '../components/ProductCard';
import { ListingType, ProductCondition } from '../types';

export const ExploreScreen: React.FC = () => {
  const {
    searchQuery,
    setSearchQuery,
    selectedCategoryId,
    setSelectedCategoryId,
    selectedListingType,
    setSelectedListingType,
    selectedCondition,
    setSelectedCondition,
    filterOnlyUrgent,
    toggleFilterOnlyUrgent,
    filterOnlyExchange,
    toggleFilterOnlyExchange,
    sortBy,
    setSortBy,
    resetFilters,
    filteredListings,
    categories,
    selectedCampus,
    viewProductDetails,
    toggleSave,
  } = useExOwn();

  const dealTypes: { label: string; value: ListingType | null }[] = [
    { label: 'All Deals', value: null },
    { label: 'For Sale', value: 'SELL' },
    { label: 'Rental', value: 'RENT' },
    { label: 'Exchange (Barter)', value: 'EXCHANGE' },
    { label: 'Wanted', value: 'BUY' },
  ];

  const conditions: { label: string; value: ProductCondition | null }[] = [
    { label: 'All Conditions', value: null },
    { label: 'Like New', value: 'LIKE_NEW' },
    { label: 'Good', value: 'GOOD' },
    { label: 'Brand New', value: 'NEW' },
    { label: 'Usable', value: 'FAIR' },
  ];

  const hasActiveFilters =
    searchQuery.trim() !== '' ||
    selectedCategoryId !== 'all' ||
    selectedListingType !== null ||
    selectedCondition !== null ||
    filterOnlyUrgent ||
    filterOnlyExchange ||
    sortBy !== 'newest';

  return (
    <div className="pb-24 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-3.5">
      {/* 1. Search Bar */}
      <div className="relative">
        <Search className="w-4 h-4 text-[#2A72E8] absolute left-3.5 top-1/2 -translate-y-1/2" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder={`Search bikes, books, monitors in ${selectedCampus.code}...`}
          className="w-full bg-[#10141B] border border-[#1F2633] rounded-xl pl-10 pr-9 py-2.5 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            aria-label="Clear search"
            className="absolute right-3 top-1/2 -translate-y-1/2 text-[#64748B] hover:text-white"
          >
            <X className="w-4 h-4" />
          </button>
        )}
      </div>

      {/* 2. Listing Type Tabs */}
      <div className="flex gap-1.5 overflow-x-auto pb-1 scrollbar-none">
        {dealTypes.map((type) => {
          const isSelected = selectedListingType === type.value;
          return (
            <button
              key={type.label}
              onClick={() => setSelectedListingType(type.value)}
              className={`text-xs px-3 py-1.5 rounded-lg border whitespace-nowrap font-medium transition-all ${
                isSelected
                  ? 'bg-[#2A72E8] text-white border-[#2A72E8] font-bold'
                  : 'bg-[#10141B] text-[#94A3B8] border-[#1F2633] hover:border-[#2A72E8]/40 hover:text-white'
              }`}
            >
              {type.label}
            </button>
          );
        })}
      </div>

      {/* 3. Category Chips */}
      <div className="flex gap-1.5 overflow-x-auto pb-1 scrollbar-none">
        {categories.map((cat) => {
          const isSelected = selectedCategoryId === cat.id;
          return (
            <button
              key={cat.id}
              onClick={() => setSelectedCategoryId(cat.id)}
              className={`text-[11px] px-2.5 py-1 rounded-md border whitespace-nowrap transition-all ${
                isSelected
                  ? 'bg-[#161C25] text-white border-[#2A72E8] font-bold'
                  : 'bg-[#10141B]/60 text-[#64748B] border-[#1F2633] hover:text-white'
              }`}
            >
              {cat.name}
            </button>
          );
        })}
      </div>

      {/* 4. Controls Row: Quick Filters & Sort */}
      <div className="flex flex-wrap items-center justify-between gap-2 pt-1 border-t border-[#1F2633]/60">
        <div className="flex items-center gap-2">
          {/* Urgent filter toggle */}
          <button
            onClick={toggleFilterOnlyUrgent}
            className={`flex items-center gap-1 text-[11px] px-2.5 py-1 rounded-md border transition-all ${
              filterOnlyUrgent
                ? 'bg-[#EF4444]/15 border-[#EF4444] text-[#EF4444] font-bold'
                : 'bg-[#10141B] border-[#1F2633] text-[#94A3B8] hover:text-white'
            }`}
          >
            <Flame className="w-3 h-3 text-[#EF4444]" /> Urgent
          </button>

          {/* Exchange filter toggle */}
          <button
            onClick={toggleFilterOnlyExchange}
            className={`flex items-center gap-1 text-[11px] px-2.5 py-1 rounded-md border transition-all ${
              filterOnlyExchange
                ? 'bg-[#10B981]/15 border-[#10B981] text-[#10B981] font-bold'
                : 'bg-[#10141B] border-[#1F2633] text-[#94A3B8] hover:text-white'
            }`}
          >
            <Repeat className="w-3 h-3 text-[#10B981]" /> Barter
          </button>
        </div>

        <div className="flex items-center gap-2">
          {/* Sort selector */}
          <div className="flex items-center gap-1 bg-[#10141B] border border-[#1F2633] rounded-md px-2 py-1 text-[11px] text-[#94A3B8]">
            <ArrowUpDown className="w-3 h-3 text-[#2A72E8]" />
            <select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
              className="bg-transparent text-white focus:outline-hidden text-[11px] cursor-pointer"
            >
              <option value="newest" className="bg-[#10141B]">Newest</option>
              <option value="price_low" className="bg-[#10141B]">Price: Low to High</option>
              <option value="price_high" className="bg-[#10141B]">Price: High to Low</option>
            </select>
          </div>

          {/* Reset filter button */}
          {hasActiveFilters && (
            <button
              onClick={resetFilters}
              aria-label="Reset filters"
              className="p-1 rounded-md bg-[#161C25] hover:bg-[#1F2633] text-[#94A3B8] hover:text-white text-[11px] flex items-center gap-1 transition-colors"
              title="Reset all filters"
            >
              <RotateCcw className="w-3 h-3" />
            </button>
          )}
        </div>
      </div>

      {/* 5. Results Count */}
      <div className="flex items-center justify-between text-xs text-[#94A3B8]">
        <span>
          Showing <span className="text-white font-bold">{filteredListings.length}</span> matching listings
        </span>
        <span className="text-[11px] text-[#64748B]">Scoped to {selectedCampus.name}</span>
      </div>

      {/* 6. Product Grid */}
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
        <div className="p-12 text-center bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-3">
          <p className="text-sm font-bold text-white">No items found</p>
          <p className="text-xs text-[#94A3B8]">
            Try adjusting your search terms, changing category, or clearing active filters.
          </p>
          <button
            onClick={resetFilters}
            className="px-4 py-2 bg-[#161C25] hover:bg-[#1F2633] border border-[#1F2633] text-white text-xs font-bold rounded-lg transition-colors"
          >
            Clear All Filters
          </button>
        </div>
      )}
    </div>
  );
};
