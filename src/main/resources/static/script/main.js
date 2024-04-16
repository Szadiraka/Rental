
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