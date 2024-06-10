import axios from 'axios';

// Obtém o token de acesso do Keycloak
import { keycloak } from '@/main.js'; // Certifique-se de substituir pelo caminho correto para o seu arquivo main.js

// Cria uma instância do Axios com a configuração básica
const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Interceptar respostas para verificar se o token expirou
api.interceptors.response.use(response => {
  return response;
}, error => {
  if (error.response.status === 401) {
    keycloak.logout(); // Redirecionar para o logout do Keycloak se o token estiver expirado
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
