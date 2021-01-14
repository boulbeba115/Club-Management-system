<template>
  <div>
    <div class="row">
      <div class="col-sm-12">
        <div class="card card-wizard" id="wizardCard">
 <form-wizard @on-complete="onComplete"
                           color="#6178ca"         
                          back-button-text="Précédent"
                          next-button-text="Suivant" >
            <h2 slot="title">Demande De Creation d'un Événement </h2>
            <tab-content title="Information Génerale" icon="ti-info" :before-change="validatestep1" >
                  <form class="form-horizontal" >
                      <div class="card-content">
                        <div class="row">
                          <div class="col-sm-6">
                          <fieldset>
                            <div class="form-group">
                              <label class="col-sm-4 control-label" >Nom D'Événement</label>
                              <div class="col-sm-6">
                                <input type="text"
                                      name="nomEvent"
                                      v-model="nomEvent"
                                      class="form-control myinput"
                                      @click="reqNomEven=false">

                              </div>
                                <small class="text-danger col-sm-6" v-show="reqNomEven">
                                  Ce Champ Nécessaire
                                </small>
                            </div>
                          </fieldset>
                          </div>
                          <div class="col-sm-6">
                                <div class="col-sm-12">
                                  <fieldset>
                                    <div class="form-group">
                                      <el-radio-group @change="filterType" v-model="radioexterne">
                                        <el-radio label="0" border>Événement interne</el-radio>
                                        <el-radio label="1" border>Événement Externe</el-radio>
                                      </el-radio-group>
                      
                                    </div>
                                  </fieldset>
                                </div> 
                          </div>
                        </div>
                          <div style="margin-top:3%" class="row">
                            <div class="col-sm-6">
                            <fieldset>
                              <div class="form-group">
                              <label class="col-sm-4 control-label">Date Prévu événement</label>
                              <div class="col-sm-6">
                                <el-date-picker
                                  v-model="dureevent"
                                  @change="test"
                                  type="daterange"
                                  range-separator="à"
                                  start-placeholder="Date Debut "
                                  end-placeholder="Date Fin "
                                  :picker-options = "pickerOptions">
                                </el-date-picker> 
                                </div>
                                <small class="text-danger col-sm-6" v-show="reqdatedeb">
                                s'il vous plaît specifier La Durée de l'événement
                                </small>
                              </div>
                            </fieldset>
                            </div>
                            <div class="col-sm-6">
                                  <fieldset>
                                      <div class="form-group">
                                        <label class="col-sm-4 control-label">Type d'évenement</label>
                                        <div class="col-sm-6" >
                                          <el-select class="select-default" @change="reqType=false"
                                                    size="large"
                                                    placeholder="selectionner le Type"
                                                    v-model="selects.simple">
                                            <el-option v-for="option in selects.types"
                                                      class="select-default" 
                                                      :value="option.typeEvenement"
                                                      :v-model="option.typeEvenement"
                                                      :label="option.typeEvenement"
                                                      :key="option.typeEvenement">
                                            </el-option>
                                          </el-select>
                                          <span class="addtypebtn ti-plus" id="show-modal" @click="showaddtype = true"></span>
                                        </div>
                                        <small class="text-danger col-sm-4" v-show="reqType">
                                          s'il vous plaît choisir le Type
                                        </small>
                                      </div>
                                  </fieldset>
                              </div>
                              </div>           
                      </div>
                  </form>
            </tab-content>
            <template v-if="radioexterne=='1'">
                <tab-content title="Lieu"
                            icon="ti-location-pin" :before-change="validatevoyage">
                          <template v-if="selects.simple=='voyage'">
                                    <div class="row" style="margin-top:2%">
                                      <div class="col-sm-8">
                                      <fieldset>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" style="margin-top:2%" >Ville A visité</label>
                                              <div class="col-sm-6">
                                              <el-select  class="select-default"
                                                        size="large"
                                                        placeholder="selectionner la Ville"
                                                        v-model="villes.simple" >
                                                <el-option v-for="option in villes.ville"
                                                          class="select-default"
                                                          :value="option.id"
                                                          :v-model="option.id"
                                                          :label="option.ville"
                                                          :key="option.ville">
                                                </el-option>
                                              </el-select>
                                              <span class="addtypebtn ti-plus" id="show-modal" @click="showaddville = true"></span>  
                                              </div>
                                              <small class="text-danger col-sm-12" v-show="VilleSelectErr">
                                                s'il vous plaît choisir la Ville
                                              </small>
                                        </div>
                                      </fieldset>
                                      </div>
                                    </div>
                                    <div class="row" style="margin-top:3%">
                                    <div class="col-sm-6">
                                       <fieldset>
                                        <div class="form-group">
                                          <label style="margin-top:2%" class="col-sm-2 control-label">Site</label>
                                          <div class="col-sm-10">
                                            <input type="text"
                                                  name="site"
                                                  v-model="site"
                                                  class="form-control myinput">
                                          </div>
                                            <small class="text-danger col-sm-12" v-show="siteErr">
                                              Ce champs est vide
                                            </small>                                          
                                        </div>
                                      </fieldset>
                                    </div>
                                    <div class="col-sm-6">
                                        <fieldset>
                                        <div class="form-group">
                                          <label style="margin-top:2%" class="col-sm-2 control-label">Adresse</label>
                                          <div class="col-sm-9">
                                            <input type="text"
                                                  name="adresse"
                                                  v-model="adresse"
                                                  class="form-control myinput">
                                          </div>
                                            <small class="text-danger col-sm-12" v-show="adresseErr">
                                              L'adresse est Vide
                                            </small>
                                        </div>
                                        <span class="ti-plus adddestVoyage" @click="adddestVoyage"></span>
                                      </fieldset>
                                    </div>
                                    </div>
                                    <div class="col-sm-12" style="margin-top:1%">
                                        <el-table  empty-text="Pas De Resultat" :data="destination" class="lieuliste">
                                          <el-table-column type="index">

                                          </el-table-column>
                                          <el-table-column prop="site"
                                                          align="center"
                                                          header-align="center"
                                                          :min-width="300"   
                                                          label="Site">
                                          </el-table-column>
                                          <el-table-column prop="adresse"
                                                          align="center"
                                                          header-align="center"
                                                          :min-width="300"
                                                          label="Adresse">
                                          </el-table-column>
                                          <el-table-column prop="ville.ville"
                                                           align="center"
                                                           header-align="center"
                                                           :min-width="200"
                                                            label="Ville">
                                          </el-table-column>
                                          <el-table-column
                                          align="center"
                                          header-align="center"
                                           :min-width="80"
                                            label="Actions">
                                            <template slot-scope="props">
                                              <a class="btn btn-simple btn-danger btn-xs btn-icon remove"  @click="handleDelete(props.$index, props.row)"><i class="ti-close"></i></a>
                                            </template>
                                          </el-table-column>
                                        </el-table>
                                      </div>
                          </template> 
                          <template v-else>
                          <form class="form-horizontal">
                          <div class="card-content">
                                    <div class="row" style="margin-top:2%" >
                                      <div class="col-sm-6">
                                      <fieldset>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Ville</label>
                                            <div class="col-sm-6">
                                              <el-select  class="select-default "
                                                        size="large"
                                                        placeholder="selectionner la Ville"
                                                        v-model="villes.simple" >
                                                <el-option v-for="option in villes.ville"
                                                          class="select-default"
                                                          :value="option.id"
                                                          :v-model="option.id"
                                                          :label="option.ville"
                                                          :key="option.ville">
                                                </el-option>
                                              </el-select>
                                          <span class="addtypebtn ti-plus" id="show-modal" @click="showaddville = true"></span>  
                                          </div>
                                            <small class="text-danger col-sm-12" v-show="VilleSelectErr">
                                              s'il vous plaît choisir la Ville
                                            </small>
                                        </div>
                                      </fieldset>
                                      </div>  
                                      <div class="col-sm-6 ">
                                      <fieldset>
                                        <div class="form-group">
                                          <label class="col-sm-2 control-label">Site</label>
                                          <div class="col-sm-10">
                                            <input type="text"
                                                  name="site"
                                                  v-model="site"
                                                  class="form-control myinput">
                                            <small class="text-danger" v-show="true">
                                            </small>
                                          </div>
                                          <small class="text-danger col-sm-12" v-show="siteErr">
                                            Ce champs est vide
                                          </small>   
                                        </div>
                                      </fieldset>
                                      </div>  
                                </div>   
                                  <div class="row" style="margin-top:2%" >
                                  <div class="col-sm-6">
                                      <fieldset>
                                        <div class="form-group">
                                          <label class="col-sm-2 control-label">Adresse</label>
                                          <div class="col-sm-10">
                                            <input type="text"
                                                  name="adresse"
                                                  v-model="adresse"
                                                  class="form-control myinput">
                                            <small class="text-danger" v-show="true">
                                            </small>
                                          </div>
                                            <small class="text-danger col-sm-12" v-show="adresseErr">
                                              Ce champs est vide
                                            </small>   
                                        </div>
                                      </fieldset>
                                  </div>
                                      <div class="col-sm-6 ">
                                      <fieldset>
                                        <div class="form-group">
                                          <label class="col-sm-3 control-label">Code Postal</label>
                                          <div class="col-sm-6">
                                            <input type="number"
                                                  name="nomEvent"
                                                  v-model="codepostal"
                                                  class="form-control myinput">
                                          </div>
                                            <small class="text-danger col-sm-12" v-show="posteerr">
                                              Ce champs est vide
                                            </small>  
                                        </div>
                                      </fieldset>
                                      </div>                                    
                                  </div>
                          </div>
                          </form>
                          </template>

                </tab-content>
                <tab-content title="Plus d'informations"
                            icon="ti-notepad" >
                  <vue-editor                  
                  name="description"
                  v-model="moreInfo">
                  </vue-editor>
                </tab-content>

            </template>
            <template v-else>
                <tab-content title="Salle"
                            icon="ti-home" >
                            <div class="row">
                            <div class="col-sm-3 pull-right">
                            <el-input v-model="search"  placeholder="Recherche Salle"/>
                            </div>
                            </div>
                              <el-table
                                  :data="sallesList.filter(data => !search || data.nomSalle.toLowerCase().includes(search.toLowerCase()))"
                                  style="width: 100%"
                                  height="200"
                                  align="center"
                                  header-align="center"
                                  :default-sort = "{prop: 'salleType.type'}"
                                  empty-text="Pas De Resultat">
                                  <el-table-column type="expand">
                                    <template slot-scope="scope">
                                       <h5 style="text-align:center" v-if="lesPlaniSalle(scope.row)==null ||lesPlaniSalle(scope.row).length==0">Pas de Planification</h5>
                                      <ul style="text-align:center"  v-for="p in lesPlaniSalle(scope.row)">
                                        <li Style=" list-style-type: none;">
                                          <div>
                                            <div class="col-sm-12" style="text-align:center">
                                              Le : {{ p.dateResSalle | moment("DD-MM-YYYY") }} De : {{p.tempdebRes}} à {{p.tempFinRes}}
                                              <a class="btn btn-simple btn-xs btn-danger btn-icon remove"  @click="supprimerPanificationSalle(p,lesPlaniSalle(scope.row))"><i class="ti-close"></i></a>
                                            </div>
                                          </div>
                                        </li>
                                      </ul>
                                    </template>
                                  </el-table-column>
                                  <el-table-column
                                  align="center"
                                  header-align="center"
                                    property="nomSalle"
                                    label="Nom de Salle"
                                    min-width="200"
                                    sortable>
                                  </el-table-column>
                                  <el-table-column
                                    align="center"
                                    header-align="center"
                                    property="capcite"
                                    label="Capcité"
                                    sortable
                                    min-width="200"
                                  >
                                  </el-table-column>
                                  <el-table-column
                                    align="center"
                                    header-align="center"
                                    property="salleType.type"
                                    label="Type"
                                    sortable
                                   min-width="200"
                                  >
                                  </el-table-column>
                                  <el-table-column
                                  align="center"
                                  header-align="center"
                                  label="Retro Projecteur"
                                  min-width="150">
                                  <template slot-scope="scope">
                                    <p v-if="scope.row.retroproj">Équipé</p>
                                    <P v-else >Non Équipé</P>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                align="center"
                                header-align="center"
                                label="Planifier"
                                min-width="100"
                                >
                                <template slot-scope="scope">
                                <el-button class="btn-planif" icon="ti-plus" circle @click="PlanifSalle(scope.row)"></el-button>
                                </template>
                                </el-table-column>

                                </el-table>
              </tab-content>
                <tab-content title="Materiel"
                            icon="ti-package">
                             <div class="row">
                            <div class="col-sm-3 pull-right">
                            <el-input v-model="search"  placeholder="Recherche Salle"/>
                            </div>
                            </div>
                              <el-table
                                  ref="materielTable"
                                  :data="materielListe.filter(data => !search || data.nomMateriel.toLowerCase().includes(search.toLowerCase()))"
                                  style="width: 100%"
                                  height="250"
                                  align="center"
                                  header-align="center"                                  
                                  :default-sort = "{prop: 'nomMateriel'}"
                                  empty-text="Pas De Resultat">
                                  <el-table-column type="expand">
                                    <template slot-scope="scope">
                                        <h5 style="text-align:center" v-if="lesMateriel(scope.row)==null || lesMateriel(scope.row).length==0">Pas de Planification</h5>
                                      <ul style="text-align:center"  v-for="p in lesMateriel(scope.row)">
                                          <li Style=" list-style-type: none;">
                                            <el-row :gutter="20">
                                              <el-col :span="5"><div class="grid-content bg-purple">Le :  <span>{{ p.dateResSalle | moment("DD-MM-YYYY") }}</span> </div></el-col>
                                              <el-col :span="5"><div class="grid-content bg-purple">De : {{ p.tempdebRes}}</div></el-col>
                                              <el-col :span="5"><div class="grid-content bg-purple">à  : {{ p.tempFinRes}}</div></el-col>
                                              <el-col :span="5"><div class="grid-content bg-purple">Quantité :{{p.qteDemander}}</div></el-col>
                                              <el-col :span="1"><div class="grid-content bg-purple">
                                                <a class="btn btn-simple btn-xs btn-danger btn-icon remove"  @click="supprimerPanificationMat(p,lesMateriel(scope.row))"><i class="ti-close"></i></a>
                                              </div>
                                              </el-col>
                                            </el-row>
                                        </li>
                                      </ul>
                                    </template>
                                  </el-table-column>
                                  <el-table-column
                                    property="nomMateriel"
                                    label="Matériel"
                                    align="center"
                                    min-width="180"
                                    sortable>
                                  </el-table-column>
                                  <el-table-column
                                  property="marqueMateriel"
                                  label="Marque"
                                  min-width="180"
                                  align="center"
                                  sortable>
                                  </el-table-column>
                                  <el-table-column
                                    property="qteTotale"
                                    align="center"
                                    label="Quantité Totale"
                                    sortable
                                    min-width="180"
                                  >
                                  </el-table-column>
                                  <el-table-column
                                  align="center"
                                  label="Planification"
                                  min-width="180"
                                  >
                                  <template slot-scope="scope">
                                    <p v-if="lesMateriel(scope.row)!=null">{{ lesMateriel(scope.row).length}}</p>
                                    <p v-else>0</p>
                                  </template>
                                  </el-table-column>
                                  <el-table-column
                                    align="center"
                                    label="Planifier"
                                    min-width="180"
                                  >
                                  <template slot-scope="scope">
                                  <el-button class="btn-planif" icon="ti-plus" circle @click="planifMatFaculter(scope.row)"></el-button>
                                  </template>
                                  </el-table-column>
                                </el-table>
                </tab-content>
                <tab-content title="Plus d'informations"
                            icon="ti-notepad" >
                 <vue-editor name="description" v-model="moreInfo"></vue-editor>
                </tab-content>
            </template>
        </form-wizard>
        </div>
      </div>
    </div>


    <div v-if="showaddville">
          <transition name="modal">
              <div class="modal-mask">
                <div class="modal-wrapper">
                  <div class="modal-container">

                    <div class="modal-header">
                      <slot name="header">
                        <div class="modheader">Ajouter Un Type d'événem</div>
                      </slot>
                    </div>

                    <div class="modal-body">
                      <slot name="body">
                        <form class="form-horizontal">
                         <fieldset>
                            <div class="form-group">
                              <template v-if="showaddville">
                                <div class="col-sm-4">
                                <label  style="margin-top:7%" class="typelabel">Ville</label>
                                </div>
                                <div class="col-sm-8">
                                  <input type="text"
                                        name="TypeClub"
                                        v-model="newVille"
                                        class="form-control">
                                  <small class="text-danger" >
                                  </small>
                                </div>
                              </template>
                            </div>
                        </fieldset>
                        </form>
                      </slot>
                    </div>

                    <div class="modal-footer">
                      <slot name="footer">
                        <button  type="button" class="btn btn-wd btn-success" @click="addVille">
                          <span class="btn-label">
                              <i class="fa fa-check"></i>
                          </span>
                          Ajouter 
                        </button>

                        <button type="button" class="btn btn-wd btn-danger" @click="showaddville=false">
                          <span class="btn-label">
                              <i class="fa fa-times"></i>
                          </span>
                          Annuler
                        </button>                        
                      </slot>
                    </div>
                  </div>
                </div>
              </div>
            </transition>
      </div>

  <el-dialog
  title="Ajouter Type Evénement"
  :modalAppendToBody="false"
  :visible.sync="showaddtype"
    width="40%">
      <div class="row">
      <div class="col-sm-11">
      <el-form :model="typeEventForm" :rules="eventTypeRules" ref="typeEventForm" label-width="180px" label-position='left' class="demo-ruleForm">
      <el-form-item  label="Type d'évenement"  prop="typeEvenement"  >
        <el-input  v-model="typeEventForm.typeEvenement" ></el-input>
      </el-form-item>
      <el-form-item label="localité" prop="localiter">
        <el-radio-group v-model="typeEventForm.localiter">
          <el-radio-button label="Externe"></el-radio-button>
          <el-radio-button label="interne"></el-radio-button>
          <el-radio-button label="Les Deux"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-button style="float:left" type="primary" @click="submitajoutType('typeEventForm')">Ajouter Type</el-button>
      </el-form>
      </div>
      </div>
  </el-dialog>


