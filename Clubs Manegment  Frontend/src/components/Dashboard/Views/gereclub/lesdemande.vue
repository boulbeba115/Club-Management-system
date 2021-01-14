<template>
  <div class="row">
    <div class="col-md-12 card">
      <h4 style="text-align:center;color:#a7abb4">Liste des demande de creation de clubs</h4>
      <div class="card-content row">
        <div class="col-sm-6">
          <el-select
            class="select-default"
            v-model="pagination.perPage"
            placeholder="Per page">
            <el-option
              class="select-default"
              v-for="item in pagination.perPageOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </div>
        <div class="col-sm-6">
          <div class="pull-right">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Search records" v-model="searchQuery" aria-controls="datatables">
            </label>
          </div>
        </div>
        <div class="col-sm-12">
          <el-table class="table-striped"
                    :data="queriedData"
                    border
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label">
            </el-table-column>
            <el-table-column
              :min-width="100"
              fixed="right"
              label="Description">
              <template slot-scope="props">
                <span class="eyestyle ti-eye" id="show-modal" @click="showDetailModal(props.row.description)"></span>
              </template>
            </el-table-column>
            <el-table-column
              :min-width="110"
              fixed="right"
              label="Membre Fondateur">
              <template slot-scope="props"> 
                <span class="eyestyle ti-eye" id="show-modal" @click="showlisteFonda(props.row.membresFondateur)"></span>
              </template>
            </el-table-column>
            <el-table-column
            :min-width="100"
            fixed="right"
            label="Telecharger">
              <template slot-scope="props">
                    <template v-if="props.row.confirmer">
                <span class="eyestyle ti-printer"  @click="topdf(props.row)"></span>
                    </template>
                    <template v-else>
                <span disabled class="eyestyle ti-printer" ></span>
                    </template>              
              </template>
          </el-table-column>            
            <el-table-column
              :min-width="120"
              fixed="right"
              label="Etat Demande">
              <template slot-scope="props">
                <template v-if="props.row.confirmer==null">
                      <div class="col-sm-6">
                      <a type="button" @click="confirmerCree(props.row)" class="pull-right btn btn-xs btn-success fixit">
                        <span class="btn-label">
                        <i class="fa fa-check"></i>
                        </span>
                      </a>
                      </div>
                      <div class="col-sm-6" >
                      <a type="button" @click="refuser(props.row)" class="pull-left btn btn-xs btn-danger fixit">
                        <span class="btn-label">
                        <i class="fa fa-times"></i>
                        </span>
                      </a>
                      </div>
                </template>
                <template v-else >
                    <template v-if="props.row.confirmer">
                      <a type="button" class="  btn btn-fill btn-xs   btn-success">Accepter</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill btn-xs btn-danger">Refusé</a>
                    </template>
                </template>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="col-sm-6 pagination-info">
          <p class="category">Showing {{from + 1}} to {{to}} of {{total}} entries</p>
        </div>
        <div class="col-sm-6">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
      </div>
    </div>
    <div v-if="showdetail">
          <transition name="modal">
              <div class="modal-mask">
                <div class="modal-wrapper">
                  <div class="modal-container scroll">

                    <div class="modal-header">
                      <slot name="header">
                        <div class="modheader">Description de l'activité de Club</div>
                      </slot>
                    </div>

                    <div class="modal-body">
                      <slot name="body">
                        <span v-html="desActivite"></span>
                      </slot>
                    </div>

                    <div class="modal-footer">
                      <slot name="footer">        
                        <a @click="showdetail=false" class="btn btn-info">close</a>               
                      </slot>
                    </div>
                  </div>
                </div>
              </div>
            </transition>
      </div>  
    <div v-if="showfounders">
          <transition name="modal">
              <div class="modal-mask">
                <div class="modal-wrapper">
                  <div class="modal-container scroll">

                    <div class="modal-header">
                      <slot name="header">
                        <div class="modheader">Liste Memebre Fondateur</div>
                      </slot>
                    </div>

                    <div class="modal-body">
                      <slot name="body">
                              <div class="card-content table-responsive table-full-width">
                              <el-table :data="founders">
                                <el-table-column label="Matricule" property="etudiant.matricule"></el-table-column>
                                <el-table-column label="Nom" property="etudiant.nom"></el-table-column>
                                <el-table-column label="Prenom" property="etudiant.prenom"></el-table-column>
                                <el-table-column label="Email" min-width="200px" property="etudiant.email"></el-table-column>
                                <el-table-column label="Role" min-width="200px" property="roleEtudiant.role"></el-table-column>
                              </el-table>
                            </div>
                      </slot>
                    </div>

                    <div class="modal-footer">
                      <slot name="footer">        
                        <a @click="showfounders=false" class="btn btn-info">close</a>               
                      </slot>
                    </div>
                  </div>
                </div>
              </div>
            </transition>
      </div>      
  </div>
