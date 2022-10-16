



/***********
 SLIDE LEFT
 ************/
function slideLeft(){
    pos--;
    if(pos == -1){ pos = totalSlides-1; }
    $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos));

    //*> optional
    countSlides();
    pagination();
}


/************
 SLIDE RIGHT
 *************/
function slideRight(){
    pos++;
    if(pos == totalSlides){ pos = 0; }
    $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos));

    //*> optional
    countSlides();
    pagination();
}




/************************
 //*> OPTIONAL SETTINGS
 ************************/
function countSlides(){
    $('#counter').html(pos+1 + ' / ' + totalSlides);
}

function pagination(){
    $('#pagination-wrap ul li').removeClass('active');
    $('#pagination-wrap ul li:eq('+pos+')').addClass('active');
}
    