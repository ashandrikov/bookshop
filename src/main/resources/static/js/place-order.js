$(document).ready(function () {
    $(".place-order").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to create order from your shopping cart?");
        $("#confirmModalOrder").modal('show');
    })
})
