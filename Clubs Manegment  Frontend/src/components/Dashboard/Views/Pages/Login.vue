<template>
  <div>

    <div class="wrapper wrapper-full-page">
      <div class="full-page login-page" data-color=""
           data-image="static/img/background/background-4.jpg">
        <div class="content">
          <div class="container">
            <div  style="margin: auto -144px;" class="row">
              <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                <form method="#" action="#">
                  <img class="logimg" src="static\img\fseglogo.png">
                  <div class="card" data-background="color" data-color="blue">
                    <div class="card-header"  style="text-align:center;position:relative;top:40px">
                      <h3 class="card-title">Connecter</h3>
                          <small class="text-danger" v-show="auth==true">{{validauth}}</small>               
                    </div>
                    <div class="card-content">
                      <div class="form-group">
                        <label>Nom Utilisateur</label>
                        <input type="Text" placeholder="Entré Matricule" v-model="log" @focus="errlog=false"  class="form-control input-no-border inpu">
                                <small class="text-danger" v-show="errlog">
                                  {{validlog}}
                                </small>                        
                      </div>
                      <div class="form-group">
                        <label>Mot de Pass</label>
                        <input type="password" placeholder="Entré Mot de Pass" @focus="errpass=false" v-model="pass" class="form-control input-no-border inpu">
                                <small class="text-danger" v-show="errpass">
                                  {{validpass}}
                                </small>                    
                      </div>
                    </div>
                    <div class="card-footer text-center">
                      <button type="submit" class="btn btn-fill btn-wd " v-loading.fullscreen.lock="fullscreenLoading"  @click.prevent="login" >Connecter</button>
                      <div class="forgot">
                        <router-link to="/register">
                          Mot de Pass Oubliez ?
                        </router-link>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>

        <footer class="footer footer-transparent">

        </footer>
        <div class="full-page-background" style="background-image: url(static/img/background/background-3.jpg) "></div>
      </div>
    </div>

<el-dialog
        title="Connecter au Club"
        :modalAppendToBody="false"
        center
        :visible.sync="choix"
        width="40%">
          <ul class="listeclub">
            <li class="lielem">
               <img  style="width:100px" :src='"static/img/membrePhoto/"+studentaccess.utilisateurAvatar' @click="sessionetudiant()">
               <br/>
                <span >Etudiant</span>
            </li>
            <li v-for="club in lesclub.filter(data => data.roleEtudiant ==='Secrétaire Génerale')" class="lielem" @click="sessionclub(club)">
               <img  style="width:100px" :src='"static/img/ClubLogos/"+club.club.clubLogo'>
               <br/>
                <span >{{club.club.nomClub}}</span>

            </li>
          </ul>
      </el-dialog>



  </div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import http from '../axiosbase.js'
  import { router } from '../../../../main.js'
  import {Loading, TableColumn, Select, Button} from 'element-ui'

  export default {
    created () {
       // userService.logout();
        this.returnUrl = this.$route.query.returnUrl || '/';
        if(localStorage.getItem('session')!=null)
        this.$router.push('/')
    },
    data () {
      return {
        fullscreenLoading:false,
        lesclub:[],
        log: '',
        pass: '',
        validlog: '',
        dataacces:'',
        errlog:false,
        validpass: '',
        errpass:false,
        auth:false,
        returnUrl:'',
        validauth:'',
        user:{
          lelogin:'',
          motPass:''
        },
        session:{
          username:'',
          role:''
        },
        studentaccess:{
          id:'',
          utilisateurAvatar:''
        },
        choix:false
      }
    },
    methods: {
      login () {
        var user
        let self=this
        if(this.log==''||this.pass==''){
        if(this.log=='')
        {
          this.validlog='La Matricule est Vide'
          this.errlog=true;
        }
        if(this.pass=='')
        {
          this.validpass='le Mot de Pass est Vide'
          this.errpass=true;
        }
        }
        else
        {
            this.user.lelogin=this.log;
            this.user.motPass=this.pass
            http
            .post("login",this.user)
            .then(response => {
              self.dataacces=response.data

              if(self.dataacces=='no_acces'||self.dataacces==''||self.dataacces==null)
              {
                this.auth=true
                this.validauth="Verifier vos Données"
              }
              else
              {
               self.auth=false
               if(self.dataacces=="etudiant")
               {
                 this.isStudent()
               }
               else
               {
               self.session.username=this.log
               self.session.role=self.dataacces
               localStorage.setItem('session', JSON.stringify(self.session))
               this.openFullScreen()
               }
              }
            })
            .catch(e => {
              this.validlog="Utilisateur n'existe Pas"
              this.errlog=true            
              })
        }         


      },
      openFullScreen() {
        this.fullscreenLoading = true
        setTimeout(() => {
          this.fullscreenLoading = false
          this.$router.push('/')
        }, 500)

      },
      isStudent(){
        var self = this
        var session={
              username:'',
              role:'',
              clubaccess:'',
              club:''
            }
          http
          .get("/etudiant/findbylog/"+this.user.lelogin)
          .then(response => {
            self.studentaccess=response.data
            let obj = self.studentaccess.lesclub.find(data => data.roleEtudiant ==='Secrétaire Génerale')
            if(self.studentaccess.lesclub!=null||self.studentaccess.lesclub!=undefined)
            {
                if(self.studentaccess.lesclub.length==0)
                {
                    self.session.username=this.log
                    self.session.role=self.dataacces
                    localStorage.setItem('session', JSON.stringify(self.session))
                    this.openFullScreen()              
                }
                else
                if(self.studentaccess.lesclub.length>=1 && obj!=null)
                {
                  self.choix=true
                  self.lesclub=self.studentaccess.lesclub
                }
                else
                {
                self.sessionetudiant()
                }
            }
          })
          .catch(e => {
          console.log(e);
          })

      },
      sessionetudiant(){
        var session={
              username:'',
              role:'',
            }
          session.username=this.log
          session.role='etudiant'
          localStorage.setItem('session', JSON.stringify(session))
          this.$router.push('/')
        this.openFullScreen()              
      },
      sessionclub(c){
            var session={
              username:'',
              role:'',
              clubaccess:'',
              club:''
            }
          session.username=this.log
          session.role=this.dataacces
          session.clubaccess=c.roleEtudiant
          session.club=c.club.nomClub
          localStorage.setItem('session', JSON.stringify(session))
          this.$router.push('/')
          this.openFullScreen()  

      }
    },

  }

</script>
<style>
.login-page > .content, .lock-page > .content {
    padding-top: 0;
}
.logimg {
    margin: 0 auto;
    display: block;
    width: 28%;
    position: relative;
    top: 60px;
    z-index: 10000;
}
body {
    overflow-x: hidden;
    overflow-y: hidden;
}
.inpu{
  border-radius: 60px;
}
.card-content {
    margin-top: 6%;
}
.listeclub {
list-style: none;
display: flex;
flex-wrap: wrap;
justify-content: center;
width: 90%;
}
.lielem {
  margin-left:2%;
  text-align:center;
  border: #9dd1ff 1px solid;
}
.lielem:hover{
    background-color: #f6fbfb;
}
</style>
