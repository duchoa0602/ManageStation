// Call the dataTables jQuery plugin
$(document).ready(function () {
    $('#dataTable').DataTable();
    $('#content #btnDeleteProfileButtonRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#deleteConfirmPermissionModal #deletePermissionRef').attr('href', href);
        $('#deleteConfirmPermissionModal').modal();
    })

    //EDIT USER STATION MODAL
    $('#content #btnEditStationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (userStationEntity) {
            console.log("userStationEntity", userStationEntity)
            $('#editStationModal #idUserEdit').val(userStationEntity.id.userId);
            $('#editStationModal #idStationEdit').val(userStationEntity.id.stationId);
            $('#editStationModal #UsernameEdit').val(userStationEntity.user.username);
            $('#editStationModal #nameEdit').val(userStationEntity.station.name);
            $('#editStationModal #addressEdit').val(userStationEntity.station.address);
            $('#editStationModal #descriptionEdit').val(userStationEntity.station.description);
        });
        $('#editStationModal').modal();
    });

    $('#content #btnDeleteStationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#confirmDeleteStationModal #deleteStationRef').attr('href', href);
        $('#confirmDeleteStationModal').modal();
    })

    //EDIT DEVICE STATION MODAL
    $('#content #btnEditDeviceStationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (deviceEntity) {
            console.log("deviceEntity", deviceEntity)
            $('#editDeviceStationModal #idStationEdit').val(deviceEntity.station.id);
            $('#editDeviceStationModal #idDeviceEdit').val(deviceEntity.id);
            $('#editDeviceStationModal #nameEdit').val(deviceEntity.name);
            $('#editDeviceStationModal #unitEdit').val(deviceEntity.unit);
            $('#editDeviceStationModal #lowValueEdit').val(deviceEntity.lowValue);
            $('#editDeviceStationModal #highValueEdit').val(deviceEntity.highValue);
        });
        $('#editDeviceStationModal').modal();
    });

    $('#content #btnDeleteDeviceStationRow').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#confirmDeleteDeviceStationModal #deleteDeviceStationRef').attr('href', href);
        $('#confirmDeleteDeviceStationModal').modal();
    })

});
