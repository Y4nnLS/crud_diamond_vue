<template>
  <div>
    <h2>{{ movie.title }}</h2>
    <p><strong>Release Year:</strong> {{ movie.releaseYear }}</p>
    <p><strong>Studios:</strong> {{ movie.studios }}</p>
    <p><strong>Producers:</strong> {{ movie.producers }}</p>
    <p><strong>Winner:</strong> {{ movie.winner ? 'Sim' : 'Não' }}</p>
  </div>
</template>

<script>
export default {
  props: {
    movie: {
      type: Object,
      required: true
    }
  }
};
</script>
