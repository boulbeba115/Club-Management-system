<template>
<div>
  <div class="row">
    <div class="col-md-12 card">
      <div class="card-content row">
                <h4 style="text-align:center;color:#a7abb4">Liste d'événement du Club</h4>
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche Salles" v-model="searchQuery" aria-controls="datatables">
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
                   <router-link v-if="verifExpired(props.row.demandeEvenement.datePrevuEvent)" :to="{ name: 'DetailEvenement', params: { props} }">
                  <button class="btn btn-icon btn-instagram">
                    <i class="ti-receipt"></i>
                  </button>
                </router-link>
                 <router-link v-else :to="{ name: 'Detail événement', params: { props} }">
                  <button class="btn btn-icon btn-instagram">
                    <i class="ti-receipt"></i>
                  </button>
                </router-link>
                </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="Etat"
              align="center"
              header-align="center">
              <template slot-scope="props">
                    <template v-if="props.row.publication">
                      <a type="button" class="  btn btn-fill   btn-xs btn-success">Publié </a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-xs  btn-danger" @click="publicEvent(props.row)">Non Publié</a>
                    </template>
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
  import * as moment from 'moment';

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
    http.get('event/ByClub/list/'+clubname)
    .then(function (response) {
    self.tableData = response.data;
    self.tableData.sort(function(a, b) {
    return b.demandeEvenement.datedemande - a.demandeEvenement.datedemande
    }).reverse()
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
        propsToSearch: ['nomEvenement'],
        tableColumns: [
          {
            prop: 'demandeEvenement.nomEvenement',
            label: 'Evénement',
            minWidth: 200,
          },
          {
            prop: 'demandeEvenement.datedemande',
            label: "Date D'Evénement",
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.datePrevuEvent',
            label: "Date Debut",
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.dateFinEvent',
            label: "Date Fin",
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.typeEvent.typeEvenement',
            label: "Type",
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
    publicEvent(demandeEv){
      var self = this
      swal({
        text: `Voulez Vous Vraiment publier ce événement`,
        type: 'info',
        showCancelButton: true,
        confirmButtonClass: 'btn btn-success btn-fill',
        cancelButtonClass: 'btn btn-danger btn-fill',
        confirmButtonText: 'Publier',
        buttonsStyling: false
      }).then(function () {
        self.publication(demandeEv);
      })
    },
    publication(demandeEv){
    if(demandeEv==null)
    return
    http
    .put("/event/publier", demandeEv)
    .then(response => {
      if(response.data==true)
      demandeEv.publication=true
      swal({
      title: "l'évenement a été publier",
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
    verifExpired(dateEvent){
      let sysDate = moment(new Date()).format("YYYY-MM-DD");
      return sysDate<dateEvent
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
