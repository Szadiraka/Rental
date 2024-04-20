
document.addEventListener("DOMContentLoaded",function(){
    let element= document.getElementById("text");
    if(element){
        let messageText= element.innerText || element.textContent;
        if(messageText.trim()!==""){
            element.style.display="block";

            if(!messageText.includes("Ви зар")){
               element.style.color="red";
            }
            else
                element.style.color="green";
            setTimeout(function (){
                element.style.display="none";
            },2000);
        }
    }
})


let modalAuthorization=document.getElementById("modal");
let modalRegistration=document.getElementById("modal2");
let openFormForAuthorization= document.getElementById("authorization");
let openFormForAddElement= document.getElementById("addElement")
let openFormForRegistration=document.getElementById("registration");
let closeFormForAuthorization=document.getElementById("close");
let closeFormForRegistration=document.getElementById("close2");
if(openFormForAuthorization){
openFormForAuthorization.onclick=function (){
    modalAuthorization.style.display="block";
}
}
if(openFormForAddElement){
openFormForAddElement.onclick=function (){
    modalAuthorization.style.display="block";
}
}
if(openFormForRegistration){
openFormForRegistration.onclick=function (){
    modalRegistration.style.display="block";
}
}
if(closeFormForAuthorization){
closeFormForAuthorization.onclick=function(){
    modalAuthorization.style.display="none";
}
}

if(closeFormForRegistration){
closeFormForRegistration.onclick=function(){
    modalRegistration.style.display="none";
}
}

let countrySelect = document.getElementById("InputCountry");
let citySelect=document.getElementById("InputCity");

if(countrySelect && citySelect){
countrySelect.addEventListener("change",function (){
    let selectedCountryId= countrySelect.value;
    let cityOptions= citySelect.getElementsByTagName('option')

    for(let i=0; i<cityOptions.length;i++){
        let option= cityOptions[i];
        let cityCountryId= option.getAttribute("country-id");
        if(cityCountryId!==selectedCountryId)
            option.style.display = "none";
        else
            option.style.display="";
    }
    citySelect.value="";

});
}