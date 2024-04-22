

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





let openFormForAddElement= document.getElementById("addElement");

let closeFormForAddElement=document.getElementById("close");
let modalAdd=document.getElementById("modal");

if(openFormForAddElement) {
    openFormForAddElement.onclick = function () {
        modalAdd.style.display = "block";
    }
}
if(closeFormForAddElement) {
    closeFormForAddElement.onclick = function () {
        modalAdd.style.display = "none";
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


document.querySelectorAll('.container-items').forEach(function(container) {
    /* Получение элементов внутри каждой таблицы */


    let countrySelect = container.querySelector("[name='countryId']");
    let citySelect = container.querySelector("[name='cityId']");

    if (countrySelect && citySelect) {
        countrySelect.addEventListener("change", function () {
            let selectedCountryId = countrySelect.value;
            let cityOptions = citySelect.getElementsByTagName('option');

            for (let i = 0; i < cityOptions.length; i++) {
                let option = cityOptions[i];
                let cityCountryId = option.getAttribute("country-id");
                if (cityCountryId !== selectedCountryId)
                    option.style.display = "none";
                else
                    option.style.display = "";
            }
            citySelect.value = "";
        });
    }
});




