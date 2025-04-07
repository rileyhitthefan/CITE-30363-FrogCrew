<template>
    <div>
        <form action="#">
            First Name: <input
                type="text"
                required
            />
            <br />
            Last Name: <input
                type="text"
                required
            />
            <br />
            Email: <input
                type="email"
                required
            />
            <br />
            Phone Number: <input
                type="tel"
                pattern="[0-9]{3}-[0-9]{3}-[0-9{4}]"
                required
            />
            <br />
            Password: <input
                type="password"
            />
            <br />
            Role: <input
                type="text"
                required
            />
            <br />
            Qualified Position: <input
                type="text"
                required
            />
            <br />

        </form>

    </div>
</template>

<script setup>

</script>

<style scoped>

</style>