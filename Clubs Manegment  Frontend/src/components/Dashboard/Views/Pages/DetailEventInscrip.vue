<template>
<div v-loading.fullscreen.lock="fullscreenLoading" style="background:aliceblue">
    <full-page v-if="!fullscreenLoading" ref="fullpage" :options="options" id="fullpage"> 
      <section  class="parallax section" style=" background-image: url('static/img/landing.png')">
      <div class='title-Container animate fadeIn'>
      <h1 class="coverTitle">Club {{evenment.demandeEvenement.leClub.nomClub}} </h1>
      <h1 class="coverTitle"> Événement <span style="color:#f82249">{{evenment.demandeEvenement.nomEvenement}}</span></h1><br/>
      <p class="dateEv" v-if="evenment.demandeEvenement.datePrevuEvent==evenment.demandeEvenement.dateFinEvent">
      Le: {{evenment.demandeEvenement.datePrevuEvent}}
      <span v-if="!evenment.demandeEvenement.externe">, Faculté des sciences économiques et de gestion de Sfax</span>
      </p>
      <p class="dateEv" v-else>Du: {{evenment.demandeEvenement.datePrevuEvent}} á {{evenment.demandeEvenement.dateFinEvent}}
      <span v-if="!evenment.demandeEvenement.externe">, Faculté des sciences économiques et de gestion de Sfax</span>
      </p>
      <a v-if="evenment.vedioLink!=null" style="margin:10px auto" class="venobox play-btn mb-4 vbox-item" @click="showVedio=true"></a>
      <a href="#restbody" v-smooth-scroll class="btnDetail">A propos de l'événement</a>
      </div>
      </section>
      <section  class="section"  id="programeEvent" >
      <div v-if="evenment.lesProgrammes.length>0">
       <div style="position:relative;top:10px">
      <h5 class="subTilte">Programme d'événement</h5>
      <h6 class="sousSubTitle">Voici notre calendrier des événements</h6>
      <hr class="underTitle" />
       </div>
      <div class="flex-container">
      <div class="contentProgramme" v-for="prog in evenment.lesProgrammes"   style="margin:10px">
        <div style="text-align:center"  @click="selectedPrograme=prog">
              <h5 class="ProgHeader"><span style="position: relative;top: 7px;">{{getday(prog.dateProgramme)}}</span></h5>
              <div style="font-size:1vw; font-size:23px;position: relative;top: -11px;color: #999494;">
              <h5>{{ prog.dateProgramme| moment("DD") }}</h5>
              <h5 style="font-size:1vw; font-size:18px;">{{ prog.dateProgramme | moment("MM-YYYY") }}</h5>
              </div>
        </div>
      </div>
      </div>
      <div class="row" v-if="selectedPrograme!=null">
        <p v-if="selectedPrograme.lesAction.length==0" style="font-size:3vw;text-align:center">Pas de Programme disponible pour ce jour</p> 
        <div v-else  class="col-sm-8 scrollbar" id="style-1">
        <template v-for="action in selectedPrograme.lesAction">
        <div class="lestache"> 
        <div class="col-md-5">{{format(action.tempDebAct)}} </div>
        <div class="col-md-7">{{action.action}}</div>
        <br/>
        </div>
        </template>
        </div>
      </div>
      <div v-else >
        <h2 v-if="evenment.lesProgrammes[0].lesAction.length==0" style="font-size:3vw;text-align:center" >Pas de Programme disponible pour ce jour</h2> 
        <div v-else  class="col-sm-8 scrollbar" id="style-1">
        <template  v-for="action in evenment.lesProgrammes[0].lesAction">
        <div class="lestache"> 
        <div class="col-md-5">{{format(action.tempDebAct)}} </div>
        <div class="col-md-7">{{action.action}}</div>
        <br/>
        </div>
        </template>
        </div>
      </div>
      </div>
      <div v-else>
         <p style="font-size:3vw;text-align:center;margin-top: 11%;">Pas de Programme  pour ce événement</p> 
      </div>
      </section>
      <section class=" section" >
        <div  >
        <div style=" position: relative; top: 43px;">
        <h5 class="subTilte" >Lieu d'événement</h5>
        <h6 class="sousSubTitle" style="font-weight:600">Informations sur l'emplacement de la salle et la galerie</h6>
        <hr class="underTitle" />
        </div>
        <leslieu v-if="!evenment.demandeEvenement.externe" :lelieu="evenment.demandeEvenement.leslieu" />
        <template v-else>
        <Lieusexterne :eventlieux="evenment.demandeEvenement.leslieu" />
        </template>
        </div>
      </section>
        <section   class="section" >
        <div style="position:relative;top:10px">
        <h5 class="subTilte">les sponsors</h5>
        <h6 class="sousSubTitle">Voici les sponsors de notre événement</h6>
        <hr class="underTitle" />
        </div>
        <template v-if="evenment.lesSponseur.length>0">
            <div class="flex-container">
            <template v-for="sponsor in evenment.lesSponseur ">
              <div class="supporter-logo">
                <a :href="'http://'+sponsor.sponseur.urlSiteSponseur"><img class="img-fluid" :src="'static/img/sponseurlogo/'+sponsor.sponseur.logoSponseur"></a>
              </div>
            </template>
          </div>
        </template>
        <p v-else style="font-size:3vw;text-align:center;margin-top: 11%;">Pas de Sponsors pour ce événement</p> 
        </section>
        <section  class="section">
        <div style="position:relative;top:10px">
        <h5 class="subTilte">Tarification</h5>
        <h6 class="sousSubTitle">Voici les tarifications de notre événement </h6>
        <hr class="underTitle" />
        </div>
        <template v-if=" evenment.lesTarifs.length>0">
             <div class="flex-tarif">
                  <div class="contentTarif" v-for="t in evenment.lesTarifs "   style="margin:40px">
                    <div style="text-align:center">
                      <h5>{{t.tarifation.tarif}}</h5>
                      <div style="margin-top:26%">
                      <a><i class="ti-user iconeTarif"></i></a>
                      </div>
                      <h3 style="margin-top:16%">{{t.montant}} Dt</h3>
                      <h3 v-if="t.nombrePalceDisponible > 0" style="margin-top:5%">{{t.nombrePalceDisponible}} Place</h3>
                    </div>
                  </div>
             </div>
        </template>
                <p v-else style="font-size:3vw;text-align:center;margin-top: 11%;"> L'inscription a ce événement est Gratuite</p>
        <div style="text-align:center;margin-top:3%">
          <el-button type="success" @click="Inscrire">Inscrire a ce événement</el-button>
        </div> 
        </section>
        <section   class="parallax section" style=" background-image: url('static/img/landing-footer.png')">
          <!-- <QrcodeVue ref="test" id="test" :value="" size="300"  level="H"></QrcodeVue>-->
        </section>
    </full-page>
    <el-dialog
      :modalAppendToBody="false"
      :visible.sync="showVedio"
      width="50%">
      <iframe width="100%" height="500" :src="evenment.vedioLink" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </el-dialog>

