<template>
<div>
 <el-tabs type="border-card" style="max-height:590px;" >
    <el-tab-pane >
    <span slot="label"><i class="fa  fa-history"></i>Mes Inscriptions aux événements</span>
       <h3 style="text-align:center"> Mes Inscriptions aux événements </h3>
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
                label="Date Inscription"
                align="center"
                header-align="center">
                <template slot-scope="props">
                  <span>{{ props.row.dateInscription | moment("DD-MM-YYYY") }}</span>
                </template>
              </el-table-column>
               <el-table-column 
                min-width="100px"
                label="Télécharger le reçu "
                align="center"
                header-align="center">
                <template slot-scope="props">
                 <el-button v-if="!props.row.etatInscription" type="info" icon="ti-download" circle @click="downloadRecu(props.row)"></el-button>
                 <el-button v-else type="info" icon="ti-download" circle @click="dejaAccepter"></el-button>
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
        width="40%">
        <div class="row"  v-if="selectedInscription.evenement.demandeEvenement!=null">
          <el-button type="info" style="position: absolute;bottom: 53px;left: 46%;" icon="ti-download" circle @click="printIt()"></el-button>
          <div id="Recu" >
            <div style="text-align:center;">
            <img style="width:100px;heigth:100px;text-align:center" :src="'/static/img/ClubLogos/'+selectedInscription.evenement.demandeEvenement.leClub.clubLogo">
            </div>
            <div style="width:80%;margin:0 auto;text-align:center;margin-bottom: 19%;">
              <h4 style="text-align:center">Inscription au événement <br/> {{selectedInscription.evenement.demandeEvenement.nomEvenement}} </h4>
               <h5>Matricule : {{selectedInscription.etudiant.matricule}}</h5>
              <h5>Etudiant : {{selectedInscription.etudiant.nom}} {{selectedInscription.etudiant.prenom}}</h5>
              <h5> Date d'inscription : {{ selectedInscription.dateInscription | moment("DD-MM-YYYY") }}</h5>
              <h5 v-if="selectedInscription.tarif==null" style="color:green;font-weight"> Inscription Gratuite</h5>
              <h5 v-else  style="font-weight:600">  Montant a Payer : {{selectedInscription.tarif.montant}} dt</h5>
              <QrcodeVue style="margin-top:4%"  ref="test" id="test" :value="selectedInscription.codeInscription" size="100"  level="H"></QrcodeVue>
              <h6 style="margin-top:10%;text-align:center">Signature <br/> Responsable Club</h6>
            </div>
          </div>
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
  import QrcodeVue from 'qrcode.vue';
  import html2canvas from "html2canvas";

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
      QrcodeVue
    },
    created() {
    var self = this
    let etudiant = JSON.parse(localStorage.getItem('session')).username
    http.get('InscriptionEvent/Etudiant/'+etudiant)
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
      selectedInscription:{
        etudiant:{
          nom:'',
          prenom:'',
          matricule:''
        },
        evenement:{
        demandeEvenement:{
          leClub:{

          }
        }
        }
      },
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
            prop: 'id',
            label: 'numero',
            minWidth: 100
          },
          {
            prop: 'evenement.demandeEvenement.nomEvenement',
            label: 'Événement',
            minWidth: 100
          },
          {
            prop: 'evenement.demandeEvenement.leClub.nomClub',
            label: 'Club',
            minWidth: 100
          },
        ],
        tableData: [],
      
      }
    },
    methods: {
      downloadRecu(row){
        this.selectedInscription=row
        this.dialogshow=true
      },
      printIt(){
      let element = document.getElementById("Recu")
        var doc = new jsPDF("p","mm","a4");
      var self=this
      var canvasElement = document.createElement('canvas');
          html2canvas(element, { canvas: canvasElement 
            }).then(function (canvas) {
          const img = canvas.toDataURL("image/png");
          doc.addImage(img,'JPEG',10,0);
          doc.save("Inscription "+self.selectedInscription.evenement.demandeEvenement.nomEvenement+".pdf");
        });
      },
      dejaAccepter(){
        swal("","Nous avons déjà signé a votre reçu d'inscription tu ne peut pas imprimer un autre reçu","info")
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
