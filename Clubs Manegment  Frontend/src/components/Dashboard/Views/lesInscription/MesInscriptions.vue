<template>
<div>
 <el-tabs type="border-card" style="max-height:590px;" @tab-click="changeTabs">
    <el-tab-pane >
    <span slot="label"><i class="fa  fa-history"></i>Mes Inscriptions aux Clubs</span>
       <h3 style="text-align:center"> Mes Inscriptions aux Clubs </h3>
            <div class="col-sm-12" style="margin-top:2%">
            <div class="pull-right" style="margin-bottom: 1%;">
              <el-select @change="selectYear" class="select-default"
                           size="large"
                           placeholder="Année Universitaire"
                           v-model="historyYear">
                  <el-option
                  class="select-primary"
                  value="-1"
                  label="Tous Les Années"
                  key="-1"></el-option>
                  <el-option v-for="option in lesAnneeUniv"
                             class="select-primary"
                             :value="option.id"
                             :label="option.anneeDeb+'-'+option.anneeFin"
                             :key="option.id">
                  </el-option>
                </el-select>
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
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Année Universitaire">
                <template slot-scope="props"> 
                   {{props.row.anneUnive.anneeDeb}} - {{props.row.anneUnive.anneeFin}}
                </template>
              </el-table-column>
              <el-table-column
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Frais d'abonnement">
                <template slot-scope="props"> 
                   <p v-if="getTarifation(props.row).tarifation>0">{{getTarifation(props.row).tarifation}}</p>
                   <p v-else>Inscription Gratuite</p>

                </template>
              </el-table-column>
              <el-table-column
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Inscription">
                <template slot-scope="props"> 
                    <el-button v-if="props.row.inscriptionConfirmation" size="mini"  type="success" round>Actif</el-button> 
                    <el-button v-else size="mini"  type="danger" round>Inactif</el-button>                                 
                </template>
              </el-table-column>
              <el-table-column
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Paiement">
                <template slot-scope="props"> 
                    <el-button v-if="!props.row.inscripValid" size="mini"  type="warning" round>pas encore payées</el-button>                                 
                    <el-button v-else size="mini" type="success"  round>frais payés</el-button>                 
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
        title=""
        :modalAppendToBody="false"
        :visible.sync="dialogshow"
        width="40%">
        <div class="row">
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
      PPagination
    },
    created() {
    var self = this
    let etudiant = JSON.parse(localStorage.getItem('session')).username
    http.get('/Inscription/etudiant/'+etudiant)
    .then(function (response) {
      self.tableData=response.data
      self.originalHistory= self.tableData
      self.tableData.sort(function(a, b) {
     return b.inscripValid - a.inscripValid
     }).reverse()
    })

    http.get('/AnneeUnive/List/all')
    .then(function (response) {
     self.lesAnneeUniv=response.data
     self.tableData.sort(function(a, b) {
     return b.anneeFin - a.anneeFin
    })
    })
    
    },
    data () {
      return {
      dialogshow:false,
      pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        donnais:this.boulbebadata,
        searchQuery: '',
        propsToSearch: ['nomClub'],
        tableColumns: [
          {
            prop: 'membresdeClub.club.nomClub',
            label: 'Club',
            minWidth: 100
          },
        ],
        tableData: [],
        originalHistory:[],
        lesAnneeUniv:[],
        historyYear:'',
        SelectedAnneUnive: {
          anneeDeb: '',
          anneeFin: ''
        },
      }
    },
    methods: {
      selectYear(){
        this.tableData=this.originalHistory
        if(this.historyYear==-1)
        return
        this.tableData=this.tableData.filter(data => data.anneUnive.id === this.historyYear)
      },
      getTarifation(row){
        let tarif= row.membresdeClub.club.tarifInscription.find(data => data.anneeUniver.id ===row.anneUnive.id)
        return tarif
      },
      changeTabs()
      {
        console.log('change')
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
