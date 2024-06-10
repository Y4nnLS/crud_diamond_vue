<template>
  <div>
    <h1>Projeto Proposto</h1>
    <section>
      <h2>Backend Imperativo</h2>
      <p>
        O backend foi implementado utilizando o padrão RESTful. Foi criada uma classe `MovieResource` que contem os métodos CRUD (Create, Read, Update, Delete). Cada método foi mapeado para um endpoint específico usando as anotações do JAX-RS:
      </p>
      <ul>
        <li><strong>@POST:</strong> Endpoint para criar um novo filme.</li>
        <li><strong>@GET:</strong> Endpoint para ler (recuperar) filmes.</li>
        <li><strong>@PUT:</strong> Endpoint para atualizar um filme existente.</li>
        <li><strong>@DELETE:</strong> Endpoint para deletar um filme.</li>
      </ul>
    </section>

    <section>
      <h2>Frontend</h2>
      <p>
        O frontend foi desenvolvido usando Vue.js e consiste em três componentes principais:
      </p>
      <h3>MovieList.vue</h3>
      <p>
        Este componente exibe uma lista de filmes e inclui as seguintes funcionalidades:
      </p>
      <ul>
        <li><strong>Filtro por nome:</strong> Permite ao usuário filtrar a lista de filmes pelo nome.</li>
        <li><strong>Paginação sob demanda:</strong> Carrega filmes conforme necessário para melhorar a performance.</li>
        <li><strong>Ordenação na coluna nome:</strong> Permite ao usuário ordenar a lista de filmes pelo nome.</li>
      </ul>

      <h3>MovieForm.vue</h3>
      <p>
        Este componente fornece um formulário para adicionar ou editar um filme. Ele lida com a entrada do usuário e envia os dados para o backend para salvar o filme.
      </p>

      <h3>Movie.vue</h3>
      <p>
        Este componente exibe os detalhes de um filme em um painel. Ele permite ao usuário visualizar informações detalhadas sobre um filme específico.
      </p>
    </section>
  </div>
</template>

