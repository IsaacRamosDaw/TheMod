const API_URL = "http://localhost:8080/api/pack";

export const getAllPacks = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getPackById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const registerPack = async (pack) => {
    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pack),
    });
    return await response.json();
};

export const updatePack = async (id, pack) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pack),
    });
    return await response.json();
};

export const deletePack = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};

export const addModToPack = async (packId, modId) => {
    const response = await fetch(`${API_URL}/${packId}/mod/${modId}`, {
        method: "POST",
    });
    return await response.json();
};

export const removeModFromPack = async (packId, modId) => {
    const response = await fetch(`${API_URL}/${packId}/mod/${modId}`, {
        method: "DELETE",
    });
    return await response.json();
};
