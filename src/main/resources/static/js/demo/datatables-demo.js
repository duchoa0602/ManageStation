// Call the dataTables jQuery plugin
$(document).ready(function () {
    $('#dataTable').DataTable();
    $('#content #btnDeleteProfileButtonRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmPermissionModal #deletePermissionRef').attr('href', href);
        $('#deleteConfirmPermissionModal').modal();
    })
});
