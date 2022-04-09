$(document).ready(function() {
    $("#logoutLink").on("click", function(e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropdownMenu();
});

    $(document).ready(function(){
        $(".link-delete").on("click", function(e){
            e.preventDefault();
            link = $(this);
            $("#yesButton").attr("href",link.attr("href"));
            $("#confirmText").text("Are you sure you want to delete this user ?")
            $("#confirmModal").modal();
        });
    });

    function clearSearch() {
        window.location = "[[@{/users}]]";
    };

    function customizeDropdownMenu() {
        $(".navbar .dropdown").hover(
            function(){
                $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
            },
            function(){
                $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
            }
        );
        $(".dropdown > a").click(function(){
            location.href = this.href;
        });
    }
