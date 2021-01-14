<template>
<div>
      <h4 style="text-align:center;color:#a7abb4">Liste des Materiel Disponible</h4>
  <div class="row">
    <div class="col-md-12 card">    
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche Materiel" v-model="searchQuery" aria-controls="datatables">
            </label>
            <a class="btn btn-info ti-plus boutadd"  @click="ajoutMat=true"></a>
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
        title="Ajouter Matériel"
        :modalAppendToBody="false"
        :visible.sync="ajoutMat"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  label-position='left' label-width="180px">
        <el-form-item label="Reference Matériel" prop="referenceMateriel"  >
        <el-input  v-model="ruleForm.referenceMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Matériel" class="labellef" prop="materiel"  >
        <el-input  v-model="ruleForm.materiel" ></el-input>
        </el-form-item>
        <el-form-item label="Marque Materiel" prop="marqueMateriel">
        <el-input  type="text" v-model="ruleForm.marqueMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Quantité Totale" class="labellef" prop="qtetotal">
        <el-input min="0" type="number" v-model="ruleForm.qtetotal" ></el-input>
        </el-form-item>  
        <el-button style="float:left" type="primary" @click="submitForm('ruleForm')">Ajouter</el-button>
        <el-button style="float:left" @click="resetForm('ruleForm')">Décharger</el-button>
        </el-form>
        </div>
        </div>
      </el-dialog>

      <el-dialog
        title="Modifier Matériel"
        :modalAppendToBody="false"
        :visible.sync="dialogVisible"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="materfac" :rules="rules" ref="materfac" label-width="180px" label-position='left'>
        <el-form-item  label="Reference Matériel" prop="referenceMateriel"  >
        <el-input disabled  v-model="materfac.referenceMateriel" ></el-input>
        </el-form-item>
        <el-form-item  label="Matériel"  prop="materiel"  >
        <el-input  v-model="materfac.materiel" ></el-input>
        </el-form-item>
         <el-form-item label="Marque Materiel" prop="marqueMateriel">
        <el-input  type="text" v-model="materfac.marqueMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Quantité Totale" class="labellef" prop="qtetotal">
        <el-input  min="0" type="number" v-model="materfac.qtetotal" ></el-input>
        </el-form-item>
        <el-button style="float:left" type="primary" @click="submitForm('materfac')">Modifier</el-button>
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
      http.get('MaterielFaculter/list')
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
      var validateTotale = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('Ce Champ est néssaisiare'));
        }
        setTimeout(() => {
            if (value < 0) {
              callback(new Error('La Quantité Totale Ne Peut Pas Etre négative'));
            }
            else {
                  callback();
                }
        }, 1000);
      };
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
        tableColumns: [
          {
            prop: 'referenceMateriel',
            label: 'Reference',
            minWidth: 100,
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
            prop: 'qteTotale',
            label: 'Quantité Totale',
            minWidth: 100
          },
          
        ],
        tableData: [],
        ruleForm: {
        referenceMateriel:'',
        materiel: '',
        marqueMateriel:'',
        qtetotal:'',
        },
        materfac:{
        referenceMateriel:'',
        materiel: '',
        marqueMateriel:'',
        qtetotal:'',
        },
        rules: {
          referenceMateriel: [
           { required: true, message: 'La Reference de Materiel est Obligatoire', trigger: 'blur' }
          ],
          materiel: [
            { required: true, message: 'Le Nom de Matériel est nécessaire', trigger: 'blur' }
          ],
          qtetotal: [
            { required: true, message: 'La Quantité Totale est nécessaire', trigger: 'blur' },
            { validator: validateTotale, trigger: 'blur' }
          ]
        }
      }

    },
    methods: {
       submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(formName=="ruleForm")
            this.ajoutMatriel()
            else
            this.editMatriel()

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      ajoutMatriel(){
        var self = this;
        var Materiel = {
            nomMateriel:this.ruleForm.materiel,
            marqueMateriel:this.ruleForm.marqueMateriel,
            qteTotale: this.ruleForm.qtetotal,
        }
        http.put("MaterielFaculter/create",Materiel)
        .then(response => {
          self.tableData.push(response.data)
         this.resetForm('ruleForm')
         this.ajoutMat=false          
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Matériel Ajouter avec succès',
          })
        })
        .catch(e => {
          this.ajoutMat=false
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
            text: 'il est possible que ce Le Nom de Matériel deja exite',
          })          
          console.log(e);
        })
      },
      editMatriel(){
        var self = this;
        var Materiel = {
            id:this.materfac.id,
            marqueMateriel:this.materfac.marqueMateriel,
            nomMateriel:this.materfac.materiel,
            qteTotale: this.materfac.qtetotal,
        }
        http.put("MaterielFaculter/create",Materiel)
        .then(response => {
          this.dialogVisible=false
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Matériel Modifier avec succès',
          })
        })
        .catch(e => {
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
            text: 'Le Nom de Materiel Existe Deja',
          })          
          console.log(e);
        })
      },
      handleEdit (index, row) {
        this.dialogVisible=true,
        this.materfac.id=row.id
        this.materfac.referenceMateriel=row.referenceMateriel
        this.materfac.marqueMateriel=row.marqueMateriel
        this.materfac.materiel=row.nomMateriel
        this.materfac.qtetotal=row.qteTotale
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
