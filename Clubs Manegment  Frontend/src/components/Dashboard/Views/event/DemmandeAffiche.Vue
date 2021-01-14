<template>
<div>
  <div class="row">
    <div class="col-md-12 card">
        <h4 style="text-align:center;color:#a7abb4">Liste des Demande d'affiche</h4>
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche  événements" v-model="searchQuery" aria-controls="datatables">
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
              label="Demande Ecrite"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                <a ><i class="ti-blackboard btn-Affiche" @click="showImgDemande(props.row)"></i></a> 
              </template>
             </el-table-column>
              <el-table-column
              fixed="right"
              label="L'Affiche"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                <a ><i class="ti-image btn-Affiche" @click="showDemande(props.row) "></i></a>
              </template>
             </el-table-column>

            <el-table-column
              fixed="right"
              label="Confirmer"
              align="center"
              header-align="center">
              <template slot-scope="props">
                  <template v-if="props.row.confirmation==false">
                  <button class="btn btn-icon btn-success" @click="confirmation(props.row,true)">
                    <i class="ti-check"></i>
                  </button>  
                  <button class="btn btn-icon btn-danger" @click="confirmation(props.row,false)">
                    <i class="ti-close"></i>
                  </button>
                </template>
                <template v-else >
                    <template v-if="props.row.etat">
                      <a type="button" class="  btn btn-fill btn-xs btn-success">Accepter</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill btn-xs  btn-danger">Refusé</a>
                    </template>
                </template>
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
      </div>
    </div>
  </div>
  <el-dialog
        title="Demmande Affiche Publicitaire"
        :modalAppendToBody="false"
        :visible.sync="AfficheDialogue"
        width="60%"
        center>
        <div class="row" >
          <div class="col-sm-10" v-html="selectedAffiche.demandeEcrite" style="margin: 0 auto;float: none;">

          </div>
       </div>
</el-dialog>
<el-dialog
        title="L'Affiche Publicitaire a Poster"
        :modalAppendToBody="false"
        :visible.sync="ImgDialogue"
        width="60%"
        center>
        <div class="row" style="margin:0 auto;text-align:center">
        <img style="max-width: 600px;" :src='"static/img/LesDemandeAffiche/"+this.selectedAffiche.demandeImage'>
       </div>
</el-dialog>
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
    var Affiche ={
        id:'',
        confirmation: false,
        etat: false,
        dateCreation:'',
        demandeImage:'',
        demandeEcrite:'',

    }
    http.get('demandeAffiche/list')
    .then(function (response) {
    var data = response.data
    let lesAffiche=[]
    for(let event of data )
    {
        for(let aff of event.lesDemandeAffiche)
        {
            Affiche=aff
            Affiche.leClub=event.demandeEvenement.leClub
            Affiche.nomEvenement=event.demandeEvenement.nomEvenement
            lesAffiche.push(Affiche)
        }
    }
    self.tableData = lesAffiche
    self.tableData.sort(function(a, b) {
    return b.dateCreation - a.dateCreation
    }).reverse()
    })
    },
    data () {
      return {
        AfficheDialogue:false,
        ImgDialogue:false,
        selectedAffiche:'',
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
            minWidth: 150,
          },
          {
            prop: 'nomEvenement',
            label: 'Evénement',
            minWidth: 150,
          },
          {
            prop: 'dateCreation',
            label: 'Date De Demande',
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
    showImgDemande(affiche){
      this.selectedAffiche=affiche
      this.AfficheDialogue=true
    },
    showDemande(affiche){
      this.selectedAffiche=affiche
      this.ImgDialogue=true
    },
    confirmation(demande,confirm){
        var self = this;
        var demAffiche ={
        id: demande.id,
        confirmation:true,
        etat: confirm,
        dateCreation:demande.dateCreation,
        demandeImage:demande.demandeImage,
        demandeEcrite:demande.demandeEcrite
        }
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Confirmer cette Demmande D'affiche !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Confirmer!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
        }).then(function () {
            http
            .put("demandeAffiche/Confirme", demAffiche)
            .then(response => {
              swal("","Affecter Avec Success", "success")
            })
            .catch(e => {
              swal("Erreur","Une Erreur et Produit", "error")
              console.log(e);
            })
        })
        
        
    }
    }
  }
</script>
<style scoped>
</style>
