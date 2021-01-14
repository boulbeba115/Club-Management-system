/* eslint-disable */
import DashboardLayout from '../components/Dashboard/Layout/DashboardLayout.vue'
// GeneralViews
import NotFound from '../components/GeneralViews/NotFoundPage.vue'
// Dashboard pages
import Overview from 'src/components/Dashboard/Views/Dashboard/Overview.vue'
import Stats from 'src/components/Dashboard/Views/Dashboard/Stats.vue'
import MonClub from 'src/components/Dashboard/Views/Dashboard/MonClub.vue'

// Pages
import User from 'src/components/Dashboard/Views/Pages/UserProfile.vue'
import TimeLine from 'src/components/Dashboard/Views/Pages/TimeLinePage.vue'
import Login from 'src/components/Dashboard/Views/Pages/Login.vue'
import Register from 'src/components/Dashboard/Views/Pages/Register.vue'
import Lock from 'src/components/Dashboard/Views/Pages/Lock.vue'

// Components pages
import Buttons from 'src/components/Dashboard/Views/Components/Buttons.vue'
import GridSystem from 'src/components/Dashboard/Views/Components/GridSystem.vue'
import Panels from 'src/components/Dashboard/Views/Components/Panels.vue'
import SweetAlert from 'src/components/Dashboard/Views/Components/SweetAlert.vue'
import Notifications from 'src/components/Dashboard/Views/Components/Notifications.vue'
import Icons from 'src/components/Dashboard/Views/Components/Icons.vue'
import Typography from 'src/components/Dashboard/Views/Components/Typography.vue'
import Boulbeba from 'src/components/Dashboard/Views/Components/Boulbeba.vue'

// Forms pages
import RegularForms from 'src/components/Dashboard/Views/Forms/RegularForms.vue'
import ExtendedForms from 'src/components/Dashboard/Views/Forms/ExtendedForms.vue'
import ValidationForms from 'src/components/Dashboard/Views/Forms/ValidationForms.vue'
import Wizard from 'src/components/Dashboard/Views/Forms/Wizard.vue'

// TableList pages
import RegularTables from 'src/components/Dashboard/Views/Tables/RegularTables.vue'
import ExtendedTables from 'src/components/Dashboard/Views/Tables/ExtendedTables.vue'
import PaginatedTables from 'src/components/Dashboard/Views/Tables/PaginatedTables.vue'
// Maps pages
import GoogleMaps from 'src/components/Dashboard/Views/Maps/GoogleMaps.vue'
import FullScreenMap from 'src/components/Dashboard/Views/Maps/FullScreenMap.vue'
import VectorMaps from 'src/components/Dashboard/Views/Maps/VectorMapsPage.vue'

// Calendar
import Calendar from 'src/components/Dashboard/Views/Calendar/CalendarRoute.vue'
// Charts
import Charts from 'src/components/Dashboard/Views/Charts.vue'
// gere club
import Ajoutdemande from 'src/components/Dashboard/Views/gereclub/ajoutdemande.vue'
import Lesdemande from 'src/components/Dashboard/Views/gereclub/lesdemande.vue'
import Clubs from 'src/components/Dashboard/Views/gereclub/LesClub.vue'
//gérer Eevenement
import DemandeEvent from 'src/components/Dashboard/Views/event/demandeEvent.vue'
import LesDemandeEvent from 'src/components/Dashboard/Views/event/lesDemandeEvent.vue'
import LesClubDemandeEvent from 'src/components/Dashboard/Views/event/clubDemandesEvenemet.vue'
import DetailDemandeEvent from 'src/components/Dashboard/Views/event/detailDemandeEvent.vue'
import TousEventClubs from 'src/components/Dashboard/Views/event/TousEventClubs.vue'
import EvenementDeClub from 'src/components/Dashboard/Views/event/EvenementDeClub.vue'
import DetailEvenement from 'src/components/Dashboard/Views/event/DetailEvenement.vue'
import DetailEvenementSoft from 'src/components/Dashboard/Views/event/DetailEvenementSoft.vue'
import DemmandeAffiche from 'src/components/Dashboard/Views/event/DemmandeAffiche.vue'
import DemandeFinanciere from 'src/components/Dashboard/Views/event/DemandeFinanciere.vue'


