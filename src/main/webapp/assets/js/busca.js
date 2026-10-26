(function () {
    "use strict";

    function semAcento(s) {
        return s.normalize("NFD").replace(/[\u0300-\u036f]/g,
            "").toLowerCase();
    }

    function somenteDigitos(s) {
        return s.replace(/\D/g, "");
    }
})