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
        <el-input placeholder="Entrez quelque chose" v-model="vedioLink">
        <template slot="prepend">Http://</template>
        </el-input>
        <small>Doit étre sous la forme https://www.youtube.com/embed/ </small>
        <el-button size="small" type="info" icon="ti-angle-right" @click="setvediolink" circle></el-button>
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
                <div v-if=" showcrudtarif==t.id">
                  <el-button type="info" icon="el-icon-edit" circle @click="editTarif(t)"></el-button>
                  <el-button type="danger" icon="el-icon-delete" circle @click="suppTarif(t)"></el-button>
                </div>

              </div>
            </el-carousel-item>
        </el-carousel>
    <div style="text-align:center">
    <el-button type="success" round @click="ajoutTarifs=true">Ajouter Tarifation</el-button>
    </div>
    </div>
    </div>
    </template>
    <template v-else>
    <div style="text-align:center">
    <h1 class="Tarifation">Pas de Tarifation Disponible Saisie L'Événement Par Défaut est Gratuit</h1>
    <el-button type="success" round @click="ajoutTarifs=true">Ajouter Tarifation</el-button>
    </div>
    </template>
    </el-tab-pane>
    
    <el-tab-pane>

    <span slot="label"><i class="ti-notepad"> </i> Programme</span>
    <div v-if="dataRow.lesProgrammes.length==0 " style="text-align:center">
    <h1 class="Tarifation">Pas de Programme Disponible Pour ce Evenement</h1>
    <el-button style="margin-bottom: 10%;" type="success" round @click="ajoutProg=true">Ajouter Programme</el-button>
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
        <el-button style="margin-bottom:5%;" type="success" round @click="ajoutProg=true">Ajouter Programme</el-button>
      </div>
    </div>
    </el-tab-pane>

    <el-tab-pane>
    <span slot="label"><i class="ti-stats-up"> </i> Sponseurs</span>
          <div v-if="dataRow.lesSponseur.length==0 && !showSponsorForm" style="text-align:center">
            <h1 class="Tarifation">Pas de sponseurs  disponible pour ce Evenement</h1>
            <el-button style="margin-bottom: 10%;" type="success" round @click="showSponsorForm=true">ajouter sponseur</el-button>
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

           <el-button style="margin-bottom: 10%;" type="success" round @click="showSponsorForm=true">ajouter Sponsor</el-button>
          </div>
          <div v-if="showSponsorForm">
            <div class="row">
              <div v-if="!addFormSponseur" class="col-sm-12">
                <el-card shadow="always">
                  <el-button type="primary" style="position: absolute;margin: -11px;" icon="el-icon-back" @click="showSponsorForm=false" circle></el-button>
                 <h5 style="text-align:center;margin-top: 1px;margin-bottom: 24px;">Ajouter Un Sponsor Au événement</h5>
                <el-form :model="FormDemSponseur" :rules="rulesFormDemSponseur"  style="margin: 0 auto;" ref="FormDemSponseur" label-width="150px" label-position='left' >
                 <div class="row">
                    <div class="col-sm-12">
                      <div class="row" >
                        <div class="col-sm-4">
                          <el-form-item label="Sponseur" prop="sponseur"  >
                              <el-select
                                      size="large"
                                      placeholder=" le Sponseur"
                                      v-model="FormDemSponseur.sponseur">
                              <el-option v-for="option in lesSponseur"
                                          :value="option.id"
                                          :v-model="option.id"
                                          :label="option.raisonSociale"
                                          :key="option.raisonSociale">
                              </el-option>
                              </el-select>
                          </el-form-item>
                        </div> 
                        <div class="col-sm-1">
                              <el-button @click="addFormSponseur=true" class="pull-left"  icon="ti-plus" type="primary" circle></el-button>
                        </div>
                        <div class="col-sm-2"/>
                        <div class="col-sm-4">
                        <el-form-item label="Montant ( Dt )"  class="pull-right" prop="montant" >
                          <el-input-number v-model="FormDemSponseur.montant" :min=0 ></el-input-number>
                        </el-form-item> 
                        </div>
                       </div>        
                        
                          <div class="row">
                            
                            <div class="col-sm-4">
                                <el-form-item label="Materiel"  prop="materiel"  >
                                  <el-select
                                          size="large"
                                          placeholder=" le Materiel"
                                          v-model="FormDemSponseur.materiel">
                                  <el-option v-for="option in lesMatExterne"
                                              :value="option.id"
                                              :v-model="option.id"
                                              :label="option.nomMateriel"
                                              :key="option.nomMateriel">
                                  </el-option>
                                  </el-select>
                                </el-form-item>                           
                            </div>
                            <div class="col-sm-1 ">
                                    <el-button class="pull-left" type="primary" icon="ti-plus" @click="showaddMat=true" circle></el-button>
                            </div>
                            <div class="col-sm-2"/>
                            <div class="col-sm-4">
                              <el-form-item class="pull-right" label="Quantité a Affecter" prop="quantite">
                                  <el-input-number v-model="FormDemSponseur.quantite" :min=0 ></el-input-number>
                              </el-form-item> 
                            </div>
                             <div class="col-sm-1"/>
                            <div class="col-sm-1 ">
                            <el-button type="primary" class="pull-left" icon="ti-control-play" @click="addMaterielSpon()" circle></el-button>
                            </div>
                          </div>
                        <div class="row" v-if="lesMaterielSpon.length>0" style="margin-top: -4%;">
                          <el-table style="margin-top:5%" :data="lesMaterielSpon" width="100%" header-align="center" height="200">
                          <el-table-column prop="materielExterne.nomMateriel"
                                          label="Materiel"
                                          min-width="150px"
                                          align="center">
                          </el-table-column>
                          <el-table-column prop="materielExterne.marqueMateriel"
                                          label="Marque"
                                          min-width="150px"
                                          align="center">
                          </el-table-column>
                          <el-table-column prop="materielExterne.coutMateriel"
                                          label="Valeur financière "
                                          min-width="150px"
                                          align="center">
                          </el-table-column>
                          <el-table-column prop="quantite"
                                          label="Quantité Affecter"
                                          min-width="150px"
                                          align="center">
                          </el-table-column>
                          <el-table-column
                            label="">
                            <template slot-scope="props">
                              <a class="btn btn-simple btn-danger btn-xs btn-icon trash"  @click="SuppSponMat(props.row)"><i class="ti-trash"></i></a>
                            </template>
                          </el-table-column>
                        </el-table>
                        </div>   
                        <div style="text-align:center;" >
                          <el-button style="mmargin-bottom: 2%;margin-top: 4%;" type="success" round @click="SubmitSponsarisation('FormDemSponseur')">ajouter sponsorisation</el-button>                  
                        </div>
                    </div>
                 </div>
  
                </el-form>
                </el-card>
                </div>
                <div v-else class="col-sm-12">
                  <el-card shadow="always" style="width: 54%; min-width: 390px;margin: 0px auto;">
                  <div>
                  <el-button type="primary" style="position: absolute;margin: -11px;" icon="el-icon-back" @click="addFormSponseur=false" circle></el-button>
                  <h5 style="text-align:center">Ajouter Un Sponseur Au Evénement</h5>
                  </div>
                  <el-form :model="FormSponseur" :rules="rulesFormSponseur" ref="FormSponseur" label-width="150px" label-position='left' >
                  <el-form-item label="Raison Sociale" prop="raisonSociale">
                    <el-input  v-model="FormSponseur.raisonSociale" placeholder="Raison Sociale"></el-input>
                  </el-form-item>
                  <el-form-item  label="Site De Sponseur" prop="urlSiteSponseur">
                    <el-input  v-model="FormSponseur.urlSiteSponseur" placeholder="Site De Sponseur"></el-input>
                  </el-form-item>
                  <el-form-item label="Logo De Sponseur" prop="logoSponseur">
                    <el-upload
                      :auto-upload="false"
                      class="logoUp"
                      v-model="FormSponseur.logoSponseur"
                      ref="sponseurLogo"
                      drag
                      :limit=1
                      :on-success="succesLogoUpload"
                      action="http://localhost:8090/api/Sponseur/logo"
                      :before-upload="beforeUpload"
                      :multiple="false"
                    >
                      <i class="el-icon-upload"></i>
                      <div class="el-upload__text">Déposer les fichiers ici ou <em> cliquez pour envoyer</em></div>
                    </el-upload>
                  </el-form-item>
                  <div style="text-align:center;">
                    <el-button  type="success" round @click="submitSponseur('FormSponseur')">Ajouter Sponseur</el-button>                  
                  </div>
                </el-form>
                  </el-card>
                </div>
            </div>

          </div>
    </el-tab-pane>

   <el-tab-pane>
    <span slot="label"><i class="ti-announcement"> </i> Les affiches </span>
         <div>
          <div v-if="dataRow.lesDemandeAffiche.length==0 && !showdemandeAffiche" style="text-align:center">
          <h1 class="Tarifation">Pas d'affiche Publicitaire  Disponible Pour ce Evenement</h1>
          <el-button style="margin-bottom: 10%;" type="success" round @click="showdemandeAffiche=true">Demande d'Affiche</el-button>
          </div>
          <div v-else-if="dataRow.lesDemandeAffiche.length >0 && !showdemandeAffiche">
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
              <el-button style="margin-bottom:5%;" type="success" round @click="showdemandeAffiche=true">Ajouter Demmande</el-button>
            </div>
          </div>
          <div v-else-if="showdemandeAffiche">
           <div class="row">
             <div class="col-sm-1 pull-left">
              <el-button  v-if="dataRow.lesDemandeAffiche.length>0" style="margin-top:20%;" type="primary" icon="el-icon-back" @click="showdemandeAffiche=false" circle></el-button>
             </div>
              <h3 class="col-sm-11 pull-left" style="text-align:center;color:gray">Demande D'affiche Publicitaire</h3>
           </div> 
          <el-form :model="FormAffiche" :rules="rulesFormAffiche" ref="FormAffiche"  >
          <el-form-item>
            <div class="row" style="margin-top:2%">
              <div class="col-sm-6">
                <el-form-item prop="demandeEcrite"  > 
                  <vue-editor  v-model="FormAffiche.demandeEcrit"></vue-editor> 
                </el-form-item>
              </div>
              <div class="col-sm-6">
                <el-upload
                style="margin-top:10%"
                  class="upload-demo"
                  :auto-upload="false"
                  ref="upload"
                  drag
                  :limit=1
                  :on-success="succesUpload"
                  action="http://localhost:8090/api/demandeAffiche/upload"
                  :before-upload="beforeUpload"
                  :multiple="false"
                  >
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">Déposer les fichiers ici ou<em>cliquez pour envoyer</em></div>
                  <div class="el-upload__tip" slot="tip">Fichiers jpg/png avec une taille inférieure à 500kb</div>
                </el-upload>
              </div>  
            </div>
          </el-form-item>
          </el-form>
          <div style="text-align:center">
           <el-button v-if="!UpdateAffiche" style="margin-bottom: 2%;margin-top:2%;" type="success" round @click="submitUpload">Envoyer</el-button>
           <el-button v-else style="margin-bottom: 2%;margin-top:2%;" type="success" round @click="submitUpload">Modifer</el-button>
          </div>
          </div>
         </div>
    </el-tab-pane>

  <el-tab-pane>
    <span slot="label"><i class="ti-stats-up"> </i>  demande d'aide financière</span>
      <div v-if="dataRow.lesDemandeFinance.length==0 && !showdemfinanceform" style="text-align:center">
        <h1 class="Tarifation">Pas de demande d'aide financière disponible pour ce événement</h1>
        <el-button style="margin-bottom: 10%;" type="success" round @click="showdemfinanceform=true">ajouter demande de Financement</el-button>
      </div>    
      <div v-if="dataRow.lesDemandeFinance.length>0 && !showdemfinanceform"  style="text-align:center" >
                  <h3 style="text-align:center" >Les demandes d'aide financière</h3>

          <div  class="col-sm-12" style="margin: 10px auto;float: none;text-align: right;">
              <el-button type="success" size="mini" icon="ti-plus" @click="showdemfinanceform=true" > Demande de financement</el-button>
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
      <div v-if="showdemfinanceform">
        <div class="row">
          <div v-if="!formAddFournisseur" class="col-sm-12">
            <el-card shadow="always">
              <el-button type="primary" icon="el-icon-back" @click="showdemfinanceform=false" circle></el-button>
              <h5 style="text-align:center;margin-top: 1px;margin-bottom: 24px;">Ajouter  demande d'aide financière</h5>
              <el-form :model="FormDemFinance"  style="margin: 0 auto;" ref="FormDemFinance" label-width="80px" label-position='left' >
                <div class="row">
                  <div class="col-sm-12">
                    <div class="row" style="margin-bottom:2%" >
                      <div class="col-sm-4">
                        <el-form-item label="les fournisseurs" prop="fournisseur"   label-width="150px">
                            <el-select
                                    size="large"
                                    placeholder=" choisir le fournisseur "
                                    v-model="FormDemFinance.fournisseur">
                            <el-option v-for="option in lesfournisseurs"
                                        :value="option.id"
                                        :v-model="option.id"
                                        :label="option.raisonSociale"
                                        :key="option.raisonSociale">
                            </el-option>
                            </el-select>
                        </el-form-item>
                      </div> 
                      <div class="col-sm-1 pull-left">
                        <el-button @click="formAddFournisseur=true" class="pull-left"  icon="ti-plus" type="primary" circle></el-button>
                      </div>
                      <div class="col-sm-6">
                        <div>
                         <el-form-item label="télécharger le devis " label-width="150px">
                          <el-upload
                            ref="ledevi"
                            :limit=1
                            :before-upload="erreurUpDevi"
                            :multiple="false"
                            :on-success="ajoutDemFinance"
                            action="http://localhost:8090/api/DemandeFinance/Devi"
                            :auto-upload="false">
                            <el-button slot="trigger" size="small" type="primary">Choisir un fichier</el-button>
                          </el-upload>
                         </el-form-item>
                         </div>
                      </div>
                      </div>        
                        <div class="row">
                          <div class="col-sm-4">
                              <el-form-item label="Materiel"  prop="materiel"  >
                                <el-select
                                        size="large"
                                        placeholder=" le Materiel"
                                        v-model="FormDemFinance.materiel">
                                <el-option v-for="option in lesMatExterne"
                                            :value="option.id"
                                            :v-model="option.id"
                                            :label="option.nomMateriel"
                                            :key="option.nomMateriel">
                                </el-option>
                                </el-select>
                              </el-form-item>                         
                          </div>
                          <div class="col-sm-1 ">
                            <el-button class="pull-left" type="primary" icon="ti-plus" @click="showaddMat=true" circle></el-button>
                          </div>
                          <div class="col-sm-3">
                            <el-form-item  label="Quantité" prop="quantite">
                                <el-input-number v-model="FormDemFinance.quantite" :min=0 ></el-input-number>
                            </el-form-item> 
                          </div>
                          <div class="col-sm-3">
                            <el-form-item  label="Prix (dt)" prop="prix">
                                <el-input-number v-model="FormDemFinance.prix" :precision="3"  :min=0 ></el-input-number>
                            </el-form-item> 
                          </div>
                          <div class="col-sm-1 ">
                          <el-button type="primary" class="pull-rigth" icon="ti-control-play" @click="ajouterMatFinance()" circle></el-button>
                          </div>
                        </div>
                      <div class="row" v-if="lesMataFinance.length>0" style="margin-top:-4%;">
                        <el-table style="margin-top:5%" :data="lesMataFinance" width="100%" header-align="center" height="200">
                        <el-table-column prop="matExterne.nomMateriel"
                                        label="Materiel"
                                        min-width="150px"
                                        align="center">
                        </el-table-column>
                        <el-table-column prop="matExterne.marqueMateriel"
                                        label="Marque"
                                        min-width="150px"
                                        align="center">
                        </el-table-column>
                        <el-table-column prop="montant"
                                        label="Prix (dt) "
                                        min-width="150px"
                                        align="center">
                        </el-table-column>
                        <el-table-column prop="quantite"
                                        label="Quantité Affecter"
                                        min-width="150px"
                                        align="center">
                        </el-table-column>
                        <el-table-column
                          label="">
                          <template slot-scope="props">
                            <a class="btn btn-simple btn-danger btn-xs btn-icon trash"  @click="SuppLigneDevi(props.row)"><i class="ti-trash"></i></a>
                          </template>
                        </el-table-column>
                      </el-table>
                      </div>   
                      <div style="text-align:center;" >
                        <el-button style="mmargin-bottom: 2%;margin-top: 4%;" type="success" round @click="uploadDevi('FormDemFinance')">Ajouter demande financière  </el-button>                  
                      </div>
                  </div>
                </div>

              </el-form>
              </el-card>
              </div>
              <div v-else >
               <el-button type="primary" icon="el-icon-back" style="margin-left:2%" @click="formAddFournisseur=false" circle></el-button>
               <div class="col-sm-10" style="margin: 0 auto;float: none;">
                <el-form :model="FormAjoutFourniss" :rules="rulesFormAjoutFourniss" ref="FormAjoutFourniss" label-width="150px" label-position='left' >
                 <div class="col-sm-6">
                   <el-form-item label="Matricule Fiscale" prop="matriculeFiscale">
                    <el-input  v-model="FormAjoutFourniss.matriculeFiscale" placeholder="Matricule Fiscale"></el-input>
                  </el-form-item>
                 </div>
                  <div class="col-sm-6">
                  <el-form-item label="Raison Sociale" prop="raisonSociale">
                  <el-input  v-model="FormAjoutFourniss.raisonSociale" placeholder="Raison Sociale"></el-input>
                  </el-form-item>
                  </div>
                  <div class="col-sm-6">
                  <el-form-item  label="Email" prop="email">
                  <el-input  v-model="FormAjoutFourniss.email" placeholder="Email"></el-input>
                  </el-form-item>
                  </div>
                  <div class='col-sm-6'>
                  <el-form-item  label="Téléphone" prop="tel">
                  <el-input  v-model="FormAjoutFourniss.tel" placeholder="tel"></el-input>
                  </el-form-item>
                  </div>
                  <div class='col-sm-6'>
                  <el-form-item  label="Fax" prop="fax">
                  <el-input  v-model="FormAjoutFourniss.fax" placeholder="Fax"></el-input>
                  </el-form-item>
                  </div>
                  <div class="col-sm-12" style="text-align:center;">
                  <el-button  type="success" round @click="ajoutfournisseur('FormAjoutFourniss')">Ajouter Fournisseur</el-button>                  
                  </div>
                </el-form>
               </div>
              </div>
          </div>  
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
        :title="getTariTitle(Tarifmofid)"
        :modalAppendToBody="false"
        :visible.sync="ajoutTarifs"
        :before-close="resetTarifForm"
        width="40%">
        <div class="row">
        <div class="col-sm-10" style="float: none;margin: 0 auto;">
        <el-form :model="tarifForm" :rules="rules" ref="tarifForm"  label-position='left' label-width="180px" >
        <el-form-item v-if="!Tarifmofid" label="Type de Tarif" prop="tarif"  >
          <el-select
          size="large"
          @change="verifDuplicatetarif(tarifForm.tarif)"
          placeholder="selectionner le Type"
          v-model="tarifForm.tarif">
            <el-option v-for="option in lestarifs"
            :value="option.id"
            :v-model="option.id"
            :label="option.tarif"
            :key="option.tarif">
            </el-option>
          </el-select>
           <el-button type="primary" @click="ajoutTypeTarifs=true" icon="ti-plus" circle></el-button>
        </el-form-item> 
        <el-form-item label="Montant (Dt)" class="labellef" prop="montant">
        <el-input-number v-model="tarifForm.montant" :min=0 :precision="2" :step="0.5"></el-input-number>
        </el-form-item>  
        <el-form-item label="Nombre Disponible" class="labellef" prop="nbrPlace">
        <el-input-number v-model="tarifForm.nbrPlace" :min=0 ></el-input-number>
        </el-form-item>  
        <div style="text-align: center;">
        <el-button v-if="!Tarifmofid" type="primary" @click="submitForm('tarifForm')">Ajouter</el-button>
         <el-button v-else  type="primary" @click="submitForm('tarifForm')">Modifier</el-button>
         <el-button  @click="resetTarifForm" >Annuler</el-button>
        </div>
        </el-form>
        </div>
        </div>
