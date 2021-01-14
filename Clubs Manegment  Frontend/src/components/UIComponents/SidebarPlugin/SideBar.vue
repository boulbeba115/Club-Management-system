<template>
  <div :class="sidebarClasses"
       :data-background-color="backgroundColor"
       :data-active-color="activeColor">
    <div class="logo">
      <a class="simple-text logo-mini"
         href="">
          <div class="logo-img">
              <img :src="logo" alt="">
          </div>
      </a>
      <a class="simple-text logo-normal"
         href="">
          {{ title }}
      </a>
    </div>
    <div class="sidebar-wrapper" ref="sidebarScrollArea">
      <slot>

      </slot>
      <ul :class="navClasses">
        <slot name="links">
          <sidebar-item v-for="(link, index) in userSideBar"
                        :key="link.name + index"
                        :link="link">

            <sidebar-item v-for="(subLink, index) in link.children"
                          :key="subLink.name + index"
                          :link="subLink">
            </sidebar-item>
          </sidebar-item>
          
        </slot>

      </ul>
    </div>
  </div>
</template>
<script>
/* eslint-disable */

import sidebarLinks from 'src/sidebarLinks'
import adminSideBar from 'src/sidebarlinks/sidebaradmin.js'
import Sidebaretudiant from 'src/sidebarlinks/sidebaretudiant.js'
import SidebarresponsableMat from 'src/sidebarlinks/sidebarresponsableMat.js'
import SidebarresponsableSalle from 'src/sidebarlinks/sidebarresponsableSalle.js'
import ClubAdminsidebnar from 'src/sidebarlinks/clubadminsidebnar.js'
  export default {
    created(){
    const authUser = JSON.parse(window.localStorage.getItem('session'))
   // console.log(authUser.role)
    if(authUser==null)
    {
        this.$router.push('/login')
    }
    else
    {
      if(authUser.role=='etudiant')
      {
        if(authUser.clubaccess!=null)
        {
          if(authUser.clubaccess=='Secrétaire Génerale' || authUser.clubaccess=='Président')
          this.userSideBar=ClubAdminsidebnar
        }
        else
        this.userSideBar=Sidebaretudiant
      }
      else 
      if(authUser.role == 'admin')
      this.userSideBar=adminSideBar
      else 
      if(authUser.role == 'ResponsableSalles')
      this.userSideBar=SidebarresponsableSalle
      else 
      if(authUser.role == 'ResponsableMateriel')
      this.userSideBar=SidebarresponsableMat
      else
      this.userSideBar=this.sidebarLinks
    }
    },
    data(){
      return {
        userSideBar:[]
      }
    },
    props: {
      title: {
        type: String,
        default: 'Gestion des Club Fsegs'
      },
      type: {
        type: String,
        default: 'sidebar',
        validator: (value) => {
          let acceptedValues = ['sidebar', 'navbar']
          return acceptedValues.indexOf(value) !== -1
        }
      },
      backgroundColor: {
        type: String,
        default: 'black',
        validator: (value) => {
          let acceptedValues = ['white', 'brown', 'black']
          return acceptedValues.indexOf(value) !== -1
        }
      },
      activeColor: {
        type: String,
        default: 'success',
        validator: (value) => {
          let acceptedValues = ['primary', 'info', 'success', 'warning', 'danger']
          return acceptedValues.indexOf(value) !== -1
        }
      },
      logo: {
        type: String,
        default: 'static/img/fseglogo.png'
      },
      sidebarLinks: {
        type: Array,
        default: () => []
      }
    },
    computed: {
      sidebarClasses () {
        if (this.type === 'sidebar') {
          return 'sidebar'
        } else {
          return 'collapse navbar-collapse off-canvas-sidebar'
        }
      },
      navClasses () {
        if (this.type === 'sidebar') {
          return 'nav'
        } else {
          return 'nav navbar-nav'
        }
      }
    },
    methods: {
      async initScrollBarAsync () {
        await import('perfect-scrollbar/dist/css/perfect-scrollbar.css')
        const PerfectScroll = await import('perfect-scrollbar')
        PerfectScroll.initialize(this.$refs.sidebarScrollArea)
      }
    },
    mounted () {
      this.initScrollBarAsync()
    },
    beforeDestroy () {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.showSidebar = false
      }
    }
  }

</script>
<style>
  @media (min-width: 992px) {
    .navbar-search-form-mobile,
    .nav-mobile-menu{
      display: none;
    }
  }
</style>
