$(document).ready(function() {
    $('#listProduct').DataTable();


});

function showBill(orderId){
    openPopup('/admin/booking/bill/' + orderId);
}
function pay(paymentId){

}