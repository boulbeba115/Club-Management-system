<template>
  <div class="card card-wizard" id="wizardCard">
    <form-wizard @on-complete="onComplete"
    color="#35843c"         
    back-button-text="Précédent"
    next-button-text="Suivant" >
      <div slot="title" class="card-header">
        <h4 style="text-align:center" class="card-title">
          Formulaire Demande creation Club 
        </h4>
      </div> 
      <tab-content title="Formulaire Demande Club" icon="ti-info" :before-change="validationetap1" >
      <form class="form-horizontal">
      <div class="card-content">
        <div class="row">
        <div class="col-sm-6">
        <fieldset>
          <div class="row">
          <div class="form-group">
            <label class="col-sm-4 control-label">Nom de Club</label>
            <div class="col-sm-6">
              <input type="text"
                     v-model="model.nomClub"
                     class="form-control myinput">
            </div>
          </div>
          </div>
          <div class="row">
          <small class="text-danger" style="text-align: right;"  v-if="reqNomClub">
          s'il vous Entré le Nom de Club
          </small>
          </div>
        
        </fieldset>
        </div>
        <div class="col-sm-6">
        <fieldset>
              <div class="form-group">
                <label class="col-xs-4 control-label">Type de Club</label>
                <div class="col-xs-5" >
                  <el-select @click="addType" class="select-default"
                            size="large"
                            placeholder="selectionner le Type"
                            v-model="selects.simple">
                    <el-option v-for="option in selects.types"
                              class="select-default"
                              :value="option.id"
                              :v-model="option.id"
                              :label="option.type"
                              :key="option.label">
                    </el-option>
                  </el-select>
                </div>
                <div class="col-xs-2" >
                <el-button type="success" icon="ti-plus" @click="showaddtype=true" circle></el-button>
                </div>
              </div>
              <small class="text-danger col-sm-12" v-show="reqType" style="text-align: center;">
              s'il vous plaît choisir le Type De Club
              </small>
        </fieldset>
        </div>
        </div>


        <h4 style="text-align:center;margin-left:4%;margin-top:1%" class="card-title">
                    Liste des Membres fondateurs de club
        </h4>
        
          <div class="row" style=" position: relative;left: 4%;">

                      <div class="col-sm-2">
                        <fieldset>
                        <div class="form-group">
                        <input  type="number"
                        name="matricueetud" 
                        placeholder="Matricule"
                        autocomplete="off"
                        @keyup="findetud"
                        @keydown.enter.prevent=""
                        @focus="clearfield"
                        v-model="mat"
                        class=" form-control myinput">
                        </div>
                        </fieldset>
                      </div>

                       <div class="col-sm-2">
                         <fieldset>
                        <div class="form-group">
                          <el-select @click="addType" class="select-default" style="margin-right:5%"
                                    size="large"
                                    placeholder="Role"
                                    v-model="roleEtudiant">
                            <el-option v-for="option in roles"
                                      class="select-default"
                                      :value="option.id"
                                      :v-model="option.id"
                                      :label="option.role"
                                      :key="option.role">
                            </el-option>
                          </el-select>
                        </div>
                        </fieldset>
                      </div>
                      <div class="col-sm-3">   
                      <fieldset>
                        <div class="form-group">           
                          <input disabled type="text"
                          name="nometudiant"
                          v-model="nom"
                          placeholder="nom"
                          class="form-control myinput">  
                          </div>
                          </fieldset>
                          </div>
                        <div class="col-sm-3">
                        <fieldset>
                        <div class="form-group"> 
                            <input disabled type="text"
                            name="prenometudiant"
                            placeholder="Prenom"
                            v-model="prenom"
                            class="form-control myinput">
                        </div>
                      </fieldset>
                      </div>
                      <div class="col-sm-2">
                          <el-button type="primary ti-arrow-circle-right"  @click="addtolist" circle></el-button>
                      </div>

          </div>
               <div class="row">
                  <div class="card-content row">
                    <div class="col-sm-12" v-if="queriedData.length > 0">
                      <el-table class="table-striped"
                                :data="queriedData"
                                border
                                height="150px"
                                style="width: 100%">
                        <el-table-column style="text-align:center" v-for="column in tableColumns"
                                        :key="column.label"
                                        :min-width="column.minWidth"
                                        :prop="column.prop"
                                        :label="column.label">
                        </el-table-column>
                        <el-table-column
                          :min-width="50"
                          fixed="right"
                          label="Actions">
                          <template slot-scope="props">
                            <a class="btn btn-simple btn-xs btn-danger btn-icon remove"  @click="handleDelete(props.$index, props.row)"><i class="ti-close"></i></a>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                    <div class="col-sm-6">
                      <template v-if="tableData.length>=5">
                      <p-pagination class="pull-right"
                                    v-model="pagination.currentPage"
                                    :per-page="pagination.perPage"
                                    :total="pagination.total">
                      </p-pagination>
                      </template>
                    </div>


                  </div>
          </div>
      </div>
    </form>
    </tab-content>
    <tab-content title="Plus d'informations"
    icon="ti-notepad" >
      <div class="col-sm-12">
        <h4 style="text-align:center;margin-left:4%;margin-top:1%" class="card-title">Description d'Activté de Club</h4>
          <div class="row pull-center">
        <div class="col-sm-12 centred">
            <vue-editor                  
            name="description"
            v-model="model.description">
            </vue-editor>            
        </div>    
          </div>   
      </div> 
    </tab-content>
    </form-wizard>

