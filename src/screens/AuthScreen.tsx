import React, { useState } from 'react';
import { Building2, ShieldCheck, CheckCircle2, ArrowRight, Sparkles } from 'lucide-react';
import { useExOwn } from '../context/ExOwnContext';

export const AuthScreen: React.FC = () => {
  const { signInStudent, campuses } = useExOwn();

  const [name, setName] = useState('Hari Pandey');
  const [email, setEmail] = useState('hari.pandey@lpu.in');
  const [university, setUniversity] = useState('Lovely Professional University');
  const [hostel, setHostel] = useState('BH-4, Room 312');
  const [selectedCampusId, setSelectedCampusId] = useState('lpu');

  const handleCampusChange = (campusId: string) => {
    setSelectedCampusId(campusId);
    const c = campuses.find((x) => x.id === campusId);
    if (c) {
      setUniversity(c.name);
      setEmail(`student${c.verificationDomain}`);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const c = campuses.find((x) => x.id === selectedCampusId) || campuses[0];
    signInStudent(name, email, university, `${c.code} • ${c.city}`, hostel);
  };

  return (
    <div className="min-h-screen bg-[#07090D] flex flex-col justify-center px-4 py-8">
      <div className="w-full max-w-md mx-auto space-y-6">
        {/* Brand Header */}
        <div className="text-center space-y-2">
          <div className="w-14 h-14 rounded-2xl bg-[#2A72E8] flex items-center justify-center text-white text-2xl font-black mx-auto shadow-xl shadow-[#2A72E8]/25">
            XO
          </div>
          <h1 className="text-2xl font-black text-white tracking-tight">ExOwn</h1>
          <p className="text-xs text-[#94A3B8] max-w-xs mx-auto">
            Campus marketplace & student community platform for Indian universities
          </p>
        </div>

        {/* Verification Card */}
        <div className="p-5 bg-[#10141B] border border-[#1F2633] rounded-2xl space-y-4 shadow-xl">
          <div className="flex items-center gap-2 pb-2 border-b border-[#1F2633]">
            <ShieldCheck className="w-4 h-4 text-[#10B981]" />
            <span className="text-xs font-bold text-white uppercase tracking-wider">
              Student Institutional Sign-In
            </span>
          </div>

          <form onSubmit={handleSubmit} className="space-y-3">
            <div>
              <label className="text-xs font-semibold text-white block mb-1">Select Campus</label>
              <select
                value={selectedCampusId}
                onChange={(e) => handleCampusChange(e.target.value)}
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white focus:outline-hidden focus:border-[#2A72E8]"
              >
                {campuses.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.code} — {c.name} ({c.city})
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-white block mb-1">Full Student Name</label>
              <input
                type="text"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="e.g. Hari Pandey"
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-white block mb-1">
                Institutional Email (.ac.in / .edu)
              </label>
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="e.g. rollnumber@lpu.in"
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-white block mb-1">Hostel / Campus Room</label>
              <input
                type="text"
                required
                value={hostel}
                onChange={(e) => setHostel(e.target.value)}
                placeholder="e.g. BH-4, Room 312"
                className="w-full bg-[#161C25] border border-[#1F2633] rounded-xl px-3 py-2 text-xs text-white placeholder-[#64748B] focus:outline-hidden focus:border-[#2A72E8]"
              />
            </div>

            <button
              type="submit"
              className="w-full py-3 bg-[#2A72E8] hover:bg-[#2563EB] text-white font-bold text-xs rounded-xl transition-all shadow-lg shadow-[#2A72E8]/25 flex items-center justify-center gap-1.5 pt-3"
            >
              Enter Verified Campus Market <ArrowRight className="w-4 h-4" />
            </button>
          </form>
        </div>

        {/* Trust Badges */}
        <div className="flex items-center justify-around text-center text-[11px] text-[#64748B]">
          <span>✓ Zero Middlemen</span>
          <span>•</span>
          <span>✓ Hostel Gate Handovers</span>
          <span>•</span>
          <span>✓ Student Verified</span>
        </div>
      </div>
    </div>
  );
};
