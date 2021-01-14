<template>
<div>
 <el-tabs type="border-card" style="max-height:590px;" @tab-click="getonlynotpayed">
    <el-tab-pane >
      <span slot="label"><i class="fa fa-users"></i> Les Membre du club </span>
        <h3 style="text-align:center"> Liste d'inscription des adhérants au club</h3>
        <h3 style="text-align:center">{{SelectedAnneUnive.anneeDeb}}-{{SelectedAnneUnive.anneeFin}}</h3>
          <div class="col-sm-12" style="margin-top:2%">
          <div class="pull-right" style="margin-bottom: 1%;">
          <el-button icon="ti-printer" v-if="tableData.length>0" @click="generateList(0)" round> Imprimer la liste</el-button>
          <el-button icon="ti-printer" v-else disabled round> Imprimer la liste</el-button>
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
              label="Inscription">
              <template slot-scope="props"> 
                  <el-button v-if="props.row.inscriptionConfirmation" size="mini" @click="activitInscription(false,props.row)" type="success" round>Actif</el-button> 
                  <el-button v-else size="mini"  @click="activitInscription(true,props.row)" type="danger" round>Inactif</el-button>                                 
              </template>
            </el-table-column>
            <el-table-column
              :min-width="100"
              fixed="right"
              align="center"
              header-align="center"
              label="Paiement">
              <template slot-scope="props"> 
                  <el-button v-if="!props.row.inscripValid" type="success" @click="validPayment(props.row)" icon="el-icon-check" circle></el-button>
                   <el-button v-else size="mini" type="success" disabled round>Valide</el-button>                 
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
    <el-tab-pane >
      <span slot="label"><i class="fa fa-check-circle"></i> Les Insriptions Valide et payé </span>
          <h3 style="text-align:center"> Liste d'inscription des adhérants Valide et payé</h3>
          <h3 style="text-align:center">{{SelectedAnneUnive.anneeDeb}}-{{SelectedAnneUnive.anneeFin}}</h3>
            <div class="col-sm-12" style="margin-top:2%">
              <div class="pull-right" style="margin-bottom: 1%;">
              <el-button icon="ti-printer" v-if="tableData.length>0" @click="generateList(1)" round> Imprimer la liste</el-button>
              <el-button icon="ti-printer" v-else disabled round> Imprimer la liste</el-button>
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
                label="Inscription">
                <template slot-scope="props"> 
                    <el-button v-if="props.row.inscriptionConfirmation" size="mini" @click="activitInscription(false,props.row)" type="success" round>Actif</el-button> 
                    <el-button v-else size="mini"  @click="activitInscription(true,props.row)" type="danger" round>Inactif</el-button>                                 
                </template>
              </el-table-column>
              <el-table-column
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Paiement">
                <template slot-scope="props"> 
                    <el-button v-if="!props.row.inscripValid" type="success" @click="validPayment(props.row)" icon="el-icon-check" circle></el-button>
                    <el-button v-else size="mini" type="success" disabled round>Valide</el-button>                 
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
    <el-tab-pane >
      <span slot="label"><i class="fa fa-user-times"></i> Les Insription non payé  </span>
        <h3 style="text-align:center"> Liste d'inscription des adhérants pas encore regler les frais</h3>
        <h3 style="text-align:center">{{SelectedAnneUnive.anneeDeb}}-{{SelectedAnneUnive.anneeFin}}</h3>
          <div class="pull-right" style="margin-bottom: 1%;">
          <el-button icon="ti-printer" v-if="tableData.length>0" @click="generateList(2)" round> Imprimer la liste</el-button>
          <el-button icon="ti-printer" v-else disabled round> Imprimer la liste</el-button>
          </div>
          <div class="col-sm-12" style="margin-top:2%">
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
              label="Inscription">
              <template slot-scope="props"> 
                  <el-button v-if="props.row.inscriptionConfirmation" size="mini" @click="activitInscription(false,props.row)" type="success" round>Actif</el-button> 
                  <el-button v-else size="mini"  @click="activitInscription(true,props.row)" type="danger" round>Inactif</el-button>                                 
              </template>
            </el-table-column>
            <el-table-column
              :min-width="100"
              fixed="right"
              align="center"
              header-align="center"
              label="Paiement">
              <template slot-scope="props"> 
                  <el-button v-if="!props.row.inscripValid" type="success" @click="validPayment(props.row)" icon="el-icon-check" circle></el-button>
                   <el-button v-else size="mini" type="success" disabled round>Valide</el-button>                 
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
    <el-tab-pane >
    <span slot="label"><i class="fa  fa-history"></i> Historique des inscriptions</span>
       <h3 style="text-align:center"> Historique des inscriptions au club </h3>
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
                label="Inscription">
                <template slot-scope="props"> 
                    <el-button v-if="props.row.inscriptionConfirmation" size="mini" @click="activitInscription(false,props.row)" type="success" round>Actif</el-button> 
                    <el-button v-else size="mini"  @click="activitInscription(true,props.row)" type="danger" round>Inactif</el-button>                                 
                </template>
              </el-table-column>
              <el-table-column
                :min-width="100"
                fixed="right"
                align="center"
                header-align="center"
                label="Paiement">
                <template slot-scope="props"> 
                    <el-button v-if="!props.row.inscripValid" type="success" @click="validPayment(props.row)" icon="el-icon-check" circle></el-button>
                    <el-button v-else size="mini" type="success" disabled round>Valide</el-button>                 
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

    let anneeDeb,anneeFin
    let month = new Date().getMonth()+1
    if(month>0 && month<=6)
    {
    anneeDeb=new Date().getFullYear()-1
    anneeFin=new Date().getFullYear()
    }
    else
    if(month>=8 && month<1)
    {
    anneeDeb=new Date().getFullYear()
    anneeFin=new Date().getFullYear()+1
    }
    this.SelectedAnneUnive.anneeDeb=anneeDeb
    this.SelectedAnneUnive.anneeFin=anneeFin

    let clubname = JSON.parse(localStorage.getItem('session')).club
    http.put('/Inscription/all/'+clubname,this.SelectedAnneUnive)
    .then(function (response) {
     self.tableData=response.data
     self.originalTable=response.data
     self.tableData.sort(function(a, b) {
     return b.inscripValid - a.inscripValid
     }).reverse()
    })

    http.get('/AnneeUnive/List/'+clubname)
    .then(function (response) {
     self.lesAnneeUniv=response.data
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
        propsToSearch: ['id', 'nomClub', 'datedemande', 'description', 'confirmer'],
        tableColumns: [
          {
            prop: 'id',
            label: 'num',
            Width: 80
          },
          {
            prop: 'membresdeClub.etudiant.matricule',
            label: 'Matricule',
            minWidth: 100
          },
          {
            prop: 'membresdeClub.etudiant.nom',
            label: 'Nom',
            minWidth: 100
          },
          {
            prop: 'membresdeClub.etudiant.prenom',
            label: 'Prenom',
            minWidth: 100
          },
          {
            prop: 'membresdeClub.etudiant.email',
            label: 'Email',
            minWidth: 200
          },
          {
            prop: 'membresdeClub.etudiant.tel',
            label: 'Télephone',
            minWidth: 100
          }
        ],
        tableData: [],
        originalTable:[],
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
      activitInscription(etat,inscription){
        var self=this
        let msg
        if(!etat)
          msg="Voulez-vous vraiment met l'etat d'inscription en inactif"
        else
         msg="Voulez-vous vraiment met l'etat d'inscription en actif"
        let inscriptionAnn ={
          id:inscription.id,
          membresdeClub:inscription.membresdeClub,
          anneUnive:inscription.anneUnive,
          inscripValid:inscription.inscripValid,
          inscriptionConfirmation:etat
        }
          swal({
            title: 'Confirmation',
            text: msg,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Confirmer',
            cancelButtonText:'Annuler',
          }).then(function () {
             http.put("/Inscription/etat/update",inscriptionAnn)
                .then(response => {
                if(response.data)
                {
                inscription.inscriptionConfirmation=etat
                swal({
                type: 'success',
                title: 'Succès',
                text: 'met a jour Avec succès',
                })
                }
              })
          })
      },
      validPayment(inscription){
        var self=this
        let inscriptionAnn ={
          id:inscription.id,
          membresdeClub:inscription.membresdeClub,
          anneUnive:inscription.anneUnive,
          inscriptionConfirmation:inscription.inscriptionConfirmation,
          inscripValid:true
        }
          swal({
            title: 'Confirmation',
            text:"Voulez-vous vraiment valider le règlement du frai d'inscription",
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Confirmer',
            cancelButtonText:'Annuler',
          }).then(function () {
             http.put("/Inscription/etat/update",inscriptionAnn)
                .then(response => {
                if(response.data)
                {
                inscription.inscripValid=true
                swal({
                type: 'success',
                title: 'Succès',
                text: 'met a jour Avec succès',
                })
                }
              })
          })
      },
      getonlynotpayed(tab, event){

        if(tab.index==1){
        this.tableData = this.originalTable
        this.tableData= this.tableData.filter(data => data.inscripValid && data.inscriptionConfirmation)
        }
        else
        if(tab.index==2){
        this.tableData = this.originalTable
        this.tableData= this.tableData.filter(data => !data.inscripValid && data.inscriptionConfirmation)
        }
        else
        if(tab.index==3)
        {
          this.getHistoInscrip()
        }
        else
        this.tableData = this.originalTable
      },
      generateList(index){
        let clubname = JSON.parse(localStorage.getItem('session')).club
        var logos = []
        let self=this
        let studentlist=[]
        this.tableData.forEach(row => {
          let student = row.membresdeClub.etudiant
          if(row.inscriptionConfirmation)
            student.activInscrip="Inscription actif"
          else
            student.activInscrip="Inscription inactif"
          if(row.inscripValid)
            student.paymentvalid="Cotisations payées"
          else
            student.paymentvalid="Cotisations pas encore payées"
          studentlist.push(student)
        })
        http.get("/club/logo/base64/"+clubname)
        .then(response => {
          logos=response.data
        var doc = new jsPDF("p","mm","a4");
        doc.setFontSize(20);
        if(index==0)
        {
        doc.text(55, 30, "Liste des membres inscrit au Club ");
        doc.text(90, 40, self.SelectedAnneUnive.anneeDeb +"-"+self.SelectedAnneUnive.anneeFin);
        }
        else
        if(index==1)
        {
          doc.text("Liste des membres actif",70,30);
          doc.text(90, 40, self.SelectedAnneUnive.anneeDeb +"-"+self.SelectedAnneUnive.anneeFin);
        }
        else
        if(index==2)
        {
        doc.text("Liste de ceux qui n'ont pas payé\rles frais",110, 30, 'center');
        doc.text(93, 50, self.SelectedAnneUnive.anneeDeb +"-"+self.SelectedAnneUnive.anneeFin);
        }
        doc.addImage(logos[0],'png', 10, 10, 35, 35)
        doc.addImage(logos[1],'png', 165, 10, 35, 35)
        doc.setFontSize(12);
        doc.text(20, 70, "Le:")
        doc.text(35, 70, moment(new Date()).format("DD-MM-YYYY"));
        doc.text(20, 80, "Club : ")
        doc.text(35, 80, clubname);

        let columns = [
        {title: 'Matricule', dataKey: "matricule"}, 
        {title: 'Nom', dataKey: "nom"},
        {title: 'Prenom', dataKey: "prenom"},
        {title: 'Email', dataKey: "email"},
        {title: 'Télephone', dataKey: "tel"},
        {title: 'Paiement', dataKey: "paymentvalid"},
        {title: 'Inscription', dataKey: "activInscrip"},
        ]
        if(index==0)
        doc.text(65, 95, "Liste de tous les Membres du club");
        else
        if(index==1)
        doc.text(80, 95, "Liste des membres actif ");
        else
        if(index==2)
        doc.text(65, 95, "Liste de ceux qui n'ont pas payé de cotisation ");
        doc.autoTable(columns,studentlist,{margin: {top: 105,button: 10},})
        doc.save("Membres de "+clubname+".pdf")
        return
        })
      },
      getHistoInscrip(){
        var self=this
        let clubname = JSON.parse(localStorage.getItem('session')).club
        http.get('/Inscription/history/'+clubname)
        .then(function (response) {
          self.tableData=response.data
          self.originalHistory= self.tableData
        })
      },
      selectYear(){
        this.tableData=this.originalHistory
        if(this.historyYear==-1)
        return
        this.tableData=this.tableData.filter(data => data.anneUnive.id === this.historyYear)
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
    },
    
  }
</script>
<style >
.el-input__suffix {
    position: absolute;
    height: 100%;
    right: 5px;
    top: 14px;
    text-align: center;
    color: #c0c4cc;
    transition: all .3s;
    pointer-events: none;
}

span.addfound:hover{
  background:#68B3C8;
  color:white;
}
</style>
