<template>
<div>
 <el-tabs type="border-card">
   <template v-if="!dataRow.externe">
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Lieu Dévenement</span>
    <template v-for="lieu in dataRow.leslieu">
      <div class="row">
        <div class="col-sm-6">
          <div class="cont">
            <div class="row">
              <div class="col-sm-3" >
                <h5>Lieu :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.site}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Ville :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.ville.ville}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
               <h5> Adresse :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.adresse}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Code Postale</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.codePostal}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Tel :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.tel}}</h5>
              </div>
            </div> 
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Fax : </h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.fax}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Email:</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.emailContact}}</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <img src="static\img\fsegs.jpg">
        </div>
      </div>
      
    </template>
    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> Salles a Réserver</span>
    <el-table
        :data="dataRow.lesSalles"
        style="width: 100%"
        min-height="250"
        align="center"
        header-align="center"
        :default-sort = "{prop: 'salle.salleType.type'}"
        empty-text="Pas De Resultat">
        <el-table-column type="expand">
        <template slot-scope="scope">
            <ul style="text-align:center"  v-for="p in scope.row.lesplanification">
            <li Style=" list-style-type: none;">
            <div class="row">
            <div class="col-sm-3">Le : {{ p.dateResSalle }}</div>
            <div class="col-sm-3">De : {{p.tempdebRes}}</div>
            <div class="col-sm-3">à : {{p.tempFinRes}}</div>
            <div class="col-sm-3">
                <template v-if="p.etatConfirmationSalle==false">
                <a type="button" class="  btn btn-fill btn-xs btn-warning">En Attente</a>
                </template>
                <template v-else >
                <template v-if="p.acceptation">
                <a type="button" class="  btn btn-fill btn-xs btn-success">Accepter</a>
                </template>
                <template v-else>
                <a type="button" class=" btn  btn-fill btn-xs  btn-danger">Refusé</a>
                </template>
                </template>              
              </div>
            </div>
            </li>
            </ul>
        </template>
        </el-table-column>
        <el-table-column
        align="center"
        header-align="center"
        property="salle.nomSalle"
        label="Nom de Salle"
        min-width="250"
        sortable>
        </el-table-column>
        <el-table-column
        align="center"
        header-align="center"
        property="salle.capcite"
        label="Capcité"
        sortable
        min-width="250"
        >
        </el-table-column>
        <el-table-column
        align="center"
        header-align="center"
        property="salle.salleType.type"
        label="Type"
        sortable
        min-width="250"
        >
        </el-table-column>
        <el-table-column
        align="center"
        header-align="center"
        label="Retro Projecteur"
        min-width="250">
        <template slot-scope="scope">
          <template v-if="scope.row.salle.retroproj">
            Équipé
          </template>
          <template v-else>
            Non Équipé
          </template>
        </template>
        </el-table-column>
    </el-table>


    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> Matériel a Réserver</span>
      <el-table
          :data="dataRow.lesMaterielAffecter"
          style="width: 100%"
          height="250"
          align="center"
          header-align="center"
          :default-sort = "{prop: 'salle.salleType.type'}"
          empty-text="Pas De Resultat">
          <el-table-column type="expand">
          <template slot-scope="scope">
              <ul style="text-align:center"  v-for="p in scope.row.lesplanificationMateriel">
              <li Style=" list-style-type: none;">
              <div class="row">
              <div class="col-sm-2">Le : {{ p.dateResSalle }}</div>
              <div class="col-sm-2">De : {{p.tempdebRes}}</div>
              <div class="col-sm-2">à : {{p.tempFinRes}}</div>
              <div class="col-sm-3">Quantité Demander: {{p.qteDemander}}</div>
              <div class="col-sm-3">Quantité Affecter: {{p.qteAffecter}}</div>

              </div>
              </li>
              </ul>
          </template>
          </el-table-column>
          <el-table-column
          align="center"
          header-align="center"
          property="matFaculter.nomMateriel"
          label="Nom de Materiel"
          min-width="250"
          sortable>
          </el-table-column>
          <el-table-column
          align="center"
          header-align="center"
          property="matFaculter.qteTotale"
          label="Quantité Totale"
          sortable
          min-width="250"
          >
          </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> Plus D'information</span>
         <div v-html="dataRow.descriptionEvenement"></div>
    </el-tab-pane>
   </template>
   <template v-else-if="dataRow.typeEvent.typeEvenement=='voyage'">
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Lieu A visité </span>
    <el-table
          :data="dataRow.leslieu"
          style="width: 100%"
          height="250"
          align="center"
          header-align="center"
          :default-sort = "{prop: 'salle.salleType.type'}"
          empty-text="Pas De Resultat">
          <el-table-column type="expand">
          <template slot-scope="scope">
          </template>
          </el-table-column>
          <el-table-column
          align="center"
          header-align="center"
          property="site"
          label="Site a visiter"
          min-width="250"
          sortable>
          </el-table-column>
          <el-table-column
          align="center"
          header-align="center"
          property="adresse"
          label="Adresse"
          sortable
          min-width="250"
          >
          </el-table-column>
          <el-table-column
          align="center"
          header-align="center"
          property="ville.ville"
          label="Ville"
          sortable
          min-width="250"
          >
          </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> Plus D'information</span>
         <div style="text-algin:center" v-html="dataRow.descriptionEvenement"></div>
    </el-tab-pane>
   </template>
   <template v-else>
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Lieu Dévenement</span>
      <template v-for="lieu in dataRow.leslieu">
      <div class="row">
        <div class="col-sm-6">
          <div class="cont">
            <div class="row">
              <div class="col-sm-3" >
                <h5>Lieu :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.site}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Ville :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.ville.ville}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
               <h5> Adresse :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.adresse}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Code Postale</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.codePostal}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Tel :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.tel}}</h5>
              </div>
            </div> 
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Fax : </h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.fax}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Email:</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.emailContact}}</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <img src="static\img\fsegs.jpg">
        </div>
      </div>
      </template>
    </el-tab-pane>
  
    <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> Plus D'information</span>
    <div v-html="dataRow.descriptionEvenement"></div>
    </el-tab-pane>
   </template>

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
        if (this.props) {
        this.dataRow = this.props.row   
        }
        if(this.dataRow==null||this.dataRow.length==0||this.dataRow==undefined)
        this.handleBack(this.fromRoute)
    },
    data () {
      return {
       dataRow:[],
      fromRoute: null
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
</style>
