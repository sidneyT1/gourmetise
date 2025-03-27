import axios from "axios";
import { useRouter } from "vue-router";

const api = axios.create({
  baseURL: "http://localhost:8000/api", // URL de l'API
  headers: {
    "Content-Type": "application/json",
  },
});


api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("access_token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);


api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("access_token");
      const router = useRouter();
      router.push("/login");
    }
    return Promise.reject(error);
  }
);

export default api;
