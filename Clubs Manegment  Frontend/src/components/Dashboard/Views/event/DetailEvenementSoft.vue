<template>
<div>
 <el-tabs type="border-card" style="max-height:590px;">
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Evénement</span>

    <div class="genetaleInfo">
    <h4 style="text-align:center">{{dataRow.demandeEvenement.nomEvenement}}</h4>
    <hr style="width:50%"/>
    <flip-clock v-if="!expired" ref="flipclock" :options="options" />
   <div class="mainDIV" style="text-align:center" >
      <h5> Type  D'Événement : <span style="text-align:right">{{dataRow.demandeEvenement.typeEvent.typeEvenement}}</span> </h5>
      <h5> Date Creation : {{dataRow.demandeEvenement.datedemande}} </h5>
      <h5> Date D'Événement : {{dataRow.demandeEvenement.datePrevuEvent}} </h5>
      <h5> Date Fin D'Événement: {{dataRow.demandeEvenement.datedemande}} </h5>
    </div>
    </div>

    </el-tab-pane>
    <el-tab-pane >
    <span slot="label"><i class="ti-money"></i> Tarifation</span>
    <template v-if="dataRow.lesTarifs.length>0">
    <div style="text-align: center;">
    <div class="row" style="margin-top:37px;">
        <h4>Les Tarifation D'événement</h4>
        <el-carousel  :autoplay='false' type="card" height="340px">
          <el-carousel-item  v-for="t in dataRow.lesTarifs " :key="t.id">
              <div class="contentcard" @mouseover="showcrudtarif=t.id" @mouseleave="showcrudtarif=0"> 
                <h5>{{t.tarifation.tarif}}</h5>
                <div style="margin-top:26%">
                  <a><i class="ti-user iconeTarif"></i></a>
                </div>
                <h3 style="margin-top:16%">{{t.montant}} Dt</h3>
                <h3 v-if="t.nombrePalceDisponible > 0" style="margin-top:5%">{{t.nombrePalceDisponible}} Place</h3>
              </div>
            </el-carousel-item>
        </el-carousel>
    <div style="text-align:center">
    </div>
    </div>
    </div>
    </template>
    <template v-else>
    <div style="text-align:center">
    <h1 class="Tarifation">Pas de tarifation disponible </h1>
    </div>
    </template>
    </el-tab-pane>
    
    <el-tab-pane>

    <span slot="label"><i class="ti-notepad"> </i>Programme</span>
    <div v-if="dataRow.lesProgrammes.length==0 " style="text-align:center">
    <h1 class="Tarifation">Pas de programme disponible pour ce événement</h1>
    </div>
    <div v-else style="text-align:center"  >
      <h5 style="color:gray;margin-bottom:2%">Les Programes D'événement</h5>
      <div class="row" style="text-align:center" >
      <el-carousel  :autoplay='false' type="card" >
          <el-carousel-item v-for="prog in dataRow.lesProgrammes " :key="prog.id" >
            <div class="contentProgramme" @click="opentaches(prog)">
              <h5 class="ProgHeader"><span style="position: relative;top: 7px;">{{getday(prog.dateProgramme)}}</span></h5>
              <h1 ><span>{{ prog.dateProgramme| moment("DD") }}</span></h1>
              <h4 class="datePorg"><span>{{ prog.dateProgramme | moment("MM-YYYY") }}</span></h4>
            </div>
            </el-carousel-item>
        </el-carousel>
      </div>
    </div>
    </el-tab-pane>

    <el-tab-pane>
    <span slot="label"><i class="ti-stats-up"> </i> Sponseurs</span>
          <div v-if="dataRow.lesSponseur.length==0 && !showSponsorForm" style="text-align:center">
            <h1 class="Tarifation">Pas de sponseurs  disponible pour ce Evenement</h1>
          </div>    
          <div v-if="dataRow.lesSponseur.length>0 && !showSponsorForm"  style="text-align:center" >

                 <h3 style="color:gray;margin-bottom:2%;    text-align: center;">Les Sponseur D'événement</h3>
                  <el-carousel  :autoplay='false' type="card" >
                      <el-carousel-item v-for="sponseur in dataRow.lesSponseur " :key="sponseur.id" >
                        <div >
                          <img class="imglogo" :src="'static/img/sponseurlogo/'+sponseur.sponseur.logoSponseur" @click="showsponsorisation(sponseur)">
                          <h6>{{sponseur.sponseur.raisonSociale}}</h6>
                        </div>

                        </el-carousel-item>
                    </el-carousel>           

          </div>
    </el-tab-pane>

   <el-tab-pane>
    <span slot="label"><i class="ti-announcement"> </i> Les affiches </span>
         <div>
            <h3 style="color:gray;margin-bottom:2%;    text-align: center;">Les Programes D'événement</h3>
            <div class="row" style="text-align:center" >
            <el-carousel  :autoplay='false' type="card" >
                <el-carousel-item v-for="affiche in dataRow.lesDemandeAffiche " :key="affiche.id" >
                  <div >
                    <div class="affichePub" :style="{ 'background-image': 'url(static/img/LesDemandeAffiche/'+affiche.demandeImage+ ')' }">
                      <div v-if="affiche.confirmation==false" class="overlay-waiting">
                              <div style="margin-top:59%">
                              <a ><i class="ti-time AfficheIcone"></i></a>
                              <div style="margin-top:22%">
                                <h5 style="color:white">En Attente</h5>
                                <a ><i class="ti-blackboard btn-Affiche" @click="showImgDemande(affiche)"></i></a> 
                                <a ><i class="ti-image btn-Affiche" @click="showDemande(affiche) "></i></a>
                                <a ><i class="ti-pencil btn-Affiche" @click="ModifierAffiche(affiche) "></i></a>
                                <a ><i class="ti-trash btn-Affiche" @click="SuppAffiche(affiche) "></i></a>
                              </div>
                              </div>
                      </div>
                      <div v-else-if="affiche.confirmation && affiche.etat" class="overlay-confirmed">
                        <div style="margin-top:59%">
                          <a ><i class="ti-check AfficheIcone"></i></a> <br/>
                          <div style="margin-top:22%">
                             <h5 style="color:white">Accepter</h5>
                            <a ><i class="ti-blackboard btn-Affiche" @click="showImgDemande(affiche)"></i></a> 
                            <a ><i class="ti-image btn-Affiche" @click="showDemande(affiche) "></i></a>
                          </div>
                        </div>
                      </div>
                      <div v-else-if="affiche.confirmation && !affiche.etat" class="overlay-rejected">
                        <div style="margin-top:59%">
                          <a ><i class="ti-close AfficheIcone"></i></a> <br/>
                          <div style="margin-top:22%">
                            <h5 style="color:white">Réfuser</h5>
                            <a ><i class="ti-blackboard btn-Affiche" @click="showImgDemande(affiche)"></i></a> 
                            <a ><i class="ti-image btn-Affiche" @click="showDemande(affiche) "></i></a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  </el-carousel-item>
              </el-carousel>
            </div>
          </div>
    </el-tab-pane>
  <el-tab-pane>
    <span slot="label"><i class="ti-stats-up"> </i>  demande d'aide financière</span>  
      <div style="text-align:center" >
         <h3 style="text-align:center" >Les demandes d'aide financière</h3>
          <div  class="col-sm-12" style="margin: 10px auto;float: none;text-align: right;">
          </div>
          <el-table 
            :data="dataRow.lesDemandeFinance"
            style="width: 100%" 
            min-height="250"
            header-align="center"
            empty-text="Pas De Resultat">
            <el-table-column type="expand">
            <template slot-scope="scope">
            <h5 style="text-align:center">Détail de Matériel à financer</h5>
            <ul style="text-align:center"  v-for="mat in scope.row.deviMateriel">
              <li Style=" list-style-type: none;">
                <div class="row">
                  <div class="col-sm-3">Matériel : {{ mat.matExterne.nomMateriel }}</div>
                  <div class="col-sm-3">Marque: {{mat.matExterne.marqueMateriel}}</div>
                  <div class="col-sm-3">Quantité Demander: {{mat.quantite}}</div>
                  <div class="col-sm-3">Prix d'unité : {{mat.montant}}</div>
                </div>
              </li>
            </ul>
            <hr style="width:90%"> 
            <div class="col-sm-10" style="margin: 0 auto;float: none;">
            <h5 style="text-align:right"> Total = {{calcleMtTotale(scope.row.deviMateriel)}} dt</h5> 
            </div>         
            </template>
            </el-table-column>
            <el-table-column
            property="fournisseur.raisonSociale"
            label="Fournisseur"
            min-width="150"
            header-align="center"
            align="center"
            sortable>
            </el-table-column>
            <el-table-column
            property="dateCreation"
            label="Date de création"
            min-width="150"
            header-align="center"
            align="center"
            sortable>
            </el-table-column>
            <el-table-column
              label="Devi"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                     <a :href="'static/img/devi/'+props.row.leDevi">
                       <el-button type="primary" icon="ti-download" circle></el-button>
                     </a>
              </template>
            </el-table-column>
            <el-table-column
              label="Confirmation"
              align="center"
              minWidth="150"
              header-align="center">
              <template slot-scope="props">
                    <template v-if="props.row.etat">
                      <a type="button" class="  btn btn-fill   btn-xs btn-success">Accepté</a>
                    </template>
                    <template v-else>
                      <a type="button" class=" btn  btn-fill  btn-xs  btn-danger">Pas encore accepté</a>
                    </template>
              </template>
            </el-table-column>
          </el-table>
       </div>
    </el-tab-pane>

   <template v-if="!dataRow.demandeEvenement.externe">
    <el-tab-pane >
    <span slot="label"><i class="ti-location-pin"></i> Lieu D'évenement</span>
    <template v-for="lieu in dataRow.demandeEvenement.leslieu">
      <div class="row">
        <div class="col-sm-6">
          <div class="cont">
            <div class="row">
              <div class="col-sm-3" >
                <h5>Lieu :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.site}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Ville :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.ville.ville}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
               <h5> Adresse :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.adresse}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Code Postale</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.codePostal}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Tel :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.tel}}</h5>
              </div>
            </div> 
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Fax : </h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.fax}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Email:</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.emailContact}}</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <img src="static\img\fsegs.jpg">
        </div>
      </div>
      
    </template>
    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="ti-home"></i> Salles  Réserver</span>
    <h1 v-if="dataRow.demandeEvenement.lesSalles.length == 0" class="Tarifation">Ce Evenement Ne Nésssiste Pas Des Salle </h1>
    <el-table 
        v-else
        :data="dataRow.demandeEvenement.lesSalles"
        style="width: 100%" 
        min-height="250"
        header-align="center"
        :default-sort = "{prop: 'salle.salleType.type'}"
        empty-text="Pas De Resultat">
        <el-table-column type="expand">
        <template slot-scope="scope">
            <ul style="text-align:center"  v-for="p in scope.row.lesplanification">
            <li Style=" list-style-type: none;">
            <div class="row">
            <div class="col-sm-3">Le : {{ p.dateResSalle }}</div>
            <div class="col-sm-3">De : {{p.tempdebRes}}</div>
            <div class="col-sm-3">à : {{p.tempFinRes}}</div>
            <div class="col-sm-3">
                <template v-if="p.etatConfirmationSalle==false">
                <a type="button" class="  btn btn-fill btn-xs btn-warning">En Attente</a>
                </template>
                <template v-else >
                <template v-if="p.acceptation">
                <a type="button" class="  btn btn-fill btn-xs btn-success">Accepter</a>
                </template>
                <template v-else>
                <a type="button" class=" btn  btn-fill btn-xs  btn-danger">Refusé</a>
                </template>
                </template>              
              </div>
            </div>
            </li>
            </ul>
        </template>
        </el-table-column>
        <el-table-column
        property="salle.nomSalle"
        label="Nom de Salle"
        min-width="250"
        align="center"
        sortable>
        </el-table-column>
        <el-table-column
        property="salle.capcite"
        label="Capcité"
        align="center"
        sortable
        min-width="250"
        >
        </el-table-column>
        <el-table-column
        property="salle.salleType.type"
        label="Type"
        align="center"
        sortable
        min-width="250"
        >
        </el-table-column>
        <el-table-column
        align="center"
        label="Retro Projecteur"
        min-width="250">
        <template slot-scope="scope">
          <template v-if="scope.row.salle.retroproj">
            Équipé
          </template>
          <template v-else>
            Non Équipé
          </template>
        </template>
        </el-table-column>
    </el-table>


    </el-tab-pane>
    <el-tab-pane>
    <span slot="label"><i class="ti-package"></i> Matériel Réserver</span>
      <h1 v-if="dataRow.demandeEvenement.lesMaterielAffecter.length == 0" class="Tarifation">Ce Evenement Ne Nésssiste Pas De Materiel De Faculté </h1>
      <el-table 
          v-else
          :data="dataRow.demandeEvenement.lesMaterielAffecter"
          width="100%"
          min-height="250"
          header-align="center"
          :default-sort = "{prop: 'salle.salleType.type'}"
          empty-text="Pas De Resultat">
          <el-table-column type="expand">
          <template slot-scope="scope">
              <ul style="text-align:center"  v-for="p in scope.row.lesplanificationMateriel">
              <li Style=" list-style-type: none;">
              <div class="row">
              <div class="col-sm-2">Le : {{ p.dateResSalle }}</div>
              <div class="col-sm-2">De : {{p.tempdebRes}}</div>
              <div class="col-sm-2">à : {{p.tempFinRes}}</div>
              <div class="col-sm-3">Quantité Demander: {{p.qteDemander}}</div>
              <div class="col-sm-3">Quantité Affecter: {{p.qteAffecter}}</div>

              </div>
              </li>
              </ul>
          </template>
          </el-table-column>
          <el-table-column
          property="matFaculter.nomMateriel"
          label="Nom de Materiel"
          min-width="250"
          align="center"
          sortable>
          </el-table-column>
          <el-table-column
          property="matFaculter.marqueMateriel"
          label="Marque"
          min-width="250"
          align="center"
          sortable>
          </el-table-column>
          <el-table-column
          property="matFaculter.qteTotale"
          label="Quantité Totale"
          sortable
          align="center"
          min-width="250"
          >
          </el-table-column>
      </el-table>
    </el-tab-pane>
   </template>
   <template v-else-if="dataRow.demandeEvenement.typeEvent.typeEvenement=='voyage'||dataRow.demandeEvenement.typeEvent.typeEvenement=='camping'">
    <el-tab-pane >
    <span slot="label"><i class="el-icon-date"></i> Lieu A visité </span>
    <div class="row">
      <div class="col-sm-6">
        <h5 style="text-align:center">Les Lieus a visité </h5>
        <div class="infoLieu">
          <div class="row">
            <div class="lieucont" v-for="loc in dataRow.demandeEvenement.leslieu">
              <div class=" ville">
                <div class="contentlieu">
                <i class="fa fa-map-marker iconlieu" aria-hidden="true"></i>
                <h6>{{loc.ville.ville}}</h6>
                </div>

              </div>
              <div class="site">
                <div class="contentsite">
                <i class="fa fa-location-arrow iconlieu" aria-hidden="true"></i>
                 <h6>{{loc.site}}</h6>
                </div>
                </div>
              <div class="adr">
               <div class="contentsite">
                <i class="fa fa-globe iconlieu" aria-hidden="true"></i>
                 <h6>{{loc.adresse}}</h6>
                </div>
                </div>
            </div>
            
          </div>
        </div>
         
         
      </div>
      <div class="col-sm-6">
      <tnMap class="pull-right" :lesville="lesvilleEvent()"/>
      </div>
    </div>
  
    </el-tab-pane>
   </template>
   <template v-else>
    <el-tab-pane >
    <span slot="label"><i class="ti-location-pin"></i> Lieu Dévenement</span>
      <template v-for="lieu in dataRow.leslieu">
      <div class="row">
        <div class="col-sm-6">
          <div class="cont">
            <div class="row">
              <div class="col-sm-3" >
                <h5>Lieu :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.site}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Ville :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.ville.ville}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
               <h5> Adresse :</h5>
              </div>
              <div class="col-sm-9" >
                <h5>{{lieu.adresse}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Code Postale</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.codePostal}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Tel :</h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.tel}}</h5>
              </div>
            </div> 
            <div class="row">
              <div class="col-sm-3" >
                <h5>Contact Fax : </h5>
              </div>
              <div class="col-sm-9" >
              <h5>(216) {{lieu.fax}}</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3" >
                <h5>Email:</h5>
              </div>
              <div class="col-sm-9" >
              <h5>{{lieu.emailContact}}</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <img src="static\img\fsegs.jpg">
        </div>
      </div>
      </template>
    </el-tab-pane>
  
   </template>