</el-dialog>
<el-dialog
        title="Type de Tarifation"
        :modalAppendToBody="false"
        :close-on-click-modal="false"
        :visible.sync="ajoutTypeTarifs"
        width="40%"
        center>
        <div class="row">
          <div class="col-sm-11">
          <el-form >
          <el-form-item label="Type" label-width="70px">
          <el-input v-model="typetarif" autocomplete="off" style="width:90%" required></el-input>
          </el-form-item>
          </el-form>
          <div style="text-align:center">
          <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addTypeTarif">Confirmer</el-button>
          <el-button @click="ajoutTypeTarifs = false">Annuler</el-button>
          </span>
          </div>
        </div>
       </div>
</el-dialog>
<el-dialog
    title="Ajouter Programme d'événement"
    center
    :modalAppendToBody="false"
    :visible.sync="ajoutProg"
    width="40%">
        <div class="row">
          <div class="col-sm-11">
            <el-form  :model="ProgForm" :rules="progRules" ref="ProgForm" label-width="135px" label-position='left' class="demo-ruleForm">
            <el-form-item  label="Date " prop="dateProgramme">
            <el-date-picker  v-model="ProgForm.dateProgramme" 
            value-format="yyyy-MM-dd"
            @change="checkDate"
            type="date"
            :picker-options = "pickerOptions2"
            placeholder="Choississez un jour">
            </el-date-picker> 
            </el-form-item> 
            <el-form-item label="Duré " required label-position="left" label-width="120px">
            <div class="col-sm-6">
            <el-form-item  prop="tempDebProg">
            <el-time-select
            style="width:100%;"
            placeholder="Horaire de début"
            v-model="ProgForm.tempDebProg"
            :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59'
            }">
            </el-time-select>
            </el-form-item>
            </div>
            <div class="col-sm-6">
            <el-form-item  prop="tempFinProg">
            <el-time-select
            style="width:100%;"
            placeholder="Horaire de fin"
            v-model="ProgForm.tempFinProg"
            :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59',
            minTime: ProgForm.tempDebProg
            }">
            </el-time-select> 
            </el-form-item>
            </div>
            </el-form-item>    
            </el-form>
        </div>
        <div style="text-align:center">
          <el-button type="primary"  @click="submitProgr('ProgForm')" plain>Ajouter</el-button>
          <el-button   @click="ajoutProg=false" plain>Annuler</el-button>
        </div>
        </div>