<el-dialog
    title="Planifier Salle"
    center
    :modalAppendToBody="false"
    :visible.sync="planifcatSalle"
    width="40%">
        <div class="row">
            <el-form  :model="PlanifSalleForm" :rules="salleFormrules" ref="PlanifSalleForm" label-width="180px" label-position='left' class="demo-ruleForm">
            <el-form-item  label="Date Réservation" prop="dateResSalle">
            <el-date-picker  @change="verifiSalleDispo()" v-model="PlanifSalleForm.dateResSalle" 
            value-format="yyyy-MM-dd"
            type="date"
            :picker-options = "pickerOptions2"
            placeholder="Choisissez un jour">
            </el-date-picker> 
            </el-form-item> 
            <el-form-item label="Duré de Réservation" required label-position="left" label-width="180px">
            <div class="col-sm-6">
            <el-form-item  prop="tempdeb">
            <el-time-select
            style="width:100%;"
            placeholder="Horaire de début"
            v-model="PlanifSalleForm.tempdeb"
            :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:00'
            }">
            </el-time-select>
            </el-form-item>
            </div>
            <div class="col-sm-6">
            <el-form-item  prop="tempfin">
            <el-time-select
            style="width:100%;"
            placeholder="Horaire de fin"
            v-model="PlanifSalleForm.tempfin"
            :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:00',
            minTime: PlanifSalleForm.tempdeb
            }">
            </el-time-select> 
            </el-form-item>
            </div>
            </el-form-item>     
            <div v-if="showsalleDisponibility" v-html="salleDisponibility"></div>                   
            <div style="text-align:center">
            <el-button type="primary"  @click="submitPlanifSalle('PlanifSalleForm')" plain>Ajouter</el-button>
            </div>
            </el-form>
        </div>
