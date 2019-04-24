function getListUserMember(idKomunitas) {
    $.get(ajaxUserMember + "?idKomunitas=" + idKomunitas).done(function (data) {
        if (data.status === 'SUCCESS') {
            var listMember = data.data;
            var content = "<option disabled='true' selected='selected' value=''>-- None --</option>";
            for (var i = 0; i < listMember.length; i++) {
                content += "<option value='" + listMember[i].id + "'>" + listMember[i].fullname + "</option>";
            }
            $("#ketua").html(content).selectpicker('refresh');
            $("#wakilKetua").html(content).selectpicker('refresh');
            $("#sekretaris").html(content).selectpicker('refresh');
            $("#wakilSekretaris").html(content).selectpicker('refresh');
            $("#bendahara").html(content).selectpicker('refresh');
            $("#wakilBendahara").html(content).selectpicker('refresh');
        }
    });
}

function getDewanPengawas(listMember, totalDewan) {
    var content = "<option disabled='true' selected='selected' value=''>-- None --</option>";
    for (var i = 0; i < listMember.length; i++) {
        content += "<option value='" + listMember[i].id + "'>" + listMember[i].fullname + "</option>";
    }
    for (var j = 0; j < totalDewan; j++) {
        var idDewanPengawas = "dewanPengawas_" + j;
        $("#" + idDewanPengawas).html(content).selectpicker('refresh');
    }
}

function setValueDewanPengawas(listIdDewanPengawas, listMember, totalDewan, type) {
    for (var i = 0; i < listMember.length; i++) {
        for (var j = 0; j < listIdDewanPengawas.length; j++) {
            if (listMember[i].id === listIdDewanPengawas[j].split("_")[0]) {
                var total = 0;
                if (type === "add") {
                    total = totalDewan - 1;
                } else {
                    total = totalDewan;
                }
                for (var k = 0; k < total; k++) {
                    if (parseInt(listIdDewanPengawas[j].split("_")[1]) === k) {
                        var idDewanPengawas = "dewanPengawas_" + k;
                        $('#' + idDewanPengawas).selectpicker('val', listIdDewanPengawas[j].split("_")[0]);
                    }
                }
            }
        }
    }
}