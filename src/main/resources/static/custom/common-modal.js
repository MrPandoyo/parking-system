$("a.deleteData").confirm({
    content: "Apakah anda yakin ingin menghapus ?",
    buttons: {
        hapus: function(){
            location.href = this.$target.attr('href');
        },
        tidak:function () {

        }
    }
});

//kalo pengen gk ada blocking modal, maka pakai class no-prevent-dc di button-nya
$("form:not(.no-prevent-dc)").submit(function() {
    $.confirm({
        theme: 'supervan',
        icon: 'fa fa-spinner fa-spin',
        title: 'Loading!',
        content: 'Memproses Request...',
        buttons:{
            ya:{isHidden: true}
        }
    })
});