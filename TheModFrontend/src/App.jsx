import { useState } from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { Home } from './views/Home'
import { Login } from './views/Login'
import { Seeder } from './views/Seeder'
import { GameSearch } from './views/GameSearch'
// import { Mod } from './views/Mod'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/home" replace />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/test" element={<Seeder />} />
        <Route path="/game-search" element={<GameSearch />} />
        {/* <Route path="/game/:id" element={<Game />} /> */}
        {/* <Route path="/mod/:id" element={<Mod />} /> */}
      </Routes>
    </BrowserRouter>
  )
}

export default App
