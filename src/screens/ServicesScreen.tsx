import React from 'react';
import {
  Wrench,
  Home,
  Users,
  MapPin,
  Star,
  Clock,
  Phone,
  CheckCircle2,
  Sparkles,
  BadgePercent,
  Building,
} from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';
import { ServicesTab } from '../types';

export const ServicesScreen: React.FC = () => {
  const {
    servicesTab,
    setServicesTab,
    housingListings,
    roommateListings,
    campusServices,
    selectedCampus,
    viewHousingDetails,
  } = useExOwn();

  return (
    <div className="pb-24 pt-2 max-w-4xl mx-auto px-3 sm:px-4 space-y-4">
      {/* Header */}
      <div>
        <h1 className="text-lg font-black text-white flex items-center gap-2">
          Campus Services & Living
          <span className="text-[10px] bg-[#2A72E8]/20 text-[#2A72E8] px-2 py-0.5 rounded font-bold">
            {selectedCampus.code}
          </span>
        </h1>
        <p className="text-xs text-[#94A3B8]">
          Doorstep repairs, trusted PG/flat rentals, and roommate finder
        </p>
      </div>

      {/* Tabs */}
      <div className="grid grid-cols-3 gap-1.5 p-1 bg-[#10141B] border border-[#1F2633] rounded-xl">
        <button
          onClick={() => setServicesTab('SERVICES')}
          className={`py-2 text-xs font-bold rounded-lg transition-all flex items-center justify-center gap-1.5 ${
            servicesTab === 'SERVICES'
              ? 'bg-[#2A72E8] text-white shadow-xs'
              : 'text-[#94A3B8] hover:text-white'
          }`}
        >
          <Wrench className="w-3.5 h-3.5" />
          Campus Gigs
        </button>

        <button
          onClick={() => setServicesTab('HOUSING')}
          className={`py-2 text-xs font-bold rounded-lg transition-all flex items-center justify-center gap-1.5 ${
            servicesTab === 'HOUSING'
              ? 'bg-[#2A72E8] text-white shadow-xs'
              : 'text-[#94A3B8] hover:text-white'
          }`}
        >
          <Home className="w-3.5 h-3.5" />
          Housing & PGs
        </button>

        <button
          onClick={() => setServicesTab('ROOMMATES')}
          className={`py-2 text-xs font-bold rounded-lg transition-all flex items-center justify-center gap-1.5 ${
            servicesTab === 'ROOMMATES'
              ? 'bg-[#2A72E8] text-white shadow-xs'
              : 'text-[#94A3B8] hover:text-white'
          }`}
        >
          <Users className="w-3.5 h-3.5" />
          Roommates
        </button>
      </div>

      {/* Content depending on tab */}
      {servicesTab === 'SERVICES' && (
        <div className="space-y-3">
          <div className="text-xs text-[#94A3B8]">
            Verified student vendors and peers offering doorstep assistance around {selectedCampus.name}.
          </div>

          <div className="space-y-3">
            {campusServices.map((service) => (
              <div
                key={service.id}
                className="p-4 bg-[#10141B] border border-[#1F2633] rounded-xl hover:border-[#2A72E8]/40 transition-colors space-y-3"
              >
                <div className="flex items-start justify-between gap-3">
                  <div>
                    <span className="text-[10px] font-bold text-[#2A72E8] uppercase tracking-wider">
                      {service.category}
                    </span>
                    <h3 className="text-sm font-bold text-white mt-0.5">{service.title}</h3>
                    <p className="text-xs text-[#94A3B8] mt-1">{service.description}</p>
                  </div>
                  <div className="text-right shrink-0">
                    <span className="text-sm font-black text-[#10B981]">
                      ₹{service.priceStarting}
                    </span>
                    <span className="text-[10px] text-[#64748B] block">starting</span>
                  </div>
                </div>

                <div className="flex flex-wrap items-center justify-between gap-2 pt-2 border-t border-[#1F2633]/60 text-xs">
                  <div className="flex items-center gap-3 text-[#94A3B8]">
                    <span className="flex items-center gap-1">
                      <Star className="w-3.5 h-3.5 text-[#F59E0B] fill-[#F59E0B]" />
                      <strong className="text-white">{service.rating}</strong>
                    </span>
                    <span className="flex items-center gap-1">
                      <Clock className="w-3.5 h-3.5 text-[#64748B]" />
                      {service.deliveryTime}
                    </span>
                  </div>

                  <div className="flex items-center gap-1.5">
                    {service.tags.map((tag) => (
                      <span
                        key={tag}
                        className="text-[10px] bg-[#161C25] text-[#94A3B8] border border-[#1F2633] px-2 py-0.5 rounded"
                      >
                        {tag}
                      </span>
                    ))}
                    <button
                      onClick={() => alert(`Contacting ${service.providerName} for ${service.title}`)}
                      className="ml-1 text-xs px-3 py-1 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-bold rounded-lg transition-colors"
                    >
                      Book Gig
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {servicesTab === 'HOUSING' && (
        <div className="space-y-3">
          <div className="text-xs text-[#94A3B8]">
            Curated student accommodations within 1km of {selectedCampus.name} campus gates.
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            {housingListings.map((house) => (
              <div
                key={house.id}
                onClick={() => viewHousingDetails(house)}
                className="cursor-pointer bg-[#10141B] border border-[#1F2633] rounded-xl overflow-hidden hover:border-[#2A72E8]/50 transition-all flex flex-col group"
              >
                <div className="relative aspect-16/9 w-full bg-[#161C25] overflow-hidden">
                  <img
                    src={house.imageUrl}
                    alt={house.title}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                  />
                  <span className="absolute top-2.5 left-2.5 bg-[#10141B]/80 text-white text-[10px] font-bold px-2 py-0.5 rounded backdrop-blur-xs">
                    {house.type} · {house.occupancy}
                  </span>
                  <span className="absolute bottom-2.5 right-2.5 bg-black/75 text-[#10B981] text-[10px] font-bold px-2 py-0.5 rounded backdrop-blur-xs">
                    {house.distanceToCampus}
                  </span>
                </div>

                <div className="p-3.5 flex flex-col flex-1 justify-between gap-3">
                  <div>
                    <h3 className="text-xs font-bold text-white line-clamp-1">{house.title}</h3>
                    <p className="text-[11px] text-[#94A3B8] mt-1 flex items-center gap-1">
                      <MapPin className="w-3 h-3 text-[#64748B] shrink-0" />
                      <span className="truncate">{house.address}</span>
                    </p>
                  </div>

                  <div className="flex flex-wrap gap-1">
                    {house.amenities.slice(0, 3).map((am) => (
                      <span
                        key={am}
                        className="text-[9px] bg-[#161C25] text-[#94A3B8] px-1.5 py-0.5 rounded border border-[#1F2633]"
                      >
                        {am}
                      </span>
                    ))}
                    {house.amenities.length > 3 && (
                      <span className="text-[9px] text-[#64748B] self-center">
                        +{house.amenities.length - 3} more
                      </span>
                    )}
                  </div>

                  <div className="flex items-center justify-between pt-2 border-t border-[#1F2633]/60">
                    <div>
                      <span className="text-base font-black text-[#2A72E8]">
                        ₹{house.rentPrice.toLocaleString('en-IN')}
                      </span>
                      <span className="text-[10px] text-[#94A3B8]">/month</span>
                    </div>
                    <span className="text-[11px] font-semibold text-[#10B981] flex items-center gap-1">
                      <CheckCircle2 className="w-3 h-3" /> Verified PG
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {servicesTab === 'ROOMMATES' && (
        <div className="space-y-3">
          <div className="text-xs text-[#94A3B8]">
            Find compatible campus roommates based on course, budget, and lifestyle habits.
          </div>

          <div className="space-y-3">
            {roommateListings.map((rm) => (
              <div
                key={rm.id}
                className="p-4 bg-[#10141B] border border-[#1F2633] rounded-xl space-y-3"
              >
                <div className="flex items-start justify-between gap-3">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-full bg-[#2A72E8]/20 text-[#2A72E8] font-bold text-sm flex items-center justify-center">
                      {rm.studentName.slice(0, 1)}
                    </div>
                    <div>
                      <h3 className="text-sm font-bold text-white flex items-center gap-2">
                        {rm.studentName}
                        <span className="text-[10px] bg-[#161C25] text-[#94A3B8] px-1.5 py-0.2 rounded font-normal">
                          {rm.gender}
                        </span>
                      </h3>
                      <p className="text-xs text-[#94A3B8]">{rm.course}</p>
                    </div>
                  </div>

                  <div className="text-right">
                    <div className="text-sm font-black text-[#2A72E8]">
                      ₹{rm.budgetPerMonth.toLocaleString('en-IN')}
                    </div>
                    <div className="text-[10px] text-[#64748B]">budget / month</div>
                  </div>
                </div>

                <p className="text-xs text-[#94A3B8] italic bg-[#161C25] p-2.5 rounded-lg border border-[#1F2633]">
                  "{rm.bio}"
                </p>

                <div className="space-y-1.5">
                  <div className="text-[11px] text-[#64748B] font-semibold">Habits & Preferences:</div>
                  <div className="flex flex-wrap gap-1.5">
                    {rm.habits.map((h) => (
                      <span
                        key={h}
                        className="text-[10px] bg-[#161C25] text-[#10B981] border border-[#10B981]/20 px-2 py-0.5 rounded-full"
                      >
                        ✓ {h}
                      </span>
                    ))}
                  </div>
                </div>

                <div className="pt-2 border-t border-[#1F2633]/60 flex items-center justify-between text-xs">
                  <span className="text-[#94A3B8] flex items-center gap-1">
                    <MapPin className="w-3 h-3 text-[#2A72E8]" /> {rm.preferredLocation}
                  </span>
                  <button
                    onClick={() => alert(`Connecting with ${rm.studentName} for roommate pairing!`)}
                    className="px-3 py-1 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-bold rounded-lg transition-colors"
                  >
                    Connect
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};
