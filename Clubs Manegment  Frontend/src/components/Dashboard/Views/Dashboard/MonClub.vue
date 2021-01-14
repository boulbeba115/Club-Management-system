<template>
  <div class="row">
    <div class="col-lg-4 col-md-5">
      <div class="card card-user">
        <el-upload
          class="upload-cover"
          :action='"http://localhost:8090/api/club/cover/"+club.id'
          :on-success="showCoverSuc"
          :limit="1">
        <el-button type="primary" class="btnClub" icon="el-icon-edit" circle></el-button>
        </el-upload>
        <div class="image">
          <img :src='"static/img/ClubCover/"+club.couvertureClub' alt="...">
        </div>
        <div class="card-content">
          <div class="author">
            <img class="avatar border-white" style="background-color: ghostwhite;" :src="avatarClub" alt="...">
            <h4 class="title" style="margin-top: 0;">{{club.nomClub}}
              <br>
              <a href="#">
                <small>DateCreation : {{club.dateCreation}}</small><br/>
                <small>Etat : 
                  <template v-if="!club.activityStop">Active</template>
                  <template v-else>Suspendu</template>
                </small><br/>
                <small>permission d'inscription :
                  <el-switch
                  @change="changeInscriptionState"
                    validate-event
                    v-model="club.inscriptionOn"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                  </el-switch>
                </small><br/>
                 <small>
                   Condition d'inscription :
                   <a @click="showterms=true"><i class="fa fa-check-square-o"></i></a>
                 </small>
              </a>
            </h4>
          </div>
        </div>
        <hr>
      </div>
      <div class="card">
            <div class="card-header">
            </div>
              <div class="card-content" style="text-align:center">
              <el-upload
                v-model="Avatar"
                ref="utilisateurAvatar"
                drag
                :limit=1
                :on-success="showUploadSuc"
                :action='UpImg()'
                :before-upload="beforeUpload"
                :multiple="false"
              >
                      <i class="el-icon-upload"></i>
                      <div class="el-upload__text">Changer le logo de club </div>
              </el-upload>
              </div>
      </div>
    </div>
    <div class="col-lg-8 col-md-7">
          <div class="card">
              <div class="card-header">
                <h4 class="title">Option de  Club</h4>
              </div>
              <div class="card-content" style="display: flow-root;">
                <div class="formcont" style="float:right">
                <el-form :model="clubForm" :rules="rulesclubForm" ref="clubForm" >
                  <el-form-item label="Année Universitaire" label-width="150px" label-position='left'>
                    <div class="row">
                      <div class="col-md-3">
                        <el-form-item prop="anneeDeb">
                          <el-input disabled v-model="clubForm.anneeDeb"></el-input>
                        </el-form-item>
                      </div> 
                      <div class="col-md-1">
                        /
                      </div>
                      <div class="col-md-3">
                        <el-form-item  prop="anneeFin">
                            <el-input disabled v-model="clubForm.anneeFin"></el-input>
                        </el-form-item>
                      </div>
                    </div> 
                  </el-form-item>
                  <el-form-item label="Tafrifation de l'année" prop="tarifation" label-width="160px" label-position='left'>
                     <el-input-number v-model="clubForm.tarifation" :precision="2" :step="0.1" :min="0"></el-input-number>
                  </el-form-item>
                  <div style="text-align:right;margin-right:5%">
                    <el-button type="success" @click="updateTarif" round>Valider</el-button>
                  </div>
                </el-form>
                </div>
              </div>
            </div>
    </div>
       <div class="col-lg-8 col-md-7">
          <div class="card">
              <div class="card-header">
                <h4 class="title">Liste des membres responsable de club</h4>
              </div>
                <div class="card-content">
                  <el-table
                    :data="club.lesEtudiant.filter(data => data.roleEtudiant !='membre')"
                    style="width: 100%">
                    <el-table-column
                      label="#"
                      align="center"
                      header-align="center"
                       min-width="50">
                      <template slot-scope="props">
                        <img :src="'static/img/membrePhoto/'+props.row.etudiant.utilisateurAvatar">
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="etudiant.nom"
                      label="Nom"
                      align="center"
                      header-align="center"
                      min-width="80">
                    </el-table-column>
                    <el-table-column
                      prop="etudiant.prenom"
                      align="center"
                      header-align="center"
                      label="Prenom"
                      min-width="80">
                    </el-table-column>
                    <el-table-column
                      prop="etudiant.email"
                      align="center"
                      header-align="center"
                      label="Email"
                      min-width="150">
                    </el-table-column>
                    <el-table-column
                      prop="roleEtudiant"
                      align="center"
                      header-align="center"
                      label="Role"
                      min-width="150">
                    </el-table-column>
                  </el-table>
                </div>
          </div>
          </div>


          
<el-dialog
  title="Condition D'Inscription de Club"
  :modalAppendToBody="false"
  :visible.sync="showterms"
  center
  :width='lewidth'>
  <div class="row">

    <div class="col-sm-11" style="float: none;margin: 0 auto;text-align:center">
    <div v-if="!editorOn">
    <a class="modifbtn" @click="showeditor"> <i class="el-icon-edit"></i></a>
    <div v-html="club.termsofUse">
    </div>
    </div>
    <div v-else>
      <a class="backbtn" @click="backfrom"> <i class="ti-angle-left"></i></a>
     <vue-editor  v-model="club.termsofUse"></vue-editor> 
     <div style="text-align:center;margin-top:2%">
        <el-button type="success" @click="UpdatetermsOfInsc" round>Valider</el-button>
      </div>
    </div>
    </div>
  </div>
