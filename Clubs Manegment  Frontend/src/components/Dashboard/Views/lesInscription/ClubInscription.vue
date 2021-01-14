<template>
<div>
    <div class="card row" style="min-height: 600px;border-raduis:5px">
        <div class="main">
        <h1>Les Club de la faculté</h1>
        <ul class="cards" >
            <li class="cards_item" v-for="club in lesClub">
            <div class="card">
                <div class="card_image"><img class='imgCover' :src='"static/img/ClubCover/"+club.couvertureClub'></div>
                <div class="card_content">
                <div class="clubInfoContaner">
                <img class="avatar border-white" style="background-color: ghostwhite;" :src='"static/img/ClubLogos/"+club.clubLogo' alt="...">
                <h2 class="card_title">{{club.nomClub}}</h2>

                <button class="btnInscrip btn card_btn" v-if="!club.isMember" @click="selectClub(club)">Inscription</button>
                <button class="btnInscrip btn card_btn" style="background:black" v-else disabled >Déja Inscrit</button>

                </div>
            </div>
            </div>
            </li>
        </ul>
        </div>
    </div>     

<el-dialog
  :title="'Inscription au Club '+selectedClub.nomClub"
  center
  :modalAppendToBody="false"
  :visible.sync="showterms"
  width="40%"
  :before-close="clearselection"
  custom-class="dialgIns">
  <div class="row">
  <div class="col-sm-11" style="float: none;margin: 0 auto;">
    <div v-if="selectedClub.inscriptionOn">
        <template v-if="yeartarif!='' || yeartarif!=null || yeartarif.tarifation==null">
                 <h4 class="tarifclub" v-if="yeartarif.tarifation > 0 ">Tarif d'inscription : <span>{{yeartarif.tarifation}} Dt</span></h4> 
                 <h4 class="tarifclub" v-else>L'inscription a ce club est gratuite</h4> 
         <template v-if="!selectedClub.termsofUse=='' && !typePayment">
           <h5 style="text-align:center">Les Conditions d'inscription</h5>
          <div style="text-align:center;min-height: 150px; margin-bottom: 2%;" v-html="selectedClub.termsofUse"/>
          <div class="text-align:center" style="width:fit-content;margin: 0 auto;">
              <el-checkbox v-model="checkTerms">accepter les condition d'inscription</el-checkbox>
          </div>
             <el-button v-if="checkTerms" type="primary" @click="typePayment=true" style="float:right;" round>Suivant</el-button>
             <el-button v-else disabled type="primary" style="float:right;" round>Suivant</el-button>
          </template>
            <div v-else-if="typePayment">
              <h5 style="text-align:center">Choisir le type de payment</h5>
              <ul style="list-style:none;display: flex;">
                <li class="typepay" @click="onlinePay" >
                  <div>
                    <i style="font-size: 52px;" class="ti-credit-card"></i>
                  </div>
                  <div>
                   <h5 style="margin-top: 4%;"> Payer Online </h5>
                  </div>
                </li>
                <li class="typepay" @click="inscriptionClub(true)">
                  <div>
                    <i style="font-size: 52px;" class="ti-wallet"></i>
                  </div>
                  <div>
                   <h5 style="margin-top: 4%;"> Inscrire puis Payer </h5>
                  </div>
                </li>
              </ul>
            </div>
              <div v-else>
                <template v-if="yeartarif.tarifation > 0 ">
                <h5 style="text-align:center">Choisir le type de payment</h5>
                <ul style="list-style:none;display: flex;">
                  <el-popover
                    placement="top-start"
                    width="250"
                    style="text-align: center;"
                    trigger="hover"
                    popper-class="centredpop"
                    content="le Paiement électronique n'est pas encore integré">
                    <li slot="reference" class="typepay" @click="onlinePay" >
                    <div>
                      <i style="font-size: 52px;" class="ti-credit-card"></i>
                    </div>
                    <div>
                    <h5 style="margin-top: 4%;"> Payer Online </h5>
                    </div>
                  </li>
                  </el-popover>

                 
                  <li class="typepay" @click="inscriptionClub(true)">
                    <div>
                      <i style="font-size: 52px;" class="ti-wallet"></i>
                    </div>
                    <div>
                    <h5 style="margin-top: 4%;"> Inscrire puis Payer </h5>
                    </div>
                  </li>
                </ul>
                </template>
                <template v-else>
                 <a class="typepay" @click="inscriptionClub(false)" >
                    <h5  class="inscrip"> Inscription au club </h5>
                 </a>
                </template>
            </div>
          </template>
          <template v-else-if="yeartarif==null">
            <h4 class="tarifclub">L'inscription est impossible pas de tarification a affecté a ce club </h4> 
          </template>
    </div>
    <div v-else>
    <h4 class="tarifclub">L'inscription a ce est fermé</h4> 
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
import {Checkbox,Popover} from 'element-ui'
Vue.use(Checkbox)
Vue.use(Popover)

