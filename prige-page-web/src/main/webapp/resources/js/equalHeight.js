(function($){
    // add a new method to JQuery
    $.fn.equalHeight = function() {
        tallest = 0;
        this.each(function(){
            thisHeight = $(this).height();
            if(thisHeight > tallest)
                tallest = thisHeight;
        });

        this.each(function(){
            $(this).height(tallest);
        });
    }
})(jQuery); // make sure the $ is pointing to JQuery