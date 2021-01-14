<template>
<div class="col-md-12 card" style="min-height: 585px;">
 <h4 style="text-align:center;color:#a7abb4">Liste des Sponsors du Club</h4>
 <el-tabs type="border-card" tab-click style="max-height:590px;" @tab-click="changeTab">
  <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Les sponsors du club</span>
    <el-table class="table-striped"
                    :data="queriedData"
                    border
                    empty-text="Pas De Donnais"
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label"
                             sortable
                             align="center"
                             header-align="center">
            </el-table-column>
             <el-table-column
              label="Le Logo"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
              <a ><i class="ti-image btn-Affiche" @click="showLogo(props.row) "></i></a>
              </template>
             </el-table-column>
             <el-table-column
              label="Autorisation"
              align="center"
              minWidth="150px"
              header-align="center">
              <template slot-scope="props">
                    <template v-if="props.row.active">
                      <a type="button" class="  btn btn-fill   btn-xs btn-success">Autoriser</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-xs  btn-danger">Non Autoriser</a>
                    </template>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="Actions"
              align="center"
              minWidth="150px"
              header-align="center">
              <template slot-scope="props">
                <a v-if="props.row.active" class="btn btn-simple btn-xs btn-warning btn-icon edit" @click="handleEdit(props.$index, props.row)"><i class="ti-pencil-alt"></i></a>
                <a disabled v-else type="button"  class=" btn  btn-fill  btn-xs  btn-danger">Interdit</a>
              </template>
            </el-table-column>
          </el-table>
          <div class="col-sm-12">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
  </el-tab-pane>
    <el-tab-pane >
        <span slot="label"><i class="el-icon-date"></i> Tous les Sponsors</span>
        <el-table class="table-striped"
                    :data="queriedData"
                    border
                    empty-text="Pas De Donnais"
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label"
                             sortable
                             align="center"
                             header-align="center">
            </el-table-column>
             <el-table-column
              label="Le Logo"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
              <a ><i class="ti-image btn-Affiche" @click="showLogo(props.row) "></i></a>
              </template>
             </el-table-column>
             <el-table-column
              label="Autorisation"
              align="center"
              minWidth="150px"
              header-align="center">
              <template slot-scope="props">
                    <template v-if="props.row.active">
                      <a type="button" class="  btn btn-fill   btn-xs btn-success">Autoriser</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-xs  btn-danger">Non Autoriser</a>
                    </template>
              </template>
            </el-table-column>
          </el-table>
          <div class="col-sm-12">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
  </el-tab-pane>
  </el-tabs >
  <el-dialog
        title="Le Logo de Sponsor"
        :modalAppendToBody="false"
        :visible.sync="logoSponsor"
        width="60%"
        center>
        <div class="row" style="margin:0 auto;text-align:center">
        <img style="max-width: 600px;" :src='"static/img/sponseurlogo/"+this.selectedSponor.logoSponseur'>
       </div>
</el-dialog>
  <el-dialog
        title="L'Affiche Publicitaire a Poster"
        :modalAppendToBody="false"
        :before-close="resetForm"
        :visible.sync="showAddSponsor"
        width="40%"
        center>
        <div class="row">
          <div class="col-sm-11" style="margin: 0 auto; float: none;">
               <el-form :model="FormSponseur" :rules="rulesFormSponseur" ref="FormSponseur" label-width="150px" label-position='left' >
                    <div v-if='!isedit'>
                    <el-form-item label="Raison Sociale" prop="raisonSociale">
                    <el-input  v-model="FormSponseur.raisonSociale" placeholder="Raison Sociale"></el-input>
                    </el-form-item>
                    <el-form-item  label="Site De Sponseur" prop="urlSiteSponseur">
                    <el-input  v-model="FormSponseur.urlSiteSponseur" placeholder="Site De Sponseur"></el-input>
                    </el-form-item>
                    <el-form-item label="Logo De Sponseur" prop="logoSponseur">
                    <el-upload
                    :auto-upload="false"
                    class="logoUp"
                    v-model="FormSponseur.logoSponseur"
                    ref="sponseurLogo"
                    drag
                    :limit=1
                    :on-success="succesLogoUpload"
                    action="http://localhost:8090/api/Sponseur/logo"
                    :before-upload="beforeUpload"
                    :multiple="false"
                    >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">Déposer les fichiers ici ou <em> cliquez pour envoyer</em></div>
                    </el-upload>
                    </el-form-item>
                    <div style="text-align:center;">
                    <el-button  type="success" round @click="submitSponseur('FormSponseur')">Ajouter Sponseur</el-button>                  
                    </div>
                    </div>
                    <div v-else>
                    <el-form-item label="Raison Sociale" prop="raisonSociale">
                    <el-input  disabled v-model="FormSponseur.raisonSociale" placeholder="Raison Sociale"></el-input>
                    </el-form-item>
                    <el-form-item  label="Site De Sponseur" prop="urlSiteSponseur">
                    <el-input  v-model="FormSponseur.urlSiteSponseur" placeholder="Site De Sponseur"></el-input>
                    </el-form-item>
                    <el-form-item label="Logo De Sponseur" prop="logoSponseur">
                    <el-upload
                    :auto-upload="false"
                    class="logoUp"
                    v-model="FormSponseur.logoSponseur"
                    ref="sponseurLogo"
                    drag
                    :limit=1
                    :on-success="succesLogoUpload"
                    action="http://localhost:8090/api/Sponseur/logo"
                    :before-upload="beforeUpload"
                    :multiple="false"
                    >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">Déposer les fichiers ici ou <em> cliquez pour envoyer</em></div>
                    </el-upload>
                    </el-form-item>
                    <div style="text-align:center;">
                    <el-button  type="success" round @click="submitSponseur('FormSponseur')">Modifier Sponseur</el-button>                  
                    </div>
                    </div>
                </el-form>
          </div>
       </div>
