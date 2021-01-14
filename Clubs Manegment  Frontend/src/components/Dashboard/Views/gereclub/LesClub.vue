<template>
  <div class="row">
    <div class="col-md-12 card">
      <h4 style="text-align:center;color:#a7abb4">Liste des Club de la faculté </h4>
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
              label="Etat Demande">
              <template slot-scope="props">
                    <template v-if="!props.row.activityStop">
                      <a type="button"  @click="activer(props.row,false,props.$index)" class="  btn btn-fill btn-xs   btn-success">Club Actif</a>
                    </template>
                    <template v-else>
                      <a type="button" @click="activer(props.row,true,props.$index)" class=" btn  btn-fill btn-xs btn-danger">Club Suspendu</a>
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
  import http from './axiosbase.js';
  export default{
    components: {
      PPagination
    },
    created() {
      var self = this;
      http
        .get("club/list")
        .then(response => {
          self.tableData = response.data; 
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
        pagination: {
          perPage: 5,
          currentPage: 1,
          perPageOptions: [5, 10, 25, 50],
          total: 0
        },
        searchQuery: '',
        propsToSearch: ['id', 'nomClub', 'dateCreation'],
        tableColumns: [
          {
            prop: 'id',
            label: 'numero',
            minWidth: 100
          },
          {
            prop: 'nomClub',
            label: 'nom de Club',
            minWidth: 100
          },
          {
            prop: 'dateCreation',
            label: 'date Creation Club',
            minWidth: 100
          }
        ],
        tableData: []
      }
    },
    methods: {
      activer(club,decision,index)
      { var message
        var  self = this;
          if(!decision)
            message="Voulez Vous Confirme La suspension de L'activité de Ce Club !!!"
            else
            message="Voulez Vous Confirme Le Reprise de L'activité de Ce Club !!!"
        swal({
            text: message,
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Confirmer',
            cancelButtonText: 'Annuler',
            confirmButtonClass: 'btn btn-success btn-fill spacing',
            cancelButtonClass: 'btn btn-info btn-fill',
            buttonsStyling: false
            }).then(function () {
                http
                .put("/club/activation", club)
                .then(response => {
                  self.tableData[index].activityStop=!club.activityStop
                  swal({
                  title: `Demande Confirmer et Club a Activé avec Succes`,
                  buttonsStyling: false,
                  confirmButtonClass: 'btn btn-success btn-fill',
                  type: 'success'
                  })
                })
                .catch(e => {
                  swal("Erreur","Une Erreur et Produit lors de Désactivation de Club", "error")
                  console.log(e);
                })
              
              })


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
.spacing
{
    margin-right: 10%;
}
.card-content
{
  margin-top:0px
}
</style>