//salles
import LesSalles from 'src/components/Dashboard/Views/gerelesSalle/LesSalles.vue'
import SalleCalendar from 'src/components/Dashboard/Views/gerelesSalle/SalleCalendarRoute.vue'

//materielle
import Lesmateriels from 'src/components/Dashboard/Views/gereLesMat/Lesmateriels.vue'
import MatCalendar from 'src/components/Dashboard/Views/gereLesMat/MatCalendarRoute.vue'
import LesmaterielExterne from 'src/components/Dashboard/Views/gereLesMat/LesmaterielExterne.vue'
//Resource
import LesSponsors from 'src/components/Dashboard/Views/monClub/LesSponsor.vue'
import LesMembres from 'src/components/Dashboard/Views/monClub/LesMembre.vue'
import LesFournisseur from 'src/components/Dashboard/Views/monClub/LesFournisseur.vue'


//lesRéunion
import PlanifReunion from 'src/components/Dashboard/Views/reunion/PlanifReunion.vue'
import TousLesReunion from 'src/components/Dashboard/Views/reunion/TousLesReunion.vue'
import LesReuniondeClub from 'src/components/Dashboard/Views/reunion/LesReuniondeClub.vue'
import DetailReunion from 'src/components/Dashboard/Views/reunion/DetailReunion.vue'
//les Inscription
import EventInscription from 'src/components/Dashboard/Views/lesInscription/eventInscription.vue'
import ClubInscription from 'src/components/Dashboard/Views/lesInscription/ClubInscription.vue'
import lesInscriptionClub from  'src/components/Dashboard/Views/lesInscription/lesInscriptionClub.vue'
import MesInscriptions from  'src/components/Dashboard/Views/lesInscription/MesInscriptions.vue'
import DetailEventInscrip from  'src/components/Dashboard/Views/Pages/DetailEventInscrip.vue'
import MesEventInscrit from  'src/components/Dashboard/Views/lesInscription/MesEventInscrit.vue'
import EvenementsInscription from  'src/components/Dashboard/Views/lesInscription/EvenementsInscription.vue'
import AllClubEventInscrip from  'src/components/Dashboard/Views/lesInscription/AllClubEventInscrip.vue'


let gerelesClub = {
  path: '/gereclub',
  component: DashboardLayout,
  redirect: '/gereclub/lesdemande',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'lesdemande',
      name: 'lesdemande de creation de club',
      component: Lesdemande,
      meta: { isadmin: true }
    },
    {
      path: 'ajoutdemande',
      name: 'ajoutdemande',
      component: Ajoutdemande,
      meta: { isStudent: true , isMember:true },

    },
    {
      path: 'Clubs',
      name: 'Clubs',
      component: Clubs,
      meta: { isadmin: true }
    }
  ]
}


let lesinscriptions = {
  path: '/inscriptions',
  component: DashboardLayout,
  redirect: '/inscriptions/inscriptionClub',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'inscriptionClub',
      name: 'Inscription au Club',
      component: ClubInscription,
      meta: { isStudent: true , isMember:true },
    },
    {
      path: 'inscriptionEvenement',
      name: 'Inscription AU Evénement',
      component: EventInscription,
      meta: { isStudent: true , isMember:true },

    },
    {
      path: 'membreDeClub',
      name: 'Gestion des adhérents',
      component: lesInscriptionClub,
      meta: {isSecretaire:true},
    },
    {
      path: 'mesInscriptions',
      name: 'Mes Inscriptions',
      component: MesInscriptions,
      meta: {isStudent:true,isMember:true},
    },
    {
      path: 'mesEventInscription',
      name: 'Mes événement ',
      component: MesEventInscrit,
      meta: {isStudent:true,isMember:true},
    },
    {
      path: 'evenementsInscritption',
      name: 'Événements Inscription ',
      component: EvenementsInscription,
      meta: {isSecretaire:true},
    },
    {
      path: 'allClubEventInscrip',
      name: 'Clubs Événements',
      component: AllClubEventInscrip,
      meta: {isadmin:true},
    }
  ]
}


