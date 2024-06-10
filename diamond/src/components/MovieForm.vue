<template>
    <div class="col-12">
        <div class="card">
            <h5>{{ movie.id ? 'Editar Filme' : 'Adicionar Filme' }}</h5>
            <div class="p-fluid formgrid grid">
                <div class="field col-12">
                    <label for="title">Título</label>
                    <InputText id="title" v-model="movie.title" required />
                </div>
                <div class="field col-12 md:col-6">
                    <label for="releaseYear">Ano de Lançamento</label>
                    <InputText id="releaseYear" v-model="movie.releaseYear" type="number" required />
                </div>
                <div class="field col-12 md:col-6">
                    <label for="studios">Estúdios</label>
                    <InputText id="studios" v-model="movie.studios" required />
                </div>
                <div class="field col-12 md:col-6">
                    <label for="producers">Produtores</label>
                    <InputText id="producers" v-model="movie.producers" required />
                </div>
                <div class="field col-12">
                    <label for="winner">Vencedor</label>
                    <InputSwitch id="winner" v-model="movie.winner" />
                </div>
            </div>
            <span class="p-buttonset">
                <Button type="submit" @click="salvarFilme">{{ movie.id ? 'Salvar Alterações' : 'Adicionar Filme' }}</Button>
                <Button type="button" @click="goBack">Voltar</Button>
            </span>
        </div>
    </div>
</template>

<script>
import api from '../service/api';

export default {
    data() {
        return {
            movie: {
                id: '',
                title: '',
                releaseYear: '',
                studios: '',
                producers: '',
                winner: false
            }
        };
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        salvarFilme() {
            if (this.movie.id) {
                api.updateMovie(this.movie.id, this.movie)
                    .then(() => {
                        this.$router.push({ name: 'movies' });
                        alert('Filme atualizado com sucesso');
                    })
                    .catch((error) => {
                        console.error('Erro ao atualizar filme:', error);
                    });
            } else {
                api.createMovie(this.movie)
                    .then(() => {
                        this.$router.push({ name: 'movies' });
                        alert('Filme cadastrado com sucesso');
                    })
                    .catch((error) => {
                        console.error('Erro ao adicionar filme:', error);
                    });
            }
        },
        carregarFilme(id) {
            api.getMovie(id)
                .then((response) => {
                    this.movie = response.data;
                })
                .catch((error) => {
                    console.error('Erro ao carregar filme:', error);
                });
        }
    },
    mounted() {
        if (this.$route.params.id) {
            this.carregarFilme(this.$route.params.id);
        }
    }
};
</script>
