<script setup>
import AppConfig from '@/layout/AppConfig.vue';
</script>

<template>
    <div class="flex h-screen">
        <div class="w-full lg:w-4 h-full text-center px-6 py-6 flex flex-column justify-content-between">
            <img :src="'/layout/images/logo-dark.svg'" class="h-4rem mt-4" alt="diamond-layout" />
            <div class="flex flex-column align-items-center gap-4">
                <div class="mb-3">
                    <h2>Login to your account</h2>
                    <p>Forgot password? <a class="text-primary hover:underline cursor-pointer font-medium">Click here</a> to reset.</p>
                </div>
                <InputText id="email" placeholder="Email" class="w-20rem" />
                <Password id="password" placeholder="Password" :feedback="true" inputClass="w-20rem"></Password>
                <Button label="CONTINUE" type="button" class="w-20rem"></Button>
            </div>

            <p class="text-color-secondary font-semibold">A problem? <a class="text-primary hover:underline cursor-pointer font-medium">Click here</a> and let us help you.</p>
        </div>
        <div
            class="w-8 hidden lg:flex flex-column justify-content-between align-items-center px-6 py-6 bg-cover bg-norepeat"
            :style="{
                backgroundImage: `url('/demo/images/auth/bg-login.jpg') `
            }"
        >
            <div class="mt-auto mb-auto">
                <span class="block text-white text-7xl font-semibold">Access to your <br />Diamond <br />Account</span>
                <span class="block text-white text-3xl mt-4"
                    >Lorem ipsum dolor sit amet, consectetur<br />
                    adipiscing elit. Donec posuere velit nec enim<br />
                    sodales, nec placerat erat tincidunt.
                </span>
            </div>
            <div class="flex align-items-center gap-5">
                <span class="text-white font-semibold">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</span>
                <i class="pi pi-github text-3xl p-1 surface-overlay border-circle cursor-pointer"></i>
                <i class="pi pi-twitter text-3xl p-1 surface-overlay border-circle cursor-pointer"></i>
            </div>
        </div>
    </div>

    <AppConfig simple />
</template>
