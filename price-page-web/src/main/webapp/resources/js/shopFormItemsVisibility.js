(function($){
    // add a new method to JQuery
    $.fn.hideFormItems = function() {
        this.each(function(){
            $(this).hide();
        });
    }
})(jQuery);

(function($){
    // add a new method to JQuery
    $.fn.showFormItems = function() {
        this.each(function(){
            $(this).show();
        });
    }
})(jQuery);
