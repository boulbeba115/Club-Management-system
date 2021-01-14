<template>
<div>
    <div class="card row" style="min-height: 600px;border-raduis:5px">
        <div class="main" style="margin-bottom:5%">
            <h1>Les événement Disponible du clubs</h1>
            <ul class="cards" >
                <li class="cards_item container" v-for="(event,index) in lesEvent">
                    <div class="container">
                    <template v-if="event.lesDemandeAffiche.length>0">
                    <img class='affiche_img' :src='"static/img/LesDemandeAffiche/"+getAffiche(event.lesDemandeAffiche)'>
                    <div :class="getClass(index)">
                        <div class="clublogo">
                         <img class="avatar border-white"  :src='"static/img/ClubLogos/"+event.demandeEvenement.leClub.clubLogo' alt="...">
                        </div>
                        <div class="eventContent">
                          <div style=" height: 211px;">
                            <h3 class="titles">Club {{event.demandeEvenement.leClub.nomClub}}</h3>
                            <h2 class="event">{{event.demandeEvenement.nomEvenement}}</h2>
                            <h1 class="titles" v-if="event.demandeEvenement.datePrevuEvent==event.demandeEvenement.dateFinEvent">Le: {{event.demandeEvenement.datePrevuEvent}}</h1>
                            <h1 class="titles" v-else>Du: {{event.demandeEvenement.datePrevuEvent}} á {{event.demandeEvenement.dateFinEvent}}</h1>
                            </div>
                            <div style="text-align: center;">
                          <!--  <router-link :to="{ name: 'Événement Détail', params: { props} }">-->
                             <button class="mybtn  btn"  @click="goToDetail(event)">Plus de Détail</button>
                            </div>
                        </div>
                    </div>
                    </template>
                      <div v-else class="noImgCover">
                          <div class="clublogo">
                          <img class="avatar border-white"  :src='"static/img/ClubLogos/"+event.demandeEvenement.leClub.clubLogo' alt="...">
                          </div>
                          <div class="eventContent">
                              <h3 class="titles">Club {{event.demandeEvenement.leClub.nomClub}}</h3>
                              <h2 class="event">{{event.demandeEvenement.nomEvenement}}</h2>
                              <h1 class="titles" v-if="event.demandeEvenement.datePrevuEvent==event.demandeEvenement.dateFinEvent">Le: {{event.demandeEvenement.datePrevuEvent}}</h1>
                              <h1 class="titles" v-else>Du: {{event.demandeEvenement.datePrevuEvent}} á {{event.demandeEvenement.dateFinEvent}}</h1>
                              <div style="margin-top:5%;text-align: center;">
                             <button class="mybtn  btn"  @click="goToDetail(event)">Plus de Détail</button>
                              </div>
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
var VueCookie = require('vue-cookie');
Vue.use(VueCookie);
Vue.use(Checkbox)
Vue.use(Popover)
Vue.use(VueCookie);

export default {
data (){
    return {
    showterms:false,
    typePayment:false,
     lesEvent:[],
     selectedClub:'',
     yeartarif:{},
     checkTerms:''

    }
},
created(){
    var self = this;
    http.get('/event/NotExp/All/list')
    .then(function (response) {
    self.lesEvent = response.data
    self.lesEvent.sort(function(a, b) {
    return b.demandeEvenement.datePrevuEvent - a.demandeEvenement.datePrevuEvent
    })
    })
},
methods:{
  goToDetail(event){
    this.$cookie.delete('event');
    this.$cookie.set('event', event.id, 1)
    this.$router.push('/evnetInscripDetail')
  },
  getClass(index){
    if(index%2)
    return "overlay-one"
    return "overlay-two"    
  },
  getAffiche(lesaffiches){
    let validaff = []
    for(let affiche of lesaffiches)
    {
      if(affiche.etat)
      validaff.push(affiche)
    }

   if(validaff.length==0)
   return ''
   return validaff[0].demandeImage
  },
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
                text: 'Tarifation Modifer Avec succès',
                })
              })
          })
 }
}
}
</script>

<style >
@import url('https://fonts.googleapis.com/css?family=Quicksand:400,700');
@import url('https://fonts.googleapis.com/css?family=Nunito&display=swap');
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
    background: none;
    border: white 4px solid;
    padding: 1%;
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
.affiche_img{
    width: 476px;
    height: 480px;
    border-radius: 20px;
}
.overlay-one {
  position: absolute;
  bottom: 0;
  left: 100%;
  right: 0;
  opacity: 0;
  background-color: #008CBA;
  overflow: hidden;
  width: 0;
  height: 100%;
  transition: .3s ease;
  border-radius: 20px;

}
.overlay-two {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 100%;
  opacity: 0;
  background-color: #008CBA;
  overflow: hidden;
  width: 0;
  height: 100%;
  transition: .3s ease;
  border-radius: 20px;
}
.noImgCover{
  position: absolute;
  background-image: linear-gradient(to right, #314755 0%, #26a0da 51%, #314755 100%);
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  overflow: hidden;
  height: 100%;
  width: 100%;
  left:0;
  border-radius: 20px;
}
.noImgCover:hover { background-position: right center; }

.container {
  position: relative;
}

.container:hover .overlay-one {
  width: 100%;
  left: 0;
  opacity: 1;
}
.container:hover .overlay-two {
  width: 100%;
  opacity: 1;
}
.clublogo{
    width: 25%;
    margin: 8% auto;
}
.titles{
color:white;
text-align: center;
font-weight: 900;
font-family: 'Nunito';
}
.event{
font-size: 40px;
font-weight: 900;
color: #F9AF4A;
text-align: center;
}
.mybtn{
margin: 3% auto;
font-size: 21px;
color: white;
border: white 0.5px solid;
width: 172px;
}
.mybtn:hover {
  background-color: rgba(255, 255, 255, 0.12);
}

</style>
