import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface NavigationState {
  activeSection: string;
}

const initialState: NavigationState = {
  activeSection: 'overview'
};

export const navigationSlice = createSlice({
  name: "navigation",
  initialState,
  reducers: {
    setActiveSection: (state, action: PayloadAction<string>) => {
      state.activeSection = action.payload;
    }
  }
});

export const { setActiveSection } = navigationSlice.actions;
export default navigationSlice.reducer;