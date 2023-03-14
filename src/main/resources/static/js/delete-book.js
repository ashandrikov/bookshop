$(document).ready(function () {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        showDeleteConfirmModal(link, "book");
    })
})
