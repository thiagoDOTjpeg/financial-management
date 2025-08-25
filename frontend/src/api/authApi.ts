import apiClient from ".";

export const login = async ({ username, password }: { username: string; password: string }) => {
  const response = await apiClient.post("/api/v1/auth/signin", { username, password });
  return response.data;
}