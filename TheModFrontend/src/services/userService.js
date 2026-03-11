const API_URL = "http://localhost:8080/api/user";

export const registerUser = async (user) => {
    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
    });
    return await response.json();
};

export const getAllUsers = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getUserById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const updateUser = async (id, user) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
    });
    return await response.json();
};

export const deleteUser = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};
