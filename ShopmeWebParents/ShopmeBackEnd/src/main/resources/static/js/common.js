$(document).ready(function() {
    $("#logoutLink").on("click", function(e) {
        e.preventDefault();
        document.logoutForm.submit();
    });
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
