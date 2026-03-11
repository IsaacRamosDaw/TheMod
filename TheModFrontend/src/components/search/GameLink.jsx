import React from 'react';
import '../../styles/components/GameLink.css';

export const GameLink = ({ name, image, mods, collections, downloads }) => {

  return (
    <div className="game-card">
      <div className="image-container">
        <img src={image} alt={name} className="game-poster" />
        <button className="save-icon-btn">
          {/* <FaBookmark /> */}
        </button>
      </div>
      
      <div className="game-info">
        <h3 className="game-title">{name}</h3>
        <div className="game-stats">
          <span title="Mods">
            {mods}
          </span>
          <span title="Collections">
            {collections}
          </span>
          <span title="Downloads">
            {downloads}
          </span>
        </div>
      </div>
    </div>
  );
};