</el-dialog>
<el-dialog
        :title="'Programde De : ' + SelectedProg.dateProgramme" 
        :modalAppendToBody="false"
        :visible.sync="programmeJour"
        width="55%"
        :before-close="function (){editTarifction=false , programmeJour=false} "
        center>
        <div v-if="!ajoutAction && SelectedProg.lesAction.length==0 " class="row">
        <div  style="text-align:center">
        <h4 class="Tarifation">Pas de Planification de ce Jour Trouver</h4>
        <el-button type="success" round @click="ajoutAction=true">Ajouter Tache</el-button>        
        </div>
        </div>
        <div v-else class="row">
           <el-form  :model="ActionForm" :rules="ActionRules" ref="ActionForm" class="demo-ruleForm">
            <el-form-item  required label-position="left" >
            <div class="col-sm-6">
              <el-form-item   prop="action"    >
              <el-input  v-model="ActionForm.action" placeholder="Entrez La Tache" ></el-input>
            </el-form-item>
            </div>
            <div class="col-sm-4">
            <el-form-item  prop="tempDebAct">
              <el-time-select
                style="width:100%;"
                placeholder="début"
                v-model="ActionForm.tempDebAct"
                :picker-options="{
                start: SelectedProg.tempDebProg,
                step: '00:05',
                end: SelectedProg.tempFinProg
                }">
              </el-time-select>
              </el-form-item>
              </div>
            <div class="col-sm-2">
                <el-button type="success" icon="ti-plus" @click="submitAction('ActionForm')" circle></el-button>
            </div>
          </el-form-item>
           </el-form>
          <div v-if="dejaExiste" style="text-align:center;margin-top:2%">
            <small style="color:red;">Une Tache existe Pendant cette Duré </small>
          </div> 
           <template>
              <el-table style="margin-top:5%" :data="this.SelectedProg.lesAction" header-align="center" height="350">
                <el-table-column prop="tempDebAct"
                                label="Temp Debut"
                                min-width="100px"
                                header-align="center"
                                align="center">
                </el-table-column>
                <el-table-column prop="action"
                                label="Tache"
                                header-align="center"
                                min-width="250px"
                                align="center">
                </el-table-column>
                <el-table-column
                  label="">
                  <template slot-scope="props">
                    <a class="btn btn-simple btn-warning btn-xs btn-icon edit" @click="handleEdit(props.$index, props.row)"><i class="ti-pencil-alt"></i></a>
                    <a class="btn btn-simple btn-danger btn-xs btn-icon remove"  @click="handleDelete(props.$index, props.row)"><i class="ti-close"></i></a>
                  </template>
                </el-table-column>
              </el-table>
           </template>
           <hr/>
        </div>       