</el-dialog>

  <el-dialog
    title="Planifier Materiel"
    center
    :modalAppendToBody="false"
    :visible.sync="showPlanifMat"
      width="50%">
        <div class="row">
        <div class="col-sm-9" style="margin: 0 auto;float: none;">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  label-position='left' label-width="180px" >
        <div class="row">

        <el-form-item label="Date De Réservation" prop="dateResMat">
        <el-date-picker v-model="ruleForm.dateResMat" @change="getQteDisp()" type="date"
        :picker-options = "pickerOptions2"
        value-format="yyyy-MM-dd"
        placeholder="Choisissez un jour">
        </el-date-picker> 
        </el-form-item>
        
          <div class="row">
            <el-form-item label="Duré de Réservation" required label-position="left" label-width="180px">
            <div class="col-sm-6">
            <el-form-item  prop="tempdeb">
              <el-time-select
                style="width:100%;"
                placeholder="Horaire de début"
                v-model="ruleForm.tempdeb"
                :picker-options="{
                start: '08:30',
                step: '00:15',
                end: '18:30'
                }">
              </el-time-select>
              </el-form-item>
            </div>
            <div class="col-sm-6">
              <el-form-item  prop="tempfin">
              <el-time-select
                style="width:100%;"
                placeholder="Horaire de fin"
                v-model="ruleForm.tempfin"
                :picker-options="{
                start: '08:30',
                step: '00:15',
                end: '18:30',
                minTime: ruleForm.tempdeb
                }">
              </el-time-select> 
              </el-form-item>
            </div>
            </el-form-item>
          </div>    
          <div class="row">
            <div class="col-sm-6">
              <el-form-item label="Quantité a Demander" prop="qteDemm" label-position="left" label-width="170px">
              <el-input   type="number" min="0" :max="qteDisponible" style="width:100%" v-model="ruleForm.qteDemm" ></el-input>
              </el-form-item>             
            </div>
            <div class="col-sm-6" v-if="showqteDispible">
              <el-form-item label="Qte Disponible"  label-position="left" label-width="144px">
              <el-input  disabled min="0" type="number" style="width:100%" v-model="qteDisponible" ></el-input>
              </el-form-item>             
            </div>
          </div>


        <div style="text-align:center"> 
         <el-button type="primary" @click="submitForm('ruleForm')" plain>Ajouter</el-button>
        </div>
        </div>
        </el-form>
        </div>
        </div>
    </el-dialog>
  </div>
