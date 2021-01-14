<template>
<div>
  <h4 style="text-align:center;color:#a7abb4">Liste des Demande d'événement</h4>
  <div class="row">
    <div class="col-md-12 card">
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche événement" v-model="searchQuery" aria-controls="datatables">
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
                <!--<router-link :to="{ name: 'Profile', params: { msg } }">-->
                 <router-link :to="{ name: 'Plusdetails', params: { props} }">
                  <button class="btn btn-icon btn-instagram">
                    <i class="ti-receipt"></i>
                  </button>
                </router-link>
                </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="Confirmer"
              align="center"
              header-align="center">
              <template slot-scope="props">
                  <template v-if="props.row.confirmerEvent==false">
                  <button class="btn btn-icon btn-success" @click="confirmation(props.row,true)">
                    <i class="ti-check"></i>
                  </button>  
                  <button class="btn btn-icon btn-danger" @click="confirmation(props.row,false)">
                    <i class="ti-close"></i>
                  </button>
                </template>
                <template v-else >
                    <template v-if="props.row.accepterEvent">
                      <a type="button" class="  btn btn-fill btn-success  btn-xs">Accepter</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-danger  btn-xs">Refusé</a>
                    </template>
                </template>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="col-sm-6 pagination-info">
          <p class="category">Showing {{from + 1}} to {{to}} of {{total}} entries</p>
        </div>
        <div class="col-sm-6">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
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
  import { Button , Row , Col,Table, TableColumn } from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'

  Vue.use(Button)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Table)
  Vue.use(TableColumn)
  export default {
    created() {
    var self = this;
    http.get('DemandeEvent/list')
    .then(function (response) {
    self.tableData = response.data
    self.tableData.sort(function(a, b) {
    return b.datedemande - a.datedemande
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
            prop: 'leClub.nomClub',
            label: 'Club',
            minWidth: '100',
          },
          {
            prop: 'nomEvenement',
            label: 'Evénement',
            minWidth: 100,
          },
          {
            prop: 'datedemande',
            label: 'Date Demande',
            minWidth: 100
          },
          {
            prop: 'datePrevuEvent',
            label: "Date Prevu",
            minWidth: 100
          },
          {
            prop: 'dateFinEvent',
            label: "Date Fin",
            minWidth: 100
          },
          {
            prop: 'typeEvent.typeEvenement',
            label: "Type",
            minWidth: 100
          }
          
        ],
        tableData: [],
        msg:"hhhh"
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
<style scoped>
</style>
