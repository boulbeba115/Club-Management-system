/* eslint-disable */

export default [
  {
    name: 'Dashboard',
    icon: 'ti-panel',
    collapsed: false,
    children: [{
      name: 'Overview',
      path: '/admin/overview'
    },
    {
      name: 'Stats',
      path: '/admin/stats'
    }]
  },
  {
    name: 'les Clubs',
    icon: 'ti-package',
    children: [{
    name: 'Les Club',
    path: '/gereclub/Clubs'
    },
    {
      name: 'Les Demmande Creations',
      path: '/gereclub/lesdemande'
    },
    {
      name: 'Clubs Événements',
      path: '/inscriptions/allClubEventInscrip'
    },
    ]
  },
  {
    name: 'les événements',
    icon: 'ti-agenda',
    children: [
    {
    name: "Demandes d'événement",
    path: '/demandeEvenement/lesDemandesEvenemet'
    },
    {
    name: 'Les Evenement Des Club',
    path: '/demandeEvenement/touseventclubs'
    },
    {
      name: "Les demmande D'Affiche",
      path: '/demandeEvenement/lesDemandeAffiche'
    },
    {
      name: "Aide financière",
      path: '/demandeEvenement/lesDemandeFinance'
    },
  ] 
  }
]
