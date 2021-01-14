<template>
  <div class="user">
    <div class="photo">
      <img :src='avatarUser'/>
    </div>
    <div class="info">
      <a data-toggle="collapse" @click="toggleMenu" href="javascript:void(0)">
           <span>
             {{utilisateur.nom +" "+utilisateur.prenom}}
             <b class="caret"></b>
          </span>
      </a>
      <div class="clearfix"></div>
      <div>
        <el-collapse-transition>
          <ul class="nav" v-show="!isClosed">
            <li>
              <router-link tag="li" to="/pages/user">
                <a href="javascript:void(0)">
                <div style="margin-left: 22%;">
                <span class="sidebar-mini">MP</span>
                <span class="sidebar-normal">Mon Profil</span></div>
                </a>
              </router-link>
            </li>
            <li>
            </li>
          </ul>
        </el-collapse-transition>
      </div>
    </div>
  </div>
</template>
<script>
/* eslint-disable */

  import CollapseTransition from 'element-ui/lib/transitions/collapse-transition'
  import http from 'src/components/Dashboard/Views/axiosbase.js'
  export default {
    components: {
      [CollapseTransition.name]: CollapseTransition
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
          self.utilisateur = response.data; 
          self.avatarUser += self.utilisateur.utilisateurAvatar
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
          self.utilisateur = response.data; 
          self.avatarUser += self.utilisateur.utilisateurAvatar
        })
        .catch(e => {
          console.log(e);
        })
    }

    },
    data () {
      return {
        isClosed: true,
        utilisateur:'',
        avatarUser:'static/img/membrePhoto/'
      }
    },
    methods: {
      toggleMenu () {
        this.isClosed = !this.isClosed
      }
    }
  }
</script>
<style>
  .collapsed {
    transition: opacity 1s;
  }
</style>
