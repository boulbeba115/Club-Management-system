<template>
 <div>
    <div class="row lieuExterne">
      <div class="col-sm-6">
        <h4 style="text-align:center;color:white">Les Lieus a visité </h4>
        <div class="infoLieu">
          <div class="row">
            <div class="lieucont" v-for="loc in eventlieux">
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
      <div class="col-sm-6" style="text-align:center">
      <tnMap  :lesville="lesvilleEvent()"/>
      </div>
    </div>
</div>
</template>

<script>
/* eslint-disable */
  import tnMap from "src/components/Dashboard/Views/event/TunisieMap.vue"

export default {
components: {
    tnMap
},
props:["eventlieux"],
created()
{
  console.log(this.eventlieux)
},
methods:{
    lesvilleEvent(){
    let lieus = this.eventlieux
    let result = lieus.map(lieu => lieu.ville.ville)
    return result
    },
}

}
</script>

<style>
.infoLieu {
    padding: 5%;
    border: 1px solid lightgray;
    box-shadow: 0 6px 10px -4px rgba(0,0,0,.15);
    border-radius: 19px;
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
.lieuExterne
{
  margin-top: 7%;
  background: cadetblue;
  opacity: 0.9;
  width: 89%;
  margin: 0 auto;
  border-radius: 19px;
  padding: 10px;
  position: relative;
  top:35px;
  overflow: hidden;
}
</style>
