function _getEmployees(cb){

    var select = document.getElementById('comboboxService');
    var service = select.options[select.selectedIndex].value;

    $.ajax({
        type: "get",
        url: "/loadService/"+service,
        success: function (response) {
            cb(response)
        }
    });

}