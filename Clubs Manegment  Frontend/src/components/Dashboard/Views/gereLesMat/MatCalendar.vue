<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-12">
        <div class="card card-calendar">
          <div class="card-content">
            <div id="fullCalendar"></div>
          </div>
        </div>
      </div>
    </div>
     <el-dialog
        title=""
        :modalAppendToBody="false"
        :visible.sync="Planifdialogue"
        :before-close="closeDialog"
        center
        custom-class="headerspecial"
        top="15%"
        width="30%">
          <div slot="title">           
            <p class="titleRes">Réservation du Materiel</p>
            <div class="row" style="text-align:center">
            <div class="col-sm-12">
            <p> <span><i style="" class="ti-calendar"></i> </span> {{reservation.planif.dateResSalle}} &nbsp
            <span><i style="" class="ti-time"></i></span> {{reservation.planif.tempdebRes}} - {{reservation.planif.tempFinRes}} </p>
            </div>
            <el-button v-if="reservation.qteinitiale < 1" disabled type="primary" class="btnedit" icon="el-icon-edit" circle></el-button>
            <el-button v-else  type="primary" class="btnedit" icon="el-icon-edit" circle></el-button>
            </div>
        </div>
        <div class="row" style="margin-top: 5%;">
        <div class="col-sm-12">
          <div class="row">
            <div class="col-sm-6"  >
                <p><span><i style="margin-right: 5%;" class="ti-microsoft-alt"></i></span> Club :</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.leClub.nomClub}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-clipboard"></i></span> Evenement :</p>
            </div>
            <div class="col-sm-6" style="text-align:center" >
                <p>{{reservation.evenement.nomEvenement}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-harddrives"></i></span> Materiel Demander</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.materiel.nomMateriel}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-bar-chart"></i></span> Quantité Disponible</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{qteDisponible}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-bar-chart-alt"></i></span> Quantité Demander</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.planif.qteDemander}}</p>
            </div>
          </div>
          <div class="row">
             <h5 v-if="reservation.qteinitiale<=0 && !isExpr(reservation.planif.dateResSalle)" style="color:red;text-align:center">L'événement est éxpiré</h5>
            <template v-else>
            <div class="col-sm-6" style="margin-top:2%">
                <p><span><i style="margin-right: 5%;" class="ti-share"></i></span> Quantité Affecter</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
            <template v-if="reservation.qteinitiale < 1">
              <div class="form-group">
              <el-input-number @change="affecterqte" v-model="reservation.planif.qteAffecter"  :min="0" :max='reservation.planif.qteDemander'></el-input-number>
              </div>
            </template>
            <template v-else>
              <p style="color:green;margin-top: 4%;">{{reservation.planif.qteAffecter}}</p> 
            </template>
            </div>
            </template>
          </div>
          <div class="row" v-if="isedit">
            <div class="col-sm-12 " style="text-align: center;margin-top:2%">
                <el-button type="success"  @click="affecterDemande(reservation.planif , reservation.demandeMat)" icon="el-icon-check" circle></el-button>
                <el-button type="danger" @click="cancel()" icon="el-icon-close" circle></el-button>
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
  import swal from 'sweetalert2'
  import $ from 'jquery'
  import 'fullcalendar/dist/locale/fr'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  import {InputNumber} from 'element-ui'
  import moment from 'moment'
  Vue.prototype.moment = moment
  Vue.use(InputNumber)
  var today = new Date()
  var y = today.getFullYear()
  var m = today.getMonth()
  var d = today.getDate()
  export default {
      created(){
        var self = this
        http
        .get("/DemandeEvent/leMateriel")
        .then(response => {
        self.affectRes(response.data) 
        })
        .catch(e => {
        console.log(e);
        });
      },
      data () {
      return {
        events: [],
        Planifdialogue:false,
        reservation:{
          planif:'',
          leClub:'',
          materiel:'',
          evenement:'',
          qteinitiale:0,
          className:'',
          demandeMat:''
        },
        qteDisponible:'',
        isedit:false,
        test:'',
      }
    },
    methods: {
      getQteDisp(mat , planif) {
        if(mat==null||planif.dateResSalle==null)
        return
        var self=this
        http.post('MaterielFaculter/QteDisp/'+planif.dateResSalle,mat)
        .then(function (response) {
          self.qteDisponible = mat.qteTotale - response.data
        })        
      },
      affecterDemande(planif,demandeMatId){
        var self = this
      http.put("/DemandeEvent/Materiel/affectMat/"+demandeMatId , planif)
        .then(response => {
          var leevent
         self.Planifdialogue=false    
         self.isedit=false
          var  eventIndex = this.events.findIndex((obj => obj.Planif.id == planif.id))
          leevent = self.events[eventIndex]
          self.events.splice(eventIndex, 1) 
          leevent.Planif=planif
          leevent.qteinitiale=planif.qteAffecter
          leevent.className='event-green'
          self.events.splice(eventIndex, 0, leevent);
          self.reservation.Planif=planif
          self.reservation.qteinitiale=planif.qteAffecter
          window.$ = window.jQuery = $
          $('#fullCalendar').fullCalendar( 'removeEvents')
          $('#fullCalendar').fullCalendar( 'addEventSource', self.events);   
          $('#fullCalendar').fullCalendar( 'rerenderEvents' );
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Quantité Affecter avec succès',
          })
        })
        .catch(e => {
          this.ajoutMat=false
          swal({
            type: 'error',
            title: 'Une erreur est Produit'})          
          console.log(e);
        })
      },
      cancel(){
        this.isedit=false,
        this.reservation.planif.qteAffecter=0,
        this.Planifdialogue=false   
      },
      affecterqte(){
        this.isedit=true
      },
      closeDialog(){
        this.isedit=false
        this.Planifdialogue=false
      },
      isExpr(dateRes){
      let sysDate = moment(new Date()).format("YYYY-MM-DD")
      return sysDate<dateRes
      },
      affectRes(data){
       for(let row of data)
        {
          for(let lesMaterielAffecter of row.lesMaterielAffecter )
          {
            for(let planification of lesMaterielAffecter.lesplanificationMateriel)
            {   
              var Classname
              if(planification.qteAffecter<=0 && !this.isExpr(planification.dateResSalle))
              Classname="event-red"
              else
              if(planification.qteAffecter>0)
              Classname="event-green"
              else
              Classname="event-orange"
                var eventCalender = {
                id:row.id,
                title:'Reservation de '+lesMaterielAffecter.matFaculter.nomMateriel + ' pour : ' + row.leClub.nomClub,
                start:new Date(moment(planification.dateResSalle).format('YYYY-MM-DD')+' ' + planification.tempdebRes),
                end:new Date(moment(planification.dateResSalle).format('YYYY-MM-DD')+' ' + planification.tempFinRes ),
                allDay:false,
                className: Classname,
                imageurl:'static/img/ClubLogos/'+row.leClub.clubLogo,
                materiel:lesMaterielAffecter.matFaculter,
                Planif:planification,
                evenement:{
                  id:row.id,
                  nomEvenement:row.nomEvenement
                },
                demandeMat:lesMaterielAffecter.id,
                club:row.leClub,
                qteinitiale:planification.qteAffecter
                }
                this.events.push(eventCalender)
                //console.log(new Date(moment(planification.dateResSalle).format('YYYY-MM-DD')+' ' + planification.tempFinRes ))

            }
        }
        }
    
      window.$ = window.jQuery = $
      this.initCalendar($)
       
      
      },
      initCalendar ($) {
        var self = this
        var $calendar = $('#fullCalendar')
        $calendar.fullCalendar({
          header: {
            left: 'Les Reservation de Materiel',
            center: 'month,agendaWeek,agendaDay,agendaWeek',
            right: 'prev,next,today'
          },
          defaultDate: today,
          selectable: true,
          selectHelper: true,
          views: {
            month: { // name of view
              titleFormat: 'MMMM YYYY'
              // other view-specific options here
            },
            week: {
              titleFormat: ' MMMM D YYYY'
            },
            day: {
              titleFormat: 'D MMM, YYYY'
            }
          },
          select: function (start, end) {
            // on select we show the Sweet Alert modal with an input
            swal({
              title: 'Create an Event',
              html: '<div class="form-group">' +
              '<input class="form-control" placeholder="Event Title" id="input-field">' +
              '</div>',
              showCancelButton: true,
              confirmButtonClass: 'btn btn-success',
              cancelButtonClass: 'btn btn-danger',
              buttonsStyling: false
            }).then(function (result) {
              var eventData
              var eventTitle = $('#input-field').val()
              if (eventTitle) {
                eventData = {
                  title: eventTitle,
                  start: start,
                  end: end
                }
                $calendar.fullCalendar('renderEvent', eventData, true) // stick? = true
              }
              $calendar.fullCalendar('unselect')
            })
          },
          editable: true,
          eventLimit: true, // allow "more" link when too many events

          events: self.events,
            eventClick: function(info) {
              self.reservation.dateRes=info.Planif.dateResSalle
              self.reservation.planif=info.Planif
              self.reservation.leClub=info.club
              self.reservation.materiel=info.materiel
              self.reservation.evenement=info.evenement 
              self.reservation.qteinitiale=info.Planif.qteAffecter 
              self.reservation.demandeMat=info.demandeMat
              self.className=info.className       
              self.Planifdialogue=true
              self.getQteDisp(info.materiel,info.Planif)
            },
            eventRender: function hello () {
              console.log("event")
            //$('#fullCalendar').fullCalendar('updateEvent', event.Classname);

            },
            eventRender: function(event, eventElement) {
            if (event.imageurl) {
                    eventElement.find("div.fc-content").prepend("<img src='" + event.imageurl +"' width='30' height='30' style ='float:right;    margin-top: 5%;'>");
                }
            },
  
        })
      }
    },
    mounted () {

    }
  }
