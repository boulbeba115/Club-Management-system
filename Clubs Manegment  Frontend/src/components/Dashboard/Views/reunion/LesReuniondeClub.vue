<template>
<div>
  <div class="row">
    <div class="col-md-12 card">
        <h4 style="text-align:center;color:#a7abb4">Liste des Réunion de Club</h4>

      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche Réunion" v-model="searchQuery" aria-controls="datatables">
            </label>
        </div>
        <div class="col-sm-6">
          <div class="pull-right">
          </div>
        </div>
        <div class="col-sm-12">
          <el-table class="table-striped"
                    :data="queriedData"
                    empty-text="Pas De Donnais"
                    border
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label"
                             align="center"
                             header-align="center">
            </el-table-column>
            <el-table-column
            fixed="right"
            label="Détails"
            align="center"
            header-align="center">
                <template slot-scope="props">
                 <router-link :to="{ name: 'DetailReunion', params: { props} }">
                  <button class="btn btn-icon btn-instagram">
                    <i class="ti-receipt"></i>
                  </button>
                </router-link>
                </template>
            </el-table-column>
           
          </el-table>
         
        </div>
        <div class="col-sm-6 pagination-info">
          <p class="category">afficher {{from + 1}} de {{to}} a {{total}} record</p>
        </div>
        <template v-if="total > 5">
        <div class="col-sm-6">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
        </template>
      </div>
    </div>
  </div>
  </div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import { Button , Row , Col,Table, TableColumn, } from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'

  Vue.use(Button)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Table)
  Vue.use(TableColumn)
  export default {
    created() {
    var self = this;
    var clubname = JSON.parse(localStorage.getItem('session')).club
    if(clubname==null||clubname==undefined || clubname=='')
    return
    http.get('/reunion/club/'+clubname)
    .then(function (response) {
    self.tableData = response.data;
    })

    },
    data () {
      return {
        lesEvenement:[],
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        searchQuery: '',
        propsToSearch: ['titre'],
        tableColumns: [
          {
            prop: 'titre',
            label: 'Reunion de',
            minWidth: 200,
          },
          {
            prop: 'sujet',
            label: 'Sujet',
            minWidth: 100
          },
          {
            prop: 'dateCreation',
            label: "Date Creation",
            minWidth: 100
          },
          {
            prop: 'dateReunion',
            label: "Date Reunion",
            minWidth: 100
          },
          {
            prop: 'tempdebReunion',
            label: "Heur Début",
            minWidth: 100
          },
          {
            prop: 'tempfinReunion',
            label: "Heur Fin",
            minWidth: 100
          }
          
        ],
        tableData: [],
      }
    },
    computed: {
      pagedData () {
        return this.tableData.slice(this.from, this.to)
      },
      queriedData () {
        if (!this.searchQuery) {
          this.pagination.total = this.tableData.length
          return this.pagedData
        }
        let result = this.tableData
          .filter((row) => {
            let isIncluded = false
            for (let key of this.propsToSearch) {
              if(key=="type")
              {
              let starvakue = row[key].type
              if (starvakue.includes && starvakue.includes(this.searchQuery)) {
                isIncluded = true
              }}
              let rowValue = row[key].toString().toUpperCase()
              if (rowValue.includes && rowValue.includes(this.searchQuery.toUpperCase())) {
                isIncluded = true
              }
            }
            return isIncluded
          })
        this.pagination.total = result.length
        return result.slice(this.from, this.to)
      },
      to () {
        let highBound = this.from + this.pagination.perPage
        if (this.total < highBound) {
          highBound = this.total
        }
        return highBound
      },
      from () {
        return this.pagination.perPage * (this.pagination.currentPage - 1)
      },
      total () {
        this.pagination.total = this.tableData.length
        return this.tableData.length
      }
    },
    components: {
      PPagination,
    },
    methods: {
    creeEvent(demandeEv){
      var self = this
      swal({
        title: 'Confirmation',
        text: `Voulez Vous Vraiment Approuver Cette Demande`,
        type: 'info',
        showCancelButton: true,
        confirmButtonClass: 'btn btn-success btn-fill',
        cancelButtonClass: 'btn btn-danger btn-fill',
        confirmButtonText: 'Approuver',
        buttonsStyling: false
      }).then(function () {
        self.ajouterEvent(demandeEv);
      })
    },
    ajouterEvent(demandeEv){
      var evenement={
        tarifAdherant:0,
        tarifNonAdherant:0,
        demandeEvenement:demandeEv
      }
    http
    .put("/event/create", evenement)
    .then(response => {
      swal({
      title: "l'évenement a été Approuver !",
      type: 'success',
      confirmButtonClass: 'btn btn-success btn-fill',
      buttonsStyling: false
      })
    })
    .catch(e => {
    swal("Erreur","Une Erreur et Produit", "error")
    console.log(e);
    })    
    },
    confirmation(demande,confirm){
        var self = this;

        demande.confirmerEvent=true;
        demande.accepterEvent=confirm;
        http
        .put("/DemandeEvent/update", demande)
        .then(response => {
        })
        .catch(e => {
          swal("Erreur","Une Erreur et Produit", "error")
          console.log(e);
        })
    }
    }
  }
</script>
<style >
.card-content
{
  margin-top:0px
}
</style>
