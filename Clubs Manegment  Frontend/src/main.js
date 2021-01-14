/* eslint-disable */

import Vue from 'vue'
import './pollyfills'
import VueRouter from 'vue-router'
import VueNotify from 'vue-notifyjs'
import VeeValidate from 'vee-validate'
import lang from 'element-ui/lib/locale/lang/fr'
import locale from 'element-ui/lib/locale'
import App from './App.vue'

// Plugins
import GlobalComponents from './gloablComponents'
import GlobalDirectives from './globalDirectives'
//sidebars
import SideBar from './components/UIComponents/SidebarPlugin'


// router setup
import routes from './routes/routes'

// library imports

import './assets/sass/paper-dashboard.scss'
import './assets/sass/element_variables.scss'
import './assets/sass/demo.scss'

import sidebarLinks from './sidebarLinks'


Vue.use(VueRouter)
Vue.use(GlobalDirectives)
Vue.use(GlobalComponents)
Vue.use(VueNotify)
Vue.use(VeeValidate)
locale.use(lang)
Vue.use(SideBar, {sidebarLinks: sidebarLinks})


// configure router
var router = new VueRouter({
  routes, // short for routes: routes
  linkActiveClass: 'active'
})


router.beforeEach((to, from, next) => {
  const authUser = JSON.parse(window.localStorage.getItem('session'))
  const publicPages = ['/login']
  const authRequired = !publicPages.includes(to.path)
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!authUser && authRequired) {
      return next({ 
        path: '/login', 
        query: { returnUrl: to.path } 
      })
    } 
    else {
      if(authUser.role=='etudiant')
      {

        if(authUser.club !=null && authUser.clubaccess !=null)
        {
          if(authUser.clubaccess=='Secrétaire Génerale')
          {
            if (to.matched.some(record => record.meta.isSecretaire))
              {
              next()
              }
            else
              {
              next("/404")
              }
          }
          else if(authUser.clubaccess=='membre')
          {
            if (to.matched.some(record => record.meta.isMember))
              {
              next()
              }
            else
              {
              next("/404")
              }
          }
        }
        else
        { 
          if (to.matched.some(record => record.meta.isStudent))
          {
            next()
          }
          else
          {
            next("/404")     
          }
        }
      }
      else
      if(authUser.role == 'ResponsableSalles')
      {
        if (to.matched.some(record => record.meta.isResponsableSalle))
          {
            next()
          }
          else
            next("/404")
      }
      else
      if(authUser.role == 'ResponsableMateriel')
      {
        if (to.matched.some(record => record.meta.isResponsableMat))
          {
            next()
          }
          else
            next("/404")
      }
      else
      if(authUser.role == 'admin')
      {
        if (to.matched.some(record => record.meta.isadmin))
          {
            next()
          }
          else
          {
            next("/404")
          }
      }
    }
  } 
  else {
    next() 
  }
})


        
/*
router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('session')

  if (authRequired && !loggedIn) {
    return next({ 
      path: '/login', 
      query: { returnUrl: to.path } 
    })
  }

  next()
})*/

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router
})
