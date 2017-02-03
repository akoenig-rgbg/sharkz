/* jQuery function to display a label above an input field, if it is focused
 * or if it contains text*/

$(function(){
    $("input.inputText").focus(function() {
        $(this).next("label").css("visibility", "visible");
    });
    
    $("input.inputText").blur(function() {
        if (this.value.length == 0) {
            $(this).next("label").css("visibility", "hidden");
        }
    });
});