</template>
<script>
/* eslint-disable */
  import Vue from 'vue'
  import {FormWizard, TabContent} from 'vue-form-wizard'
  import 'vue-form-wizard/dist/vue-form-wizard.min.css'
  import swal from 'sweetalert2'
  import http from '../axiosbase.js'
  import {Select, Option, DatePicker, TimePicker , Input , Button , Row , Col, Radio , RadioGroup , RadioButton,TimeSelect} from 'element-ui'
  import { VueEditor } from "vue2-editor"
  import moment from 'moment'

  Vue.prototype.moment = moment
  import { extendMoment } from 'moment-range';

  Vue.use(Select)
  Vue.use(Option)
  Vue.use(Input)
  Vue.use(Button)
  Vue.use(TimePicker)
  Vue.use(Row)
  Vue.use(Col)
  Vue.use(Radio)
  Vue.use(RadioGroup)
  Vue.use(RadioButton)
  Vue.use(TimeSelect)



  Vue.use(require('vue-moment'))
  export default {
    created() {
      var self = this
      this.findclub(JSON.parse(localStorage.getItem('session')).club)
      var temp
      http.get('TypeEvent/list')
          .then(function (response) {
            temp=response.data
            self.listeinterne = temp.filter(type => type.localiter === 1 || type.localiter === 2 ) 
            self.listeExterne = temp.filter(type => type.localiter === 0 || type.localiter === 2 ) 
            self.selects.types = self.listeinterne
          })
      http.get('Ville/list')
          .then(function (response) {
            self.villes.ville= response.data;
          })
      http.get('Salle/list')
      .then(function (response) {
        self.sallesList= response.data;
      })
      http.get('/MaterielFaculter/list')
      .then(function (response) {
        self.materielListe= response.data;
      })
    },
    data () {

    var validateqte = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('Please input the password again'));
            } 
            else if (value < 0) {
              callback(new Error("Quantité ne put pas etre négative"));
              }
            else if (value > this.qteDisponible) {
              callback(new Error("Quantité Demmander n'est pas Disponible"));
            } else {
              callback();
            }
          };

      return {
        listeExterne:[],
        listeinterne:[],
        sallesList:[],
        materielListe:[],
        lesDemandeSalle:[],
        lesplanification:[],
        lesDemandeMat:[],
        timeRange:'',
        dateResSalle:'',
        planifcatSalle:false,
        PlanifSalleForm:{
        dateResSalle:'',
        tempdeb:'',
        tempfin:'',
        },
        ruleForm: {
        dateResMat:'',
        qteDemm:'',
        tempdeb:'',
        tempfin:''
        },
        showqteDispible:false,
        qteDisponible:0,
        verifdemsalle:false,
        search:'',
        wizardModel: {},
        selects: {
          simple: '',
          types:null
        },
        villes: {
          simple: '',
          ville:[]
        },
        nomEvent:'',
        laplanif:'',
        dureevent:'',
        search:'',
        moreInfo:'',
        codepostal:'',
        adresse:'',
        site:'',
        pickerOptions: {
          disabledDate(date) {
          var dated = new Date();
          dated.setDate(dated.getDate()-1);
          date.setDate(date.getDate());
          return dated >= date
          },
        },
        pickerOptions2: {
        disabledDate: this.disabledDueDate
        },
        pickerOptions3:{
          selectableRange: '18:30:00 - 20:30:00',
          format:'HH:mm'
        },
        typeEvent:{

        },
        showsalleDisponibility:false,
        salleDisponibility:'',
        laSalleaPlanif:'',
        VilleSelectErr:false,
        siteErr:false,
        adresseErr:false,
        posteerr:false,
        showPlanifMat:false,
        leclub:'',
        radioexterne:'0',
        showaddtype:false,
        showaddville:false,
        typeEventForm:{
        typeEvenement:'',
        localiter:''
        },
        leMateriel:'',
        newVille:'',
        siteListe:[],
        destination:[],
        reqNomEven:false,
        reqType:false,
        reqdatedeb:false,   
        qtedispovalid:1,     
         rules: {
          qteDemm: [
            { required: true, message: 'La Quantité Demander est nécessaire', trigger: 'blur' },
            { validator: validateqte, trigger: 'blur' }
          
          ],
          dateResMat: [
            { required: true, message: 'Le Date est nécessaire', trigger: 'blur' }
          ],
          tempdeb: [
            { required: true, message: 'Le Date Début est nécessaire',trigger: 'blur' }
          ],
          tempfin: [
            { required: true, message: 'Le  Date Fin est nécessaire',trigger: 'blur' }
          ]
        },
        salleFormrules: {
          dateResSalle: [
            { required: true, message: 'Le Date est nécessaire', trigger: 'blur' }
          ],
          tempdeb: [
            { required: true, message: 'Le Date Début est nécessaire',trigger: 'blur' }
          ],
          tempfin: [
            { required: true, message: 'Le  Date Fin est nécessaire',trigger: 'blur' }
          ]
        },
        eventTypeRules: {
          typeEvenement: [
            { required: true, message: 'ce Champ est obligatoire', trigger: 'blur' }
          ],
          localiter: [
            { required: true, message: 'Choisir la localité', trigger: 'change' }
          ],
        }
      }
    },
    components: {
      FormWizard,
      TabContent,
      VueEditor,
      [DatePicker.name]: DatePicker
    },
    methods: {
      PlanifSalle(salleaplanif){
        this.laSalleaPlanif=salleaplanif
        this.planifcatSalle=true
      },
      getQteDisp() {
        if(this.leMateriel==null||this.ruleForm.dateResMat==null)
        return
        var self=this
        http.post('MaterielFaculter/QteDisp/'+this.ruleForm.dateResMat , this.leMateriel)
        .then(function (response) {
          self.qteDisponible = self.leMateriel.qteTotale - response.data
          self.showqteDispible=true
        })        
      },

      planifMatFaculter(mat){
        if(mat==null)
        return
        this.showPlanifMat=true
        this.leMateriel=mat
      },
      checktime(){
        if(this.timeRange==null)
        return
         const en = extendMoment(moment);
        var range = en.range(this.laplanif.tempdebRes, this.laplanif.tempFinRes);
      },
      verifiSalleDispo(){
        this.showsalleDisponibility=false
        if(this.laSalleaPlanif==null)
        return
        var row=this.laSalleaPlanif
        var planifs
        var planif
        var self=this
      if(this.PlanifSalleForm.dateResSalle==null)
      return 
      this.reqdatedeb=false
      http.get('/Salle/Planif/list/'+this.PlanifSalleForm.dateResSalle)
      .then(function (response) {
        console.log(response.data)
        planifs=response.data
        planif=planifs.find( planification => planification.demandeSalle.salle.id === row.id )
        if(planif!=null)
         {
            self.laplanif=planif
            self.showsalleDisponibility=true
            self.salleDisponibility = ' <div class="salleDisponibility" > Cette Salle Est Réservé  le '
            +planif.dateResSalle+' </br> de '+planif.tempdebRes+' a '+ planif.tempFinRes
         }
      })
      },
      disabledDueDate(date){
        return !(date >= this.dureevent[0] && date <= this.dureevent[1])
      },
      disabledDueDate(date){
        return !(date >= this.dureevent[0] && date <= this.dureevent[1])
      },
      onComplete(){
        var self = this
        var externe
        this.dureevent[0]=moment(this.dureevent[0]).format('YYYY-MM-DD')
        this.dureevent[1]=moment(this.dureevent[1]).format('YYYY-MM-DD')

        let obj = this.selects.types.find(o => o.typeEvenement === this.selects.simple)
        let laville =this.villes.ville.find(o => o.id === this.villes.simple)
        var demandeEvent = {
          nomEvenement: this.nomEvent,
          datedemande: new Date(),
          datePrevuEvent: this.dureevent[0],
          dateFinEvent: this.dureevent[1],
          externe: false,
          typeEvent: obj,
          leslieu:[],
          leClub: {},
          lesSalles:this.lesDemandeSalle,
          lesMaterielAffecter:this.lesDemandeMat,
          descriptionEvenement: this.moreInfo
        }
        demandeEvent.leClub=this.leclub
          if(this.radioexterne=="0")
          {
          demandeEvent.externe=false
          this.ajoutDemEvent(demandeEvent)
          return 
          }
          else
          demandeEvent.externe=true
          
         if(this.selects.simple!="voyage")
         {
            var lieu={
              site: this.site,
              adresse: this.adresse,
              codePostal:this.codepostal,
              ville: laville
            }
            this.siteListe.push(lieu);
            demandeEvent.leslieu=this.siteListe
            this.ajoutDemEvent(demandeEvent)
         }   
         else
         {
        demandeEvent.leslieu=this.destination
        this.ajoutDemEvent(demandeEvent)
         }
      },
      test(){
        this.reqdatedeb=false
      },
      validatestep1: function(){
        let valid=true
        if(this.nomEvent==''){
        this.reqNomEven=true
        valid=false
        }
        if(this.selects.simple==''){
        this.reqType=true
        valid=false
        }
        if(this.dureevent==''||this.dureevent==null){
        this.reqdatedeb=true
        valid=false
        }
        return valid
      },
      validatevoyage: function(){
        let valid=true
        if(this.selects.simple!='voyage')
        {
        if(this.site==""){
        this.siteErr=true
         valid=false
        }
        if(this.adresse==""){
        this.adresseErr=true
        valid=false
        }
        if(this.villes.simple=="")
        {
        this.VilleSelectErr=true
        valid=false
        }
        if(this.codepostal=="")
        {
          this.posteerr=true
          valid=false
        }
        }
        else
        {
          if(this.destination.length==0)
          {
            valid=false;
            swal({
            type: 'error',
            title: 'Erreur',
            text: 'Au moins ajouter un Site a visiter dans Le voyage',
          }) 
          }
          else
          valid=true
        }
        return valid
      },
      addEventType(){
        var self = this;
        var typeEvent = this.typeEventForm
        http.put("TypeEvent/create",typeEvent)
        .then(response => {
          self.selects.types.push(response.data) 
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
            text: 'il est possible que ce Type deja existe',
          })          
          console.log(e);
        })
        this.showaddtype=false
      },  
      ajoutDemEvent(demandeEvent)
      {
        console.log(demandeEvent)
        
        http.put("DemandeEvent/create",demandeEvent)
        .then(response => {
          swal({
            type: 'success',
            title: 'Succès',
            text: 'Demande Envoyer avec Succès',
          }).then(okay => {
            if (okay) {
            this.$router.push('/demandeEvenement/clubDemandesEvenemet')
            }
          })
        })
        .catch(e => {
          swal({
            type: 'error',
            title: 'Une erreur est Produit',
            text: 'il est possible que ce Type deja existe',
          })          
          console.log(e)
        })
      },
      addVille(){
          var self = this;
          var eventVille = {
              ville: this.newVille
          }
          http.put("/Ville/create",eventVille)
          .then(response => {
            self.villes.ville.push(response.data) 
            this.showaddville=false         
            swal({
              type: 'success',
              title: 'Succès',
              text: 'Ajouter avec succès',
            })
          })
          .catch(e => {
            this.showaddville=false
            swal({
              type: 'error',
              title: 'Une erreur est Produit',
              text: 'il est possible que cette Ville deja existe',
            })          
            console.log(e);
          })
      },
      adddestVoyage(){
        if(this.site==""||this.adresse==""||this.villes.simple=="")
        { 
          if(this.site=="")
          this.siteErr=true
          if(this.adresse=="")
          this.adresseErr=true
          if(this.villes.simple=="")
          this.VilleSelectErr=true
          return
        }
        let obj = null
        obj = this.villes.ville.find(o => o.id === this.villes.simple)
        if(obj==null)
        {
          this.VilleSelectErr=true
          return
        }
        
        var lieu={
          site: this.site,
          adresse: this.adresse,
          ville: obj
        }
        this.destination.push(lieu)
        this.adresse=''
        this.site=''
        this.adresseErr=false
        this.VilleSelectErr=false
        this.adresseErr=false

      },
      filterType()
      {         
          var list
        if(this.radioexterne==0)
        {
          this.selects.types=this.listeinterne
        }
        else
          this.selects.types=this.listeExterne
      },
      handleDelete (index, row) {
        let indexToDelete = this.destination.findIndex((tableRow) => tableRow.id === row.id)
        if (indexToDelete >= 0) {
          this.destination.splice(indexToDelete, 1)
        }
      },
      supprimerPanificationSalle (row,l) {
      let indexToDelete = l.findIndex((tableRow) => tableRow.id === row.id)
      if (indexToDelete >= 0) {
      l.splice(indexToDelete, 1)
      }
      },
      supprimerPanificationMat (row,l) {
        let indexToDelete = l.findIndex((tableRow) => tableRow.id === row.id)
        if (indexToDelete >= 0) {
          l.splice(indexToDelete, 1)
        }
      },
      addPlanification(){
      var lasalle=this.laSalleaPlanif
      var demandeSalle={
          salle : null,
          lesplanification:[],
        }
      var planification={
          dateResSalle:this.PlanifSalleForm.dateResSalle,
          tempdebRes:this.PlanifSalleForm.tempdeb,
          tempFinRes:this.PlanifSalleForm.tempfin,
          etatConfirmationSalle: false,
          typePlanification: "salle",
          qteDemander: null,
          qteAffecter: null
        }
        if(this.lesDemandeSalle.length==0)
        {
          demandeSalle.salle=lasalle
          demandeSalle.lesplanification.push(planification)
          this.lesDemandeSalle.push(demandeSalle)
          this.planifcatSalle=false
          this.$refs["PlanifSalleForm"].resetFields();

        }
        else
        {
        let lademande = this.lesDemandeSalle.find( demandeSalle => demandeSalle.salle.id === lasalle.id )
        if(lademande==undefined)
        {
          demandeSalle.salle=lasalle
          demandeSalle.lesplanification.push(planification)
          this.lesDemandeSalle.push(demandeSalle)
          this.planifcatSalle=false
          this.$refs["PlanifSalleForm"].resetFields();
        }
        else
        {
        let index = this.lesDemandeSalle.findIndex(demandeSalle => demandeSalle.salle.id === lasalle.id )
        let existance = lademande.lesplanification.findIndex(planif=> planif.dateResSalle === planification.dateResSalle 
                                                              && planif.tempdebRes === planification.tempdebRes 
                                                              && planif.tempFinRes === planification.tempFinRes )
        if(existance>=0)
        {
            swal({
            type: 'error',
            title: 'Deja Existe',
            text: 'Cette Planification Deja Existé Pour cette Salle',
            }) 
        }
        else
        {
        lademande.lesplanification.push(planification)
        this.lesDemandeSalle[index]=lademande
        this.planifcatSalle=false
        this.$refs["PlanifSalleForm"].resetFields();
        }
        }
        }     
      },
        addPlanifMat(materiel){          
                var demandeMateriel={
                matFaculter:null,
                qteDemander:this.ruleForm.qteDemm,
                lesplanificationMateriel: []
                  }   
                var planification={
                    dateResSalle:this.ruleForm.dateResMat,
                    tempdebRes:this.ruleForm.tempdeb,
                    tempFinRes:this.ruleForm.tempfin,
                    etatConfirmationSalle: null,
                    typePlanification: "materiel",
                    qteDemander: this.ruleForm.qteDemm,
                    qteAffecter: 0
                  }
                  if(this.lesDemandeMat.length==0)
                  {
                    demandeMateriel.matFaculter=materiel
                    demandeMateriel.lesplanificationMateriel.push(planification)
                    this.lesDemandeMat.push(demandeMateriel)
                    this.$refs.materielTable.toggleRowExpansion(this.leMateriel)
                    this.$refs["ruleForm"].resetFields();
                    swal({
                    type: 'success',
                    title: 'Planification Materiel Ajouter',
                    }) 
                    this.showPlanifMat=false
                  }
                  else
                  {
                  let lademandeMat = this.lesDemandeMat.find( demandeMat => demandeMat.matFaculter.id === materiel.id )
                  if(lademandeMat==undefined)
                  {
                    demandeMateriel.matFaculter=materiel
                    demandeMateriel.lesplanificationMateriel.push(planification)
                    this.lesDemandeMat.push(demandeMateriel)
                    this.$refs.materielTable.toggleRowExpansion(this.leMateriel)
                    swal({
                    type: 'success',
                    title: 'Planification Materiel Ajouter',
                    }) 
                    this.showPlanifMat=false
                    }
                  else
                  {
                  let index = this.lesDemandeMat.findIndex(demandeMat => demandeMat.matFaculter.id === materiel.id )
                  let existance = lademandeMat.lesplanificationMateriel.findIndex(planif=> planif.dateResSalle === planification.dateResSalle 
                                                                        && planif.tempdebRes === planification.tempdebRes 
                                                                        && planif.tempFinRes === planification.tempFinRes )
                  if(existance>=0)
                  {
                      swal({
                      type: 'error',
                      title: 'Deja Existe',
                      text: 'Cette Planification Deja Existé Pour cette Salle',
                      }) 
                      return;
                  }
                  else
                  {
                  lademandeMat.lesplanificationMateriel.push(planification)
                  this.lesDemandeMat[index]=lademandeMat
                  this.$refs.materielTable.toggleRowExpansion(this.leMateriel)
                  swal({
                  type: 'success',
                  title: 'Planification Materiel Ajouter',
                  }) 
                  this.showPlanifMat=false
                  }
                  }
                  //this.lesPlaniSalle(lasalle),
                  //this.resetForm('ruleForm')
                  }   
        },
        lesPlaniSalle(lasalle){
        let lademande
        if(this.lesDemandeSalle.length>0)
        {
        lademande = this.lesDemandeSalle.find( demandeSalle => demandeSalle.salle.id === lasalle.id )
        if(lademande==undefined)
        {
          return
        }
        else
        {
        return lademande.lesplanification       
        }
        }
      },
      lesMateriel(materiel){
        let lademandeMat
        if(this.lesDemandeMat.length>0)
        {
        lademandeMat = this.lesDemandeMat.find( demandeMat => demandeMat.matFaculter.id === materiel.id )
        if(lademandeMat==undefined)
        {
          return
        }
        else
        {
        return lademandeMat.lesplanificationMateriel       
        }
        }
      },
        submitajoutType(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.typeEventForm.localiter.toLowerCase()=="externe")
              this.typeEventForm.localiter=0
            else
            if(this.typeEventForm.localiter.toLowerCase()=="interne")
              this.typeEventForm.localiter=1
            else
              this.typeEventForm.localiter=2
              this.addEventType()

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitPlanifSalle(formName) {
      this.$refs[formName].validate((valid) => {
      if (valid) {
      if(formName=="PlanifSalleForm")
      this.addPlanification()
      }
      else {
      return false;
      }
      })
      },
      submitForm(formName) {
        this.qtedispovalid=this.leMateriel.qteTotale
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if(formName=="ruleForm")
            this.addPlanifMat(this.leMateriel)
          }
          else {
          console.log('error submit!!');
          return false;
        }
      })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      findclub(nom){
      var self=this
       http
          .get("club/byname/"+nom)
          .then(response => {
            self.leclub=response.data
          })
          .catch(e => {
          console.log(e);
          })
      }

    }
  }
