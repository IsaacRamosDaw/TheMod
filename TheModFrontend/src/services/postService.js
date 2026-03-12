const API_URL = "http://localhost:8080/api/post";

export const getAllPosts = async () => {
    const response = await fetch(`${API_URL}/`);
    return await response.json();
};

export const getPostById = async (id) => {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
};

export const registerPost = async (post) => {
    const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(post),
    });
    return await response.json();
};

export const updatePost = async (id, post) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(post),
    });
    return await response.json();
};

export const deletePost = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
    });
    return await response.json();
};
