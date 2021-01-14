<template>
  <div class="card">
    <div class="card-header">
      <h4 class="title">{{title}}</h4>
    </div>
    <div class="card-content">
      <ul class="list-unstyled team-members">
        <li>
          <div class="row" v-for="member in members">
            <div class="col-xs-3">
              <div class="avatar">
                <img :src="member.image" alt="Circle Image" class="img-circle img-no-padding img-responsive">
              </div>
            </div>
            <div class="col-xs-6">
              {{member.name}}
              <br>
              <!-- <span :class="getStatusClass(member.status)">
                <small>{{member.status}}</small>
              </span>-->
            </div>

            <div class="col-xs-3 text-right">
              <button class="btn btn-sm btn-success btn-icon">
                <i class="fa fa-envelope"></i>
              </button>
            </div>
          </div>
        </li>
      </ul>
    </div>
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
          this.isadmin=true
          return
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
        isadmin:false,
        title: 'Membres De Club',
        members: []
      }
    },
    methods: {
    }
  }

</script>
<style>

</style>
