function isNumberKey(evt) {
    var e = evt || window.event;
    var charCode = e.which || e.keyCode;
    if (charCode > 31 && (charCode < 47 || charCode > 57))
        return false;
    if (e.shiftKey)
        return false;
    return true;
}