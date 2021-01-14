<template>
  <div>
    <div  v-if="!showcalendar" class="row">
      <div class="col-sm-12">
        <div class="card card-wizard" id="wizardCard">
 <form-wizard @on-complete="onComplete"
                           color="#e78b67"         
                          back-button-text="Précédent"
                          next-button-text="Suivant" 
                          style="height:600px">
            <h4 slot="title">Planifier Un Réunion </h4>
            <tab-content title="Information Génerale" icon="ti-info" style="max-height:600px" >
             <el-form :model="ReunionForm" :rules="ReunionFormrules" ref="ReunionForm" label-width="180px" label-position="left">
               <div class="row rowbutton">
                 <div class="col-sm-6">
                    <el-form-item label="Titre de Réunion" prop="titre">
                      <el-input v-model="ReunionForm.titre"></el-input>
                    </el-form-item>
                 </div>
                  <div class="col-sm-6">
                    <el-form-item label="Sujet de Réunion" prop="sujet">
                      <el-input v-model="ReunionForm.sujet"></el-input>
                    </el-form-item>
                  </div>
               </div>
                <div class="row rowbutton">
                 <div class="col-sm-6">
                    <el-form-item label="Date de Réunion" prop="dateReunion">
                      <el-date-picker 
                        :picker-options = "pickerOptions"
                        v-model="ReunionForm.dateReunion" 
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="Choisissez le date">
                      </el-date-picker>
                    </el-form-item>
                 </div>
                  <div class="col-sm-6">
                      <el-form-item label="Duré de Réunion" >
                          <div class="col-sm-6">
                            <el-form-item  prop="tempdebReunion">
                              <el-time-select
                                style="width:100%;"
                                placeholder="Horaire début"
                                v-model="ReunionForm.tempdebReunion"
                                :picker-options="{
                                start: '08:00',
                                step: '00:05',
                                end: '19:00'
                                }">
                              </el-time-select>
                            </el-form-item>
                          </div>
                          <div class="col-sm-6">
                            <el-form-item  prop="tempfinReunion">
                              <el-time-select
                                style="width:100%;"
                                placeholder="Horaire fin"
                                v-model="ReunionForm.tempfinReunion"
                                :picker-options="{
                                start: '08:00',
                                step: '00:05',
                                end: '19:00',
                                minTime: ReunionForm.tempdebReunion
                                }">
                              </el-time-select> 
                            </el-form-item>
                          </div>
                        </el-form-item>     
                  </div>
                  </div>
                  <div class="row rowbutton">
                  <div class="col-sm-6">
                        <el-form-item label="Type de Réunion" >
                          <el-radio-group v-model="radioexterne">
                            <el-radio label="0" border>Réunion interne</el-radio>
                            <el-radio label="1" border>Réunion Externe</el-radio>
                          </el-radio-group>
                        </el-form-item>
                  </div>
                  <div class="col-sm-6">
                    <div class="row">
                      <div class="col-sm-8">
                        <el-form-item label="Salle de Réunion" prop="salleReunion" >
                          <el-select  
                                    size="large"
                                    placeholder="selectionner la Salle"
                                    v-model="ReunionForm.salleReunion">
                            <el-option v-for="option in lesSalles"                        
                                      :value="option.id"
                                      :v-model="option.id"
                                      :label="option.nomSalle"
                                      :key="option.nomSalle">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                      <div class="col-sm-1">
                        <el-button type="primary" @click="showcalendar=true" icon="ti-calendar" circle></el-button>
                      </div>
                    </div>
                       
                  </div>
                  </div>
             </el-form>
            </tab-content>
            <tab-content title="affecter Membre" icon="ti-user"  >
                <h5 style='text-align:center'>Invite des membre de Club</h5>
                <el-table
                  class="table-striped"
                  border
                  height="270px"
                  :data="ReunionForm.leclub.lesEtudiant"
                  style="width: 100%"
                  @selection-change="changeMembreClub">
                  <el-table-column
                    type="selection"
                    width="55">
                  </el-table-column>
                  <el-table-column
                    label="Nom"
                    min-width="120">
                    <template slot-scope="scope">{{ scope.row.etudiant.nom }}</template>
                  </el-table-column>
                  <el-table-column
                    property="etudiant.prenom"
                    label="Prenom"
                    min-width="120">
                  </el-table-column>
                  <el-table-column
                    property="roleEtudiant"
                    label="Role"
                    show-overflow-tooltip>
                  </el-table-column>
                </el-table>
            </tab-content>
             <tab-content title="affecter Administration" icon="fa fa-users" >
                <h5 style='text-align:center'>Invite des membre d'administration du Faculté </h5>
                <el-table
                  class="table-striped"
                  border
                  height="270px"
                  ref="multipleTable"
                  :data="administration"
                  style="width: 100%"
                  @selection-change="changeAdministration">
                  <el-table-column
                    type="selection"
                    width="55">
                  </el-table-column>
                  <el-table-column
                    label="Nom"
                    min-width="120">
                    <template slot-scope="scope">{{ scope.row.nom }}</template>
                  </el-table-column>
                  <el-table-column
                    property="prenom"
                    label="Prenom"
                    min-width="120">
                  </el-table-column>
                  <el-table-column
                    property="role"
                    label="Role"
                    show-overflow-tooltip>
                  </el-table-column>
                </el-table>
            </tab-content>
            <template v-if="radioexterne=='1'">
            <tab-content title="les Invités" icon="fa fa-user-secret" >
              <h5 style='text-align:center'>Ajouter des Invités à la réunion</h5>
              <div class="row pull-right" style="margin-bottom:1%">
                 <div class="col-sm-12">
                  <el-button icon="ti-plus" @click="addinvited=true" type="info" plain> Ajouter Invité</el-button>
                 </div>
              </div>
              <div class="row">
                <div class="col-sm-12">
                  <el-table
                  header-align="center"
                  class="table-striped"
                  border
                  height="200px"
                  style="width: 100%"
                  :data="lesInviter"
                  >
                  <el-table-column
                    align="center"                
                    property="inviter.cin"
                    label="Cin"
                    min-width="120">
                  </el-table-column>
                  <el-table-column
                    align="center"
                    property="inviter.nom"
                    label="Nom"
                    min-width="120">
                  </el-table-column>
                  <el-table-column
                    align="center"
                    property="inviter.prenom"
                    label="Prénom"
                    min-width="120">
                  </el-table-column>
                  <el-table-column
                    align="center"
                    property="inviter.email"
                    label="Email"
                    min-width="150">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="Actions"
                    align="center">
                    <template slot-scope="props">
                      <a class="btn btn-simple btn-xs btn-danger btn-icon remove"  @click="handleDelete(props.$index, props.row)"><i class="ti-trash"></i></a>
                    </template>
                  </el-table-column>
                </el-table>
                </div>
              </div>
               
            </tab-content>
          </template>
        </form-wizard>
        </div>
      </div>
    </div>
  <div v-else>
    <el-button class="returnbtn" @click="showcalendar=false" icon="ti-angle-left" circle></el-button>
    <CalendarSalle/>
  </div>

  <el-dialog
  title="Ajouter Invité"
  :modalAppendToBody="false"
  :visible.sync="addinvited"
  :before-close="resetform"
    width="45%">
      <div class="row">
        <div class="col-sm-11" style="margin: 0 auto;float: none;">
           <el-form :model="InviterForm" :rules="InviterRules" ref="InviterForm" label-width="85px" label-position="left">
             <div class="row">
              <div class='col-sm-6'>
              <el-form-item label="Cin" prop="cin">
                <input   @keydown='emptyfielfds()' min="0"  class="el-input cin-input"   @blur="findInvited" type="number"  v-model="InviterForm.cin">
              </el-form-item>
              </div>
             </div>
              <div class="row">
              <div class='col-sm-6'>
                <el-form-item label="Nom" prop="nom">
                  <el-input  @change="checkchanges()" v-model="InviterForm.nom"></el-input>
                </el-form-item>
              </div>
              <div class='col-sm-6'>
                <el-form-item label="Prénom" prop="prenom">
                  <el-input @change="checkchanges()"  v-model="InviterForm.prenom"></el-input>
                </el-form-item>
              </div>
              </div>
              <div class="row">
                <div class='col-sm-8'>
                  <el-form-item label="Email" prop="email">
                    <el-input  @change="checkchanges()" type="email" v-model="InviterForm.email"></el-input>
                  </el-form-item>
                </div>
              </div>
              <div class="row">
              <div class='col-sm-6'>
                <el-form-item label="Adresse" prop="adresse">
                  <el-input  @change="checkchanges()" v-model="InviterForm.adresse"></el-input>
                </el-form-item>
              </div>
              <div class='col-sm-6'>
                <el-form-item label="Teléphone" prop="tel">
                  <el-input  @change="checkchanges()" min="0" type="number" v-model="InviterForm.tel"></el-input>
                </el-form-item>
              </div>
              </div>
              <div class="row">
                <div class='col-sm-8'>
                <el-form-item label="Profession" prop="profession">
                  <el-input  @change="checkchanges()"  v-model="InviterForm.profession"></el-input>
                </el-form-item>
                </div>
              </div>
             </el-form>
        </div>
        <div style="text-align:center">
            <el-button  v-if="!ischanged" type="success" @click="submitAddInvit" round>Ajouter Inviter</el-button>
            <el-button v-else type="success" @click="submitAddInvit" round>Modifier est ajouter</el-button>
        </div>
      </div>
  </el-dialog>

  </div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import {FormWizard, TabContent} from 'vue-form-wizard'
  import 'vue-form-wizard/dist/vue-form-wizard.min.css'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import {Select, Option, DatePicker, TimePicker , Input , Button , Row , Col, Radio , RadioGroup , RadioButton,TimeSelect,Loading} from 'element-ui'
  import { VueEditor } from "vue2-editor"
  import moment from 'moment'
  import CalendarSalle from 'src/components/Dashboard/Views/gerelesSalle/SalleCalendar.vue'
  Vue.prototype.moment = moment
  import { extendMoment } from 'moment-range';

  Vue.use(Select)
  Vue.use(Option)
  Vue.use(Input)
  Vue.use(Button)
  Vue.use(TimePicker)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Radio)
  Vue.use(RadioGroup)
  Vue.use(RadioButton)
  Vue.use(TimeSelect)
  Vue.use(Loading)



  Vue.use(require('vue-moment'))
  export default {
    created() {
      this.findclub(JSON.parse(localStorage.getItem('session')).club)
      var self = this
       http.get('Salle/list')
      .then(function (response) {
        self.lesSalles= response.data;
      })
      http.get('Administration/list')
      .then(function (response) {
        self.administration= response.data;
      })
      http.get('Administration/list')
      .then(function (response) {
        self.administration= response.data;
      })
    },
    data () {
      return {
        addinvited:false,
        invitedExist:false,
        showcalendar:false,
        invitedUpdated:false,
        loading:false,
        ReunionForm:{
          titre:'',
          sujet:'',
          dateReunion:'',
          tempdebReunion:'',
          tempfinReunion:'',
          type:false,
          salleReunion:'',
          leclub:''
        },
        ischanged:false,
        InviterForm:{
          id:'',
          nom:'',
          prenom:'',
          email:'',
          cin:'',
          adresse:'',
          tel:'',
          profession:''
        },
        oldInvited:null,
        administration:[],
        membresAdmine:[],
        participantAdmin:[],
        lesInviter:[],
        radioexterne:'0',
        messageForm:'',
        lesSalles:[],
        ReunionFormrules: {
          titre: [
            { required: true, message: 'Le Titre est Obligatoire', trigger: 'blur' },          
          ],
          sujet: [
            { required: true, message: 'Le sujet est Obligatoire', trigger: 'blur' }
          ],
          dateReunion: [
            { required: true, message: 'Le dateReunion est nécessaire',trigger: 'change' }
          ],
          tempdebReunion: [
            { required: true, message: 'Selectionner le Temp de Debut',trigger: 'change' }
          ],
          tempfinReunion: [
            { required: true, message: 'Selectionner le Temp Fin',trigger: 'change' }
          ],
          salleReunion: [
            { required: true, message: 'Selectionner la Salle de Réunion',trigger: 'change' }
          ]
        },
         InviterRules: {
          cin: [
            {required: true, message: 'Le Champ cin est Obligatoire', trigger: 'blur' },
            { min: 8, max: 8, message: 'Le Cin est former par 8 chiffre', trigger: 'blur'}          
          ],
          nom: [
            { required: true, message: 'Le Champ nom est Obligatoire', trigger: 'blur' }
          ],
          prenom: [
            { required: true, message: 'Le Champ prenom est Obligatoire',trigger: 'blur' }
          ],
          email: [
            {required: true, message: "L'email est Obligatoire ",trigger: 'blur' },
            {type: 'email' , message: "La format de l'email n'est pas valide ",trigger: 'blur' }
          ],
          adresse: [
            { required: true, message: "L'adresse est Obligatoire" ,trigger: 'blur' }
          ],
          tel: [
            { required: true, message: "Le num teléphone est Obligatoire",trigger: 'blur' },
            { min: 8 , message: 'Le num de teléphone contient au moin 8 chiffre', trigger: 'blur' }          
          ],
          profession: [
             { required: true, message: "La Profession est Obligatoire" ,trigger: 'blur' }
          ]
        },
        pickerOptions: {
          disabledDate(date) {
          var dated = new Date();
          dated.setDate(dated.getDate()-1);
          date.setDate(date.getDate());
          return dated >= date
          },
        },
      }
    },
    components: {
      CalendarSalle,
      FormWizard,
      TabContent,
      VueEditor,
      [DatePicker.name]: DatePicker
    },
    methods: {
      onComplete(){
        var self = this
        var listparticipantadministration=[]
        var listparticipantetudiant=[]
        this.membresAdmine.forEach(row =>{
          let reunionPart={
          participantEtudiant:{
            id:'',
            cin:''
          }
          }
          reunionPart.participantEtudiant.id=row.etudiant.id
          reunionPart.participantEtudiant.cin=row.etudiant.cin
          listparticipantetudiant.push(reunionPart)
        })
        this.participantAdmin.forEach(row =>{
          let reunionPart={
          participantAdministration:{
            id:'',
            cin:''
          }
          }
          reunionPart.participantAdministration.id=row.id
          reunionPart.participantAdministration.cin=row.cin
          listparticipantadministration.push(reunionPart)
        })
        let salle = this.lesSalles.find(salle => salle.id ===this.ReunionForm.salleReunion)
        if(salle == null)
        return
        let letype = false
        if(this.lesInviter.length>0)
          letype=true
        var  reunion = {
        titre: this.ReunionForm.titre ,
        sujet:this.ReunionForm.sujet,
        dateCreation: new Date(),
        dateReunion:this.ReunionForm.dateReunion,
        tempdebReunion:this.ReunionForm.tempdebReunion,
        tempfinReunion:this.ReunionForm.tempfinReunion,
        leClub:this.ReunionForm.leclub,
        type: letype,
        salleReunion:salle,
        lesInviter:this.lesInviter,
        lesParticipantsAdmins:listparticipantadministration,
        lesParticipantsetudiants:listparticipantetudiant
      }
      
        swal({
            title: 'Tu est sure?',
            text: `voulez vous ajouter ce Réunion!`,
            type: 'warning',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Ajouter!',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
              http.put("/reunion/cree",reunion)
                .then(response => {
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Ajouter Avec succès',
                })
              })
          })
      },
      validatestep1 (){
        var res=false
          this.$refs["ReunionForm"].validate((valid) => {
          if (valid) {
            res = true
          } else {
            res = false
          }
        })
        return res
      },
      checkchanges()
      {
        if(!this.invitedExist)
        this.ischanged=false
        else
        {
        this.ischanged=true
        this.invitedUpdated=JSON.stringify(this.oldInvited).replace(/\s/g, '') === JSON.stringify(this.InviterForm).replace(/\s/g, '')
        }
      },
      changeAdministration(val) {
        this.participantAdmin = val;
      },
      changeMembreClub(val) {
        this.membresAdmine = val;
      },
      submitAddInvit() {
      var self=this
      let listinv ={
        inviter:null,
      }
      let index =this.lesInviter.findIndex(row=>row.inviter.cin === this.InviterForm.cin)
      this.$refs["InviterForm"].validate((valid) => {
      if (valid) {
        if(self.invitedExist && !self.ischanged)
        {
          if(index >= 0)
          {
            swal('','cette persone deja ajouter au liste','info')
            self.resetform()
            return
          }
          let inviter={
          id : self.InviterForm.id,
          nom : self.InviterForm.nom,
          prenom : self.InviterForm.prenom,
          email : self.InviterForm.email,
          cin : self.InviterForm.cin,
          adresse : self.InviterForm.adresse,
          tel : self.InviterForm.tel,
          profession:self.InviterForm.profession
        }
        listinv.inviter=inviter
        self.lesInviter.push(listinv)
        self.resetform()
        }
        else if(!self.invitedUpdated && self.ischanged)
        { 
          self.loading=true
          http
          .put("inviter/ajout",self.InviterForm)
          .then(response => {
          self.loading=false
          if(index >= 0)
          {
            let leinvite ={
              inviter:response.data,
            }
            self.lesInviter.splice(index, 1) 
            self.lesInviter.splice(index, 0,leinvite);
            self.resetform()
          }
          else
          if(index < 0)
          {
          listinv.inviter=response.data
          self.lesInviter.push(listinv)
          self.resetform()
          }
          swal('Succès', 'Inviter est Modifier au liste avec succès','success')
          })
          .catch(e => {
          swal('error', 'Une erreur est Produit','error')          
          })
         
        }
        else
        {
          self.loading=true
          http
          .put("inviter/ajout",self.InviterForm)
          .then(response => {
          listinv.inviter=response.data
          self.lesInviter.push(listinv)
          self.loading=false
          self.resetform()
          swal('Succès', 'Inviter est Ajouter avec succès','success')
          })
          .catch(e => {
          swal('error', 'Une erreur est Produit','error')          
          })
        }
       
      }
      else
      return false
      })
      },
      findclub(nom){
        var self=this
        http
        .get("club/byname/"+nom)
        .then(response => {
        self.ReunionForm.leclub=response.data
        })
        .catch(e => {
        console.log(e);
        })
      },
      resetform(){
        var self = this
        self.oldInvited=null
        self.InviterForm.id=''
        self.addinvited=false
        self.$refs["InviterForm"].resetFields()
        self.invitedExist=false
        self.ischanged=false
        self.invitedUpdated=false
      },
      findInvited(){
        var self=this
        var invited
        var leCin = self.InviterForm.cin
        if(self.InviterForm.cin==""||self.InviterForm.cin==null)
         {
           self.$refs["InviterForm"].resetFields()
           self.invitedExist=false
           return
         }
        http
        .get("inviter/byCin/"+self.InviterForm.cin)
        .then(response => {
          invited = response.data

          if(invited==undefined || invited==''|| invited==null)
          {
             self.InviterForm.cin=leCin
             self.invitedExist=false
            return
          }
          self.oldInvited={
            id: response.data.id,
            nom : response.data.nom,
            prenom : response.data.prenom,
            cin : response.data.cin.toString(10),
            email : response.data.email,
            adresse : response.data.adresse,
            tel : response.data.tel.toString(10),
            profession:response.data.profession
          }
            invited.cin= invited.cin.toString(10)
            invited.tel= invited.tel.toString(10)
            self.InviterForm=invited
            self.invitedExist=true
          
        })
        .catch(e => {
        })
      },
      emptyfielfds()
      { 
        var lacin =this.InviterForm.cin
        this.$refs["InviterForm"].resetFields()
        this.InviterForm.id=''
        this.InviterForm.cin=lacin
        this.oldInvited=null
        this.invitedExist=false
        this.ischanged=false
        this.invitedUpdated=false
      },
      handleDelete (index, row) {
        let indexToDelete = this.lesInviter.findIndex((tableRow) => tableRow.inviter.id === row.inviter.id)
        if (indexToDelete >= 0) {
          this.lesInviter.splice(indexToDelete, 1)
        }
      }
    }
  }
</script>
<style >
.cin-input{
    height: 40px;
    border-radius: 6px;
    border: solid 1px lightgray;
    text-align: center
}
.vue-form-wizard .wizard-icon-circle.tab_shape {
  background-color: #9A9A9A !important;
  color: white;
}
.rowbutton {
  margin-bottom: 2%;
}
input[type=number]::-webkit-inner-spin-button, 
input[type=number]::-webkit-outer-spin-button { 
  -webkit-appearance: none; 
  margin: 0; 
}
div#fullCalendar {
    margin-top: 2%;
}
.returnbtn{
    position: absolute;
    z-index: 1000;
    left: 4%;
    top: 17%;
}
</style>
