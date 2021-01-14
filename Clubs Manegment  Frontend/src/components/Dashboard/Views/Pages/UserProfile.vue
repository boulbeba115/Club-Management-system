<template>
  <div class="row">
    <div class="col-lg-4 col-md-5">
      <div class="card card-user">
        <div class="image">
          <img src="static/img/background.jpg" alt="...">
        </div>
        <div class="card-content">
          <div class="author">
            <img class="avatar border-white" :src="avatarUser" alt="...">
            <h4 class="title">{{user.nom +" "+user.prenom}}
              <br>
              <a href="#">
                <small>@{{user.nom +user.prenom}}</small>
              </a>
            </h4>
          </div>

        </div>
        <hr>
      </div>
      <div class="card">
            <div class="card-header">
            </div>
              <div class="card-content" style="text-align:center">
              <el-upload
                v-model="Avatar"
                ref="utilisateurAvatar"
                drag
                :limit=1
                :on-success="showUploadSuc"
                :action='UpImg()'
                :before-upload="beforeUpload"
                :multiple="false"
              >
                      <i class="el-icon-upload"></i>
                      <div class="el-upload__text">Changer de photo Profile </div>
              </el-upload>
              </div>
      </div>
    </div>
    <div class="col-lg-8 col-md-7">
          <div class="card">
              <div class="card-header">
                <h4 class="title">Modifier Profile</h4>
              </div>
              <div class="card-content">
              <el-form :model="user" :rules="rules" ref="user" >
                  <div class="row">
                    <div class="col-md-4">
                      <el-form-item label="Matricule" prop="matricule">
                        <el-input disabled v-model="user.matricule"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-4">
                      <el-form-item label="Cin" prop="cin">
                          <el-input disabled v-model="user.cin"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-4">
                      <el-form-item label="Login" prop="login">
                          <el-input disabled v-model="user.lelogin"></el-input>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-7">
                      <el-form-item label="Email" prop="email">
                        <el-input  v-model="user.email" placeholder="Email"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-5">
                      <el-form-item label="Téléphone" prop="tel">
                        <el-input  v-model="user.tel" placeholder="Téléphone"></el-input>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-4">
                      <el-form-item label="Nom" prop="nom">
                        <el-input disabled v-model="user.nom"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-4">
                      <el-form-item label="Prenom" prop="prenom">
                          <el-input disabled v-model="user.prenom"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-4">
                      <el-form-item label="date de Naissance" prop="dateNaiss">
                        <el-input disabled v-model="user.dateNaiss"></el-input>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12">
                      <el-form-item label="Adresse" prop="adresse">
                        <el-input  v-model="user.adresse"></el-input>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-4">
                      <el-form-item label="Ancien Mot De Pass" prop="old">
                        <el-input type="Password" v-model="user.old" placeholder="Ancien Mot De Pass"></el-input>
                      </el-form-item>
                    </div>
                    <div class="col-md-4">
                      <el-form-item label="Nouveau Mot De Pass" prop="newpass">
                        <el-input type="Password" v-model="user.newpass"  placeholder="Nouveau Mot De Pass"></el-input>
                      </el-form-item>
                    </div>
                      <div class="col-md-4">
                      <el-form-item label="Confirmer Mot De Pass" prop="confirm">
                        <el-input type="Password" v-model="user.confirm"  placeholder="Confirmer Mot De Pass"></el-input>
                      </el-form-item>
                    </div>
                  </div>
            <!--
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label>About Me</label>
                        <textarea rows="5" class="form-control border-input"
                                  placeholder="Here can be your description"
                                  v-model="user.aboutMe">

                        </textarea>
                      </div>
                    </div>
                  </div>-->
                  <div class="text-center">
                    <button type="submit" class="btn btn-info btn-fill btn-wd" @click.prevent="submitForm()">
                      Update Profile
                    </button>
                  </div>
                  <div class="clearfix"></div>
                </el-form>
              </div>
            </div>
    </div>
  </div>
