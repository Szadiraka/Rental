let flagElement1 = document.getElementById("flag");
let flagElement2= document.getElementById("flag2");
let flagElement3=document.getElementById("flag3");

let modal1=document.getElementById("modal");
let modal2=document.getElementById("modal2");
let modal3=document.getElementById("modal3");

if(flagElement1 && modal1){
    let result= flagElement1.textContent;
    if(result==="1") {
        modal1.style.display="block";
    }
}

if(flagElement2 && modal2){
    let result= flagElement2.textContent;
    if(result==="1") {
        modal2.style.display="block";
    }
}

if(flagElement3 && modal3){
    let result= flagElement3.textContent;
    if(result==="1") {
        modal3.style.display="block";
    }
}