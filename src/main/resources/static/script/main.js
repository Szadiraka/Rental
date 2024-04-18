
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
let openFormForRegistration=document.getElementById("registration");
let closeFormForAuthorization=document.getElementById("close");
let closeFormForRegistration=document.getElementById("close2")
openFormForAuthorization.onclick=function (){
    modalAuthorization.style.display="block";
}

openFormForRegistration.onclick=function (){
    modalRegistration.style.display="block";
}
closeFormForAuthorization.onclick=function(){
    modalAuthorization.style.display="none";
}
closeFormForRegistration.onclick=function(){
    modalRegistration.style.display="none";
}