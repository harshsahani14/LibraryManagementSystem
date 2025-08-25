import { createSlice,configureStore } from '@reduxjs/toolkit'


const userIdSlice = createSlice({
  name: 'userId',
  initialState: {
    value: 0
  },
  reducers: {
    update: (state,action)=> {
      
      state.value = action.payload
    },
  }
})

export const { update } = userIdSlice.actions

const store = configureStore({
  reducer: {
    userId: userIdSlice.reducer
  }
})

export default store