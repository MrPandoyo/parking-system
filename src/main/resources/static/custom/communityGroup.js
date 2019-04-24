function fetchCommunityGroup(idKecamatan, idKecamatan2, currentComunity, type) {
    $.get(ajaxKomunitasUrl + "?idKecamatan=" + idKecamatan + "&idKecamatan2=" + idKecamatan2).done(function (data) {
        if (data.status === 'SUCCESS') {

            var content = "<option disabled='true' selected='selected' value=''>Pilih Komunitas</option>";
            var details = "";

            for (var i = 0; i < data.data.length; i++) {

                if(currentComunity != null && currentComunity != '' && type == 'disabled' && data.data[i].id == currentComunity){
                    content += "<option value='" + data.data[i].id + "' disabled='disabled'>" + data.data[i].communityName + "</option>";
                }else{
                    content += "<option value='" + data.data[i].id + "'>" + data.data[i].communityName + "</option>";
                }

                details +=
                    '    <div class="card card-dark groupDetails" id="detailKomunitas_' + data.data[i].id + '" style="display: none;">\n' +
                    '        <div class="card-header">\n' +
                    '            <h4 class="card-title">Informasi Komunitas</h4>\n' +
                    '        </div>\n' +
                    '        <div class="card-body">\n' +
                    '            <div class="form-group row">\n' +
                    '                <label class="col-md-3">Nama Komunitas</label>\n' +
                    '                <div class="col-md-9">\n' +
                    '                    <input type="text" class="form-control" value="' + data.data[i].communityName + '" readonly="true" />\n' +
                    '                </div>\n' +
                    '            </div>\n';

                if (data.data[i].ketua != null) {
                    details +=
                        '            <div class="form-group row">\n' +
                        '                <label class="col-md-3">Ketua Komunitas</label>\n' +
                        '                <div class="col-md-9">\n' +
                        '                    <input type="text" class="form-control" value="' + data.data[i].ketua.fullname + '" readonly="true" />\n' +
                        '                </div>\n' +
                        '            </div>\n';
                }

                details +=
                    '            <div class="form-group row">\n' +
                    '                <label class="col-md-3">Lokasi</label>\n' +
                    '                <div class="col-md-9">\n' +
                    '                    <textarea class="form-control" style="height: 107px;" readonly="true">' + data.data[i].location + '</textarea>\n' +
                    '                </div>\n' +
                    '            </div>\n' +
                    '        </div>\n' +
                    '    </div>\n';
            }
            $("#communityGroup").html(content).selectpicker('refresh');
            $("#detailKomunitas").html(details).show();
            if(currentComunity != null && currentComunity != '' && type != 'disabled'){
                setCommunityValue(currentComunity);
            }
        } else {
            var content = "<option disabled='true' selected='selected' value=''>Pilih Komunitas</option>";
            $("#communityGroup").html(content).selectpicker('refresh');
            $("#detailKomunitas").hide();
        }
    });
}

$("#communityGroup").on('change', function () {
    var id = $(this).val();
    $(".groupDetails").hide();
    $("#detailKomunitas_" + id).show();
});

function setCommunityValue(value) {
    $('#communityGroup').selectpicker('val', value);
};
