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

