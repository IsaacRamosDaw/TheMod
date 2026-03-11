import "../../styles/components/LoginAndRegister.css"

export const LoginForm = () => {
  return (
    <form className="auth-form">
      <h2 className="form-title">Bienvenido</h2>
      <div className="input-group">
        <input type="text" placeholder="Usuario" required />
      </div>
      <div className="input-group">
        <input type="password" placeholder="Contraseña" required />
      </div>
      <button type="submit" className="submit-btn">Entrar</button>
    </form>
  )
}