</el-tabs>

<el-dialog
        :title="'Programde De : ' + SelectedProg.dateProgramme" 
        :modalAppendToBody="false"
        :visible.sync="programmeJour"
        width="55%"
        :before-close="function (){editTarifction=false , programmeJour=false} "
        center>
              <el-table style="margin-top:5%" :data="this.SelectedProg.lesAction" header-align="center" height="350">
                <el-table-column prop="tempDebAct"
                                label="Temp Debut"
                                min-width="150px"
                                header-align="center"
                                align="center">
                </el-table-column>
                <el-table-column prop="tempFinAct"
                                label="Temp Fin"
                                header-align="center"
                                min-width="150px"
                                align="center">
                </el-table-column>
                <el-table-column prop="action"
                                label="Tache"
                                header-align="center"
                                min-width="150px"
                                align="center">
                </el-table-column>
              </el-table>
           <hr/>
</el-dialog>

<el-dialog
        title="Demmande Affiche Publicitaire"
        :modalAppendToBody="false"
        :visible.sync="AfficheDialogue"
        width="60%"
        center>
        <div class="row" >
          <div class="col-sm-10" v-html="selectedAffiche.demandeEcrite" style="margin: 0 auto;float: none;">

          </div>
       </div>
</el-dialog>
<el-dialog
        title="L'Affiche Publicitaire a Poster"
        :modalAppendToBody="false"
        :visible.sync="ImgDialogue"
        width="60%"
        center>
        <div class="row" style="margin:0 auto;text-align:center">
        <img style="max-width: 600px;" :src='"static/img/LesDemandeAffiche/"+this.selectedAffiche.demandeImage'>
       </div>
