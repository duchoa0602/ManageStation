$(document).ready(function () {
    $('#listProduct').DataTable();
});

function addOrder(orderId, productId) {
    var dataOrder = {}
    dataOrder.orderId = orderId;
    dataOrder.productId = productId;
    axios.post("/api/order", dataOrder).then(function (res) {
        console.log(res);

    }, function (err) {
        swal("Lỗi!", "Đã có lỗi xảy ra!", "info");
    })
}

function detailOrder(orderId) {
    var dataOrder = {}
    dataOrder.orderId = orderId;
    var totalPart = 0;
    axios.put("/api/order", dataOrder).then(function (res) {
        console.log(res);
        if (res.data.data.length == 0) {
            $('#orderDetail tbody').html("Chưa có sản phẩm");
            $('#createBill').attr('disabled', 'disabled');
        } else {
            $('#orderDetail tbody').html("");
            $('#createBill').removeAttr('disabled');
            res.data.data.forEach(function (e, i) {
                $('#orderDetail tbody')
                    .append(`<tr id="task` + e.orderDetailId + `">
                                <td class="col-sm-8 col-md-6">
                                    <div class="media">
                                        <a class="thumbnail pull-left" href="#">
                                            <img class="media-object"
                                                 src="` + e.path + `"
                                                 style="width: 72px; height: 72px;">
                                        </a>
                                        <div class="media-body">
                                            <h4 class="media-heading"><a href="#">` + e.name + `</a></h4> 
                                        </div>
                                    </div>
                                </td>
                                <td class="col-sm-1 col-md-1" style="text-align: center">
                                    <input onchange="changePrice(this,` + e.foodId + `,` + e.price + `,` + e.orderDetailId + `)" type="number" class="form-control" value="` + e.quantity + `">
                                </td>
                                <td class="col-sm-1 col-md-1 text-center " ><strong class="fPrice">` + e.price + `</strong></td>
                                <td class="col-sm-1 col-md-1 text-center " ><strong class="totalPart" id="totalPart` + e.foodId + `" class="fPrice">` + (e.price * e.quantity) + `</strong></td>
                                <td class="col-sm-1 col-md-1">
                                    <button onclick="remove(` + e.orderDetailId + `)" type="button" class="btn btn-danger">
                                        <span class="glyphicon glyphicon-remove"></span> Remove
                                    </button>
                                </td>
                            </tr>`);
                totalPart += e.price * e.quantity;
            });
            document.querySelector('#totalBill').innerText = totalPart;
        }
    }, function (err) {
        swal("Lỗi!", "Đã có lỗi xảy ra!", "info");
    })
}

function changePrice(e, num, price, orderDetailId) {
    var totalPart = 0;
    var quantity = e.value;
    var total = quantity * price;
    document.querySelector('#totalPart' + num).innerText = total;
    document.querySelectorAll(".totalPart").forEach(function (e, i) {
        totalPart += +e.innerText;
    })
    document.querySelector('#totalBill').innerText = totalPart;

    var dataOrder ={}
    dataOrder.orderDetailId = orderDetailId;
    dataOrder.quantity = quantity;
    axios.post("/api/order/qty", dataOrder).then(function (res) {
        console.log(res);

    }, function (err) {
        swal("Lỗi!", "Đã có lỗi xảy ra!", "info");
    })

}

function remove(orderDetailId) {
    var totalPart = 0;
    axios.delete("/api/order/" + orderDetailId).then(function (res) {
        console.log(res);
        document.getElementById("task" + orderDetailId).remove();
        if ($('#orderDetail tbody tr').length == 0) {
            $('#createBill').attr('disabled', 'disabled');
            $('#orderDetail tbody').html("Chưa có sản phẩm");
        } else {
            $('#createBill').removeAttr('disabled');
            document.querySelectorAll(".totalPart").forEach(function (e, i) {
                totalPart += +e.innerText;
            })
            document.querySelector('#totalBill').innerText = totalPart;
        }
    }, function (err) {
        swal("Lỗi!", "Đã có lỗi xảy ra!", "info");
    })
}

function createBill(orderId) {
    var dataOrder = {}
    dataOrder.orderId = orderId;
    dataOrder.type = $('#shipping')[0].checked ? 1 : 2 ;
    var totalPart = 0;
    axios.post("/api/order/create", dataOrder).then(function (res) {
        console.log(res);
        if (res.data.meta.status_code == 200) {
            openPopup('/admin/booking/bill/' + orderId);
            location.reload();
        }
    }, function (err) {
        swal("Lỗi!", "Đã có lỗi xảy ra!", "info");
    })
}

