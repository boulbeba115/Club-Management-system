<template>
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-minimize">
        <button class="btn btn-fill btn-icon" @click="minimizeSidebar">
          <i :class="$sidebar.isMinimized ? 'ti-menu-alt' : 'ti-more-alt'"></i>
        </button>
      </div>
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" :class="{toggled: $sidebar.showSidebar}" @click="toggleSidebar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar bar1"></span>
          <span class="icon-bar bar2"></span>
          <span class="icon-bar bar3"></span>
        </button>
        <a class="navbar-brand">{{this.$route.name}}</a>
      </div>
      <div class="collapse navbar-collapse">
        <form class="navbar-form navbar-left navbar-search-form" role="search">
          <div class="input-group">
            <input type="text" value="" class="form-control" placeholder="Search...">
            <span class="input-group-addon"><i class="fa fa-search"></i></span>
          </div>
        </form>
        <ul class="nav navbar-nav navbar-right">
          <li class="open">
            <router-link to="/admin/stats" class="dropdown-toggle btn-magnify" data-toggle="dropdown">
              <i class="ti-panel"></i>
              <p>Stats</p>
            </router-link>
          </li>
          <drop-down tag="li" :title='demEvent.length.toString()' icon="ti-bell">
            <div>
            <hr style="margin-top:5px;"/>
            <template v-for="notif in lesnotifications">
              <li class="notifi" style="min-width:400px;max-width:500px">
                <div class="row" style="margin-left: 5px;">
                  <div class="col-sm-3">
                    <img class="notif-logo" src="static/img/microsoftLogo.png" >
                  </div>
                  <div class="col-sm-7">
                    <div class="row">
                     <a href="#">{{notif.titre}}</a>
                    </div>
                    <div class="row">
                      {{notif.contenu}}
                    </div>
                  </div>
                  <span><a class=""><i class=" closeico ti-close"></i></a></span>
                </div>
                <hr/>
              </li>
            </template>
           </div>
          </drop-down>
          <li>
            <router-link to="/admin/overview" class="btn-rotate">
              <i class="ti-settings"></i>
            </router-link>
          </li>
          <li class="open">
              <a @click="logout" class="dropdown-toggle btn-magnify" data-toggle="dropdown">
                <p class="cur">Déconnection</p>
              </a>
          </li>          
        </ul>
      </div>
    </div>
  </nav>
</template>
<script>
/* eslint-disable */
  import http from '../Views/axiosbase.js'
  export default {
    created()
    {
        var self = this;
        http.get('DemandeEvent/nonconfirmer')
        .then(function (response) {
        self.demEvent = response.data;
        if(self.demEvent.length>0)
        {
        for(let demande of self.demEvent)
        {
         var Notification = {
                      id:'',
                      titre:'',
                      contenu:'',
                      date:''
                  }
          Notification.id=demande.id,
          Notification.titre="Demande Evénement",
          Notification.contenu=demande.nomEvenement,
          Notification.date=demande.datePrevuEvent,
          self.lesnotifications.push(Notification)
        }
        }
      })
    },
    data () {
      return {
        activeNotifications: false,
        demEvent:[],
        lesnotifications:[]
      }
    },
    methods: {
      capitalizeFirstLetter (string) {
        return string.charAt(0).toUpperCase() + string.slice(1)
      },
      toggleNotificationDropDown () {
        this.activeNotifications = !this.activeNotifications
      },
      closeDropDown () {
        this.activeNotifications = false
      },
      toggleSidebar () {
        this.$sidebar.displaySidebar(!this.$sidebar.showSidebar)
      },
      hideSidebar () {
        this.$sidebar.displaySidebar(false)
      },
      minimizeSidebar () {
        this.$sidebar.toggleMinimize()
      },
      logout () {
        localStorage.removeItem('session')
        location.reload()
      }
    }
  }

</script>
<style >
.cur{
  cursor: default;
}
.notif-logo{
    width: 50px;
    height: 50px;
    background-color: #ebe3e3;
    border-radius: 37px;
}
.closeico{
    font-size: 10px;
    background: #e5e4e4;
    padding: 4px;
    border-radius: 11px;
    position: relative;
    top: 11px;
    color: white;
}
.closeico:hover {
    background-color: gray;
    color: white;
}

.notif:hover {
    background-color: gray;
    color: white;
}
.notifi:hover{
    background-color: #f6fbfb;
}
</style>
