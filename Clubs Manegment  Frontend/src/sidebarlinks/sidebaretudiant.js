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
    ]
  },
  {
    name: 'Créer Club',
    icon: 'ti-package',
    children: [
    {
      name: 'demande de création du club',
      path: '/gereclub/ajoutdemande'
    }]
  },
  {
    name: 'Inscription',
    icon: 'ti-bookmark',
    children: [
    {
      name: 'Inscription Au Club',
      path: '/inscriptions/inscriptionClub'
    },
    {
      name: 'Inscription Au Evénement',
      icon: 'ti-layout-cta-left',
      path: '/inscriptions/inscriptionEvenement'
    },
    {
      name: 'Mes Inscriptions',
      icon: 'ti-layout-cta-left',
      path: '/inscriptions/mesInscriptions'
    },
    {
      name: 'Mes événements',
      icon: 'ti-layout-cta-left',
      path: '/inscriptions/mesEventInscription'
    }
  ]
  }
]