</div>
</template>
<script>
/* eslint-disable */
import Vue from 'vue'
import http from 'src/components/Dashboard/Views/axiosbase.js'
import swal from 'sweetalert2'
import {Loading } from 'element-ui'
import vueSmoothScroll from 'vue2-smooth-scroll'
import moment from 'moment'
import leslieu from './composentMain/LieuEvent'
import Lieusexterne from './composentMain/Lieusexterne'
import VueFullPage from 'vue-fullpage.js'
import QrcodeVue from 'qrcode.vue';
import jsPDF from 'jspdf';
import html2canvas from "html2canvas"

Vue.use(vueSmoothScroll)
Vue.use(VueFullPage)
Vue.use(QrcodeVue)

//require('vue2-animate/dist/vue2-animate.min.css')
var VueCookie = require('vue-cookie');
Vue.use(VueCookie);
Vue.use(Loading);
Vue.prototype.moment = moment
export default {
components:{
  leslieu,
  Lieusexterne,
  QrcodeVue
},
created()
{  
  let eventId=this.$cookie.get('event')
  if(eventId==null||!eventId>0)
  {
    this.$router.go(-1)
    return
  }
  var self = this;
    let etudiant = JSON.parse(localStorage.getItem('session')).username
    http.get('/etudiant/findbylog/'+etudiant)
    .then(function (response) {
      self.etudiant=response.data
    })
  http.get('event/By/'+eventId)
  .then(function (response) {
  self.fullscreenLoading=false,
  self.evenment = response.data
  self.evenment.lesProgrammes = self.evenment.lesProgrammes.sort(function(a, b) {
    return new Date(a.dateProgramme) - new Date(b.dateProgramme); 
  })
  console.log(self.evenment)
  })
},
data(){
  return {
    fullscreenLoading:true,
    showVedio:false,
    selectedPrograme:null,
    evenment:{
      vedioLink:''
    },
    etudiant:'',
    options: {
     //   sectionsColor: ['#aliceblue', '#aliceblue','#369fab','skyblue','#8bd58e']
      },
    
  }
},
methods:{
getday(date){
  return moment(date).locale("fr").format("dddd");
},
format(time){
let formatted
return  formatted = moment(time, "HH:mm:ss").format("HH:mm");
},
Inscrire(){
  var self=this
  let gen= this.evenment.id.toString() + this.etudiant.id
   for(let i=0;i<4;i++){
   gen += (((1+Math.random())*0x10000)|0).toString(16).substring(1)
   }
  let inscription={
    evenement:this.evenment,
    etudiant:this.etudiant,
    codeInscription:gen
  }

  http.put('/Inscription/eventCheck',inscription)
  .then(response => {
    if(response.data>0){
      swal({
      type: 'success',
      title: 'Succès',
      text: 'Tu as déja Insrit a ce événement',
      })
      this.$router.push("/inscriptions/mesEventInscription")
      return
    }
    else
    {
      http.put('/InscriptionEvenement/Intene',inscription)
      .then(response => {
      swal({
      type: 'success',
      title: 'Succès',
      text: 'Inscription au événement terminer  Avec succès',
      })
      })
    }
})

  
        /*let element = document.getElementById("test")
        var doc = new jsPDF("p","mm","a4");
        doc.setFontSize(20);
      var canvasElement = document.createElement('canvas');
          html2canvas(element, { canvas: canvasElement 
            }).then(function (canvas) {
          const img = canvas.toDataURL("image/png");
          doc.addImage(img,'JPEG',20,20);
          doc.save("sample.pdf");
        });*/

      /* this.fullscreenLoading=true
      setTimeout(()=>{
        self.fullscreenLoading=false
      },500);*/
}
}
}
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');
a{outline : none;}
html {
    overflow-x: hidden;
}
.parallexBack{
  min-height: 400px;
  overflow: hidden;
  height: auto;
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}
.scrollbar{
  text-align:center;
  margin:0 auto;
  float:none;
  max-height: 377px;
  overflow-y: auto;
}
#style-1::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

