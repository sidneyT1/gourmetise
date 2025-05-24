// src/composables/auth.js
import { ref } from 'vue';

const isLoggedIn = ref(localStorage.getItem('access_token') !== null);

const login = (token) => {
  localStorage.setItem('access_token', token);
  isLoggedIn.value = true;
};

const logout = () => {
  localStorage.removeItem('access_token');
  isLoggedIn.value = false;
};

export { isLoggedIn, login, logout };