let lesReunion = {
  path: '/Reunion',
  component: DashboardLayout,
  redirect: '/Reunion/PlanifierReunion',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'planifierReunion',
      name: 'PlanifierReunion',
      component: PlanifReunion,
      meta: {isSecretaire:true},
    },
    {
      path: 'lesReuniondeClub',
      name: 'LesReuniondeClub',
      component: LesReuniondeClub,
      meta: {isSecretaire:true},
    },
    {
      path: 'tousLesReunion',
      name: 'TousLesReunion',
      component: TousLesReunion,
      meta: {isadmin:true},
    },
    {
      path: 'DetailReunion',
      name: 'DetailReunion',
      component: DetailReunion,
      props:true,
      meta: {isadmin:true,isSecretaire:true},
    }
  ]
}

let monClub = {
  path: '/monclub',
  component: DashboardLayout,
  redirect: '/monclub/lesmembre',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'lesmembres',
      name: 'Lesmembres',
      component: LesMembres,
      meta: {isSecretaire:true},
    },
    {
      path: 'lesSponsors',
      name: 'Les Sponsors',
      component: LesSponsors,
      meta: {isSecretaire:true},
    },
    {
      path: 'lesfournisseurs',
      name: 'Les Fournisseurs',
      component: LesFournisseur,
      meta: {isSecretaire:true},
    },
    
  ]
}

let gereevent = {
  path: '/demandeEvenement',
  component: DashboardLayout,
  redirect: '/demandeEvenement/evenementdeclub',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'demandeEvent',
      name: "Demande d'événement",
      component: DemandeEvent,
      meta: {isSecretaire:true},
    },
    {
      path: 'lesDemandesEvenemet',
      name: "Les Demande D'évenement",
      component: LesDemandeEvent,
      meta: { isadmin: true }
    },
    {
      path: 'clubDemandesEvenemet',
      name: "ListeDemande",
      component: LesClubDemandeEvent,
      meta: {isSecretaire:true}

    },
    {
      path: 'detailDemandeEvent',
      name: "Plusdetails",
      component: DetailDemandeEvent,
      props: true,
      meta: { isSecretaire:true , isadmin : true},

    },
    {
      path: 'touseventclubs',
      name: "TousEventClubs",
      component: TousEventClubs,
      props: true,
      meta: {isadmin : true},

    },
    {
      path: 'evenementdeclub',
      name: "EvenementDeClub",
      component: EvenementDeClub,
      props: true,
      meta: {isSecretaire:true},
    },
    {
      path: 'detailEvenement',
      name: "DetailEvenement",
      component: DetailEvenement,
      props: true,
      meta: {isSecretaire:true, isadmin : true},
    },
    {
      path: 'detailDemandeEventSoft',
      name: "Detail événement",
      component: DetailEvenementSoft,
      props: true,
      meta: { isSecretaire:true , isadmin : true},

    },
    {
      path: 'lesDemandeAffiche',
      name: "lesDemmandeAffiche",
      component: DemmandeAffiche,
      props: true,
      meta: {isadmin : true},
    },
    {
      path: 'lesDemandeFinance',
      name: "Les demande Financements",
      component: DemandeFinanciere,
      meta: {isadmin : true},
    },
    
  ]
}


let gerelesmat = {
  path: '/gere-les-materiel',
  component: DashboardLayout,
  redirect: '/gere-les-materiel/lesmateriels',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'lesmateriels',
      name: 'Lesmateriels',
      component: Lesmateriels,
      meta: { isResponsableMat: true }

    },
    {
      path: 'lesdemandemateriel',
      name: 'affectationMat',
      component: MatCalendar,
      meta: { isResponsableMat: true }

    },
    {
      path: 'lesmaterielexterne',
      name: 'LesmaterielExterne',
      component: LesmaterielExterne,
      meta: {isSecretaire:true }

    }
  ]
}

let gerelessalle = {
  path: '/gere-les-salles',
  component: DashboardLayout,
  redirect: '/gere-les-salles/lessalles',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'lessalles',
      name: 'LesSalles',
      component: LesSalles,
      meta: { isResponsableSalle: true }
    },
    {
      path: 'lesdemandesalle',
      name: 'affecterSalles',
      component: SalleCalendar,
      meta: { isResponsableSalle: true }

    }]
}

