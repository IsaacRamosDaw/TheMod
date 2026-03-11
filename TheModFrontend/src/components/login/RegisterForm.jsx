import { useState } from "react";
import "../../styles/components/LoginAndRegister.css";

export const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [avatar, setAvatar] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    setAvatar(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("Las contraseñas no coinciden");
      return;
    }

    // 1. PRIMERO creamos el objeto
    const data = new FormData();
    data.append("username", formData.username);
    data.append("email", formData.email);
    data.append("password", formData.password);

    // 2. DESPUÉS aplicamos la lógica condicional
    if (avatar) {
      data.append("avatar", avatar);
    } else {
      // Si no hay foto, enviamos un archivo vacío con un nombre especial
      // o simplemente no añades nada y lo gestionamos en Spring Boot
      data.append("avatar", new File([], "default-placeholder"));
    }

    try {
      const response = await fetch("http://localhost:8080/api/users/register", {
        method: "POST",
        body: data,
      });

      if (response.ok) {
        console.log("Registro exitoso");
      }
    } catch (error) {
      console.error("Error al conectar con el servidor:", error);
    }
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit}>
      <h2 className="form-title">Crea tu cuenta</h2>

      <div className="input-group">
        <input
          type="text"
          name="username"
          placeholder="Usuario"
          required
          onChange={handleChange}
        />
      </div>

      <div className="input-group">
        <input
          type="email"
          name="email"
          placeholder="Email"
          required
          onChange={handleChange}
        />
      </div>

      <div className="input-group">
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          required
          onChange={handleChange}
        />
      </div>

      <div className="input-group">
        <input
          type="password"
          name="confirmPassword"
          placeholder="Confirmar Contraseña"
          required
          onChange={handleChange}
        />
      </div>

      {/* Campo para la foto de perfil */}
      <div className="input-group">
        <label htmlFor="avatar-upload">
          Foto de perfil
        </label>
        <input
          id="avatar-upload"
          type="file"
          accept="image/*"
          onChange={handleFileChange}
        />
      </div>

      <button type="submit" className="submit-btn secondary">Registrarse</button>
    </form>
  );
};