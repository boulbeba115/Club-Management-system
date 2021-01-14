<template>
<div>
  <div class="row">
    <div class="col-md-12 card">
        <h4 style="text-align:center;color:#a7abb4">Liste des demandes d'aide financière </h4>
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche événements" v-model="searchQuery" aria-controls="datatables">
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
            <el-table-column type="expand">
                  <template slot-scope="scope">
                  <h5 style="text-align:center">Détail de Matériel à financer</h5>
                    <ul style="text-align:center"  v-for="mat in scope.row.deviMateriel">
                    <li Style=" list-style-type: none;">
                    <div class="row">
                    <div class="col-sm-3">Matériel : {{ mat.matExterne.nomMateriel }}</div>
                    <div class="col-sm-3">Marque: {{mat.matExterne.marqueMateriel}}</div>
                    <div class="col-sm-3">Quantité Demander: {{mat.quantite}}</div>
                    <div class="col-sm-3">Prix d'unité : {{mat.montant}}</div>
                    </div>
                    </li>
                    </ul>
                  <hr style="width:90%"> 
                  <div class="col-sm-10" style="margin: 0 auto;float: none;">
                  <h5 style="text-align:right"> Total = {{calcleMtTotale(scope.row.deviMateriel)}} dt</h5> 
                  </div>         
                  </template>
            </el-table-column>
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label"
                             align="center"
                             header-align="center">
            </el-table-column>
             <el-table-column
              label="Devi"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                     <a :href="'static/img/devi/'+props.row.leDevi">
                       <el-button type="primary" icon="ti-download" circle></el-button>
                     </a>
              </template>
            </el-table-column>
            <el-table-column
              label="Confirmation"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                    <template v-if="props.row.etat">
                      <a type="button" class="  btn btn-fill   btn-xs btn-success">Accepté</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-xs  btn-danger" @click="confirmation(props.row)">Pas encore accepté</a>
                    </template>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="col-sm-12">
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
    var demandeFinance ={
        id:'',
        dateCreation:'',
        leDevi:'',
        fournisseur:'',
        deviMateriel:''
    }
    http.get('demandeFinance/list')
    .then(function (response) {
    var data = response.data
    let lesDemandes=[]
    for(let event of data )
    {
        for(let demfina of event.lesDemandeFinance)
        {
            demandeFinance=demfina
            demandeFinance.leClub=event.demandeEvenement.leClub.nomClub
            demandeFinance.nomevenement=event.demandeEvenement.nomEvenement
            lesDemandes.push(demandeFinance)
        }
    }
    self.tableData = lesDemandes
    self.tableData.sort(function(a, b) {
    return b.dateCreation - a.dateCreation
    }).reverse()
    })
    },
    data () {
      return {
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        searchQuery: '',
        propsToSearch: ['nomevenement'],
        tableColumns: [
          {
            prop: 'leClub',
            label: 'Club',
            minWidth: 150,
          },
          {
            prop: 'nomevenement',
            label: 'Événement',
            minWidth: 150,
          },
          {
            prop: 'dateCreation',
            label: 'Date De Demande',
            minWidth: 150
          },
          {
            prop: 'fournisseur.raisonSociale',
            label: 'Fournissuer',
            minWidth: 150
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
    calcleMtTotale(row){
      var mtTotal=0
      for(let obj of row){
      mtTotal+=(obj.montant*obj.quantite)
      }
      return mtTotal
    },
    confirmation(row){
        var self = this
        let demandeFinance={
          id:row.id,
          dateCreation:row.dateCreation,
          leDevi:row.leDevi,
          fournisseur:row.fournisseur,
          deviMateriel:row.deviMateriel,
        }
        let index = this.tableData.findIndex(row => row.id === demandeFinance.id) 
        swal({
          text: `voulez vous Confirmer cette Demmande d'aide financière `,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Confirmer!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
        }).then(function () {
            http
            .put("demandeFinance/Confirme", demandeFinance)
            .then(response => {
              row.etat=true
              self.tableData.splice(index, 1) 
              self.tableData.splice(index, 0, row)
              swal("","Demande confirmé avec success", "success")
            })
            .catch(e => {
              swal("Erreur","Une erreur et produit", "error")
            })
        })
        
        
    }
    }
  }
</script>
<style >
button.swal2-cancel.btn.btn-danger.btn-fill {
    margin: 2%;
}
</style>
