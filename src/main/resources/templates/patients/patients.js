$(document).ready(function () {
    $('#dtBasicExample').DataTable({
        "ordering": true // false to disable sorting (or any other option)
    });
    $('.dataTables_length').addClass('bs-select');
});