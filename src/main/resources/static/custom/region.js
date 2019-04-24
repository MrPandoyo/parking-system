function getListProvinsi(idField, provinsiValue) {
    $.get(ajaxProvinsiUrl).done(function (data) {
        if (data.status === 'SUCCESS') {
            var content = "<option disabled='true' selected='selected' value=''>Pilih Provinsi</option>";
            for (var i = 0; i < data.data.length; i++) content += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            $("#" + idField).html(content).selectpicker('refresh');
            if (provinsiValue) setRegionSelectValue(idField, provinsiValue);
        }
    });
};

function getListKota(idField, idParent, kotaValue) {
    $.get(ajaxKotaUrl + "?idParent=" + idParent).done(function (data) {
        if(data.status === 'SUCCESS') {
            var content = "<option disabled='true' selected='selected' value=''>Pilih Kota</option>";
            for (var i = 0; i < data.data.length; i++) content += "<option value='" + data.data[i].id + "'>" + data.data[i].name + "</option>";
            $("#" + idField).html(content).selectpicker('refresh');
            if (kotaValue) setRegionSelectValue(idField, kotaValue);
        }
    });
};

function getListKecamatan(idField, idParent, kecamatanValue) {
    $.get(ajaxKecamatanUrl + "?idParent=" + idParent).done(function (data) {
        if(data.status === 'SUCCESS') {
            var content = "<option disabled='true' selected='selected' value=''>Pilih Kecamatan</option>";
            for (var i = 0; i < data.data.length; i++) content += "<option value='" + data.data[i].id + "'>" + data.data[i].name + "</option>";
            $("#" + idField).html(content).selectpicker('refresh');
            if (kecamatanValue !== null) setRegionSelectValue(idField, kecamatanValue);
        }
    });
};

function getListKelurahan(idField, idParent, kelurahanValue) {
    $.get(ajaxKelurahanUrl + "?idParent=" + idParent).done(function (data) {
        if(data.status === 'SUCCESS') {
            var content = "<option disabled='true' selected='selected' value=''>Pilih Kelurahan</option>";
            for (var i = 0; i < data.data.length; i++) content += "<option value='" + data.data[i].id + "'>" + data.data[i].name + "</option>";
            $("#" + idField).html(content).selectpicker('refresh');
            if (kelurahanValue !== null) setRegionSelectValue(idField, kelurahanValue);
        }
    });
};

function resetKecamatan(idField) {
    var content = "<option disabled='true' selected='selected' value=''>Pilih Kecamatan</option>";
    $("#" + idField).html(content).selectpicker('refresh');

}

function resetKelurahan(idField) {
    var content = "<option disabled='true' selected='selected' value=''>Pilih Kelurahan</option>";
    $("#" + idField).html(content).selectpicker('refresh');

}


function setRegionSelectValue(id, value) {
    $('#' + id).selectpicker('val', value);
};