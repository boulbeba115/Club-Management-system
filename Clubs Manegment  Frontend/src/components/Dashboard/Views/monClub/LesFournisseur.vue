<template>
<div>
  <div class="row">
    <div class="col-md-12 card">  
            <h4 style="text-align:center;color:#a7abb4">Liste des fournisseurs</h4>  
      <div class="card-content row">
        <div class="col-sm-6">
            <label>
              <input type="search" class="form-control input-sm" placeholder="Recherche fournisseur" v-model="searchQuery" aria-controls="datatables">
            </label>
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
        :visible.sync="addFournisseur"
        width="40%">
        <div class="row">
          <el-form :model="FormAjoutFourniss" :rules="rulesFormAjoutFourniss" ref="FormAjoutFourniss" label-width="140px" label-position='left' >
            <el-form-item label="Matricule Fiscale" prop="matriculeFiscale">
              <el-input  disabled v-model="FormAjoutFourniss.matriculeFiscale" placeholder="Matricule Fiscale"></el-input>
            </el-form-item>
            <el-form-item label="Raison Sociale" prop="raisonSociale">
              <el-input disabled v-model="FormAjoutFourniss.raisonSociale" placeholder="Raison Sociale"></el-input>
            </el-form-item>
            <el-form-item  label="Email" prop="email">
              <el-input  v-model="FormAjoutFourniss.email" placeholder="Email"></el-input>
            </el-form-item>
            <div class="row">
            <div class='col-sm-6'>
            <el-form-item  label="Téléphone" prop="tel" label-width="100px">
              <el-input v-model="FormAjoutFourniss.tel" placeholder="tel"></el-input>
            </el-form-item>
            </div>
            <div class='col-sm-6'>
            <el-form-item  label="Fax" prop="fax" label-width="100px">
              <el-input v-model="FormAjoutFourniss.fax" placeholder="Fax"></el-input>
            </el-form-item>
            </div>
            </div>
            <div style="text-align:center;">
            <el-button  type="success" round @click="submitMaterielForm('FormAjoutFourniss')">Ajouter Fournisseur</el-button>                  
            </div>
          </el-form>
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
     http.get('/fournisseur/list')
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
        addFournisseur:false,
        pagination: {
          perPage: 7,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        dialogVisible: false,
        searchQuery: '',
        propsToSearch: ['raisonSociale'],
        tableColumns: [
          {
            prop: 'matriculeFiscale',
            label: 'Matricule Fiscale',
            minWidth: 100,
          },
          {
            prop: 'raisonSociale',
            label: 'Raison Sociale',
            minWidth: 100
          },
          {
            prop: 'email',
            label: 'E-mail',
            minWidth: 100
          },
          {
            prop: 'tel',
            label: 'Téléphone',
            minWidth: 100
          },
          {
            prop: 'fax',
            label: 'Fax',
            minWidth: 100
          },
          
        ],
        tableData: [],
        FormAjoutFourniss:{
        matriculeFiscale:'',
        raisonSociale:'',
        email:'',
        tel:'',
        fax:'',
        },

      rulesFormAjoutFourniss:{
        matriculeFiscale: [
        { required: true, message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        raisonSociale: [
        { required: true, message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        email: [
        { required: true , message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        tel: [
        { required: true , message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        fax: [
        { required: true , message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
      }       

      }

    },
    methods: {
       submitMaterielForm(formName){
         this.$refs[formName].validate((valid) => {
          if (valid) {
            this.updateFournisseur();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
       updateFournisseur(){
        var self=this
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Modifier ce Fournisseur !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Modifier',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          http
          .put("/fournisseur/add",self.FormAjoutFourniss)
          .then(response => {
            var founrn = self.tableData.findIndex((obj) => obj.id === self.FormAjoutFourniss.id)
            console.log(founrn)
            self.resetForm()
            self.tableData.splice(founrn, 1)
            self.tableData.splice(founrn, 0, response.data);
            swal("","Modifier Avec Success", "success")
            })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      resetForm() {
        this.addFournisseur=false
        this.$refs["FormAjoutFourniss"].resetFields();
      },
      handleEdit (index, row) {
        this.FormAjoutFourniss.id=row.id
        this.FormAjoutFourniss.matriculeFiscale=row.matriculeFiscale
        this.FormAjoutFourniss.raisonSociale=row.raisonSociale
        this.FormAjoutFourniss.email=row.email
        this.FormAjoutFourniss.tel=row.tel
        this.FormAjoutFourniss.fax=row.fax
        this.addFournisseur=true
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
