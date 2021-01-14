<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-12">
        <div class="card card-calendar">
          <h5 style="text-align:center;position: relative;top: 19px;">Les Réservation des Salles</h5>
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
        center
        custom-class="headerspecial"
        top="15%"
        width="30%">
          <div slot="title">           
            <p class="titleRes">Reservation Materiel</p>
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
                <p>{{reservation.leClub.nomClub}} </p>
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
                <p><span><i style="margin-right: 5%;" class="ti-shift-right-alt"></i></span> Saller Demander</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.salle.nomSalle}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-server"></i></span> Capacité</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.salle.capcite}}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6" >
                <p><span><i style="margin-right: 5%;" class="ti-pin2"></i></span> Type de Sallle</p>
            </div>
            <div class="col-sm-6" style="text-align:center">
                <p>{{reservation.salle.salleType.type}}</p>
            </div>
          </div>
          <div class="row" >
            <h5 v-if="!reservation.planif.etatConfirmationSalle && !isExpr(reservation.planif.dateResSalle)" style="color:red;text-align:center">L'événement est éxpiré sans affecter la salle</h5>
           <template v-else>
            <div v-if="!reservation.planif.etatConfirmationSalle" class="col-sm-12 " style="text-align: center;margin-top:2%">
                <el-button type="success"  @click="affecterSalle(reservation.planif,true,reservation.demandeSalle)" icon="el-icon-check" circle></el-button>
                <el-button type="danger" @click="affecterSalle(reservation.planif,false,reservation.demandeSalle)" icon="el-icon-close" circle></el-button>
            </div>
            <div v-else class="col-sm-12 " style="text-align: center;margin-top:2%">
                <el-button v-if="reservation.planif.acceptation"   type="succes" round>Cette Salle est Affecter au Événement</el-button>
                <el-button v-else type="danger" round>Affectation De Salle Réfuser</el-button>
            </div>
           </template>
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
        .get("/DemandeEvent/lessalles")
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
          salle:{
          salleType:{
          id:'',
          type:''
          }
          },
          evenement:'',
          qteinitiale:0,
          className:'',
          demandeSalle:''
        },
        isedit:false,
        test:'',
      }
    },
    methods: {
      checkuser(){
        const user = JSON.parse(window.localStorage.getItem('session'))
        if(user.role!="ResponsableSalles")
        return false
        else 
        return true
      },
      affecterSalle(planif,affect,demandeSalleId){
        var self=this
        planif.etatConfirmationSalle=true
        planif.acceptation=affect
        
      http.put("/DemandeEvent/Salle/affectSalle/"+demandeSalleId,planif)
        .then(response => {
         this.Planifdialogue=false    
          var  eventIndex = this.events.findIndex((obj => obj.Planif.id == planif.id))
          self.events[eventIndex].Planif=planif
          if(affect)
          self.events[eventIndex].className='event-green'
          else
          self.events[eventIndex].className='event-red'
          window.$ = window.jQuery = $
          $('#fullCalendar').fullCalendar( 'removeEvents')
          $('#fullCalendar').fullCalendar( 'addEventSource', self.events);   
          $('#fullCalendar').fullCalendar( 'rerenderEvents' );
          self.reservation.Planif=planif         
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
      isExpr(dateRes){
      let sysDate = moment(new Date()).format("YYYY-MM-DD")
      return sysDate<dateRes
      },
      affectRes(data){
       for(let row of data)
        {
          for(let lesSalles of row.lesSalles )
          {
            for(let planification of lesSalles.lesplanification)
            {   
                var Classname
                if(!planification.etatConfirmationSalle&& !this.isExpr(planification.dateResSalle))
                Classname="event-red"
                else
                if(!planification.etatConfirmationSalle)
                Classname="event-orange"
                else
                if(planification.acceptation)
                Classname="event-green"
                else
                Classname="event-red"
                var eventCalender = {
                id:row.id,
                title:'Reservation de Salle '+lesSalles.salle.nomSalle  + ' pour : ' + row.leClub.nomClub,
                start:new Date(moment(planification.dateResSalle).format('YYYY-MM-DD')+' ' + planification.tempdebRes),
                end:new Date(moment(planification.dateResSalle).format('YYYY-MM-DD')+' ' + planification.tempFinRes ),
                allDay:false,
                className: Classname,
                imageurl:'static/img/ClubLogos/'+row.leClub.clubLogo,
                salle:lesSalles.salle,
                Planif:planification,
                evenement:{
                  id:row.id,
                  nomEvenement:row.nomEvenement
                },
                demandeSalle:lesSalles.id,
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
            left: 'title',
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
           /* swal({
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
            })*/
          },
          editable: self.checkuser(),
          eventLimit: true, // allow "more" link when too many events

          events: self.events,
            eventClick: function(info) {
              if(!self.checkuser())
              return
              self.reservation.dateRes=info.Planif.dateResSalle
              self.reservation.planif=info.Planif
              self.reservation.leClub=info.club
              self.reservation.salle=info.salle
              self.reservation.evenement=info.evenement
              self.reservation.demandeSalle=info.demandeSalle 
              self.className=info.className              
              self.Planifdialogue=true 
            },
            eventMouseover: function(start , end) {
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
