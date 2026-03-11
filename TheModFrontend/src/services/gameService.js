const API_URL = "http://localhost:8080/api/game";

export const createGame = async (formData) => {
    // formData debe ser un objeto FormData con 'name' e 'image'
    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        body: formData,
    });
    return await response.json();
};

export const getAllGames = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getGameById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const searchGames = async (name) => {
    const response = await fetch(`${API_URL}/search?name=${encodeURIComponent(name)}`);
    return await response.json();
};

export const updateGame = async (id, formData) => {
    // formData debe ser un objeto FormData con 'name' e 'image'
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        body: formData,
    });
    return await response.json();
};

export const deleteGame = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};
