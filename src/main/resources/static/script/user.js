
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



let countrySelect = document.getElementById("country");
let citySelect=document.getElementById("city");

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