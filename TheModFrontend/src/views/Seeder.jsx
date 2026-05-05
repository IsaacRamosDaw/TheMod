import { USERS_DATA, GAME_CATEGORIES_DATA, GAMES_DATA, MOD_CATEGORIES_DATA, MODS_DATA, POSTS_DATA, PACKS_DATA } from "../components/values/Seeder";
import { useState } from "react";

export function Seeder() {
  const [logs, setLogs] = useState([]);
  const [isSeeding, setIsSeeding] = useState(false);

  const addLog = (message) => {
    setLogs((prev) => [...prev, `${new Date().toLocaleTimeString()}: ${message}`]);
  };

  const seedAll = async () => {
    setIsSeeding(true);
    setLogs([]);
    addLog("🚀 Starting Seeding Process...");

    try {
      // 1. Seed Users
      const userIds = [];
      for (const user of USERS_DATA) {
        const res = await fetch("http://localhost:8080/api/user/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(user),
        });
        const json = await res.json();
        
        if (json.success) {
          userIds.push(json.data.id);
          addLog(`✅ User created: ${user.name}`);
        }
      }

      // 2. Seed Game Categories
      for (const cat of GAME_CATEGORIES_DATA) {
        await fetch("http://localhost:8080/api/category/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(cat),
        });
        addLog(`✅ Game Category created: ${cat.name}`);
      }

      // 3. Seed Mod Categories
      for (const cat of MOD_CATEGORIES_DATA) {
        await fetch("http://localhost:8080/api/mod-category/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(cat),
        });
        addLog(`✅ Mod Category created: ${cat.name}`);
      }

      // 4. Seed Games with Images
      const gameIds = [];
      for (const game of GAMES_DATA) {
        try {
          const imageResponse = await fetch(game.image);
          const imageBlob = await imageResponse.blob();
          const fileName = game.image.split('/').pop();
          const imageFile = new File([imageBlob], fileName, { type: imageBlob.type });

          const formData = new FormData();
          formData.append("name", game.name);
          formData.append("image", imageFile);

          const res = await fetch("http://localhost:8080/api/game/register", {
            method: "POST",
            body: formData,
          });
          const json = await res.json();
          if (json.success) {
            gameIds.push(json.data.id);
            addLog(`✅ Game created: ${game.name}`);
          }
        } catch (e) {
          addLog(`⚠️ Fallback Game (no image): ${game.name}`);
          const res = await fetch("http://localhost:8080/api/game/register", {
            method: "POST",
            body: new FormData().append("name", game.name),
          });
          const json = await res.json();
          if (json.success) gameIds.push(json.data.id);
        }
      }

      // 5. Seed Mods
      const modIds = [];
      for (const modData of MODS_DATA) {
        const mod = {
          name: modData.name,
          description: modData.description,
          version: modData.version,
          author: { id: userIds[modData.authorIdx] }
          // Note: Game and Category relation might need extra mapping if models require them as objects
          // Adding author is the minimum for the current ModController logic
        };
        const res = await fetch("http://localhost:8080/api/mod/create", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(mod),
        });
        const json = await res.json();
        if (json.success) {
          modIds.push(json.data.id);
          addLog(`✅ Mod created: ${mod.name}`);
        }
      }

      // 6. Seed Posts
      for (const postData of POSTS_DATA) {
        const post = {
          title: postData.title,
          content: postData.content,
          author: { id: userIds[postData.authorIdx] }
        };
        await fetch("http://localhost:8080/api/post/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(post),
        });
        addLog(`✅ Post created: ${post.title}`);
      }

      // 7. Seed Packs
      for (const packData of PACKS_DATA) {
        const pack = {
          name: packData.name,
          description: packData.description,
          author: { id: userIds[packData.authorIdx] }
        };
        const res = await fetch("http://localhost:8080/api/pack/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(pack),
        });
        const json = await res.json();
        if (json.success) {
          const packId = json.data.id;
          addLog(`✅ Pack created: ${pack.name}`);
          // Associate mods to pack
          for (const idx of packData.modIndices) {
            if (modIds[idx]) {
              await fetch(`http://localhost:8080/api/pack/${packId}/mod/${modIds[idx]}`, { method: "POST" });
            }
          }
        }
      }

      addLog("🏁 Seeding Finished Successfully!");
    } catch (error) {
      addLog(`❌ CRITICAL ERROR: ${error.message}`);
      console.error(error);
    } finally {
      setIsSeeding(false);
    }
  };

  return (
    <div style={{ padding: "20px", color: "white", backgroundColor: "#1e1e1e", minHeight: "100vh" }}>
      <h1>Data Seeder</h1>
      <p>Population tool for development environment.</p>

      <button
        onClick={seedAll}
        disabled={isSeeding}
        style={{
          padding: "10px 20px",
          fontSize: "1.2rem",
          backgroundColor: isSeeding ? "#444" : "#007bff",
          color: "white",
          border: "none",
          borderRadius: "5px",
          cursor: isSeeding ? "not-allowed" : "pointer"
        }}
      >
        {isSeeding ? "Seeding..." : "Seed All Data"}
      </button>

      <div style={{
        marginTop: "20px",
        padding: "15px",
        backgroundColor: "#000",
        borderRadius: "5px",
        fontFamily: "monospace",
        height: "400px",
        overflowY: "auto",
        border: "1px solid #333"
      }}>
        {logs.map((log, i) => (
          <div key={i} style={{ marginBottom: "5px", borderBottom: "1px solid #222" }}>{log}</div>
        ))}
        {logs.length === 0 && <span style={{ color: "#666" }}>No activity logs yet...</span>}
      </div>
    </div>
  );
}