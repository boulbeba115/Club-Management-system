<template>
<div>
  <div class="row">
    <div class="col-md-12 card">
        <h4 style="text-align:center;color:#a7abb4">Liste des Salles</h4>
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche Salles" v-model="searchQuery" aria-controls="datatables">
            </label>
           <a class="btn btn-info ti-plus boutadd" @click="dialogAjout=true"></a>
        </div>
        <div class="col-sm-6">
          <div class="pull-right">
          </div>
        </div>
        <div class="col-sm-12">
          <el-table class="table-striped"
                    :data="queriedData"
                    empty-text="Pas De Donnais"
                    :default-sort = "{prop: 'id'}"
                    border
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label"
                             align="center"
                             sortable
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
      <el-dialog
        title="Ajouter Salle"
        :modalAppendToBody="false"
        :visible.sync="dialogAjout"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px" label-position='left' class="demo-ruleForm">
        <el-form-item  label="Nom Salle"  prop="nomSalle"  >
        <el-input  v-model="ruleForm.nomSalle" ></el-input>
        </el-form-item>
        <el-form-item label="Capacité"  prop="capcite">
        <el-input  type="number" min="0"  v-model="ruleForm.capcite" ></el-input>
        </el-form-item>
        <el-form-item label="TypeDeSalle" prop="salleType"  >
            <el-select
                    size="large"
                    placeholder="selectionner le Type"
                    v-model="ruleForm.salleType">
            <el-option v-for="option in lesSalleTypes"
                        :value="option.id"
                        :v-model="option.id"
                        :label="option.type"
                        :key="option.label">
            </el-option>
            </el-select>
            <a class="btn btn-info ti-plus boutadd" @click="dialogTypeSalle=true"></a>
        </el-form-item>  
        <el-form-item label="Rétroprojecteur">
            <el-switch v-model="ruleForm.retroproj"></el-switch>
        </el-form-item>
        <el-button style="float:left" type="primary" @click="submitForm('ruleForm')">Ajouter</el-button>
        <el-button style="float:left" @click="resetForm('ruleForm')">Décharger</el-button>
        </el-form>
        </div>
        </div>   
      </el-dialog>
    <el-dialog
        title="Ajouter Un Type de Salle"
        :modalAppendToBody="false"
        :visible.sync="dialogTypeSalle"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="typeform" :rules="rulesType" ref="typeform" label-width="180px" label-position='left' class="demo-ruleForm">
        <el-form-item  label="Type Salle"  prop="TypedeSalle"  >
        <el-input  v-model="typeform.TypedeSalle" ></el-input>
        </el-form-item>
        <el-button style="float:left" type="primary" @click="submitForm('typeform')">Ajouter</el-button>
        <el-button style="float:left" @click="resetForm('typeform')">Décharger</el-button>
        </el-form>
        </div>
        </div>
      </el-dialog>
      <el-dialog
        title="Modifier Salle"
        :modalAppendToBody="false"
        :visible.sync="dialogVisible"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="editForm" :rules="rules" ref="editForm" label-width="180px" label-position='left' class="demo-ruleForm">
        <el-form-item  label="Nom Salle"  prop="nomSalle"  >
        <el-input  v-model="editForm.nomSalle" ></el-input>
        </el-form-item>
        <el-form-item label="Capacité"  prop="capcite">
        <el-input  type="number" v-model="editForm.capcite" ></el-input>
        </el-form-item>
        <el-form-item label="TypeDeSalle" prop="salleType"  >
            <el-select
                    size="large"
                    placeholder="selectionner le Type"
                    v-model="editForm.salleType">
            <el-option v-for="option in lesSalleTypes"
                        :value="option.id"
                        :v-model="option.id"
                        :label="option.type"
                        :key="option.label">
            </el-option>
            </el-select>
            <a class="btn btn-info ti-plus boutadd" @click="dialogTypeSalle=true"></a>
        </el-form-item>  
        <el-form-item label="Rétroprojecteur">
            <el-switch v-model="editForm.retroproj"></el-switch>
        </el-form-item>
        <el-button style="float:left" type="primary" @click="submitForm('editForm')">Modifier</el-button>
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
  import {Table, TableColumn, Select, Option, Form , FormItem , Dialog , Switch} from 'element-ui'
  import PSwitch from 'src/components/UIComponents/Switch.vue'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Select)
  Vue.use(Option)
  Vue.use(Form)
  Vue.use(FormItem)
  Vue.use(Dialog)
  Vue.use(Switch)

  import swal from 'sweetalert2'
  import http from './axiosbase.js'
  export default{
    components: {
      PPagination,
      PSwitch
    },
    created() {
      var self = this;
      http.get('Salle/list')
          .then(function (response) {
            self.tableData= response.data;
          })
     http.get('/TypeSalle/list')
            .then(function (response) {
            self.lesSalleTypes= response.data;
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
              if(key=="type")
              {
              let starvakue = row[key].type
              if (starvakue.includes && starvakue.includes(this.searchQuery)) {
                isIncluded = true
              }}
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
        dialogAjout:false,
        dialogTypeSalle:false,
        dialogVisible: false,
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        lesSalleTypes:[],
        searchQuery: '',
        propsToSearch: ['nomSalle'],
        tableColumns: [
          {
            prop: 'id',
            label: 'numero',
            minWidth: 70,
          },
          {
            prop: 'nomSalle',
            label: 'Salle',
            minWidth: 100,
          },
          {
            prop: 'capcite',
            label: 'Capcite',
            minWidth: 100
          },
          {
            prop: 'salleType.type',
            label: 'Type de Salle',
            minWidth: 100
          }
        ],
        tableData: [],
        typeform:{
        TypedeSalle:''
        },
        editForm:{},
        ruleForm: {
        nomSalle: '',
        capcite:'',
        salleType:'',
        retroproj:false,
        },
        materfac:{
        materiel: '',
        qtetotal:'',
        qtedispo:''
        },
        rules: {
          nomSalle: [
            { required: true, message: 'Le Nom de Salle est nécessaire', trigger: 'blur' }
          ],
          capcite: [
            { required: true, message: 'La Capacité de Salle est nécessaire', trigger: 'blur' }
          ],
          salleType: [
            { required: true, message: 'Please selectionner le Type', trigger: 'change' }
          ]
        },
        rulesType: {
          TypedeSalle: [
            { required: true, message: 'Le Type de Salle est nécessaire ', trigger: 'blur' }
          ],  
        }
      }

    },
    methods: {
       submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(formName=="ruleForm")
            this.ajoutSalle()
            else
            if(formName=="typeform")
            this.ajouterTypeSalle()
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
      ajoutSalle(){
        var self = this;
        let obj = this.lesSalleTypes.find(o => o.id === this.ruleForm.salleType)
        var Salle = {
            nomSalle:this.ruleForm.nomSalle,
            capcite: this.ruleForm.capcite,
            retroproj:this.ruleForm.retroproj,
            salleType:obj
        }
        console.log(Salle)
       http.put("/Salle/create",Salle)
        .then(response => {
          self.tableData.push(response.data)
         this.resetForm('ruleForm')
         this.dialogAjout=false          
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Matériel Ajouter avec succès',
          })
        })
        .catch(e => {
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
        let obj = this.lesSalleTypes.find(o => o.id === this.editForm.salleType)
        this.editForm.salleType=obj
        http.put("/Salle/create",this.editForm)
        .then(response => {
         this.resetForm('editForm')
        this.dialogVisible=false,
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
      ajouterTypeSalle(){
        var self = this;
        var typeSalle = {
            type: self.typeform.TypedeSalle
        }
        http.put("/TypeSalle/create",typeSalle)
        .then(response => {
          self.lesSalleTypes.push(response.data) 
          this.dialogTypeSalle=false
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Ajouter avec succès',
          })
        })
        .catch(e => {
          this.dialogTypeSalle=false
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
            text: 'il est possible que ce Type deja existe',
          })          
          console.log(e);
        })
        this.showaddtype=false
      },
      handleEdit (index, row) {
        this.dialogVisible=true,
        this.editForm = {
        id:row.id,
        nomSalle:row.nomSalle,
        capcite:row.capcite,
        salleType:row.salleType.id,
        retroproj:row.retroproj,
        }
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
