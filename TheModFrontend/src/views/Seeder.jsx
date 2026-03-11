import { GAMES_DATA } from "../components/values/Seeder";

export function Seeder() {
  const games = GAMES_DATA

  const handleImageUpload = async () => {
    for (const game of games) {
      try {
        // 1. DESCARGAMOS el archivo de tu carpeta public para obtener sus bytes
        const imageResponse = await fetch(game.image);
        const imageBlob = await imageResponse.blob();

        // 2. Creamos un objeto File real a partir del Blob
        // Extraemos el nombre del archivo de la ruta original
        const fileName = game.image.split('/').pop();
        const imageFile = new File([imageBlob], fileName, { type: imageBlob.type });

        // 3. Ahora sí, preparamos el FormData con el archivo binario
        const formData = new FormData();
        formData.append("name", game.name);
        formData.append("image", imageFile); // Ahora esto es un MultipartFile real

        const response = await fetch("http://localhost:8080/api/game/register", {
          method: "POST",
          body: formData,
        });

        if (response.ok) {
          console.log(`✅ Guardado: ${game.name}`);
        } else {
          console.error(`❌ Error en ${game.name}`);
        }
      } catch (error) {
        console.error(`Error procesando ${game.name}:`, error);
      }
    }
    alert("Proceso de seeding finalizado");
  };
  return (
    <>
      <div>
        <h1>Test</h1>
        <button onClick={handleImageUpload}>Upload games</button>
      </div>

      <div className="game-card">
        <img
          src='http://localhost:8080/uploads/games/1772671647664_stardewValley.webp'
          style={{ width: '200px', height: 'auto' }}
        />
        <h3>Stardew Valley</h3>
      </div>
    </>
  );
}