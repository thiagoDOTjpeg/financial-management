import { create } from 'zustand';
import { createJSONStorage, persist } from 'zustand/middleware';

interface NavigationState {
  currentPageTitle: string;
  setCurrentPageTitle: (title: string) => void;
}

export const useNavigationStore = create<NavigationState>()(
  persist(
    (set) => ({
      currentPageTitle: 'Dashboard',
      setCurrentPageTitle: (title) => set({ currentPageTitle: title }),
    }),
    {
      name: 'navigation-storage',
      storage: createJSONStorage(() => sessionStorage),
    }
  )
);