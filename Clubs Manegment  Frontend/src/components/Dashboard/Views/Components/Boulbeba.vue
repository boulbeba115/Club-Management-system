<template>
  <div>
      <boulbeba></boulbeba>
              <div class="col-md-3">
          <div class="card">
            <div class="card-content text-center">
              <h5>Modal window with input field</h5>
              <button class="btn btn-default btn-fill" @click="showSwal('input-field')">Try me!</button>
            </div>
          </div>
        </div>
  </div>
  
</template>
<script>
/* eslint-disable */
  import PaginatedTables from 'src/components/Dashboard/Views/Tables/PaginatedTables.vue'
  export default {
    components: {
      'boulbeba': PaginatedTables
    },
    data () {
      return {
        boulbebadata: []
      }
    },
    mounted() {
      http
        .get("demandecreationclub/list")
        .then(response => {
          this.boulbebadata = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    methods: {
      showSwal (type) {
       /* if (type === 'input-field') {
          swal({
            title: 'Input something',
            html: '<div class="form-group">' +
            '<input id="input-field" type="text" class="form-control" />' +
            '</div>',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            buttonsStyling: false,
            input: 'text'
          }).then(function (text) {
            swal({
              type: 'success',
              html: 'You entered <br/> <h1>' + text + '</h1>',
              confirmButtonClass: 'btn btn-success btn-fill',
              buttonsStyling: false
            })

          }).catch(swal.noop)
        }*/
      },
    }
  }
</script>
<style>

</style>