</el-dialog>
</div>
</template>
<script>

/* eslint-disable */
  import Vue from 'vue'
  import {Table, TableColumn, Select, Option, Form , FormItem , Dialog ,Upload} from 'element-ui'
  import PSwitch from 'src/components/UIComponents/Switch.vue'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Select)
  Vue.use(Option)
  Vue.use(Form)
  Vue.use(FormItem)
  Vue.use(Dialog)
  Vue.use(Upload)

  import swal from 'sweetalert2'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  export default{
    components: {
      PPagination,
      PSwitch
    },
    created() {
      var self = this;
      var clubname = JSON.parse(localStorage.getItem('session')).club
      if(clubname==null||clubname==undefined || clubname=='')
      return
      http.get('/ClubSponsor/list/'+clubname)
      .then(function (response) {
      self.tableData = response.data;
      self.mesSponsors=self.tableData
      })
      http.get('/Sponseur/list')
      .then(function (response) {
        self.tousSponsors=response.data
      })
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
    var validateImg = (rule, value , callback) => {
        setTimeout(() => {
            if (this.$refs.sponseurLogo.uploadFiles.length == 0) {
              callback(new Error("Le Logo De Sponseur est Nécessaire"));
            }
            else {
                  callback();
                }
          
        }, 1000);
      };
    return {
      showAddSponsor:false,
      isallSponsor:false,
      isedit:false,
        pagination: {
          perPage: 7,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        dialogVisible: false,
        searchQuery: '',
        propsToSearch: ['raisonSociale'],
        showaddMat:false,
        tableColumns: [
          {
            prop: 'raisonSociale',
            label: 'Raison Sociale',
            minWidth: 250,
          },
          {
            prop: 'urlSiteSponseur',
            label: 'Site de Sponsors',
            minWidth: 250
          },
        ],
        tousSponsors:[],
        mesSponsors:[],
        tableData: [],
        FormSponseur:{
        raisonSociale: '',
        urlSiteSponseur:'',
        logoSponseur: ''
        },
        selectedSponor:'',
        logoSponsor:false,
        rulesFormSponseur:{
          raisonSociale: [
          { required: true, message: 'Le Champ Raison Social est Obligatoire', trigger: 'blur' }
          ],
          logoSponseur: [
          { validator: validateImg, trigger: 'change' },
          ],
        },
      }

    },
    methods: {
      changeTab(tab, event){
        if(tab.index==1)
        {
          this.tableData=this.tousSponsors
          this.isallSponsor=true
        }
        else
        {
        this.tableData=this.mesSponsors
        this.isallSponsor=true
        }
      },
      showLogo(sponsor){
      this.selectedSponor=sponsor
      this.logoSponsor=true
      },
      submitSponseur(formName){
      this.$refs[formName].validate((valid) => {
          if (valid) {
            this.submitlogoUpload();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
       submitlogoUpload() {
       this.$refs.sponseurLogo.submit();
      },
      succesLogoUpload(response,file){
        var self=this
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Ajouter ce Sponseur !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Ajouter!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          self.FormSponseur.logoSponseur=response
          http
          .put("/Sponseur/create", self.FormSponseur)
          .then(response => {
            var spon = response.data
            self.lesSponseur.push(spon)
            self.FormDemSponseur.sponseur=spon.id
            self.addFormSponseur=false
            swal("","Ajouter Avec Success", "success")
          })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
      },
      resetForm() {
        this.showAddSponsor=false
        this.$refs["FormSponseur"].resetFields();
        this.isedit=false
      },
      handleEdit (index, row) {
        this.isedit=true
        this.dialogVisible=true,
        this.FormSponseur.raisonSociale=row.raisonSociale
        this.FormSponseur.id=row.id
        this.FormSponseur.urlSiteSponseur=row.urlSiteSponseur
        this.showAddSponsor=true
      }

    }
  }
</script>
<style  >
@import url('https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300');

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
    margin: 0px auto;
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
input[type='number'] {
    -moz-appearance:textfield;
}
input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
.boutadd {
    border-radius: 60px;
    padding: 9px;
    margin-left: 1%;
}
.card-content
{
  margin-top:0px
}
input.el-upload__input {
    display: none;
}
</style>
