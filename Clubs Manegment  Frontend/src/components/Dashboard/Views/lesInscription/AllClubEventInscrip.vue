<template>
<div>
 <el-tabs type="border-card" style="max-height:590px;" >
    <el-tab-pane >
    <span slot="label"><i class="fa  fa-history"></i> Les Inscriptions des événements</span>
       <h3 style="text-align:center"> les Inscriptions des événements </h3>
            <div class="col-sm-12" style="margin-top:2%">
            <div class="pull-right" style="margin-bottom: 1%;">
     
            </div>
              <el-table class="table-striped"
                      :data="queriedData"
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
                min-width="100px"
                label="Télécharger le reçu "
                align="center"
                header-align="center">
                <template slot-scope="props">
                 <el-button  type="info" icon="ti-list" circle @click="afficherlesInscrit(props.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="col-sm-6 pagination-info">
          </div>
          <div class="col-sm-6">
            <p-pagination class="pull-right"
                          v-model="pagination.currentPage"
                          :per-page="pagination.perPage"
                          :total="pagination.total">
            </p-pagination>
          </div>
    </el-tab-pane>
</el-tabs>

 <el-dialog
        title="Inspecter le reçu d'inscription"
        :modalAppendToBody="false"
        center
        :visible.sync="dialogshow"
        width="70%">
        <div class="row">
          <el-table class="table-striped"
                      :data="selectedEvent.lesInscrit"
                      border
                      style="width: 100%">
              <el-table-column 
                min-width="150px"
                label="Etudiant"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <span>{{props.row.etudiant.nom}} {{props.row.etudiant.prenom}}</span>
                </template>
              </el-table-column> 
                <el-table-column 
                min-width="150px"
                label="cin"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <span>{{props.row.etudiant.cin}}</span>
                </template>
              </el-table-column> 
                <el-table-column 
                min-width="150px"
                label="Date Inscription"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <span>{{ props.row.dateInscription | moment("DD-MM-YYYY") }}</span>
                </template>
              </el-table-column>                  
               <el-table-column 
                min-width="150px"
                label="Date Inscription"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <span>{{ props.row.dateInscription | moment("DD-MM-YYYY") }}</span>
                </template>
              </el-table-column>
              <el-table-column 
                min-width="150px"
                label="Paiement"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <el-button v-if="props.row.etatInscription" type="success" round size="small" >Vérifier</el-button>
                  <el-button v-else type="danger" round size="small" >Pas encore Vérifier</el-button>
                </template>
              </el-table-column>
            </el-table>
        </div>
</el-dialog>
</div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import { Button ,Table, TableColumn ,Card,Tabs,TabPane} from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  import * as moment from 'moment';
  import 'moment/locale/fr';
  import jsPDF from 'jspdf'
  import autoTable from 'jspdf-autotable';


  moment.locale('fr');
  Vue.use(Button)
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Tabs)
  Vue.use(TabPane)
  Vue.use(Card)


  export default {
    components: {
      PPagination,
    },
    created() {
    var self = this

    http.get('Event/All/Inscription/list')
    .then(function (response) {
      self.tableData=response.data
      self.tableData.sort(function(a, b) {
     return a.id - b.id
     }).reverse()
    })

    
    },
    data () {
      return {
      dialogshow:false,
      selectedEvent:{
      lesInscrit:[],
      },
      pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        searchQuery: '',
        propsToSearch: ['id'],
        tableColumns: [
          {
            prop: 'id',
            label: 'numero',
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.nomEvenement',
            label: 'Événement',
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.datePrevuEvent',
            label: ' Date debut événement',
            minWidth: 100
          },
          {
            prop: 'demandeEvenement.leClub.nomClub',
            label: 'Club',
            minWidth: 100
          }
        ],
        tableData: [],
      
      }
    },
    methods: {
      afficherlesInscrit(row){
        this.selectedEvent=row
        this.dialogshow=true
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
              let rowValue = row[key].toString()
              if (rowValue.includes && rowValue.includes(this.searchQuery)) {
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
    }
  }
</script>
<style scoped>

</style>
