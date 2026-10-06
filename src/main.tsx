import React from 'react';
import ReactDOM from 'react-dom/client';
import { ExOwnProvider } from './context/ExOwnContext';
import { AppContent } from './App';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ExOwnProvider>
      <AppContent />
    </ExOwnProvider>
  </React.StrictMode>
);
