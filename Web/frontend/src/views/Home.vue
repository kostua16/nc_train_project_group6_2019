<template>
  <div class="home">
    <br>
    This app is made for the netcracker training center, and uses the following:<br><br>
    <ul
      v-for="(value, key) in items"
      :key="key"
    >
      <li>{{ value.message }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Api',
  computed: {
    items() {
      return [
        { message: 'Spring Boot' },
        { message: 'Vue' },
        { message: 'Thymeleaf' },
      ];
    },
  },
};
</script>
