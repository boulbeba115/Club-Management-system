<template>
<div>
 <el-tabs type="border-card">
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Réunion</span>
    <div class="genetaleInfo">
    <h4 style="text-align:center">Information génerale</h4>
    <hr style="width:50%"/>
    <div >
      <table style=" margin: 0 auto;font-size: larger;">
      <thead>
      </thead>
      <tbody>
        <tr>
          <td class="droit">Réunion :</td>
          <td class="gauche">{{dataRow.titre}}</td>
        </tr>
         <tr>
          <td class="droit">Sujet :</td>
          <td class="gauche">{{dataRow.sujet}}</td>
        </tr>
        <tr>
          <td class="droit">Date Création Réunion :</td>
          <td class="gauche">{{dataRow.dateCreation}}</td>
        </tr>
        <tr>
          <td class="droit">Horaire de Début :</td>
          <td class="gauche">{{dataRow.tempdebReunion}}</td>
        </tr>
        <tr>
          <td class="droit">Horaire de Fin :</td>
          <td class="gauche">{{dataRow.tempfinReunion}}</td>
        </tr>
        <tr>
          <td class="droit">Date de Réunion :</td>
          <td class="gauche">{{dataRow.dateReunion}}</td>
        </tr>
        <tr>
          <td class="droit">Type de Réunion :</td>
          <td v-if="!dataRow.type" class="gauche">Réunion Interne</td>
          <td v-else  class="gauche">Réunion Interne</td>
        </tr>
        <tr>
          <td class="droit">Date de Réunion :</td>
          <td class="gauche">{{dataRow.dateReunion}}</td>
        </tr>
        <tr>
          <td class="droit">Salle :</td>
          <td class="gauche">{{dataRow.salleReunion.nomSalle}}</td>
        </tr>
        <tr>
          <td class="droit">Type Salle :</td>
          <td class="gauche">{{dataRow.salleReunion.salleType.type}}</td>
        </tr>
        <tr>
          <td class="droit">Confirmation de Salle :</td>
          <td v-if="!dataRow.salleReunion.salleConfirmed" class="gauche">
          <a type="button" style="color:red">Pas Encore Confirmé</a></td>
          <td v-else class="gauche">  <a style="color:green">Salle Confirmé</a></td>
        </tr>
      </tbody>
    </table>
    </div>
    </div>
    </el-tab-pane>
    <el-tab-pane >
    <span slot="label"><i class="fa fa-users"></i> Les Etudiants Participants </span>
      <h4 style="text-align:center">Les Membres Participants</h4>
      <hr style="width:50%"/>
          <div class='row'>
            <div class="col-sm-3 pull-right" style="margin-bottom:2%">
              <el-input
                v-model="search"
                placeholder="Recherche Par nom"/>
            </div>
          </div>
          <el-table
          label="participantEtudiant"
          class="table-striped"
          border
          max-height="550px"
          style="width: 100%"
          :data="dataRow.lesParticipantsetudiants.filter(data => !search || data.participantEtudiant.nom.toLowerCase().includes(search.toLowerCase()))"
          >
          <el-table-column
            header-align="center"
            align="center"
            label="Nom"
            prop="participantEtudiant.nom"
            min-width="150px">
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            min-width="150px"
            label="Prénom"
            prop="participantEtudiant.prenom">
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            min-width="250px"
            label="Email"
            prop="participantEtudiant.email">
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            min-width="150px"
            label="Num Teléphone"
            prop="participantEtudiant.tel">
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            min-width="150px"
            label="Role">
            <template slot-scope="props">
              <template v-for="theclub in  props.row.participantEtudiant.lesClub ">
                <template v-if="dataRow.leClub.id == theclub.club.id ">
                  {{theclub.roleEtudiant}}
                </template>
              </template>
            </template>
          </el-table-column>
           <el-table-column
            min-width="100px"
            header-align="center"
            align="center"
            label="Abcence">
            <template slot-scope="props">
               <a v-if="startReuion(dataRow.dateReunion,dataRow.tempfinReunion)" type="button" disabled class="btn btn-fill btn-xs btn-warning">Pas Etablie</a>
                <template v-else>
                  <template v-if="props.row.absent==null">
                      <el-button type="success" icon="el-icon-check" circle @click="confirmPresenceMembre(props.row,true)"></el-button>
                      <el-button type="danger" icon="el-icon-close" circle @click="confirmPresenceMembre(props.row,false)"></el-button>
                  </template>
                <a type="button" v-else-if="props.row.absent" class="btn btn-fill btn-xs btn-success"  @click="confirmPresenceMembre(props.row,!props.row.absent)">Présent</a>
                <a type="button" v-else class="btn btn-fill btn-xs btn-danger" @click="confirmPresenceMembre(props.row,!props.row.absent)">Absent</a>
                </template>
            </template>
          </el-table-column>

          
        </el-table>
    </el-tab-pane>
    <el-tab-pane >
    <span slot="label"><i class="fa fa-user"></i> Les Membres Administration</span>
        <h4 style="text-align:center">Les Membres Administration Participants</h4>
          <hr style="width:50%"/>
              <div class='row'>
                <div class="col-sm-3 pull-right" style="margin-bottom:2%">
                  <el-input
                    v-model="searchAdmins"
                    placeholder="Recherche Par nom"/>
                </div>
              </div>
              <el-table
              label="Les Membres Administration"
              class="table-striped"
              border
              max-height="550px"
              style="width: 100%"
              :data="dataRow.lesParticipantsAdmins.filter(data => !searchAdmins || data.participantAdministration.nom.toLowerCase().includes(searchAdmins.toLowerCase()))"
              >
              <el-table-column
                header-align="center"
                align="center"
                label="Nom"
                prop="participantAdministration.nom"
                min-width="150px">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="150px"
                label="Prénom"
                prop="participantAdministration.prenom">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="250px"
                label="Email"
                prop="participantAdministration.email">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="150px"
                label="Num Teléphone"
                prop="participantAdministration.tel">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="150px"
                label="Role"
                prop="participantAdministration.role">
              </el-table-column>
              <el-table-column
                min-width="100px"
                header-align="center"
                align="center"
                label="Abcence">
                <template slot-scope="props">
                  <a v-if="startReuion(dataRow.dateReunion,dataRow.tempfinReunion)" type="button" disabled class="  btn btn-fill btn-xs btn-warning">Pas Etablie</a>
                    <template v-else>
                      <template v-if="props.row.absent==null">
                          <el-button type="success" icon="el-icon-check" circle @click="confirmAdministration(props.row,true)"></el-button>
                          <el-button type="danger" icon="el-icon-close" circle @click="confirmAdministration(props.row,false)"></el-button>
                      </template>
                    <a type="button" v-else-if="props.row.absent" class="btn btn-fill btn-xs btn-success"  @click="confirmAdministration(props.row,!props.row.absent)">Présent</a>
                    <a type="button" v-else class="btn btn-fill btn-xs btn-danger" @click="confirmAdministration(props.row,!props.row.absent)">Absent</a>
                    </template>
                </template>
              </el-table-column>
            </el-table>

    </el-tab-pane>
    <el-tab-pane v-if="dataRow.type">
    <span slot="label"><i class="fa fa-user-secret"></i> Les Inviter</span>
     <h4 style="text-align:center">Les Inviter Participants</h4>
          <hr style="width:50%"/>
              <div class='row'>
                <div class="col-sm-3 pull-right" style="margin-bottom:2%">
                  <el-input
                    v-model="searchInvited"
                    placeholder="Recherche Par nom"/>
                </div>
              </div>
              <el-table
              label="Les Membres Administration"
              class="table-striped"
              border
              max-height="550px"
              style="width: 100%"
              :data="dataRow.lesInviter.filter(data => !searchInvited || data.inviter.nom.toLowerCase().includes(searchInvited.toLowerCase()))"
              >
              <el-table-column
                header-align="center"
                align="center"
                label="Nom"
                prop="inviter.nom"
                min-width="150px">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="150px"
                label="Prénom"
                prop="inviter.prenom">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="250px"
                label="Email"
                prop="inviter.email">
              </el-table-column>
              <el-table-column
                header-align="center"
                align="center"
                min-width="150px"
                label="Num Teléphone"
                prop="inviter.tel">
              </el-table-column>
              <el-table-column
                min-width="100px"
                header-align="center"
                align="center"
                label="Abcence">
                <template slot-scope="props">
                  <a v-if="startReuion(dataRow.dateReunion,dataRow.tempfinReunion)" type="button" disabled class="  btn btn-fill btn-xs btn-warning">Pas Etablie</a>
                    <template v-else>
                      <template v-if="props.row.absent==null">
                          <el-button type="success" icon="el-icon-check" circle @click="confirmInvited(props.row,true)"></el-button>
                          <el-button type="danger" icon="el-icon-close" circle @click="confirmInvited(props.row,false)"></el-button>
                      </template>
                    <a type="button" v-else-if="props.row.absent" class="btn btn-fill btn-xs btn-success"  @click="confirmInvited(props.row,!props.row.absent)">Présent</a>
                    <a type="button" v-else class="btn btn-fill btn-xs btn-danger" @click="confirmInvited(props.row,!props.row.absent)">Absent</a>
                    </template>
                </template>
              </el-table-column>
            </el-table>
    </el-tab-pane>
