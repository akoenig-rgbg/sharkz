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