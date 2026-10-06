# ExOwn — Exchange. Own. Repeat.

> **Campus-focused student marketplace and services ecosystem.**

ExOwn connects university students to discover, buy, sell, rent, and exchange products, while finding campus housing, roommates, and peer-to-peer student services in one trusted, location-aware platform.

## Architecture
- **Framework:** React 19 + TypeScript + Vite
- **Styling:** Tailwind CSS with ExOwn dark-first design system (`#07090D` base, `#10141B` surface 1, `#161C25` surface 2, `#2A72E8` electric blue action, `#10B981` emerald verification)
- **State Management:** React Context API with persistent campus selection, saved items, and structured Deal Room state machine
- **Port:** Dev server listening on port 3000

## Features
- **Campus Selector:** Switch across 8 Indian universities (LPU, SRM, VIT, CU, BITS, Thapar, Manipal, Amity) with hostel-aware geography and local zones.
- **Buy & Sell:** Fast, campus-localized product listings with student verification badges and 4:3 product cards.
- **Rentals:** Daily and monthly rental rates for cycles, room coolers, monitors, and study essentials.
- **Barter & Exchange:** Zero-cash trades where students state what items they want in exchange.
- **Housing & Roommates:** Verified hostels/PGs with amenities, rent details, and roommate lifestyle matching (habits, course, budget).
- **Campus Gigs & Services:** Doorstep bike repairs, assignment printing, and peer tutoring.
- **Deal Rooms:** Structured transaction state machine (Offer Sent → Accepted → Pickup Scheduled → Handover Completed).
- **ExOwn Trust Passport:** Verified institutional identity, completed handover stats, and circular economy impact (₹ saved and kg CO₂ offset).

## Getting Started
```bash
npm install
npm run dev
```