let componentsMenu = {
  path: '/components',
  component: DashboardLayout,
  redirect: '/components/buttons',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'buttons',
      name: 'Buttons',
      component: Buttons
    },
    {
      path: 'Boulbeba',
      name: 'geré les demande',
      component: Boulbeba
    },
    {
      path: 'grid-system',
      name: 'Grid System',
      component: GridSystem
    },
    {
      path: 'panels',
      name: 'Panels',
      component: Panels
    },
    {
      path: 'sweet-alert',
      name: 'Sweet Alert',
      component: SweetAlert
    },
    {
      path: 'notifications',
      name: 'Notifications',
      component: Notifications
    },
    {
      path: 'icons',
      name: 'Icons',
      component: Icons
    },
    {
      path: 'typography',
      name: 'Typography',
      component: Typography
    }

  ]
}
let formsMenu = {
  path: '/forms',
  component: DashboardLayout,
  redirect: '/forms/regular',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'regular',
      name: 'Regular Forms',
      component: RegularForms
    },
    {
      path: 'extended',
      name: 'Extended Forms',
      component: ExtendedForms
    },
    {
      path: 'validation',
      name: 'Validation Forms',
      component: ValidationForms
    },
    {
      path: 'wizard',
      name: 'Wizard',
      component: Wizard
    }
  ]
}

let tablesMenu = {
  path: '/table-list',
  component: DashboardLayout,
  redirect: '/table-list/regular',
  meta: { requiresAuth: true },
  children: [
    {
      path: 'regular',
      name: 'Regular Tables',
      component: RegularTables,
      meta: { requiresAuth: true }

    },
    {
      path: 'extended',
      name: 'Extended Tables',
      component: ExtendedTables,
      meta: { requiresAuth: true }
    },
    {
      path: 'paginated',
      name: 'Paginated Tables',
      component: PaginatedTables,
      props: true,
      meta: { requiresAuth: true }
    }]
}
let mapsMenu = {
  path: '/maps',
  component: DashboardLayout,
  meta: { requiresAuth: true },
  redirect: '/maps/google',
  children: [
    {
      path: 'google',
      name: 'Google Maps',
      component: GoogleMaps
    },
    {
      path: 'full-screen',
      name: 'Full Screen Map',
      component: FullScreenMap
    },
    {
      path: 'vector-map',
      name: 'Vector Map',
      component: VectorMaps
    }
  ]
}

let pagesMenu = {
  path: '/pages',
  component: DashboardLayout,
  meta: { requiresAuth: true },
  redirect: '/pages/user',
  children: [
    {
      path: 'user',
      name: 'User Page',
      component: User,
      meta: { requiresAuth: true ,isResponsableSalle: true,isSecretaire:true,isStudent :true,isResponsableMat : true, isadmin:true , isMember:true},

    },
    {
      path: 'timeline',
      name: 'Timeline Page',
      component: TimeLine
    }
  ]
}

let loginPage = {
  path: '/login',
  name: 'Login',
  component: Login
}

let registerPage = {
  path: '/register',
  name: 'Register',
  component: Register
}

let DetailEventInscri = {
      path: '/evnetInscripDetail',
      name: 'Événement Détail',
      component: DetailEventInscrip,
      meta: {isStudent:true,isMember:true},
}

let lockPage = {
  path: '/lock',
  name: 'Lock',
  component: Lock,
  meta: { requiresAuth: true }
  
}

const routes = [
  {
    path: '/',
    component: DashboardLayout,
    redirect: '/admin/overview',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'calendar',
        name: 'Calendar',
        component: Calendar,
        meta: { isResponsableMat: true }
      },
      {
        path: 'charts',
        name: 'Charts',
        component: Charts
      }
    ]
  },
  gerelesClub,
  componentsMenu,
  lesReunion,
  monClub,
  formsMenu,
  tablesMenu,
  mapsMenu,
  pagesMenu,
  loginPage,
  DetailEventInscri,
  registerPage,
  lockPage,
  gereevent,
  gerelesmat,
  lesinscriptions,
  gerelessalle,
  {
    path: '/admin',
    component: DashboardLayout,
    redirect: '/admin/overview',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'overview',
        name: 'Overview',
        component: Overview,
        meta:{isSecretaire:true,isResponsableSalle: true,isStudent :true,isResponsableMat : true, isadmin:true , isMember:true}
      },
      {
        path: 'monClub',
        name: 'Mon Club',
        component: MonClub,
        meta: {isSecretaire :true},
      },
      {
        path: 'stats',
        name: 'Stats',
        component: Stats,
        meta:{isSecretaire:true,isResponsableSalle: true,isStudent :true,isResponsableMat : true, isadmin:true , isMember:true}
      }
    ]
  },
  {path: '*', component: NotFound}
]



export default routes
