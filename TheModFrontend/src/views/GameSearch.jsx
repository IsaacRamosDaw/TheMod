import React from 'react';
import { Header } from '../components/Header';
import { Footer } from '../components/Footer';

import { GameLink } from '../components/search/GameLink';
import { GAMES_DATA } from '../components/values/Seeder';

import '../styles/views/GameSearch.css'

export function GameSearch() {
  const gamesList = GAMES_DATA;

  return (
    <>
      <Header />
      <div className="searcher-container">
        <aside className="sidebar">
          <div className="filter-group">
            <h4>GAME</h4>
            <input type="text" placeholder="Search game" className="search-input" />
            <button className="apply-btn">Apply</button>
          </div>

          <div className="filter-group">
            <h4>GAME GENRE</h4>
            <input type="text" placeholder="Game genre search" className="search-input" />
            <div className="checkbox-list">
              <label><input type="checkbox" /> Action (732)</label>
              <label><input type="checkbox" /> Adventure (406)</label>
              <label><input type="checkbox" /> ARPG (166)</label>
            </div>
          </div>
        </aside>

        <main className="results-area">
          <header className="results-header">
            <span className="results-count">199 results</span>
            <div className="results-sort">
              <select>
                <option>Download count</option>
                <option>Name</option>
              </select>
            </div>
          </header>

          <div className="games-grid">
            {gamesList.map((game, index) => (
              <GameLink
                key={index}
                name={game.name}
                image={game.image}
                mods={game.mods}
                collections={game.collections}
                downloads={game.downloads}
              />
            ))}
          </div>
        </main>
      </div>
      <Footer/>
    </>
  );
};