export const USERS_DATA = [
  { name: "Isaac Ramos", email: "isaac@example.com", password: "password123" },
  { name: "John Doe", email: "john@example.com", password: "password123" },
  { name: "Jane Smith", email: "jane@example.com", password: "password123" },
  { name: "Alex Hunter", email: "alex@example.com", password: "password123" },
  { name: "Sarah Connor", email: "sarah@example.com", password: "password123" },
];

export const GAME_CATEGORIES_DATA = [
  { name: "RPG" },
  { name: "Action" },
  { name: "Simulation" },
  { name: "Adventure" },
  { name: "Strategy" },
];

export const GAMES_DATA = [
  { name: "CyberPunk 2077", image: "/seeders/games/cyberpunk.webp" },
  { name: "Baldurs Gate 3", image: "/seeders/games/baldursGate.webp" },
  { name: "Dark Souls", image: "/seeders/games/darkSouls.webp" },
  { name: "The Witcher 3: Wild Hunt", image: "/seeders/games/theWitcher.webp" },
  { name: "Stardew Valley", image: "/seeders/games/stardewValley.webp" },
  { name: "Red Dead Redemption 2", image: "/seeders/games/redDead.webp" },
  { name: "The Elder Scrolls V: Skyrim", image: "/seeders/games/skyrim.webp" },
  { name: "Fallout 4", image: "/seeders/games/fallout.webp" },
];

export const MOD_CATEGORIES_DATA = [
  { name: "Armor" },
  { name: "Weapons" },
  { name: "Gameplay" },
  { name: "Graphics" },
  { name: "Items" },
  { name: "Quests" },
  { name: "UI" },
  { name: "Tools" },
];

export const MODS_DATA = [
  { name: "enhanced_visuals", description: "Improves overall graphics and lighting.", version: "1.0.2", authorIdx: 0, gameIdx: 0, categoryIdx: 3 },
  { name: "ultimate_armor_pack", description: "Adds 20 new lore-friendly armors.", version: "2.5.0", authorIdx: 1, gameIdx: 1, categoryIdx: 0 },
  { name: "hardcore_combat", description: "Realistic damage and stamina management.", version: "0.9.8", authorIdx: 2, gameIdx: 2, categoryIdx: 2 },
  { name: "expanded_quests", description: "Adds 5 hours of new story content.", version: "1.2.0", authorIdx: 3, gameIdx: 3, categoryIdx: 5 },
  { name: "better_crops", description: "Visual variety for all farm products.", version: "3.0.1", authorIdx: 4, gameIdx: 4, categoryIdx: 4 },
  { name: "realistic_horses", description: "Improved horse animations and physics.", version: "1.1.0", authorIdx: 0, gameIdx: 5, categoryIdx: 2 },
  { name: "skyrim_redone", description: "A massive overhaul of all game systems.", version: "4.0.0", authorIdx: 1, gameIdx: 6, categoryIdx: 2 },
  { name: "settlement_limits_off", description: "Build as much as your PC can handle.", version: "1.0.0", authorIdx: 2, gameIdx: 7, categoryIdx: 7 },
];

export const POSTS_DATA = [
  { title: "Welcome to TheMod!", content: "This is our first post on the platform. Stay tuned for updates!", authorIdx: 0 },
  { title: "Modding Guide for Beginners", content: "Learn how to install your first mod in less than 5 minutes.", authorIdx: 1 },
  { title: "Top 10 Mods of March", content: "Check out the best content created by our community this month.", authorIdx: 2 },
];

export const PACKS_DATA = [
  { name: "Starter Kit", description: "Essential mods for every new player.", authorIdx: 0, modIndices: [0, 6, 7] },
  { name: "Visual Overhaul", description: "Make your game look next-gen.", authorIdx: 1, modIndices: [0, 4] },
];