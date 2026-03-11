import "../styles/views/login.css"
import { LoginForm } from "../components/login/LoginForm"
import { RegisterForm } from "../components/login/RegisterForm"
import { useState } from "react"

export function Login() {
  const [isLogin, setIsLogin] = useState(true)

  return (
    <div className="login-page">
      <div className="login-container">
        <div className={`form-box ${isLogin ? "show-login" : "show-register"}`}>
          <div className="form-content login-form-wrapper">
            <LoginForm />
            <p className="toggle-text">
              ¿No tienes cuenta?{" "}
              <span onClick={() => setIsLogin(false)}>Regístrate</span>
            </p>
          </div>
          <div className="form-content register-form-wrapper">
            <RegisterForm />
            <p className="toggle-text">
              ¿Ya tienes cuenta?{" "}
              <span onClick={() => setIsLogin(true)}>Inicia sesión</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}