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
    name: 'Club',
    icon: 'ti-package',
    children: [
      {
        name: 'Mon Club',
        path: '/admin/monClub'
      },
    ]
  },
  {
    name: 'Événements',
    icon: 'ti-agenda',
    children: [{ 
    name: 'Demande Événement',
    path: '/demandeEvenement/demandeEvent'
    },
    {
    name: 'Les Demandes Événement',
    path: '/demandeEvenement/clubDemandesEvenemet'
    },
    {
    name: 'Evenement De Club',
    path: '/demandeEvenement/evenementdeclub'
    }]
  },
  {
    name: 'Les Réunions',
    icon: 'ti-layout-media-center',
    children: [
    {
      name: 'Planifier Réunion',
      path: '/Reunion/PlanifierReunion'
    },
    {
      name: 'Les Réunion de Club',
      path: '/Reunion/lesReuniondeClub'
    }]
  },
  {
    name: 'Inscription',
    icon: 'ti-bookmark',
    children: [
    {
      name: 'Inscriptions au Club',
      icon: 'ti-layout-cta-left',
      path: '/inscriptions/membreDeClub'
    },
    {
      name: 'Inscriptions au événements',
      icon: 'ti-layout-cta-left',
      path: '/inscriptions/evenementsInscritption'
    },
  ]
  },
  {
    name: 'Les ressource',
    icon: 'ti-layout-grid2',
    children: [
    {
      name: 'Les Sponsors',
      path: '/monclub/lesSponsors'
    },
    {
      name: 'Les fournisseurs',
      path: '/monclub/lesfournisseurs'
    },
    {
      name: 'Les materiels Externe',
      icon: 'ti-layout-cta-left',
      path: '/gere-les-materiel/lesmaterielexterne'
    }
  ]
  },
]