</template>
<script>
/* eslint-disable */

  import Vue from 'vue'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  import swal from 'sweetalert2'
  import {Upload} from 'element-ui'
  Vue.use(Upload)
  export default {
    data () {
      var isdigit = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('Ce Champ est Obligatoire'));
        }
        setTimeout(() => {
          if (!Number.isInteger(parseInt(value),10)) {
            callback(new Error('Ce champ doit étre formé par des digitaux'));
          }
          else 
          {
            callback();
          }
        }, 1000);
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Entré le nouveau mot de pass'));
        } else {
          if (this.user.newpass !== '') {
            this.$refs.user.validateField('confirm');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Ré-insérer le nouveau mot de pass'));
        } else if (value !== this.user.newpass) {
          callback(new Error('Le Mot de pass et la Confirmation ne sont pas identique'));
        } else {
          callback();
        }
      };

      return {
        avatarUser:'static/img/membrePhoto/',
        Avatar:'',
        user: {
        id: '',
        email: '',
        matricule: '',
        nom:'',
        prenom:'',
        tel: '',
        old:'',
        newpass:'',
        confirm:''
        },
        rules: {
          email: [
            { required: true ,message: "l'email est obligatoire", trigger: 'blur' },
            { type:'email' ,message: "la format d'email n'est pas valide", trigger: 'blur' }
          ],
          tel: [
            { required: true ,message: "l'email est obligatoire", trigger: 'change' },
            { min: 8, max: 8, message: 'Le numéro de télephone est formé par 8 ', trigger: 'blur' },
            { validator: isdigit, trigger: 'blur' }
          ],
          old: [
            { required: true ,message: "Le saisie de Mot de Pass est nécessaire", trigger: 'blur' },
            { validator: validatePass, trigger: 'blur' }
          ],
          newpass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          confirm: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }
      
    },
 
      created() {  
        var self = this
        var user = JSON.parse(localStorage.getItem('session'))
        if(user==null||user==undefined || user=='')
          return
        if(user.role==null||user.role==undefined || user.role=='')
        return
        if(user.role!='etudiant')
        {
          http
            .get("utilisateur/"+user.username)
            .then(response => {
              console.log(response.data)
              self.user = response.data;
              self.avatarUser += self.user.utilisateurAvatar
            })
            .catch(e => {
              console.log(e);
            })
        }
        else
        {
            http
            .get("/etudiant/findbylog/"+user.username)
            .then(response => {
              self.user = response.data; 
              self.avatarUser += self.user.utilisateurAvatar
            })
            .catch(e => {
              console.log(e);
            })
        }
      },
    components: {
    },
    methods: {
      UpImg(){
        return "http://localhost:8090/api/User/logo/"+ this.user.id
      },
      beforeUpload(){

      },
      showUploadSuc(response)
      {
        this.avatarUser='static/img/membrePhoto/'+response
        swal('','Avatar Modifier Avec Succes','success')
      },
      submitForm() {
        this.$refs["user"].validate((valid) => {
          if (valid) {
            this.updateProfile()
          } else {
            return false;
          }
        });
      },
      updateProfile () {
        var self= this
        let UpdateInfo = {
        id:this.user.id,
        email:this.user.email,
        tel:this.user.tel,
        lelogin:this.user.lelogin,
        adresse:this.user.adresse,
        motPass:this.user.newpass,
        oldMotPass:this.user.old
        }
         swal({
            title: 'Confirmation',
            text: `voulez vous Confirmer La Modification de vos information personnel!`,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Modifier',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
              http.put("/utilisateur/update",UpdateInfo)
                .then(response => {
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Information Personel Modifer Avec succès',
                })
              })
          })
      }
    }
  }


</script>
<style>
input.el-upload__input {
    display: none;
}
.title {
    text-align: center;
}
.el-upload-dragger {
    padding-left: 80px;
    padding-right: 80px;
    width: 100%;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0; 
}

input[type=number] {
    -moz-appearance:textfield; 
}
.erreurSai {
  border: #f17171 1px solid;
}
</style>