</el-dialog>
 <el-dialog
        title=""
        :modalAppendToBody="false"
        :visible.sync="showsponinfo"
        width="40%">
        <div class="row">
          <div class="col-sm-12">
            <div v-if="selectedSpon.montant>0 && selectedSpon.lesMaterielSpon.length==0">
              <h5 style="text-align:center;color:green">Sponsorisation financière</h5>
                <h1 style="text-align:center">Montant : {{selectedSpon.montant}} Dt</h1>
            </div>
            <div v-else-if="selectedSpon.montant==0 && selectedSpon.lesMaterielSpon.length>0">
              <h5 style="text-align:center;color:green">Sponsorisation Matériel</h5>
                      <table class="table table-sm">
                        <thead>
                          <tr>
                            <th scope="col">Matériel</th>
                            <th scope="col">Marque</th>
                            <th scope="col">Quantité</th>
                          </tr>
                        </thead>
                        <tbody>
                          <template v-for="matspon in selectedSpon.lesMaterielSpon">
                          <tr>
                            <td>{{matspon.materielExterne.nomMateriel}}</td>
                            <td>{{matspon.materielExterne.marqueMateriel}}</td>
                            <td>{{matspon.quantite}}</td>
                          </tr>
                          </template>
                        </tbody>
                      </table>
                      <h3 style="text-align:center">Valeur Totale : {{selectedSpon.valeurTotale}} Dt</h3>
            </div>
            <div v-else>
              <h5 style="text-align:center;color:green" >Sponsorisation Matérile et financière</h5>
                    <table class="table table-sm">
                        <thead>
                          <tr>
                            <th scope="col">Matériel</th>
                            <th scope="col">Marque</th>
                            <th scope="col">Quantité</th>
                          </tr>
                        </thead>
                        <tbody>
                          <template v-for="matspon in selectedSpon.lesMaterielSpon">
                          <tr>
                            <td>{{matspon.materielExterne.nomMateriel}}</td>
                            <td>{{matspon.materielExterne.marqueMateriel}}</td>
                            <td>{{matspon.quantite}}</td>
                          </tr>
                          </template>
                        </tbody>
                      </table>
                      <h5 style="text-align:center">Sponsorisation en Espèces: {{selectedSpon.montant}} Dt</h5>
                      <h5 style="text-align:center">Sponsorisation Matériel : {{selectedSpon.valeurTotale - selectedSpon.montant}} Dt</h5>
                      <h5 style="text-align:center">Totale : {{selectedSpon.valeurTotale}} Dt</h5>
            </div>
          </div>
        </div>
