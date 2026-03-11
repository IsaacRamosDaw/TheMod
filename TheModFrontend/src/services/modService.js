const API_URL = "http://localhost:8080/api/mod";

export const createMod = async (mod) => {
    const response = await fetch(`${API_URL}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(mod),
    });
    return await response.json();
};

export const getAllMods = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getModById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const searchMods = async (name) => {
    const response = await fetch(`${API_URL}/search?name=${encodeURIComponent(name)}`);
    return await response.json();
};

export const getModsByAuthor = async (authorId) => {
    const response = await fetch(`${API_URL}/author/${authorId}`);
    return await response.json();
};

export const updateMod = async (id, mod) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(mod),
    });
    return await response.json();
};

export const deleteMod = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};