</script>
<style>
  #fullCalendar {
    min-height: 300px;
  }

  .el-loading-spinner .path {
    stroke: #66615B !important;
  }
  .fc-month-view span.fc-title{
         white-space: normal;
   }
   .headerspecial .el-dialog__header {
    background: #a7acf3;
    padding: 20px 20px 10px;
    color: white !important;
    height: 94px;
   }
   .headerspecial .el-dialog__header .el-dialog__title {
    line-height: 24px;
    font-size: 18px;
    color: #ffffff;
   }
   .headerspecial .el-dialog__header .el-dialog__headerbtn .el-dialog__close {
      color: #ffffff;
  }
  .el-dialog--center .el-dialog__body {
    text-align: initial;
    padding: 0px 25px 30px;
  }
  .btnedit {
    position: relative;
    left: 196px;
    top: -12px;
    background-color: #8765a9;
    }
    .btnedit:hover {
    background-color: #93a4e3;
    }
.fc-event.event-green {
    background-color: rgba(17, 177, 88, 0.45);
}
.fc-event.event-orange {
    background-color: rgba(249, 193, 73, 0.5);
}
.fc-event.event-red {
    background-color: rgba(255, 42, 71, 0.42);
}
.fc-event.event-blue {
    background-color: rgba(65, 139, 231, 0.45);
}
</style>
