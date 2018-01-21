
function plusIndex(n) {
    index = index+n;
    showImage(index);
}


function showImage(n){
    var i;
    var x = document.getElementsByClassName("slides");
    if(n>x.length){index=1};
    if(n<1){index=x.length};
    for(i=0;i<x.length;i++){
        x[i].style.display = "none";
    }
    x[index-1].style.display = "block";
}

function autoSlide(){
    var i;
    var x = document.getElementsByClassName("slides");
    for(i=0;i<x.length;i++){
        x[i].style.display = "none";
    }
    if(index>x.length){index=1};
    x[index-1].style.display = "block";
    index++;
    setTimeout(autoSlide,2000);
}