</template>
<script>

/* eslint-disable */
  import Vue from 'vue'
  import {Table, TableColumn, Select, Option} from 'element-ui'
  import PSwitch from 'src/components/UIComponents/Switch.vue'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Select)
  Vue.use(Option)
  import swal from 'sweetalert2'
  import jsPDF from 'jspdf'
  import autoTable from 'jspdf-autotable';
  import http from './axiosbase.js'
  export default{
    components: {
      PPagination,
      PSwitch
    },
    created() {
      var self = this
      http
        .get("demandecreationclub/list")
        .then(response => {
          self.tableData = response.data; 
          self.tableData=self.tableData.sort(function(a, b) {
            return new Date(b.datedemande) - new Date(a.datedemande); 
          })
        })
        .catch(e => {
          console.log(e);
        });
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
              if(key=="type")
              {
              let starvakue = row[key].type
              if (starvakue.includes && starvakue.includes(this.searchQuery)) {
                isIncluded = true
              }}
              let rowValue = row[key].toString()
              if (rowValue.includes && rowValue.includes(this.searchQuery)) {
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
      return {
        testing:null,
        showdetail:false,
        showfounders:false,
        founders:[],
        desActivite:'',
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        searchQuery: '',
        propsToSearch: ['id', 'nomClub', 'datedemande','type'],
        tableColumns: [
          {
            prop: 'id',
            label: 'numero',
            minWidth: 70
          },
          {
            prop: 'nomClub',
            label: 'nom de Club',
            minWidth: 100
          },
          {
            prop: 'datedemande',
            label: 'date de demande',
            minWidth: 100
          },
          {
            prop: 'type.type',
            label: 'type',
            minWidth: 100
          }
        ],
        tableData: []
      }
    },
    methods: {
      showlisteFonda(liste){
        this.showfounders=true
        this.founders=liste
      },
      confirmerCree(demande)
      {
        swal({
            text: 'Si Vous Confirmer Cette Demande Le Club Sera Crée Automatiquement!',
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Confirmer',
            cancelButtonText: 'Annuler',
            confirmButtonClass: 'btn btn-success btn-fill spacing',
            cancelButtonClass: 'btn btn-info btn-fill',
            buttonsStyling: false
            }).then(function () {
                demande.confirmer=true,
                http
                .put("/demandecreationclub/Confirm", demande)
                .then(response => {
                  swal({
                    title: `Demande Confirmer et Club a éte Crée avec Succes`,
                    buttonsStyling: false,
                    confirmButtonClass: 'btn btn-success btn-fill',
                    type: 'success'
                  })
                })
                .catch(e => {
                  swal("Erreur","Une Erreur et Produit lors de Confirmation de Demande", "error")
                  console.log(e);
                })
              })
      },
      refuser(demande)
      {
        swal({
            text: 'Voulez Vous Réfuser Cette Demande ? , Le Club Ne Sera Pas Crée',
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Refuser la Demande',
            cancelButtonText: 'Annuler',
            confirmButtonClass: 'btn btn-success btn-fill spacing',
            cancelButtonClass: 'btn btn-info btn-fill',
            buttonsStyling: false
            }).then(function () {
                demande.confirmer=false,
                http
                .put("/demandecreationclub/create", demande)
                .then(response => {
                    swal({
                    title: `Terminer Avec Succes`,
                    buttonsStyling: false,
                    confirmButtonClass: 'btn btn-success btn-fill',
                    type: 'success'
                    })
                })
                .catch(e => {
                  swal("Erreur","Une Erreur et Produit", "error")
                  console.log(e);
                })
              })
      },
      topdf(data){
        var etudiantData = data.membresFondateur.map(etu => etu.etudiant)
      var doc = new jsPDF();
      let parser = new DOMParser();
      doc.setFontSize(30);
      doc.text(50, 30, "Demande Creation Club");
      let parsedHtml = parser.parseFromString(data.description, 'text/html')
      if(parsedHtml.images[0] != undefined)
      {
      let firstImg = parsedHtml.images[0].src;
        var imgData = firstImg.toString();
       doc.addImage(imgData, 'png', 10, 10, 35, 35)
      }
      doc.setFontSize(15);
      doc.text(20, 70, "Nom De Club : ")
      doc.text(100, 70, data.nomClub);
      doc.text(20, 80, "Type De Club : ")
      doc.text(100, 80, data.type.type)
      doc.text(20, 90, "Date Creation demande:")
      doc.text(100, 90, data.datedemande);
      doc.text(20, 100, "Etat de  demande: ")
      if(data.confirmer==true)
      doc.text(100, 100, "Demande Confirmer")
      else
      doc.text(100, 100, "Demande Refuser")


      let columns = [
               {title: 'Matricule', dataKey: "matricule"}, 
               {title: 'Nom', dataKey: "nom"},
               {title: 'Prenom', dataKey: "prenom"},
               {title: 'Email', dataKey: "email"}
            ]
      doc.text('Liste Des Membre Fondateur', 70, 120);
      doc.autoTable(columns,etudiantData,{margin: {top: 130,button: 10},})
      let rawLiElements = parsedHtml.getElementsByTagName("body")[0].children
      let liElements = []
      doc.text("Description D'activiter de Club", 70, 170);
      let x=160;let j=0
      for (let i = 0; i < rawLiElements.length; i++) {
        if(rawLiElements[i].innerHTML.indexOf("src")>-1)
        {
          j++;
        }
        else
        {
            doc.fromHTML(
                rawLiElements[i].innerHTML,20,x+=10)
              liElements.push(rawLiElements[i])
        }
      }

       doc.save("demande creation Club "+data.nomClub+".pdf")

      },

      showDetailModal(article)
      {
        this.desActivite=article,
        this.showdetail=true
      },
      handleEdit(index, row) {
        alert(`Your want to edit ${row.name}`)
      },
    }
  }
</script>
<style >
@import url('https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300');
.el-input__suffix {
    position: absolute;
    height: 100%;
    right: 5px;
    top: 14px;
    text-align: center;
    color: #c0c4cc;
    transition: all .3s;
    pointer-events: none;
}
.bootstrap-switch .bootstrap-switch-handle-on, .bootstrap-switch .bootstrap-switch-handle-off {
    text-align: center;
    z-index: 1;
    float: left;
    width: 50% !important;
    background-color: green;
}
.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-default, .bootstrap-switch .bootstrap-switch-handle-off.bootstrap-switch-default {
    color: #fff;
    background: rgb(0, 128, 0);
}
.bootstrap-switch.bootstrap-switch-off .bootstrap-switch-handle-on {
    background-color: rgb(163, 41, 71);
}
.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-default, .bootstrap-switch .bootstrap-switch-handle-off.bootstrap-switch-default {
    color: #fff;
    background: rgb(163, 41, 71);
}
.el-table .cell {
  text-align: center
}
.el-select .el-input .el-select__caret.is-reverse {
    transform: rotateZ(0deg);
    position: relative;
    top: -19px;
}
.fixit
{
  min-width: 38px;
}
.fixit, .navbar .navbar-nav > li > a.fixit {
    padding: 7px;
}
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
    min-width: 40%;
    max-width: 50%;
    max-height: 600px;
    overflow: auto;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 5px;
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
.typelabel {
  font-family: 'Open Sans Condensed', sans-serif;
    position: relative;
    top: 15px;
}
.eyestyle {
    padding: 3px;
    margin-left: 2px;
    position: relative;
    top: 2px;
    color: gray;
    font-size: 16px;
    font-weight: bolder;
}
.spacing
{
    margin-right: 10%;
}
.swal2-modal    
{   margin: auto;
    border-radius: 23px!important;
}
.ql-align-center {
    text-align: center;
}
p>img{
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}
.scroll::-webkit-scrollbar {
  width: 15px;
}
 
.scroll::-webkit-scrollbar-thumb {
  background: #666;
}

.scroll::-webkit-scrollbar-track {
  background: #ddd;
}
.card-content
{
  margin-top:0px
}
</style>