</el-dialog>

  </div>
</template>
<script>
/* eslint-disable */

  import Vue from 'vue'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  import swal from 'sweetalert2'
  import {Upload} from 'element-ui'
  import { VueEditor } from "vue2-editor"

  Vue.use(VueEditor)
  Vue.use(Upload)
  export default {
    data () {

      return {
        showterms:false,
        editorOn:false,
        avatarClub:'static/img/ClubLogos/',
        Avatar:'',
        lewidth:'40%',
        club: {
        activityStop: '',
        dateCreation: '',
        utilisateurAvatar:'',
        lesEtudiant:[],
        inscriptionOn:null,
        termsofUse:''
        },
        clubForm: {
          anneeDeb:'',
          anneeFin:'',
          tarifation:'',
          inscriptOn:''
        },
        clubtarif:'',
        rulesclubForm:{},
      }
      
    },
  
      created() {  
        var self = this
        var club = JSON.parse(localStorage.getItem('session')).club
        if(club==null||club==undefined || club=='')
          return
          let anneUniv={
            anneeDeb:0,
            anneeFin:0
          }
          let month = new Date().getMonth()+1
          if(month>0 && month<=6)
          {
          anneUniv.anneeDeb=new Date().getFullYear()-1
          anneUniv.anneeFin=new Date().getFullYear()

          }
          else
          if(month>=8 && month<1)
          {
          anneUniv.anneeDeb=new Date().getFullYear()
          anneUniv.anneeFin=new Date().getFullYear()+1
          }
          http
          .post("club/tarification/"+club,anneUniv)
          .then(response => {
              self.clubtarif=response.data
              self.clubForm.anneeDeb=self.clubtarif.anneeUniver.anneeDeb
              self.clubForm.anneeFin=self.clubtarif.anneeUniver.anneeFin
              self.clubForm.tarifation=self.clubtarif.tarifation
          })
          .catch(e => {

          })

          http
            .get("club/byname/"+club)
            .then(response => {
              self.club = response.data
              self.avatarClub += self.club.clubLogo
            })
            .catch(e => {
         })

      },
    components: {
      VueEditor
    },
    methods: {
      showeditor(){
        this.lewidth="60%"
        this.editorOn=true
      },
      backfrom(){
        this.lewidth="40%"
        this.editorOn=false
      },
      UpImg(){
        return "http://localhost:8090/api/club/logo/"+this.club.id
      },
      beforeUpload(){

      },  
      showCoverSuc(response)
      {
        this.club.couvertureClub=response
        swal('','Phoro de couverture Modifier Avec Succes','success')
      },
      showUploadSuc(response)
      {
        this.avatarClub='static/img/ClubLogos/'+response
        swal('','Logo Modifier Avec Succes','success')
      },
      updateTarif () {
        var self= this
          let tarifation ={
            id:self.clubtarif.id,
            anneeUniver:self.clubtarif.anneeUniver,
            tarifation:self.clubForm.tarifation,
            club:self.club
          }
         swal({
            title: 'Confirmation',
            text: `voulez vous Confirmer La Modification de la tarif de Club`,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Modifier',
            cancelButtonText:'Annuler',
          }).then(function () {
              http.put("/club/update/tarification",tarifation)
                .then(response => {
                self.showterms=false
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Tarifation Modifer Avec succès',
                })
              })
          })
      },
      changeInscriptionState(){
        let msg,btn
        let self=this
        if(!this.club.inscriptionOn)
        {
            msg ="Tu veut Vraiment Fermée l'inscription au Club!"
            btn="Fermer"
        }
        else
           {
              msg ="Tu veut Vraiment ouvrir l'inscription au Club!"
              btn="ouvrir"

           }
       swal({
            title: 'Confirmation',
            text: msg,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: btn,
            cancelButtonText:'Annuler',
          }).then(function () {
             http.put("/club/inscriptionActiv",self.club)
                .then(response => {
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Modifer Avec succès',
                })
              })
          })
      },
      UpdatetermsOfInsc()
      {
        var self = this
          swal({
            title: 'Confirmation',
            text: 'voulez vous Confirmer les Modification ',
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Modifier',
            cancelButtonText:'Annuler',
          }).then(function () {
             http.put("/club/tearmsInscription",self.club)
                .then(response => {
                self.showterms=false
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Modifer Avec succès',
                })
              })
          })
      }
    }
  }


</script>
<style>
input.el-upload__input {
    display: none;
}
.title {
    text-align: center;
}
.el-upload-dragger {
    padding-left: 80px;
    padding-right: 80px;
    width: 100%;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0; 
}

input[type=number] {
    -moz-appearance:textfield; 
}
.erreurSai {
  border: #f17171 1px solid;
}
.modifbtn{
position: relative;
top: -59px;
left: 244px;
}
.backbtn{
  position: relative;
  top: -59px;
  left: -418px;
}
.btnClub{
  position: absolute;
  z-index: 1000000000;
  background: black;
  border: black;
  opacity: 0.4;
  right: 4px;
  top:4px;
}
.btnClub:hover{
  opacity: 1;
  background: black;
  border: black;
}
.btnClub:active{
  background: black !important;
  border: black !important;
}
.upload-cover{
    width: fit-content;
    position: absolute;
    right: 0;
}
</style>
