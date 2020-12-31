function openPopup(url, windowName, height, width) {
    newwindow = window.open(url, windowName, 'height=' + height + ',width=' + width);
    if (window.focus) {
        newwindow.focus()
    }
    return false;
}

function validate(id) {
    var texts = document.querySelectorAll('#' + id + ' input[type=text]');
    var selects = document.querySelectorAll('#' + id + ' select');
    var files = document.querySelectorAll('#' + id + ' input[type=file]');

    // for (let i = 0; i < texts.length; i++) {
    //     var text = texts[i];
    //     if (!text.value && text.id) {
    //         alert(text.placeholder + ' không được để trống');
    //         text.focus();
    //         return false;
    //     }
    // }
    // for (let i = 0; i < selects.length; i++) {
    //     var select = selects[i];
    //     if (!select.value) {
    //         alert(select.getAttribute('tag') + ' không được để trống00');
    //         select.focus();
    //         return false;
    //     }
    // }
    for (let i = 0; i < files.length; i++) {
        var file = files[i];
        if (!file.files.length && !file.getAttribute("id").eq("image")) {
            alert('File không được để trống');
            file.focus();
            return false;
        }
        for (let j = 0; j < file.files.length; j++) {
            var f = file.files[i];
            console.log(f);
            // if (['png', 'img'].indexOf(f.name.split('.').pop()) == -1) {
            //     alert('File không hỗ trợ định dạng');
            //     return false;
            // }
        }
    }
    return true;
}

function uploadFile(e, idBtn, idFileName) {
    let files = e.files;
    if (!files.length) return;
    var formData = new FormData();
    $('#' + idBtn)[0].removeAttribute("disabled");
    var type = e.getAttribute("required-type").split(",");
    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        if (type.indexOf(file.name.split('.').pop()) == -1) {
            alert('File không hỗ trợ định dạng');
            $('#' + idBtn)[0].setAttribute("disabled", "true");
            return false;
        }
        formData.append('file', file);
    }
    console.log(formData);
    axios.post("/api/upload", formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(function (res) {
        console.log(res);
        $('#' + idFileName).val(res.data.link.split('/')[2]);
    }, function (err) {
    })
}

function formatMoney(amount, decimalCount = 2, decimal = ".", thousands = ",") {
    try {
        decimalCount = Math.abs(decimalCount);
        decimalCount = isNaN(decimalCount) ? 2 : decimalCount;

        const negativeSign = amount < 0 ? "-" : "";

        let i = parseInt(amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)).toString();
        let j = (i.length > 3) ? i.length % 3 : 0;

        return negativeSign + (j ? i.substr(0, j) + thousands : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousands) + (decimalCount ? decimal + Math.abs(amount - i).toFixed(decimalCount).slice(2) : "");
    } catch (e) {
        console.log(e)
    }
};

var listPrice = document.querySelectorAll(".fPrice");
listPrice.forEach(function (e, i) {
    var price = formatMoney(e.innerText);
    e.innerText = "đ" + price;
})
