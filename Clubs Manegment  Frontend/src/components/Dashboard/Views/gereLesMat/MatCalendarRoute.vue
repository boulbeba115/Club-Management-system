<template>
  <calendar></calendar>
</template>
<script>

  import Loading from 'src/components/Dashboard/Layout/LoadingMainPanel.vue'

  const Calendar = () => ({
    component: import('./MatCalendar.vue'),
    loading: Loading,
    delay: 100
  })
  export default {
    components: {
      Calendar
    }
  }
</script>
<style>
</style>
