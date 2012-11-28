(function($){
    $.fn.validateForm = function() {
        $('#firstStep input').removeClass('error').removeClass('valid');
        var fields = $('#loginStep input[type=text], #loginStep input[type=password]');
        var error=false;
        fields.each(function(){
            var value = $(this).val();
            if(value.length < 4){
                $(this).addClass('error');
                error = true;
            } else {
                $(this).addClass('valid');
            }
        });
        return !error;
    }
})(jQuery);