export default {
data (){
    return {
    showterms:false,
    typePayment:false,
     lesClub:[],
     selectedClub:'',
     yeartarif:{},
     checkTerms:''

    }
},
created(){
    var self=this
    var user = JSON.parse(localStorage.getItem('session'))
    if(user==null||user==undefined || user=='')
    return
    http.get("/club/only/list/"+user.username)
    .then(response => {
        self.lesClub = response.data
    })
    .catch(e => {
    })
},
methods:{
  selectClub(club){
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
      this.yeartarif= club.tarifInscription.find(clubtarif => clubtarif.anneeUniver.anneeDeb==anneeDeb && clubtarif.anneeUniver.anneeFin==anneeFin)
      if(this.yeartarif==null)
      return
      this.selectedClub=club
      this.showterms=true
   console.log(this.yeartarif)
 },
 clearselection(){
    this.showterms=false
    var self = this;
    setTimeout(function () { self.selectedClub='',self.yeartarif='',self.typePayment=false,self.checkTerms=false } , 100)
 },
 onlinePay(){

 },
 offlinePay(){
   
 },
 inscriptionClub(etat){
  var self = this
  var user = JSON.parse(localStorage.getItem('session'))
  if(user==null||user==undefined || user=='')
  return
  let request
  if(etat)
  request="/Inscription/Club"
  else
  request="/Inscription/Club/Free"

   var inscription={
     membresdeClub:{
       club:this.selectedClub,
       etudiant:{
         lelogin:user.username
       }
     },
   }

   swal({
            title: 'Confirmation',
            text: `Voulez vous Confirme l'inscription a ce Club!`,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Inscrire',
            cancelButtonText:'Annuler',
          }).then(function () {
              http.put(request,inscription)
                .then(response => {
                  self.showterms=false
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Inscription effectué  Avec succès',
                })
                  this.$router.push("/inscriptions/mesInscriptions")
              })
          })
 }
}
}
</script>

<style >
@import url('https://fonts.googleapis.com/css?family=Quicksand:400,700');

/* Design */
*,
*::before,
*::after {
  box-sizing: border-box;
}


.main{
  max-width: 1200px;
  margin: 0 auto;
}

h1 {
    font-size: 24px;
    font-weight: 400;
    text-align: center;
}

img {
  height: auto;
  max-width: 100%;
  vertical-align: middle;
}

.btnInscrip {
    color: #ffffff;
    padding: 0.8rem;
    font-size: 14px;
    text-transform: uppercase;
    /* border-radius: 4px; */
    font-weight: 400;
    display: block;
    width: 38%;
    margin: 5%auto;
    cursor: pointer;
    border: 1px solid rgba(255, 255, 255, 0.2);
    background: transparent;
}

.btnInscrip:hover {
  background-color: rgba(255, 255, 255, 0.12);
}

.cards {
  display: flex;
  flex-wrap: wrap;
  list-style: none;
  margin: 0;
  padding: 0;
}

.cards_item {
  display: flex;
  padding: 1rem;
}

@media (min-width: 40rem) {
  .cards_item {
    width: 50%;
  }
}

@media (min-width: 56rem) {
  .cards_item {
    width: 33.3333%;
  }
}

.card {
    background-color: white;
    border-radius: 0.25rem;
    box-shadow: 0 20px 40px -14px rgba(0, 0, 0, 0.25);
    display: flex;
    flex-direction: column;
    overflow: hidden;
    border-radius: 18px;
}
/*
.card_content {
  padding: 1rem;
 background: linear-gradient(to bottom left, #4cb6ff 40%, #51809f 100%);
}
.card_content:hover { background-position: right center; }*/

.card_content {
    background-image: linear-gradient(to right, #314755 0%, #26a0da 51%, #314755 100%);
    text-transform: uppercase;
    transition: 0.5s;
    background-size: 200% auto;
    display: block;
    height: 100%;
    }
.card_content:hover { background-position: right center; }


.card_title {
  color: #ffffff;
  font-size: 2rem;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: capitalize;
  margin: 0px;
}
.card_image {
  max-height: 200px;
}
.imgCover{
  min-height: 200px;
}
.card_text {
  color: #ffffff;
  font-size: 0.875rem;
  line-height: 1.5;
  margin-bottom: 1.25rem;    
  font-weight: 400;
}
.made_by{
  font-weight: 400;
  font-size: 13px;
  margin-top: 35px;
  text-align: center;
}
.made_by a {
  text-decoration: none;
  color: #5e95ff;
  font-weight: 700;
}
.avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    position: relative;
    background-color: ghostwhite;
    margin-bottom: 15px;
}
.clubInfoContaner{
text-align: center;
text-transform: none;
margin-top: -65px;
}
.typepay{
text-align: center;
width: 214px;
padding-top: 18px;

}
.typepay:hover{
    color: #00ade5;
    background: #f7f8fb;
    border-radius: 20px;
}
.tarifclub{
    margin: 0 auto;
    color: #43a0d6;
    border: 5px #ceced2 solid;
    width: fit-content;
    border-radius: 10px;
    padding: 16px;
}
.dialgIns{
    border-radius: 20px;
}
.inscrip {
    margin-top: 4%;
    padding: 5%;
    border: 2px solid;
    width: fit-content;
    margin: 38px auto;
    border-radius: 14%;
}
.inscrip:hover {
  background: #f7f8fb;
}
.swal2-buttonswrapper {
    display: flex;
    width: fit-content;
    /* text-align: center; */
    margin: 0 auto;
}
.swal2-modal .swal2-styled {
    width: 100%;
}
.centredpop{
  text-align: center
}
</style>
