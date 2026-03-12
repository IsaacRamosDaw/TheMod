const API_URL = "http://localhost:8080/api/mod-category";

export const getAllModCategories = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getModCategoryById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const createModCategory = async (category) => {
    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(category),
    });
    return await response.json();
};

export const updateModCategory = async (id, category) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(category),
    });
    return await response.json();
};

export const deleteModCategory = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};
