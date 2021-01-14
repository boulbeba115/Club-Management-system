<template>
<div>
  <div class="row">
    <div class="col-md-12 card"> 
      <div class="card-content row">
              <h4 style="text-align:center;color:#a7abb4">Liste des Materiel Disponible</h4>   
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche Materiel" v-model="searchQuery" aria-controls="datatables">
            </label>
            <a class="btn btn-info ti-plus boutadd"  @click="showaddMat=true"></a>
        </div>
        <div class="col-sm-6">
          <div class="pull-right">
          </div>
        </div>
        <div class="col-sm-12">
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
              fixed="right"
              label="Actions"
              align="center"
              header-align="center">
              <template slot-scope="props">
                <a class="btn btn-simple btn-xs btn-warning btn-icon edit" @click="handleEdit(props.$index, props.row)"><i class="ti-pencil-alt"></i></a>
                <a class="btn btn-simple btn-xs btn-danger btn-icon remove"  @click="handleDelete(props.$index, props.row)"><i class="ti-close"></i></a>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="col-sm-6 pagination-info">
          <p class="category">{{total}} record</p>
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
 <el-dialog
        title="Ajouter Matériel Externe"
        :modalAppendToBody="false"
        :before-close="resetForm"
        :visible.sync="showaddMat"
        width="40%">
        <div class="row">
          <div v-if="!isedit"class="col-sm-11">
          <el-form :model="MaterielForm" :rules="Materielrules" ref="MaterielForm"  label-position='left' label-width="180px" >
          <el-form-item label="Reference Matériel" prop="referenceMateriel"  >
          <el-input  v-model="MaterielForm.referenceMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Matériel" prop="nomMateriel"  >
          <el-input  v-model="MaterielForm.nomMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Marque Materiel" prop="marqueMateriel">
          <el-input  type="text" v-model="MaterielForm.marqueMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Valeur (dt)"  prop="coutMateriel"  >
          <el-input type="number"  v-model="MaterielForm.coutMateriel" ></el-input>
          </el-form-item>   
          <el-button style="float:left" type="primary" @click="submitMaterielForm('MaterielForm')">Ajouter</el-button>
          </el-form>
          </div>
          <div v-else class="col-sm-11">
          <el-form :model="MaterielForm" :rules="Materielrules" ref="MaterielForm"  label-position='left' label-width="180px" >
          <el-form-item label="Reference Matériel" prop="referenceMateriel"  >
          <el-input  disabled v-model="MaterielForm.referenceMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Reference Matériel" prop="nomMateriel"  >
          <el-input  v-model="MaterielForm.nomMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Marque Materiel" prop="marqueMateriel">
          <el-input  type="text" v-model="MaterielForm.marqueMateriel" ></el-input>
          </el-form-item>
          <el-form-item label="Valeur (dt)"  prop="coutMateriel"  >
          <el-input type="number"  v-model="MaterielForm.coutMateriel" ></el-input>
          </el-form-item>   
          <el-button style="float:left" type="primary" @click="submitMaterielForm('MaterielForm')">Modifier</el-button>
          </el-form>
          </div>
        </div>
</el-dialog>


  </div>
</div>
</template>
<script>

/* eslint-disable */
  import Vue from 'vue'
  import {Table, TableColumn, Select, Option, Form , FormItem , Dialog} from 'element-ui'
  import PSwitch from 'src/components/UIComponents/Switch.vue'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Select)
  Vue.use(Option)
  Vue.use(Form)
  Vue.use(FormItem)
  Vue.use(Dialog)

  import swal from 'sweetalert2'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  export default{
    components: {
      PPagination,
      PSwitch
    },
    created() {
      var self = this;
      http.get('/MatExt/list')
          .then(function (response) {
            self.tableData= response.data;
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
    return {
        ajoutMat:false,
        pagination: {
          perPage: 7,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        dialogVisible: false,
        searchQuery: '',
        propsToSearch: ['nomMateriel'],
        showaddMat:false,
        tableColumns: [
          {
            prop: 'referenceMateriel',
            label: 'Reference',
            minWidth: 70,
          },
          {
            prop: 'nomMateriel',
            label: 'Materiel',
            minWidth: 100
          },
          {
            prop: 'marqueMateriel',
            label: 'Marque',
            minWidth: 100
          },
          {
            prop: 'coutMateriel',
            label: 'Cout de Materiel (dt)',
            minWidth: 100
          },
          
        ],
        tableData: [],
        MaterielForm:{
        referenceMateriel:'',
        nomMateriel:'',
        marqueMateriel: '',
        coutMateriel: ''
        },
        materfac:{
        materiel: '',
        marqueMateriel:'',
        qtetotal:'',
        },
        isedit:false,
        Materielrules:{
        referenceMateriel: [
        { required: true, message: 'La Reference de Materiel est Obligatoire', trigger: 'blur' }
        ],
        nomMateriel: [
        { required: true, message: 'Le Nom de Materiel est Obligatoire', trigger: 'blur' }
        ],
        marqueMateriel: [
        { required: true, message: 'Le Champ Marque est Obligatoire', trigger: 'blur' }
        ],
        coutMateriel: [
        { required: true, message: 'Le Champ Valeur De Materiel', trigger: 'blur' }
        ]
        },

      }

    },
    methods: {
       submitMaterielForm(formName){
         this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ajouterMaterielExterne();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
       ajouterMaterielExterne(){
        var self=this
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Ajouter ce Materiel !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Ajouter!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          http
          .put("/MatExt/create", self.MaterielForm)
          .then(response => {
            var materiel = response.data
            if(!self.isedit)
            {
            self.resetForm()
            swal("","Ajouter Avec Success", "success")
            }
            else
            {
            var mat = self.tableData.findIndex((mat) => mat.id === self.MaterielForm.id)
            self.resetForm()
            console.log(mat)
            self.tableData.splice(mat, 1)
            self.tableData.splice(mat, 0, materiel);
            swal("","Modifier Avec Success", "success")
            }
          })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      resetForm() {
        this.showaddMat=false
        this.$refs["MaterielForm"].resetFields();
        this.isedit=false
      },
      handleEdit (index, row) {
        this.isedit=true
        this.dialogVisible=true,
        this.MaterielForm.id=row.id
        this.MaterielForm.referenceMateriel=row.referenceMateriel
        this.MaterielForm.nomMateriel=row.nomMateriel
        this.MaterielForm.marqueMateriel=row.marqueMateriel
        this.MaterielForm.coutMateriel=row.coutMateriel
        this.showaddMat=true
      },
      handleDelete (index, row) {
        let indexToDelete = this.tableData.findIndex((tableRow) => tableRow.id === row.id)
        if (indexToDelete >= 0) {
          this.tableData.splice(indexToDelete, 1)
        }
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
</style>