</script>
<style >
  .vue-form-wizard .wizard-icon-circle.tab_shape {
    background-color: #9A9A9A !important;
    color: white;
  }
  .myinput{
  border-radius: 60px;
}
.addtypebtn {
    padding: 12px;
    background:#68B3C8;
    margin-left: 2px;
    position: relative;
    top: 2px;
    color: white;
    font-size: 16px;
    font-weight: bolder;
    border-radius: 50px;
}
.addPlanification{
    padding: 8px;
    margin-left: 2px;
    position: relative;
    font-size: 16px;
    font-weight: bolder;
    border-radius: 50px;
}
.myinput:focus{
  border:2px solid gray
}
span.addtype:hover{
  background:#3da2be
}
.control-label
{text-align: left!important}
.el-input--prefix .el-input__inner {
    padding-left: 30px;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
    min-width: 300px;
    max-width: 40%;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modheader {
    text-align: center;
    color: black;
    font-size: 30px;
    color: #519b72;
    font-family: 'Open Sans Condensed', sans-serif;
}
.wizard-tab-container{
min-height: 250px;
}
.adddestVoyage{
color: white;
    border-radius: 60px;
    background-color: #557fca;
    padding: 15px; 
}
.lieuliste {
    max-height: 148px;
    overflow-y: auto;
    overflow-x: hidden;
    margin-bottom: 2%;
}
small.text-danger.col-sm-12 {
    margin-top: 2%;
}
.el-input__suffix {
    position: absolute;
    height: 100%;
    right: 5px;
    top: 14px;
    text-align: center;
    color: #c0c4cc;
    transition: all .3s;
    pointer-events: none;
}
.el-select .el-input .el-select__caret.is-reverse {
    transform: rotateZ(0deg);
    position: relative;
    top: -19px;
}
.btn-planif{
  background: #6178ca;
  color: white; 
}
.btn-planif:hover{
  background:#7588cc ;
  color: white; 
}

.el-form-item__error {
    color: #EB5E28;
    font-size: 12px;
   min-width: 250px !important;
    line-height: 1;
    padding-top: 4px;
    position: absolute;
    top: 100%;
    left: 0;
}
.salleDisponibility {
    color: red;
    width: 80%;
    margin: 0 auto;
    margin-bottom: 18px;
    text-align: center;
}
</style>
