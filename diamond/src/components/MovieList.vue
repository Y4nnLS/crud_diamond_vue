<template>
    <div>
        <Button @click="LogOut" label="Logout" class="p-button-sm action-button" />

        <div class="col-12">
            <div class="card">
                <h5>Filmes</h5>
                <DataTable
                    :value="filteredAndPaginatedMovies"
                    :rows="perPage"
                    dataKey="id"
                    :totalRecords="total"
                    :rowHover="true"
                    v-model:filters="filters"
                    :first="(page - 1) * perPage"
                    :rowsPerPageOptions="[10, 20, 50]"
                    :loading="loading"
                    :filters="filters"
                    filterDisplay="menu"
                    :globalFilterFields="['title', 'releaseYear']"
                    showGridlines
                >
                    <template #header>
                        <div class="flex justify-content-between flex-column sm:flex-row">
                            <Button type="button" icon="pi pi-filter-slash" label="Clear" class="mb-2" outlined @click="clearFilter()" />
                            <span class="p-input-icon-left mb-2">
                                <i class="pi pi-search" />
                                <input v-model="searchQuery" @input="fetchMovies" placeholder="Search by title" />
                            </span>
                        </div>
                    </template>
                    <template #empty> Nenhum filme encontrado. </template>
                    <Column field="releaseYear" header="Ano" :sortable="true">
                        <template #body="{ data }">{{ data.releaseYear }}</template>
                    </Column>
                    <Column field="title" header="Título" :sortable="true">
                        <template #body="{ data }">{{ data.title }}</template>
                    </Column>
                    <Column field="winner" header="Prêmio" :sortable="false">
                        <template #body="{ data }">{{ data.winner ? 'Sim' : 'Não' }}</template>
                    </Column>
                    <Column header="Ações">
                        <template #body="{ data }">
                            <Button @click="editMovie(data.id)" label="Edit" class="p-button-sm action-button" />
                            <Button @click="deleteMovie(data.id, data.title)" label="Delete" class="p-button-sm action-button" />
                            <Button @click="showMovieInfo(data)" label="Info" class="p-button-sm action-button" />
                        </template>
                    </Column>
                </DataTable>

                <!-- Componente de paginação -->
                <Pagination :total="total" :page="page" :perPage="perPage" @page-changed="handlePageChange" />
            </div>
        </div>

        <div class="modal" v-if="selectedMovie">
            <div class="modal-content">
                <span class="close" @click="closePopup">&times;</span>
                <Movie :movie="selectedMovie" />
            </div>
        </div>
    </div>
</template>

<script>
import api from '../service/api.js';
import Movie from './Movie.vue';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import Pagination from './Pagination.vue';
import { keycloak } from '../main.js'; // Importe a instância do Keycloak de main.js

export default {
    components: { Movie, DataTable, Column, Button, InputText, Pagination },
    data() {
        return {
            movies: [],
            searchQuery: '',
            sortKey: 'releaseYear',
            sortAsc: true,
            page: 1,
            total: 0,
            perPage: 10,
            selectedMovie: null,
            loading: false,
            filters: {
                global: { value: null },
                title: { value: null },
                releaseYear: { value: null }
            }
        };
    },
    computed: {
        filteredMovies() {
            let movies = this.movies.filter((movie) => movie.title.toLowerCase().includes(this.searchQuery.toLowerCase()));

            if (this.sortKey) {
                movies.sort((a, b) => {
                    let result = 0;
                    if (a[this.sortKey] < b[this.sortKey]) {
                        result = -1;
                    } else if (a[this.sortKey] > b[this.sortKey]) {
                        result = 1;
                    }
                    return this.sortAsc ? result : -result;
                });
            }

            return movies;
        },
        filteredAndPaginatedMovies() {
            const startIndex = (this.page - 1) * this.perPage;
            const endIndex = startIndex + this.perPage;
            return this.filteredMovies.slice(startIndex, endIndex);
        }
    },
    methods: {
        LogOut() {
            console.log('Entrou na função de logout.');

            // Chama o método logout na instância do Keycloak
            keycloak
                .logout({ redirectUri: window.location.origin }) // Garantir redirecionamento para o início
                .then(() => {
                    console.log('Logout bem-sucedido.'); // Log de sucesso no logout

                    // Limpar o token de autenticação
                    keycloak.clearToken();

                    // Limpar todos os cookies relacionados ao Keycloak
                    const cookies = document.cookie.split(';');
                    for (const cookie of cookies) {
                        const eqPos = cookie.indexOf('=');
                        const name = eqPos > -1 ? cookie.slice(0, eqPos) : cookie;
                        document.cookie = `${name}=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;domain=${window.location.hostname}`;
                        document.cookie = `${name}=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;`;
                    }

                    // Redirecionar para a tela de login após o logout
                    window.location.href = window.location.origin;
                })
                .catch((error) => {
                    console.error('Erro ao fazer logout:', error); // Log de erro no logout
                });
        },
        fetchMovies() {
            this.loading = true;
            const params = {
                q: this.searchQuery,
                sort: this.sortKey,
                order: this.sortAsc ? 'asc' : 'desc',
                page: this.page,
                per_page: this.perPage
            };
            console.log('Fetching movies with params:', params);

            api.getAllMovies(params)
                .then((response) => {
                    const filmesArray = response.data;

                    this.movies = filmesArray.map((filme) => {
                        return {
                            id: filme.id,
                            releaseYear: filme.releaseYear,
                            title: filme.title,
                            winner: filme.winner,
                            studio: filme.studios,
                            producers: filme.producers
                        };
                    });

                    this.total = response.headers['x-total-count'] ? parseInt(response.headers['x-total-count'], 10) : this.filteredMovies.length;
                    console.log('Total movies:', this.total);
                })
                .catch((error) => {
                    console.error('Erro ao buscar filmes:', error);
                })
                .finally(() => {
                    this.loading = false;
                });
        },
        sortBy(key) {
            if (this.sortKey === key) {
                this.sortAsc = !this.sortAsc;
            } else {
                this.sortKey = key;
                this.sortAsc = true;
            }
            this.fetchMovies();
        },
        editMovie(id) {
            this.$router.push({ name: 'editMovie', params: { id } });
        },
        deleteMovie(id, title) {
            api.deleteMovie(id).then(() => {
                this.fetchMovies();
            });
            alert("Filme ''" + title + "'' excluído com sucesso!!");
        },
        showMovieInfo(movie) {
            this.selectedMovie = {
                id: movie.id,
                title: movie.title,
                releaseYear: movie.releaseYear,
                studios: movie.studio,
                producers: movie.producers,
                winner: movie.winner
            };
        },
        closePopup() {
            this.selectedMovie = null;
        },
        handlePageChange(newPage) {
            console.log('Page changed:', newPage);
            this.page = newPage;
            this.fetchMovies();
        },
        clearFilter() {
            this.filters = {
                global: { value: null },
                title: { value: null },
                releaseYear: { value: null }
            };
            this.fetchMovies();
        }
    },
    mounted() {
        this.fetchMovies();
    }
};
</script>

<style>
.action-button {
    margin-right: 5px; /* ou qualquer valor de margem que você preferir */
}
.even-row {
    background-color: #696969;
}
.odd-row {
    background-color: #434343;
}
.modal {
    display: block;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
}
.modal-content {
    background-color: #fefefe;
    margin: 15% auto;
    padding: 20px;
    border: 1px solid #888;
    border-radius: 5px;
    width: 80%;
    color: black;
}
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}
.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
</style>