#style-1::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

#style-1::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}

.coverTitle{
color: #fff;
font-family: "Raleway", sans-serif;
font-size: 56px;
font-weight: 600;
text-transform: uppercase;
}
.title-Container{
text-align:center;
position: relative;
top: 100px;
-webkit-animation: fadein 2s; /* Safari, Chrome and Opera > 12.1 */
-moz-animation: fadein 2s; /* Firefox < 16 */
-ms-animation: fadein 2s; /* Internet Explorer */
  -o-animation: fadein 2s; /* Opera < 12.1 */
    animation: fadein 2s;
}    
.parallax {
  width: 100%;
  background-size: cover;
  overflow: hidden;
  position: relative;
  min-height: 700px; 
  
  /*
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;*/
}

.mainContain{
  margin-top: 1%;
  min-height: 600px;
  height:auto;background-color:none;font-size:36px
}
.dateEv{
 color: #ebebeb;font-weight: 700;font-size: 20px;
 }
 .play-btn {
    width: 94px;
    outline: none;
    height: 94px;
    background: radial-gradient(#f82249 50%, rgba(101, 111, 150, 0.15) 52%);
    border-radius: 50%;
    display: block;
    position: relative;
    top: 15px;
    overflow: hidden;
}

.play-btn:hover { cursor: pointer; animation:click 1s ease-in-out; animation-iteration-count:3;}
@keyframes click {
from { transform:scale(1);}
to { transform:scale(1.1); opacity:0.1; border:0.5px solid white;}
}

.play-btn:before {
    content: '';
    position: absolute;
    width: 120px;
    height: 120px;
    -webkit-animation-delay: 0s;
    animation-delay: 0s;
    -webkit-animation: pulsate-btn 2s;
    animation: pulsate-btn 2s;
    -webkit-animation-direction: forwards;
    animation-direction: forwards;
    -webkit-animation-iteration-count: infinite;
    animation-iteration-count: infinite;
    -webkit-animation-timing-function: steps;
    animation-timing-function: steps;
    opacity: 1;
    border-radius: 50%;
    border: 2px solid rgba(163, 163, 163, 0.4);
    top: -15%;
    left: -15%;
    background: rgba(198, 16, 0, 0);
}
.play-btn::after {
    content: '';
    position: absolute;
    left: 50%;
    top: 50%;
    -webkit-transform: translateX(-40%) translateY(-50%);
    transform: translateX(-40%) translateY(-50%);
    width: 0;
    height: 0;
    border-top: 10px solid transparent;
    border-bottom: 10px solid transparent;
    border-left: 15px solid #fff;
    z-index: 100;
    transition: all 400ms cubic-bezier(0.55, 0.055, 0.675, 0.19);
}
.btnDetail{
outline: none;
text-decoration: none;
background: none;
position: relative;
top: 30px;
color: white;
font-size: 20px;
border: 2px solid #b6102f;
border-radius: 20px;
padding-left: 20px;
padding-right: 20px;
padding-top: 7px;
padding-bottom: 7px;
font-family: "Raleway", sans-serif;
font-weight: 600;
transition: .5s ease;
}
.btnDetail:hover{
background:#b6102f
}
.subTilte{
  font-size: 36px;
  color:#0e1b4d;
  font-family: "Raleway", sans-serif;
  text-transform: uppercase;
  text-align: center;
  font-weight: 700;
  margin-bottom: 10px;
}
.sousSubTitle{
text-align: center;
padding: 0;
margin: 0;
font-size: 18px;
font-weight: 500;
color: #9195a2;
}
.underTitle {
    height: 9px;
    width: 8%;
    background: #f82249;
    border:none
}
.btnProgramme{
outline: none;
text-decoration: none;
background: none;
position: relative;
top: 30px;
color: black;
font-size: 20px;
border: 2px solid #9195a2;
border-radius: 20px;
padding-left: 20px;
padding-right: 20px;
padding-top: 7px;
padding-bottom: 7px;
font-family: "Raleway", sans-serif;
font-weight: 600;
transition: .5s ease;
}
.flex-container {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
  min-height: 200px;
}
.flex-tarif {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
}
.flex-container>div {
  background-color: #f1f1f1;
  color: white;
  width: 150px;
  height: 150px;
}
.contentProgramme {
    font-size: 1vw;
    margin: 0 auto;
    background: #fbfff7;
    border-radius: 26px;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
}
.contentTarif {
    font-size: 1vw;
    margin: 0 auto;
    background: #fbfff7;
    border-radius: 26px;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
    height: 343px;
    width: 279px;
    transition: transform .2s; 

}
.contentTarif:hover{
  transform: scale(1.1);
  background-color:#ebeff3
}
.ProgHeader {
    position: relative;
    top: -10px;
    text-align: center;
    background-color: #f56a6a;
    height: 41px;
    border: none;
    border-radius: 26px 26px 0px 0px;
    color: white;
}
.lestache{
  padding-bottom: 20px;
  padding-top: 20px;
  text-align:center;
  margin:0 auto;
  float:none;
  border-bottom: solid 2px lightgray;
}
.lestache:hover{
background: lightgray;
border:none;
}
.img-fluid {
    max-width: 100%;
    height: auto;
}
.supporter-logo {
    padding: 30px;
    margin: 3%;
    border-radius: 24px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-right: 1px solid #e0e5fa;
    border-bottom: 1px solid #e0e5fa;
    overflow: hidden;
    background: rgba(255, 255, 255, 0.5);
    transition: transform .2s;
}
.supporter-logo:hover{
transform: scale(1.2);
}
.no-gutters {
    margin-right: 0;
    margin-left: 0;
}
.supporters-wrap {
    border-top: 1px solid #e0e5fa;
    border-left: 1px solid #e0e5fa;
    margin-bottom: 30px;
}
.clearfix::after {
    display: block;
    clear: both;
    content: "";
}

body {
    overflow-x: hidden !important;
}
.contentcard {
    border-radius: 12px;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
    background-color: #f9f7f7;
    color: gray;
    margin-bottom: 20px;
    position: relative;
    height: 350px;
    width:250px;
    border: 0 none;
    margin: 0 auto;
    padding: 5%;
}
.iconeTarif {
    font-size: 38px;
    border: solid 2px #bebebe;
    border-radius: 100%;
    padding: 12%;
}
@keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

/* Firefox < 16 */
@-moz-keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

/* Safari, Chrome and Opera > 12.1 */
@-webkit-keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

/* Internet Explorer */
@-ms-keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

/* Opera < 12.1 */
@-o-keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}
</style>
