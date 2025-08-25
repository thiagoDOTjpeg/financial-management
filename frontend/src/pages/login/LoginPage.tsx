import { login } from "@/api/authApi";
import { useAuthStore } from "@/store/authStore";
import { useState, type FormEvent } from "react";

const LoginPage = () => {
  const setToken = useAuthStore((state) => state.setToken);
  const setUsername = useAuthStore((state) => state.setUsername);
  const [formUsername, setFormUsername] = useState("");
  const [formPassword, setFormPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleLogin = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const data = await login({
        username: formUsername,
        password: formPassword,
      });
      setUsername(data.username);
      setToken(data.accessToken);
      setError(null);
    } catch (error) {
      setError("Usuário ou senha inválidos");
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <div>
        <label>Username:</label>
        <input
          type="text"
          value={formUsername}
          onChange={(e) => setFormUsername(e.target.value)}
        />
      </div>
      <div>
        <label>Password:</label>
        <input
          type="password"
          value={formPassword}
          onChange={(e) => setFormPassword(e.target.value)}
        />
      </div>
      {error && <div style={{ color: "red" }}>{error}</div>}
      <button type="submit">Login</button>
    </form>
  );
};

export default LoginPage;