</el-dialog>
</div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import { Button , Row , Col,Table, TableColumn ,Card, Tabs, TabPane ,InputNumber,Input,DatePicker,TimeSelect,Carousel,CarouselItem,Upload} from 'element-ui'
  import PPagination from 'src/components/UIComponents/Pagination.vue'
  import { FlipClock } from '@mvpleung/flipclock';
  import * as moment from 'moment';
  import 'moment/locale/fr';
  import { VueEditor } from "vue2-editor"
  import tnMap from "./TunisieMap.vue"
  moment.locale('fr');
  Vue.use(Button)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Table)
  Vue.use(TableColumn)
  Vue.use(Tabs)
  Vue.use(TabPane)
  Vue.use(InputNumber)
  Vue.use(Input)
  Vue.use(DatePicker)
  Vue.use(TimeSelect)
  Vue.use(Carousel)
  Vue.use(CarouselItem)
  Vue.use(VueEditor)
  Vue.use(Upload)
  Vue.use(Card)

  export default {
    props: ['props'],
    beforeCreate() {

    },
    created() {
        if(this.props==null)
        {
        this.handleBack()
        return
        }
        this.dataRow = this.props.row   
        var currentDate = new Date();
        var eventDate = new Date(this.dataRow.demandeEvenement.datePrevuEvent);
        if(eventDate.getTime() / 1000 - currentDate.getTime() / 1000 + 60000 < 0)
        this.expired=true
        if(!this.expired)
        this.options.digit =  eventDate.getTime() / 1000 - currentDate.getTime() / 1000 + 60000

        var self = this;
        http.get('/Tarif/list')
        .then(function (response) {
        self.lestarifs= response.data;
        })
        http.get('/Sponseur/Active/list')
        .then(function (response) {
        self.lesSponseur= response.data;
        })
        http.get('/MatExt/list')
        .then(function (response) {
        self.lesMatExterne= response.data;
        })
        http.get('/fournisseur/list')
        .then(function (response) {
        self.lesfournisseurs= response.data;
        })
        
    },
    data () {
      var QteNegative = (rule, value, callback) => {
      setTimeout(() => {
          if (value < 0) {
            callback(new Error('La Quantité Ne Peut Pas Etre négative'));
          }
          else {
            callback();
          }
      }, 1000);
      };
      var validateImg = (rule, value , callback) => {
        setTimeout(() => {
            if (this.$refs.sponseurLogo.uploadFiles.length == 0) {
              callback(new Error("Le Logo De Sponseur est Nécessaire"));
            }
            else {
                  callback();
                }
          
        }, 1000);
      };
      return {
      ajoutTarifs:false,
      ajoutTypeTarifs:false,
      ajoutProg:false,
      programmeJour:false,
      ajoutAction:false,
      dejaExiste:false,
      editAction:false,
      Tarifmofid:false,
      UpdateAffiche:false,
      AfficheDialogue:false,
      ImgDialogue:false,
      showSponsorForm:false,
      showaddMat:false,
      addFormSponseur:false,
      showsponinfo:false,
      ValeurTotale:0,
      selectedSpon:'',
      selectedAffiche:'',
      fileList:[],
      lesSponseur:[],
      lesMaterielSpon:[],
      showcrudtarif:0,
      showdemandeAffiche:'',
      SelectedProg:{
        lesAction:[]
      },
      typetarif:'',
      lestarifs:[],
      lesMatExterne:[],
      dataRow:{
      demandeEvenement:{
      typeEvent:{
      typeEvenement:''
      },
       externe:''
      }
      },
      fromRoute: null,
      options: {
        time: 0,
        countdown: true,
        clockFace: 'DailyCounter',
        showSeconds:false
        },
      expired:false,
      dateError:false,
      pickerOptions2: {
      disabledDate: this.disabledDueDate
      },
      showdemfinanceform:false,
      formAddFournisseur:false,
      lesMataFinance:[],
      }
    },
    components: {
      FlipClock,
      tnMap,
      VueEditor
    },
    beforeRouteEnter (to, from, next) {
      next(vm => {
        vm.fromRoute = from;
      })
    },
    methods: {
    opentaches(programe){
      this.programmeJour=true
      this.SelectedProg=programe
    },
    getday(date){
      return moment(date).locale("fr").format("dddd");
    },
    disabledDueDate(date){
      var datedeb = new Date(this.dataRow.demandeEvenement.datePrevuEvent)
      var dateFin = new Date(this.dataRow.demandeEvenement.dateFinEvent)
      datedeb.setDate(datedeb.getDate()-1);
     return !(date >= datedeb && date <= dateFin)
    },
    handleBack (fallback) {
    this.$router.back();
    },
    checkDate(){
    let index = this.dataRow.lesProgrammes.findIndex((prog) => prog.dateProgramme === this.ProgForm.dateProgramme)
    if(index>=0)
    {this.ProgForm.dateProgramme=''
    this.dateError=true
    }
    else
    this.dateError=false
    },
    addTypeTarif(){
      var self = this;
      var tarifation={
        tarif:this.typetarif
      }
      http.put("/Tarif/create",tarifation)
      .then(response => {
        self.lestarifs.push(response.data) 
        self.tarifForm.tarif=response.data
        swal({
          type: 'success',
          title: 'Succès',
          text: 'Ajouter avec succès',
        })
      })
      .catch(e => {
        this.showaddtype=false
        swal({
          type: 'error',
          title: 'Une erreur est Produit',
          text: 'il est possible que ce Type De Tarif deja existe',
        })          
        console.log(e);
      })
      this.ajoutTypeTarifs=false
    },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ajoutTarifation()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitProgr(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ajouterEventProgram()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitAction(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.addAction()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      updateAction(formName) {
        var updatedAction=this.actionAModifier
        var self=this
        var index = this.SelectedProg.lesAction.findIndex(action => action.id === updatedAction.id)
        if(index<0)
        return
        this.$refs[formName].validate((valid) => {
          if (valid) {
            http.put("/event/Prog/action/update",updatedAction)
            .then(response => {
              self.editAction=false
              self.SelectedProg.lesAction.splice(index, 1) 
                self.SelectedProg.lesAction.splice(index, 0, updatedAction);
              swal({
                type: 'success',
                title: 'Succès',
                text: 'Mise A jour avec succès',
              })
            })
            .catch(e => {
            swal({
              type: 'error',
              title: "Une erreur est Produit Lors de Mise A jour",
              })          
             })
            } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      addAction(){
        this.dejaExiste=false
        var Programe = this.SelectedProg
        var self = this
         var laction = {
          action:this.ActionForm.action,
          tempDebAct:this.ActionForm.tempDebAct,
          tempFinAct:this.ActionForm.tempFinAct
          }
        let index = this.SelectedProg.lesAction.findIndex((action) => laction.tempDebAct  >= action.tempDebAct && laction.tempDebAct  < action.tempFinAct )
        if(index>=0)
        {
        this.dejaExiste=true
        return
        }
          Programe.lesAction.push(laction)
            http.put("/event/Prog/action/create",Programe)
            .then(response => {
              self.ajoutAction=false
              self.$refs["ActionForm"].resetFields()
            })
            .catch(e => {
            swal({
              type: 'error',
              title: "Une erreur est Produit Lors de L'insertion",
              })          
             })
      },
      ajouterEventProgram(){
        var self=this
        var Programme={
          dateProgramme:this.ProgForm.dateProgramme,
          tempDebProg:this.ProgForm.tempDebProg,
          tempFinProg:this.ProgForm.tempFinProg
        }
        var evenement={
          id:this.dataRow.id,
          lesProgrammes:[],
          demandeEvenement:{
            id:this.dataRow.demandeEvenement.id
          }
        }
        evenement.lesProgrammes.push(Programme)
        http.put("/event/Programe/Affect",evenement)
        .then(response => {
          this.ajoutProg=false
          self.dataRow.lesProgrammes.push(Programme)
          this.$refs["ProgForm"].resetFields()
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Tarif Ajouter avec succès',
          })
          
        })
        .catch(e => {
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
          })          
          console.log(e);
        })
      },
      ajoutTarifation(){
        var self=this
        let latarif = this.lestarifs.find((tarif) => tarif.id === this.tarifForm.tarif )
        if(latarif==null)
        return
        if(this.Tarifmofid)
        {
        var index= this.dataRow.lesTarifs.findIndex((tarifa) => tarifa.id === this.tarifForm.id)
        var EventTarif={
          id:this.tarifForm.id,
          tarifation: latarif,
          evenement:{
              id:this.dataRow.id
            },
            montant: this.tarifForm.montant,
            nombrePalceDisponible: this.tarifForm.nbrPlace
        }
        http.put("/EventTarif/create",EventTarif)
        .then(response => {
          self.dataRow.lesTarifs[index]= EventTarif    
          this.ajoutTarifs=false     
          self.resetTarifForm
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Tarif Ajouter avec succès',
          })
          })
          .catch(e => {
            swal({
              type: 'error',
              title: 'Une erreur est Produit',
            })   
          })
          return   
        }
        var EventTarif={
          tarifation: latarif,
          evenement:{
              id:this.dataRow.id
            },
            montant: this.tarifForm.montant,
            nombrePalceDisponible: this.tarifForm.nbrPlace
        }
        http.put("/EventTarif/create",EventTarif)
        .then(response => {
          self.dataRow.lesTarifs.push(EventTarif)
          this.ajoutTarifs=false
          this.$refs["tarifForm"].resetFields()
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Tarif Ajouter avec succès',
          })
          
        })
        .catch(e => {
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
          })          
          console.log(e);
        })
      },
      editTarif(row){
        this.ajoutTarifs=true
        this.tarifForm={
        id:row.id,
        tarif:row.tarifation.id,
        montant:row.montant,
        nbrPlace:row.nombrePalceDisponible
        }
        this.Tarifmofid=true
      },

      suppTarif(row){
        var self = this
        let indexToDelete = this.dataRow.lesTarifs.findIndex((tarifEvent) => tarifEvent.id === row.id)
       // swal("peut etre des Inscription son effectuer avec cette tarif voulez vous avancer","","info")
        if(indexToDelete<0)
          return
          swal({
            title: 'Tu est sure?',
            text: `voulez vous Confirmer cette Suppression!`,
            type: 'warning',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Effacer!',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
              http.delete("/eventTarif/Delete/"+row.id)
                .then(response => {
                self.dataRow.lesTarifs.splice(indexToDelete, 1)
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Supprimer Avec succès',
                })
              })
          })
      },
      SuppAffiche(affiche){
        var self = this
        let indexToDelete = this.dataRow.lesDemandeAffiche.findIndex((demAff) => demAff.id === affiche.id)
        if(indexToDelete<0)
          return
          swal({
            title: 'Tu est sure?',
            text: `voulez vous Confirmer cette Suppression!`,
            type: 'warning',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Effacer!',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
              http.put("/affiche/delete/"+affiche.id)
                .then(response => {
                self.dataRow.lesDemandeAffiche.splice(indexToDelete, 1)
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Supprimer Avec succès',
                })
              })
          })
      },
      handleEdit(index, row)
      {
      this.actionAModifier={
        id:row.id,
        action:row.action,
        tempDebAct:row.tempDebAct,
        tempFinAct:row.tempFinAct
      }
      this.editAction=true
      },
      handleDelete (index, row) {
        var self = this
        let indexToDelete = this.SelectedProg.lesAction.findIndex((tableRow) => tableRow.id === row.id)
        if(indexToDelete<0)
          return
          swal({
            title: 'Tu est sure?',
            text: `voulez vous Confirmer cette Suppression!`,
            type: 'warning',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success btn-fill',
            cancelButtonClass: 'btn btn-danger btn-fill',
            confirmButtonText: 'Effacer!',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
              http.delete("/event/Prog/action/"+row.id)
                .then(response => {
                self.SelectedProg.lesAction.splice(indexToDelete, 1)
                swal({
                type: 'success',
                title: 'Succès',
                text: 'Supprimer Avec succès',
                })
              })
          })

      },
      showImgDemande(affiche){
        this.selectedAffiche=affiche
        this.AfficheDialogue=true
      },
      showDemande(affiche){
        this.selectedAffiche=affiche
        this.ImgDialogue=true
      },
      resetTarifForm(){
      this.tarifForm={
        id:null,
        tarif:'',
        montant:0,
        nbrPlace:0
      }
      this.Tarifmofid=false
      this.ajoutTarifs=false
      },
      uniqueImgName(){
      return (((1+Math.random())*0x10000)|0).toString(16).substring(1)
      },
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
      },
      submitUpload() {
       this.$refs.upload.submit();
      },
      submitSponseur(formName){
      this.$refs[formName].validate((valid) => {
          if (valid) {
            this.submitlogoUpload();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      SubmitSponsarisation(formName){
      this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ajoutSponsirisation()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitlogoUpload() {
       this.$refs.sponseurLogo.submit();
      },
      succesLogoUpload(response,file){
        var self=this
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Ajouter ce Sponseur !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Ajouter!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          self.FormSponseur.logoSponseur=response
          http
          .put("/Sponseur/create", self.FormSponseur)
          .then(response => {
            var spon = response.data
            self.lesSponseur.push(spon)
            self.FormDemSponseur.sponseur=spon.id
            self.addFormSponseur=false
            swal("","Ajouter Avec Success", "success")
          })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      succesUpload(response,file)
      { 
        var self = this;
        if(response==null||response=="")
        {
        swal("Tu doit poster une image de l'affiche Publicitaire","","info")
        return
        }
        if(this.UpdateAffiche)
        {
        let index = this.dataRow.lesDemandeAffiche.findIndex((demAff) => demAff.id === this.selectedAffiche.id)
        if(index<0)
        return
        var demAffiche ={
        id: this.selectedAffiche.id,
         demandeEcrite:this.FormAffiche.demandeEcrit,
         demandeImage:response
        }
         http
        .put("demandeAffiche/UpdateAffiche", demAffiche)
        .then(response => {
          self.UpdateAffiche=false
          self.showdemandeAffiche=false
          self.FormAffiche.demandeEcrit=""
          self.demAffiche.confirmation = false
          self.dataRow.lesDemandeAffiche[index]=demAffiche
          swal("succès","Modifier Avec Success", "success")
          return
        })
        .catch(e => {
          swal("Erreur","Une Erreur et Produit", "error")
          console.log(e);
          return
        })
        return
        }
        var demandeAffiche={
          demandeEcrite:this.FormAffiche.demandeEcrit,
          demandeImage:response,
        }      
          this.$refs["FormAffiche"].validate((valid) => {
          if (valid) {
             http.put("/demandeAffiche/cree/"+this.dataRow.id,demandeAffiche)
              .then(response => {
                console.log(response.data)
               self.dataRow.lesDemandeAffiche=response.data
               let lesAffiche = response.data
                swal({
                  type: 'success',
                  title: 'Succès',
                  text: 'Demande Envoyer Avec succès',
                })
                this.showdemandeAffiche=false
              })
              .catch(e => {
                swal({
                  type: 'error',
                  title: 'Une erreur est Produit',
                  text: 'il est possible que ce Type De Tarif deja existe',
                })          
                console.log(e);
              })
          } else {
          console.log('error submit!!');
          return false;
          }
          })
      },
      ModifierAffiche(affiche)
      {
        this.selectedAffiche=affiche
        this.UpdateAffiche=true
        this.showdemandeAffiche=true
        this.FormAffiche.demandeEcrit=affiche.demandeEcrite
      },
      ajouterMaterielExterne(){
        var self=this
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Ajouter ce Materiel !`,
          type: 'warning',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Ajouter!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          http
          .put("/MatExt/create", self.MaterielForm)
          .then(response => {
            var mat = response.data
            self.lesMatExterne.push(mat)
            self.FormDemSponseur.materiel=mat.id
            self.showaddMat=false
            swal("","Ajouter Avec Success", "success")
          })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      showsponsorisation(row){
        this.showsponinfo=true
        this.selectedSpon=row
      },
      ajoutSponsirisation(){
         var self=this
         var Sponsor = this.lesSponseur.find(obj => obj.id == this.FormDemSponseur.sponseur)
         if(Sponsor==null)
         return
         if(this.FormDemSponseur.montant==0 && this.lesMaterielSpon.length==0)
         {
           swal("","Tu doit Présizer Le Type de Sponsirisation si elle est financière ajouter un Montant Si"+
           " elle est Sponsirisation de Materielle Ajouter des Materielle Ou les Deux En Meme Temps","info")
           return
         }
         var ValeurTotale = this.FormDemSponseur.montant
         if(this.lesMaterielSpon.length>0)
         this.lesMaterielSpon.forEach(mat =>{ ValeurTotale =ValeurTotale + mat.materielExterne.coutMateriel * mat.quantite })

         var Sponsirisation ={
                sponseur:Sponsor,
                montant: this.FormDemSponseur.montant,
                lesMaterielSpon:this.lesMaterielSpon,
                valeurTotale:ValeurTotale
         }
        swal({
          title: 'Tu est sure?',
          text: `voulez vous Ajouter cette Sponsorisation !`,
          type: 'info',
          showCancelButton: true,
          confirmButtonClass: 'btn btn-success btn-fill',
          cancelButtonClass: 'btn btn-danger btn-fill',
          confirmButtonText: 'Ajouter!',
          cancelButtonText:'Annuler',
          buttonsStyling: false
          }).then(function () {
          http
          .put("/event/AddSponseur/"+self.dataRow.id ,Sponsirisation)
          .then(response => {
            self.dataRow.lesSponseur.push(Sponsirisation)
            self.showSponsorForm=false
            swal("","Ajouter Avec Success", "success")
          })
          .catch(e => {
            swal("Erreur","Une Erreur et Produit", "error")
            console.log(e);
          })
          })
      },
      submitMaterielForm(formName){
         this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ajouterMaterielExterne();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      addMaterielSpon()
      {
        var materiel = this.lesMatExterne.find(mat => mat.id === this.FormDemSponseur.materiel)
        if(materiel==null)
        {
        swal("Sélectionner le Materiel","","info")
        return
        }
        else if(this.FormDemSponseur.quantite==0)
         {
          swal("","La Quantité Doit etre supérieur a 0 ","info")
          return
        }
        var matExtSponseur={
          materielExterne:materiel,
          quantite:this.FormDemSponseur.quantite
        }
        var index = this.lesMaterielSpon.findIndex(obj=> obj.materielExterne.id === matExtSponseur.materielExterne.id)
        if(index>=0)
        {
          swal("Cette Ligne Deja Existe","","info")
          return
        }
       this.lesMaterielSpon.push(matExtSponseur)
       this.FormDemSponseur.materiel=''
       this.FormDemSponseur.quantite=0
      },
      SuppSponMat(row){
          let indexToDelete = this.lesMaterielSpon.findIndex((obj) => obj.materielExterne.id === row.materielExterne.id)
        if (indexToDelete >= 0) {
          this.lesMaterielSpon.splice(indexToDelete, 1)
        }
      },
      SuppLigneDevi(row){
          let indexToDelete = this.lesMataFinance.findIndex((obj) => obj.matExterne.id === row.matExterne.id)
        if (indexToDelete >= 0) {
          this.lesMataFinance.splice(indexToDelete, 1)
        }
      },
      lesvilleEvent(){
        let lieus = this.dataRow.demandeEvenement.leslieu
        let result = lieus.map(lieu => lieu.ville.ville)
        return result
      },
      
      uploadDevi(){
        if(this.FormDemFinance.fournisseur=='')
        {
          swal("","Sélectionner le fournisseur ","info")
          return
        }
        else
        if(this.$refs.ledevi._data.uploadFiles.length==0)
        {
          swal("","Tu doit mettre une copie électronique du devis","info")
          return
        }
        else 
        if(this.lesMataFinance.length==0)
        {
          swal("","Au moin ajouter un matériel pour envoyer la demande d'aide financière  ","info")
          return
        }

        var self = this
         swal({
            title: 'Tu est sure?',
            text: `voulez vous Confirmer l'ajout de cette Demande d'aide financière`,
            type: 'info',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-primary btn-fill btnswal',
            cancelButtonClass: 'btn btn-default btn-fill btnswal',
            confirmButtonText: 'Confirmer',
            cancelButtonText:'Annuler',
            buttonsStyling: false
          }).then(function () {
            self.$refs.ledevi.submit()
          })
        
      },
      erreurUpDevi(){
      },
      ajoutDemFinance(response,file)
      {
        var self = this
        let demandeFinanc={
          dateCreation:new Date(),
          leDevi:response,
          fournisseur:{
            id:this.FormDemFinance.fournisseur
          },
          deviMateriel:this.lesMataFinance
        }
          http.put("DemandeFinance/Add/"+this.dataRow.id,demandeFinanc)
          .then(response => {
           self.showdemfinanceform=false
           self.dataRow.lesDemandeFinance.push(response.data)
            swal({
              type: 'success',
              title: 'Succès',
              text: "Demande d'aide financière  ajouter avec succès",
            })
          })
          .catch(e => {
            swal({
              type: 'error',
              title: 'Une erreur est Produit',
            })          
            console.log(e);
          })
      },

      ajoutfournisseur(formName)
      {
        this.$refs[formName].validate((valid) => {
        if (valid) {
        this.ajouterFournisseur()
        } else {
        console.log('error submit!!');
        return false;
        }
        })
      },
      ajouterFournisseur(){
        var self = this
          http.put("/fournisseur/add",self.FormAjoutFourniss)
          .then(response => {
            self.lesfournisseurs.push(response.data)
            self.FormDemFinance.fournisseur=response.data
            self.formAddFournisseur=false
            self.$refs["FormAjoutFourniss"].resetFields()
            swal({
              type: 'success',
              title: 'Succès',
              text: 'Fournisseur Ajouter avec succès',
            })
          })
          .catch(e => {
            swal({
              type: 'error',
              title: 'Une erreur est Produit',
            })          
            console.log(e);
          })
      },
      ajouterMatFinance(){
        var materiel = this.lesMatExterne.find(mat => mat.id === this.FormDemFinance.materiel)
        if(materiel==null)
        {
        swal("Sélectionner le Matériel","","info")
        return
        }
        else if(this.FormDemFinance.quantite==0)
         {
          swal("","La quantité doit être supérieur a 0 ","info")
          return
        }
        else if(this.FormDemFinance.prix==0)
         {
          swal("","Le prix doit être supérieur a 0 ","info")
          return
        }
        var matFinancier={
          matExterne:materiel,
          quantite:this.FormDemFinance.quantite,
          montant:this.FormDemFinance.prix
        }
        var index = this.lesMataFinance.findIndex(obj=> obj.matExterne.id === materiel.id)
        if(index>=0)
        {
          swal("Ce Matériel Déja ajouter","","info")
          return
        }
       this.lesMataFinance.push(matFinancier)
       this.FormDemFinance.quantite=0
       this.FormDemFinance.prix=0
       this.FormDemFinance.materiel=''
        },
      calcleMtTotale(row){
        var mtTotal=0
        for(let obj of row){
          mtTotal+=(obj.montant*obj.quantite)
        }
        return mtTotal
      }
      
    }
  }
</script>
<style >
@import url('https://fonts.googleapis.com/css?family=Oswald');
.btnswal{
  background: cornflowerblue;
  border: none;
  border-radius: 8px;
  margin: 1%;
}
.titleEvent {
    text-align: center;
    font-weight: bolder;
    color: gray;
    font-family: 'Oswald', sans-serif;
}
.el-tabs__content {
    /*max-height: 600px;*/
    max-height: 550px;
    overflow-y: auto;
}
.ql-align-center {
    text-align: center;
    text-align: center
}
.flip-clock-label{ display: none !important; }
.mainDIV {
    width: 80%;
    margin: 0 auto;
    text-align: center;
}
.Tarifation{
  text-align: center;
  font-weight: bolder;
  color: gray;
  margin:5%;
}
.contentcard {
    border-radius: 12px;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
    background-color: #f9f7f7;
    color: gray;
    margin-bottom: 20px;
    position: relative;
    height: 350px;
    width:250px;
    border: 0 none;
    margin: 0 auto;
    padding: 5%;
}
i.ti-user.iconeTarif {
    font-size: 38px;
    border: solid 2px #bebebe;
    border-radius: 100%;
    padding: 12%;
}
.lestarifation {
    list-style: none;
    display: flex;
    width: 77%;
    margin: auto;
}
.lesprogramme {
    list-style: none;
    display: flex;
    width: 50%;
    margin: auto ;
}
.contentcard:hover {
  background: #fff;
}
.contentProgramme {
    width: 200px;
    height: 200px;
    margin: 0 auto;
    background: aliceblue;
    border-radius: 26px;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
}
.ProgHeader {
    text-align: center;
    background-color: #f56a6a;
    height: 41px;
    border: none;
    border-radius: 26px 26px 0px 0px;
    color: white;
}
.flip-clock-wrapper ul.flip:nth-child(2) li a div div.inn,
.flip-clock-wrapper ul.flip:nth-child(3) li a div div.inn
{
    background : #848484;
    color:#eb6e6e

}

.flip-clock-wrapper ul.flip:nth-child(5) li a div div.inn,
.flip-clock-wrapper ul.flip:nth-child(6) li a div div.inn
{
    background : #848484;
    color:#eb6e6e
}

.flip-clock-wrapper ul.flip:nth-child(8) li a div div.inn,
.flip-clock-wrapper ul.flip:nth-child(9) li a div div.inn
{
    background : #848484;
    color:#eb6e6e

}
input.el-upload__input {
    display: none;
}
.upload-demo {
    text-align: center;
    
}
.quillWrapper {
    text-align: center;
}
.affichePub {
    height: 300px;
    width: 200px;
    background-repeat: no-repeat;
    background-size: 100%;
    border-radius: 15px;
    margin: 0 auto;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
}
.overlay-rejected {
    background-color: #fb5151;
    overflow: hidden;
    overflow-x: hidden;
    overflow-y: hidden;
    width: 100%;
    height: 0;
    transition: .5s ease;
    border-radius: 15px;
}
.overlay-waiting {
    background-color: #edbd53;
    overflow: hidden;
    overflow-x: hidden;
    overflow-y: hidden;
    width: 100%;
    height: 0;
    transition: .5s ease;
    border-radius: 15px;
}
.overlay-confirmed {
    background-color: #4cad45;
    overflow: hidden;
    overflow-x: hidden;
    overflow-y: hidden;
    width: 100%;
    height: 0;
    border-radius: 15px;
    transition: .5s ease;
}
.affichePub:hover .overlay-waiting {
  bottom: 0;
  height: 100%;
}
.affichePub:hover .overlay-confirmed {
  bottom: 0;
  height: 100%;
}
.affichePub:hover .overlay-rejected {
  bottom: 0;
  height: 100%;
}
.AfficheIcone {
    color: white;
    font-size: 46px;
    border: 2px solid white;
    padding: 12%;
    border-radius: 100%;
}
.AfficheIcone:hover{
    color: white;
}
.btn-Affiche {
    color: black;
    background: white;
    border: solid 1px white;
    padding: 4%;
    border-radius: 100%;
}
.btn-Affiche:hover {
    color: white;
    background: none;
    border: solid 1px white;
}
.centerit {
    margin: 0 auto;
    float: none;
    margin-top: 6%;
}
.sponsSelect {
    width: 80%;
}
.logoUp{
  width: 100%;
  padding-left: 10px;
  padding-right: 10px;
}
.imglogo {
    width: 200px;
    height: 200px;
    background: lightblue;
    border-radius: 20px;
    border: 2px solid darkslategray;
}
td.droit {
    text-align: left;
    float: left;
}
td.gauche {
    text-align: left;
    position: relative;
    left: 15%;
}
.genetaleInfo {
border: 3px lightgrey solid;
border-radius: 30px;
margin: 0 auto;
max-width: 531px;
padding: 5%;
box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
display: block;
}
.ville{
    background: #dee9ef;
    min-height: 100px;
    width: calc(100%/5);
    margin: 2%;
    border-radius: 20px;
}
.site{
    background: #dee9ef;
    min-height: 100px;
    width: calc(100%/3);
    margin: 2%;
    border-radius: 20px;
}
.adr{
    background: #dee9ef;
    min-height: 100px;
    width: calc(100%/3);
    margin: 2%;
    border-radius: 20px;
}
.iconlieu {
    color: #93a1ff;
    font-size: 2em;
    margin-left: 1%;
}
.lieucont {
    width: 100%;
    display: -webkit-inline-box;
}
.infoLieu {
    padding: 5%;
    border: 1px solid lightgray;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
    border-radius: 19px;
}
.contentlieu {
    width: fit-content;
    margin: 0 auto;
    margin-top: 17%;
    text-align: center;
}
.contentsite{
    width: fit-content;
    margin: 0 auto;
    margin-top: 10%;
    text-align: center;
}
.el-tabs__content {
    min-height: 500px;
}
</style>
