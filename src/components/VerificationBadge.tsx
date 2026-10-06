import React from 'react';
import { CheckCircle } from 'lucide-react';

interface VerificationBadgeProps {
  label?: string;
  size?: 'sm' | 'md';
  className?: string;
}

export const VerificationBadge: React.FC<VerificationBadgeProps> = ({
  label = 'Verified Student',
  size = 'sm',
  className = '',
}) => {
  return (
    <div
      className={`inline-flex items-center gap-1 font-semibold text-[#10B981] ${
        size === 'sm' ? 'text-[11px]' : 'text-xs'
      } ${className}`}
    >
      <CheckCircle className={size === 'sm' ? 'w-3.5 h-3.5 fill-[#10B981]/20' : 'w-4 h-4 fill-[#10B981]/20'} />
      <span>{label}</span>
    </div>
  );
};