</el-dialog>
<el-dialog
        title="Modifier Action"
        :modalAppendToBody="false"
        :visible.sync="editAction"
        width="55%"
        center>
        <div class="row">
            <el-form  :model="actionAModifier" :rules="ActionRules" ref="actionAModifier" class="demo-ruleForm">
            <el-form-item  required label-position="left" >
            <div class="col-sm-7">
              <el-form-item   prop="action"    >
              <el-input  v-model="actionAModifier.action" placeholder="Entrez La Tache" ></el-input>
            </el-form-item>
            </div>
            <div class="col-sm-5">
            <el-form-item  prop="tempDebAct">
              <el-time-select
                style="width:100%;"
                placeholder="début"
                v-model="actionAModifier.tempDebAct"
                :picker-options="{
                start: '00:00',
                step: '00:15',
                end: '23:59'
                }">
              </el-time-select>
              </el-form-item>
              </div>
            <div class="col-sm-1">
                <el-button type="success" icon="el-icon-edit" @click="updateAction('actionAModifier')" circle></el-button>
            </div>
          </el-form-item>
           </el-form>         
        
       </div>
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
        title="Ajouter Matériel Externe"
        :modalAppendToBody="false"
        :visible.sync="showaddMat"
        width="40%">
        <div class="row">
        <div class="col-sm-11">
        <el-form :model="MaterielForm" :rules="Materielrules" ref="MaterielForm"  label-position='left' label-width="180px" >
        <el-form-item label="Reference Matériel" prop="referenceMateriel"  >
          <el-input  v-model="MaterielForm.referenceMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Reference Matériel" prop="nomMateriel"  >
        <el-input  v-model="MaterielForm.nomMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Marque Materiel" prop="marqueMateriel">
        <el-input  type="text" v-model="MaterielForm.marqueMateriel" ></el-input>
        </el-form-item>
        <el-form-item label="Valeur (dt)"  prop="coutMateriel"  >
        <el-input type="number"  v-model="MaterielForm.coutMateriel" ></el-input>
        </el-form-item>   
        <el-button style="float:left" type="primary" @click="submitMaterielForm('MaterielForm')">Ajouter</el-button>
        </el-form>
        </div>
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
        if(this.dataRow.lesProgrammes.length>0)
        this.dataRow.lesProgrammes = this.dataRow.lesProgrammes.sort(function(a, b) {
          return new Date(a.dateProgramme) - new Date(b.dateProgramme); 
        })
        this.vedioLink=this.dataRow.vedioLink
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
      vedioLink:'',
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
      tarifForm:{
        id:null,
        tarif:'',
        montant:0,
        nbrPlace:0
      },
      ProgForm:{
        dateProgramme:'',
        tempDebProg:'',
        tempFinProg:''
      },
      ActionForm:{
        action:'',
        tempDebAct:''
      },
      actionAModifier:{
        id:'',
        action:'',
        tempDebAct:''
      },
      FormAffiche:{
        DemandeImage:'',
        demandeEcrit:''
      },
      FormDemSponseur:{
         sponseur:'',
         montant: 0,
         materiel:'',
         quantite:0
      },
      FormSponseur:{
      raisonSociale: '',
      urlSiteSponseur:'',
      logoSponseur: ''
      },
      MaterielForm:{
      referenceMateriel:'',
      nomMateriel:'',
      marqueMateriel: '',
      coutMateriel: ''
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
      rules: {
      tarif: [
      { required: true, message: 'Le Nom de Salle est nécessaire', trigger: 'blur' }
      ],
      montant: [
      { required: true, message: 'Choisir Une Valeur', trigger: 'blur' },
      {validator: QteNegative, trigger: 'blur'}
      ],
      nbrPlace: [
      {validator: QteNegative, trigger: 'blur'}
      ]
      },
      progRules:{
      dateProgramme: [
      { required: true, message: 'Le Date De Programme est nécessaire', trigger: 'blur' }
      ],
      tempDebProg: [
      { required: true, message: 'Le Temp Début nécessaire', trigger: 'blur' }
      ],
      tempFinProg: [
      { required: true, message: 'Le Temp Fin nécessaire', trigger: 'blur' },
      ]
      },
      ActionRules:{
      action: [
      { required: true, message: 'La Champs Tache est Vider', trigger: 'blur' }
      ],
      tempDebAct: [
      { required: true, message: 'Le Temp Début est nécessaire', trigger: 'blur' }
      ]
      },
      rulesFormAffiche:{
      demandeEcrit: [
      { required: true, message: 'La Champs Tache est Vider', trigger: 'blur' }
      ],
      },
      rulesFormDemSponseur:{
      sponseur: [
      { required: true, message: 'Selection Le Sponseur', trigger: 'change' }
      ],
      montant: [
      { required: true, message: 'le Champ Montant est obligatoire', trigger: 'blur' }
      ],
      },
      matSpon:{
      quantite:[
      { required: true, message: 'le Champ Quantite est obligatoire', trigger: 'blur' }
      ],
      materiel:[
      { required: true, message: 'Selection Le Materiel', trigger: 'change' }
      ],
      },
      rulesFormSponseur:{
      raisonSociale: [
      { required: true, message: 'Le Champ Raison Social est Obligatoire', trigger: 'blur' }
      ],
      logoSponseur: [
      { validator: validateImg, trigger: 'change' },
      ],
      },
      Materielrules:{
      referenceMateriel: [
      { required: true, message: 'La Reference de Materiel est Obligatoire', trigger: 'blur' }
      ],
      nomMateriel: [
      { required: true, message: 'Le Nom de Materiel est Obligatoire', trigger: 'blur' }
      ],
      marqueMateriel: [
      { required: true, message: 'Le Champ Marque est Obligatoire', trigger: 'blur' }
      ],
      coutMateriel: [
      { required: true, message: 'Le Champ Valeur De Materiel', trigger: 'blur' }
      ],
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
      FormDemFinance:{
      dateCreation:new Date(),
      fournisseur:'',
      devi:'',
      deviMateriel:[],
      quantite:'',
      materiel:'',
      prix:''
      },
      FormAjoutFourniss:{
      matriculeFiscale:'',
      raisonSociale:'',
      email:'',
      tel:'',
      fax:'',
      },
      rulesFormAjoutFourniss:{
        matriculeFiscale: [
        { required: true, message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        raisonSociale: [
        { required: true, message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        email: [
        { required: true , message: 'ce champs est obligatoire', trigger: 'blur' },
        {type: 'email' , message: "La format de l'email n'est pas valide ",trigger: 'blur' }
        ],
        tel: [
        { required: true, message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
        fax: [
        { required: true , message: 'ce champs est obligatoire', trigger: 'blur' }
        ],
      }
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
    setvediolink(){
       var self = this
      swal({
        text: `Voulez Vous Vraiment Changer ce lien `,
        type: 'info',
        showCancelButton: true,
        confirmButtonClass: 'btn btn-success btn-fill',
        cancelButtonClass: 'btn btn-danger btn-fill',
        confirmButtonText: 'Publier',
        buttonsStyling: false
      }).then(function () {
         http
          .put("/event/vedioLink/"+self.dataRow.id, self.vedioLink)
          .then(response => {
            swal({
            title: "le Lien de védio est enregistrer a été publier",
            type: 'success',
            confirmButtonClass: 'btn btn-success btn-fill',
            buttonsStyling: false
            })
          })
      })
    },  
    verifDuplicatetarif( selectedTarif){
      console.log(selectedTarif)
      let index = this.dataRow.lesTarifs.findIndex(tarifClub => tarifClub.tarifation.id === selectedTarif )
      if(index>=0)
      {
        this.tarifForm.tarif=''
        swal("","ce type a déja été affecter a une tarification de ce événement","info")
      }
    },
    getTariTitle(ismodif){
      if(!ismodif)
      return "Ajouter Tarifation"
      else
      return "Modifier Tarifation"
    },
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
    swal("","Un Programme existe déja pendant cette date ","info")
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
          }
        let index = this.SelectedProg.lesAction.findIndex((action) => laction.tempDebAct  === action.tempDebAct )
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
          tempFinProg:this.ProgForm.tempFinProg,
          lesAction:[],
        }
        http.put("/event/Programe/Affect/"+this.dataRow.id,Programme)
        .then(response => {
          this.ajoutProg=false
          if(response.data.length>0)
          {
          self.dataRow.lesProgrammes=response.data
          self.dataRow.lesProgrammes=self.dataRow.lesProgrammes.sort(function(a, b) {
          return new Date(a.dateProgramme) - new Date(b.dateProgramme); 
          })
          }
          else
          return 
          this.$refs["ProgForm"].resetFields()
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Programme Ajouter avec succès',
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
