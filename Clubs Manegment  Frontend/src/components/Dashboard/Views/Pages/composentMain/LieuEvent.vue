<template>
 <div style="position: relative; top: 43px;margin-bottom: 100px;color: white;font-weight: 600;">
    <template v-for="lieu in lelieu">
    <div class="row" style="position: relative;left: 51px;">
        <div class="col-sm-7" style="background: teal;opacity: 0.6;border-radius: 11px;" >
        <div style="text-align:center">
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Lieu :</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
            <h5>{{lieu.site}}</h5>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Ville :</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
                <h5>{{lieu.ville.ville}}</h5>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
            <h5> Adresse :</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
                <h5>{{lieu.adresse}}</h5>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Code Postale</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
            <h5>{{lieu.codePostal}}</h5>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Contact Tel :</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
            <h5>(216) {{lieu.tel}}</h5>
            </div>
            </div> 
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Contact Fax : </h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
            <h5>(216) {{lieu.fax}}</h5>
            </div>
            </div>
            <div class="row">
            <div class="col-sm-4 col-xs-4" >
                <h5>Email:</h5>
            </div>
            <div class="col-sm-8 col-xs-8" >
            <h5>{{lieu.emailContact}}</h5>
            </div>
            </div>
        </div>
        </div>
        <div class="col-sm-5">
        <img style="border-radius: 10px;" src="static\img\fsegs.jpg">
        </div>
    </div>
    
</template>
</div>
</template>

<script>
/* eslint-disable */

export default {
props:["lelieu"],
created()
{
    console.log(this.lelieu)
}
}
</script>

<style>

</style>