<el-dialog title="Ajouter Type De Club" :visible.sync="showaddtype" :modalAppendToBody="false" width="30%">
  <el-form >
    <el-form-item label="Type" :label-width="formLabelWidth">
      <el-input v-model="newType" autocomplete="off" required></el-input>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="showaddtype = false">Annuler</el-button>
    <el-button type="primary" @click="addType">Confirmer</el-button>
  </span>
</el-dialog>
  </div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import http from './axiosbase.js'
  import swal from 'sweetalert2'
  import {Table, TableColumn, Select, Option} from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  import { VueEditor } from "vue2-editor"
  import {FormWizard, TabContent} from 'vue-form-wizard'
  import 'vue-form-wizard/dist/vue-form-wizard.min.css'

  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Select)
  Vue.use(Option)
  export default {

    components: {
      FormWizard,
      TabContent,
      PPagination,
      VueEditor
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
    data () {
      return {
        roles:null,
        roleEtudiant:'',
        formLabelWidth:"80px",
        showaddtype:false,
        reqNomClub:false,
        reqType:false,
        newType:'',
        selects: {
          simple: '',
          types:null
        },
        model: {
          nomClub: '',
          description:'',
          datedemande:new Date()
        },
        etudiant:{
          id: '',
          email:'',
          nom: '',
          cin: '',
          dateNaiss:'',
          matricule:'',
          adresse:'',
          tel: '',
          prenom: '',
          niveau: {
            id:'' ,
            niveau:'',
            filiere:''
          }
        },
        mat:'',
        nom:'',
        prenom:'',
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        membreFondateur:{
          etudiant:'',
          roleEtudiant:''
        },
        searchQuery: '',
        propsToSearch: ['etudiant.matricule'],
        tableColumns: [
          {
            prop: 'etudiant.matricule',
            label: 'Matricule',
            minWidth: 100
          },
          {
            prop: 'etudiant.nom',
            label: 'etudiant.Nom',
            minWidth: 100
          },
          {
            prop: 'etudiant.prenom',
            label: 'Prenom',
            minWidth: 100
          },
          {
            prop: 'etudiant.email',
            label: 'Email',
            minWidth: 150
          },
          {
            prop: 'roleEtudiant.role',
            label: 'Role dans le Club',
            minWidth: 100
          }
        ],
        tableData: [],
        type: ['', 'info', 'success', 'warning', 'danger'],
        notifications: {
          topCenter: false
        }        
      }
    },
    created (){
      var self=this

      http
        .get("Type/list")
        .then(response => {
        self.selects.types = response.data;
      })
        .catch(e => {
        console.log(e);
      })

      http
        .get("/etudiant/roles/list")
        .then(response => {
        self.roles = response.data;
      })
        .catch(e => {
        console.log(e);
      })

    },
    methods: {
      onComplete(){
        console.log(this.tableData)
        var demandeCreClub =
        {
        description: this.model.description,
        nomClub: this.model.nomClub ,
        confirmer:'',
        datedemande:new Date(),
        datedemande:'',
        membresFondateur:this.tableData,       
        type: {
        id:this.selects.simple,
        },
        }
        if(this.model.description=='')
        {
          swal("","La description de L'activité de Club est Obligatoire")
          return
        }
        else
        {
        this.savedemande(demandeCreClub) 
        }
      },
      getError (fieldName) {
        return this.errors.first(fieldName)
      },
      addType ()
      {
          var self = this;
          if(self.newType==="")
          return
          var tc = {
            type: this.newType,
          }
          var obj
        http
        .put("Type/create",tc)
        .then(response => {
          obj=response.data;
        self.selects.types.push(obj)
        this.showaddtype=false
        swal({
        type: 'success',
        title: 'Succès',
        text: 'Type de Club Ajouter avec succès',
        })
        this.selects.simple=obj
        })
        .catch(e => {
            console.log(e);
            this.showaddtype=false
            swal({
            type: 'error',
            title: 'Une erreur est Produit, il est possible que ce type deja exite'})          
            })
        
      },
      findetud(){

        let  x=this.mat;
       this.send(x)
      },
      send(matricul)
      {
        var self=this
        if(matricul=='')
        {
          this.nom=''
          this.prenom=''
        }
        else
        {
          http
          .get("/etudiant/findmat/" + matricul)
          .then(response => {
              self.etudiant= response.data;
              if(self.etudiant!=null)
              {
              this.nom=self.etudiant.nom
              this.prenom=self.etudiant.prenom
              }
          })
          .catch(e => {
            console.log(e);
          })


        }
      },
      addtolist()
      { 
      let obj = this.roles.find(o => o.id === this.roleEtudiant)
      let indexEtudiant = this.tableData.findIndex((row) => row.etudiant.matricule === this.etudiant.matricule)
      let indexRole = this.tableData.findIndex((row) => row.roleEtudiant.id === this.roleEtudiant)

      if(this.nom==undefined||this.prenom==null||this.mat=='')
        {
          swal("","Etudiant n'est pas trouver", "info")
          return 
        }
      else
      if(this.roleEtudiant=='')
      {
          swal("","Acune Role Selectioner", "info")
          return
      }
      if(indexEtudiant>=0)
        {
        swal("Duplication","Ce etudiant Déja Ajouter", "info")
        this.mat='',
        this.nom='',
        this.prenom=''
        return
        }
      else
      if(indexRole>=0)
        {
        swal("Duplication","Deja Existe Un Etudiant Avec Le Role "+obj.role , "info")
        this.roleEtudiant=''
        return
        }
      else
      {
         var membreFondateur = {
          etudiant:this.etudiant,
          roleEtudiant:obj
         }       
        this.tableData.push(membreFondateur)
        this.mat='',
        this.nom='',
        this.prenom='',
        this.roleEtudiant=''
      }

      },
      clearfield()
      {
        this.mat='',
        this.nom='',
        this.prenom=''
      },
      handleDelete (index, row) {
        let indexToDelete = this.tableData.findIndex((tableRow) => tableRow.id === row.id)
        if (indexToDelete >= 0) {
          this.tableData.splice(indexToDelete, 1)
        }
      },
      savedemande(DemandeClub) {
        var self=this
            http
              .put("/demandecreationclub/create", DemandeClub)
              .then(response => {
              swal({
              title: `Demende Envoyer Avec succes `,
              buttonsStyling: false,
              confirmButtonClass: 'btn btn-success btn-fill',
              type: 'success'
              })    
              this.model.nomClub=''
              this.model.description=''
              self.$router.push('/inscriptions/inscriptionClub')
              })
              .catch(e => {
                swal("Erreur","vortre demande n'est pas envoyer verifier les donnais saisie", "error")
                console.log(e);
              })
      },
      validationetap1(){
        let valid=true
        if(this.model.nomClub==''){
        this.reqNomClub=true
        valid=false
        }
        if(this.selects.simple==''){
        this.reqType=true
        valid=false
        }
        if(this.tableData.length<5)
        {
          swal("","Pour créer un club il doit au moin contenir 5 membre fondateur", "info")
          valid=false

        }
        return valid       
      },
      notifyVue (verticalAlign, horizontalAlign,message) {
        var color = 4
        this.$notify(
          {
            component: {
              template: `<span><b>Erreur de validation</b> <br/>` +message+`.</span>`
            },
            horizontalAlign: horizontalAlign,
            verticalAlign: verticalAlign,
            type: this.type[color]
          })
      }
    }
  }
</script>
<style>
@import url('https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300');
.form-horizontal .control-label {
    padding-top: 12px !important;
    text-align: center;
}
.addtypebtn {
    padding: 12px;
    background:#68B3C8;
    margin-left: 2px;
    position: relative;
    top: 12px;
    color: white;
    font-size: 16px;
    font-weight: bolder;
    border-radius: 50px;
}
span.addtype:hover{
  background:#3da2be
}
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
.el-select .el-input .el-select__caret.is-reverse {
    transform: rotateZ(0deg);
    position: relative;
    top: -19px;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
    min-width: 300px;
    max-width: 40%;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modheader {
    text-align: center;
    color: black;
    font-size: 30px;
    color: #519b72;
    font-family: 'Open Sans Condensed', sans-serif;
}
.typelabel {
  font-family: 'Open Sans Condensed', sans-serif;
    position: relative;
    top: 15px;
}
.addfound {
    padding: 12px;
    background: white;
    margin-left: 2px;
    position: relative;
    top: 13px;
    color: #68B3C8; 
    font-size: 16px;
    border: solid 2px #68B3C8;
    font-weight: bolder;
    border-radius: 50px;
}
span.addfound:hover{
  background:#68B3C8;
  color:white;
}
.cell {
    text-align: center;
}

.centred
{
float: none;
margin: 0px auto;
margin-bottom: 5%
}
fieldset{
  margin-bottom: 2%;
}
input[type=number]::-webkit-inner-spin-button, 
input[type=number]::-webkit-outer-spin-button { 
  -webkit-appearance: none; 
  margin: 0; }
.myinput{
  border-radius: 60px;
}
input.form-control.myinput {
    width: 90%;
}
</style>
