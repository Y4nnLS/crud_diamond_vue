<template>
    <div class="pagination">
        <button @click="prevPage" :disabled="page === 1">Previous</button>
        <span>Page {{ page }} of {{ pageCount }}</span>
        <button @click="nextPage" :disabled="page >= pageCount">Next</button>
    </div>
</template>

<script>
export default {
    props: {
        total: {
            type: Number,
            required: true
        },
        page: {
            type: Number,
            required: true
        },
        perPage: {
            type: Number,
            required: true,
            default: 10
        }
    },
    computed: {
        pageCount() {
            return Math.ceil(this.total / this.perPage);
        }
    },
    methods: {
        nextPage() {
            if (this.page < this.pageCount) {
                console.log('Next page clicked');
                this.$emit('page-changed', this.page + 1);
            }
        },
        prevPage() {
            if (this.page > 1) {
                console.log('Previous page clicked');
                this.$emit('page-changed', this.page - 1);
            }
        }
    }
};
</script>
