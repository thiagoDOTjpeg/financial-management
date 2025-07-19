"use client";

import { useEffect } from 'react';
import { useFinance } from '../../contexts/FinanceContext';
import { CheckCircle, XCircle, Info, X } from 'lucide-react';

export default function Notification() {
  const { state, dispatch } = useFinance();

  useEffect(() => {
    if (state.notification.show) {
      const timer = setTimeout(() => {
        dispatch({ type: 'HIDE_NOTIFICATION' });
      }, 5000);

      return () => clearTimeout(timer);
    }
  }, [state.notification.show, dispatch]);

  if (!state.notification.show) return null;

  const icons = {
    success: CheckCircle,
    error: XCircle,
    info: Info,
  };

  const colors = {
    success: 'bg-success-50 border-success-200 text-success-800',
    error: 'bg-red-50 border-red-200 text-red-800',
    info: 'bg-blue-50 border-blue-200 text-blue-800',
  };

  const Icon = icons[state.notification.type];

  return (
    <div className="fixed top-4 right-4 z-50 max-w-sm w-full">
      <div className={`p-4 rounded-lg border shadow-lg ${colors[state.notification.type]}`}>
        <div className="flex items-start">
          <Icon className="h-5 w-5 mt-0.5 mr-3 flex-shrink-0" />
          <div className="flex-1">
            <p className="text-sm font-medium">{state.notification.message}</p>
          </div>
          <button
            onClick={() => dispatch({ type: 'HIDE_NOTIFICATION' })}
            className="ml-3 flex-shrink-0"
          >
            <X className="h-4 w-4" />
          </button>
        </div>
      </div>
    </div>
  );
}