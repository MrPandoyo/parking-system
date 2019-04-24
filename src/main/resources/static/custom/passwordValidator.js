var checkPwConf = false;
var checkPw = false;

$("#confirmPassword").keyup(function () {
    var password = $("#password").val();
    var confirmPassword = $("#confirmPassword").val();
    if (confirmPassword !== password) {
        checkPwConf = false;
        $("#errorConfirmPassword").css('display', 'block');
    } else {
        $("#errorConfirmPassword").css('display', 'none');
        checkPwConf = true;
    }
    checkError(checkPw, checkPwConf);
});

function checkError(pw, pwConf) {
    if (pw && pwConf) {
        $('#btnSimpan').prop('disabled', false);
    } else {
        $('#btnSimpan').prop('disabled', true);
    }
}

$("#password").keyup(function () {
    $("#confirmPassword").val("");
    checkPwConf = false;
    validatePassword(this);
    checkError(checkPw, checkPwConf);
    if (this.value === "") {
        $('#pwValidate').hide();
    }
});

function validatePassword(field) {

    var validateLength = true;
    var validateUppercase = true;
    var validateLowercase = true;
    var validateNumber = true;

    var lowerCaseLetters = /[a-z]/g;
    var upperCaseLetters = /[A-Z]/g;
    var numbers = /[0-9]/g;

    var errorMessage = "";

    if (field.value.length >= 8) {
        validateLength = true;
    } else {
        validateLength = false;
        errorMessage = "Password minimal 8 karakter";
    }

    if (field.value.match(numbers)) {
        validateNumber = true;
    } else {
        validateNumber = false;
        errorMessage = "Password harus ada numeric [0-9]";
    }

    if (field.value.match(lowerCaseLetters)) {
        validateLowercase = true;
    } else {
        validateLowercase = false;
        errorMessage = "Password harus mengandung huruf kecil [a-z]";
    }

    if (field.value.match(upperCaseLetters)) {
        validateUppercase = true;
    } else {
        validateUppercase = false;
        errorMessage = "Password harus mengandung huruf besar [A-Z]";
    }

    checkValidationPassword(validateLength, validateUppercase, validateLowercase, validateNumber, errorMessage);
}

function checkValidationPassword(length, uppercase, lowercase, number, msg) {
    if (length && uppercase && lowercase && number) {
        checkPw = true;
        $('#pwValidate').hide();
        $('#pwValidate').text("");
    } else {
        checkPw = false;
        $('#pwValidate').show();
        $('#pwValidate').text(msg);
    }
}