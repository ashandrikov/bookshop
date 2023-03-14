$(document).ready(function () {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        userLogin = link.attr("userLogin");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user with login: " + userLogin + "?");
        $("#confirmModal").modal('show');
    })
})
