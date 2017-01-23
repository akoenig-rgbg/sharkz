;( function ( document, window, index ) {
    
    document.querySelector("input").addEventListener('click', myFunction);
    var inputFields = document.getElementsByTagName('input');
    

    var i;
    for (i = 0; i < inputFields.length; i++) {
        if(inputFields[i].type.toLowerCase() == 'text') {
            inputFields[i].addEventListener("click", myFunction);
        }
    }
    
}( document, window, 0 ));

function myFunction() {
    alert("Hello World!");
}