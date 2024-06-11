import axios from 'axios';
import { keycloak } from '@/main.js';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.request.use(async config => {
  if (keycloak.token) {
    try {
      await keycloak.updateToken(30);
      console.log("Token atualizado:", keycloak.token); // Adicione esta linha para verificar o token
    } catch (error) {
      console.log("Erro ao atualizar o token", error);
      // keycloak.login();
    }
    config.headers.Authorization = `Bearer ${keycloak.token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});


api.interceptors.response.use(response => {
  return response;
}, error => {
  if (error.response && error.response.status === 401) {
    console.log("Token expirado ou inválido, redirecionando para o login");
    // keycloak.login();
  }
  return Promise.reject(error);
});

export default {
  createMovie(movie) {
    return api.post('/movies/add', movie);
  },
  updateMovie(id, movie) {
    return api.put(`/movies/${id}/update`, movie);
  },
  getFilme(id) {
    return api.get(`/movies/${id}`);
  },
  getAllMovies() {
    return api.get('/movies');
  },
  deleteMovie(id) {
    return api.delete(`/movies/${id}/delete`);
  },
  getMovie(id) {
    return api.get(`/movies/${id}`);
  },
};