</el-tabs>
</div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import { Button , Row , Col,Table, TableColumn , Tabs, TabPane } from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  import * as moment from 'moment';
  import 'moment/locale/fr';
  moment.locale('fr');

  Vue.use(Button)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Tabs)
  Vue.use(TabPane)
  export default {
    props: ['props'],
    created() {
      var self = this
      if(this.props==null)
        {
        this.handleBack()
        return
        }
        this.dataRow = this.props.row   
         http.get('/etudiants/byReunion/'+this.dataRow.id)
        .then(function (response) {
        self.lesEtudiantPart = response.data;
        })
        
    },
    data () {
      return {
      dataRow:{
        titre:'',
        sujet:'',
        dateCreation:'',
        dateReunion:'',
        tempdebReunion:'',
        tempfinReunion:'',
        leClub:'',
        type:'',
        salleReunion:'',
        lesInviter:'',
        lesParticipants:''
      },
      lesEtudiantPart:[],
      search:'',
      searchAdmins:'',
      searchInvited:'',
      fromRoute: null,
      }
    },
    computed: {
     
    },
    components: {
    },
    beforeRouteEnter (to, from, next) {
      next(vm => {
        vm.fromRoute = from;
      })
    },
    methods: {
      handleBack (fallback) {
      this.$router.back();
      },
      startReuion(dateR,temFin){
      return new Date(moment(dateR).format('YYYY-MM-DD')+' ' + temFin) > new Date(moment(new Date()).format('YYYY-MM-DD'))
      },
      confirmPresenceMembre(Part,confirmation){
        var Particpant = Part
        Particpant.absent=confirmation
          http.put("/reunion/presentMember/"+this.dataRow.id , Particpant)
          .then(response => {
          }) 
          .catch(e => {
          swal('error', 'Une erreur est Produit','error')          
          })
      },
      confirmAdministration(Part,confirmation){
        var Particpant = Part
        Particpant.absent=confirmation
          http.put("/reunion/administration/"+this.dataRow.id , Particpant)
          .then(response => {
          }) 
          .catch(e => {
          swal('error', 'Une erreur est Produit','error')          
          })
      },
      confirmInvited(Part,confirmation){
        var Particpant = Part
        Particpant.absent=confirmation
          http.put("/reunion/Inviter/"+this.dataRow.id , Particpant)
          .then(response => {
          }) 
          .catch(e => {
          swal('error', 'Une erreur est Produit','error')          
          })
      }

    }
  }
</script>
<style >
.el-tabs__content {
    /*max-height: 600px;*/
    max-height: 550px;
    overflow-y: auto;
}
.ql-align-center {
    text-align: center;
    text-align: center
}
td.droit {
    text-align: left;
    float: left;
}
td.gauche {
    text-align: left;
    position: relative;
    left: 15%;
}
.genetaleInfo {
    border: 3px lightgrey solid;
    border-radius: 30px;
    margin: 0 auto;
    max-width: 531px;
    padding-bottom: 4%;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
}
</style>
