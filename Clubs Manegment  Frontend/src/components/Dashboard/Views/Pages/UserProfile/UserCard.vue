<template>
  <div class="card card-user">
    <div class="image">
      <img src="static/img/background.jpg" alt="...">
    </div>
    <div class="card-content">
      <div class="author">
        <img class="avatar border-white" :src="avatarUser" alt="...">
        <h4 class="title">{{utilisateur.nom +" "+utilisateur.prenom}}
          <br>
          <a href="#">
            <small>@{{utilisateur.nom +utilisateur.prenom}}</small>
          </a>
        </h4>
      </div>

    </div>
    <hr>
  </div>
</template>
<script>
/* eslint-disable */
  import http from 'src/components/Dashboard/Views/axiosbase.js'

  export default {
   created() {  
        var self = this
        var user = JSON.parse(localStorage.getItem('session'))
        if(user==null||user==undefined || user=='')
          return
        if(user.clubaccess==null||user.clubaccess==undefined || user.clubaccess=='')
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
        utilisateur:'',
        avatarUser:'static/img/membrePhoto/',
      }
    },
    methods: {
      getClasses (index) {
        const remainder = index % 3
        if (remainder === 0) {
          return 'col-md-3 col-md-offset-1'
        } else if (remainder === 2) {
          return 'col-md-4'
        } else {
          return 'col-md-3'
        }
      }
    }
  }

</script>
<style>

</style>
