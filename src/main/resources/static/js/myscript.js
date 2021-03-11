// vertikalno centrira brojeve u pojedinom zapisu knjige (klasa counter se vertikalno centrira)
window.onload = function() {
    $(".container-zapis-knjige").each(function (index, element) {
        var elementHeight = $(element).height();
        var counterObject = $('#'+this.id + ' .counter');
        $(counterObject).css("bottom", Math.round(elementHeight/2*0.7)+"px");
    })
};

// ovdje se samo određuje u kojem formatu će biti datum
$("#datetimepicker").datetimepicker({
    format : 'DD-MM-YYYY'
});


$(".btn-nova-knjiga").on("click", function () {
    $("#myModal").modal({
        backdrop: 'static',
        keyboard: false
    });

    // setiranje trenutnog datuma
    var now = new Date();
    $("#odabir-procitana").val(now.getFullYear() + '-' + now.getMonth() + "-" + now.getDate());

    $('#myModal').modal('show');
});





$("#novi-autor").on("click", function () {
    $('#myModal').modal('hide');

    $("#myModal-autor").modal({
        backdrop: 'static',
        keyboard: false
    });

    $('#myModal-autor').modal('show');
});





// gumb prihvati promjene (unos knjige) - modal za knjigu
$("#knjiga-prihvati-promjene").on("click", function () {
    var autor = $("#odabir-autora").val();
    var naslov = $("#odabir-naslova").val().substr(0, 100);
    var isbn = $("#odabir-isbn").val().substr(0,17);
    var knjizevnost = $("#odabir-knjizevnosti").val();
    var ocjena = $("#odabir-ocjene").val();
    var procitana = $("#odabir-procitana").val();
    var sadrzaj = $("#odabir-sadrzaj").val().substr(0,2000);


    var isSveDobroPopunjeno = provjeraIspravnostiPopunjenihPoljaKnjige(naslov, isbn, procitana);

    if (!isSveDobroPopunjeno) {
        return;
    }

    var podaciOKnjizi = {
        autor : autor,
        naslov : naslov,
        isbn : isbn,
        knjizevnost : knjizevnost,
        ocjena : ocjena,
        procitana : procitana,
        sadrzaj: sadrzaj
    };

    // slanje ajaxom na controller
    $.ajax({
        type: "POST",
        url: "unosKnjige",
        data: podaciOKnjizi
    });

    // brišu se vrijednosti koje su unešene u polja
    $("#odabir-naslova").val("");
    $("#odabir-isbn").val("");
    $("#odabir-sadrzaj").val("");

    // selectiraju se početne vrijednosti
    document.getElementById("odabir-autora").selectedIndex=0;
    document.getElementById("odabir-knjizevnosti").selectedIndex=0;
    document.getElementById("odabir-ocjene").selectedIndex=0;

    $('#myModal').modal('hide');

});



function provjeraIspravnostiPopunjenihPoljaKnjige(naslov, isbn, procitana) {
    var isSveUrednoPopunjeno = true;

    // provjera da li je unesen naslov
    if (naslov==="") {
        $("#alert-naslov").css("display", "block");
        isSveUrednoPopunjeno = false;
    } else {
        $("#alert-naslov").css("display", "none");
    }


    // provjera da li je isbn unesen u pravilnom obliku
    if (isbn.match(/^-?\d+$/)===null) {
        $("#alert-isbn").css("display", "block");
        isSveUrednoPopunjeno = false;
    } else {
        $("#alert-isbn").css("display", "none");
    }

    // provjera da li je datum unesen
    if (procitana==="") {
        $("#alert-procitana").css("display", "block");
        isSveUrednoPopunjeno = false;
    } else {
        $("#alert-procitana").css("display", "none");
    }

    return isSveUrednoPopunjeno;
}





$("#odustani-unos-knjige").on("click", function () {
    $("#odabir-naslova").val("");
    $("#odabir-isbn").val("");
    $("#odabir-sadrzaj").val("");

    document.getElementById("odabir-autora").selectedIndex=0;
    document.getElementById("odabir-knjizevnosti").selectedIndex=0;
    document.getElementById("odabir-ocjene").selectedIndex=0;

    $("#alert-isbn").css("display", "none");
    $("#alert-naslov").css("display", "none");
    $("#alert-procitana").css("display", "none");
});






// ovo je za modal autora - pojavljuje se kad se odabere unos novog autora
$("#autor-prihvati-promjene").on("click", function () {

    // iz polja s podacima za autora učitavaju se vrijednosti u varijable
    var ime = $("#odabir-ime").val().substr(0,50);
    var prezime = $("#odabir-prezime").val().substr(0,50);
    var biografija = $("#odabir-biografija").val().substr(0,2000);


    // brišu se stare vrijednosti liste gdje su navedeni svi autori koji se mogu odabrati
    // brišu se jer će na kraju funkcije biti dodane nove vrijednosti (ažurirat će se lista)
    $('#odabir-autora')
        .find('option')
        .remove();

    // ovdje stvaram objekt koji ću proslijediti u metodu controllera
    var podaciOAutoru = {
        ime : ime,
        prezime : prezime,
        biografija : biografija
    };


    // slanje ajaxom na controller (na endpoint definiran parametrom "url")
    $.ajax({
        type: "POST",
        url: "unosAutora",
        data: podaciOAutoru,

        // nakon što se metoda u controlleru uspješno izvrši onda se poziva ova sucess funkcija
        success: function(result) {
            // controller metoda mi vraća objekt result (u ovom slučaju mi vraća listu autora)
            $(result).each(function (index, autor) {
                var o = new Option(autor.prezime);
                $(o).html(autor.prezime + ', ' + autor.ime);
                $("#odabir-autora").append(o);
            })
        }
    });

    // brišu se vrijednosti koje su unešene u polja
    $("#odabir-ime").val("");
    $("#odabir-prezime").val("");
    $("#odabir-biografija").val("");


    // nakon šta se zatvori modal za unos novog autora, prikazuje se modal za unos podataka za knjigu
    $('#myModal').modal('show');

});





$("#autor-odustani-promjene").on("click", function () {
    // nakon šta se zatvori modal za unos novog autora, prikazuje se modal za unos podataka za knjigu
    $('#myModal').modal('show');

    // brišu se vrijednosti koje su unešene u polja
    $("#odabir-ime").val("");
    $("#odabir-prezime").val("");
    $("#odabir-biografija").val("");
});




$("#odabir-sadrzaj").keyup(function () {
    var brojZnakovaSadrzaj = $(this).val().length;

    document.getElementById("brojac-sadrzaja").innerHTML=brojZnakovaSadrzaj + " / 2000";

    if (brojZnakovaSadrzaj>2000) {
        $("#brojac-sadrzaja").css("color", "#ec8385");
    } else {
        $("#brojac-sadrzaja").css("color", "white");
    }
});


$(".btn-uredi").on("click", function () {
    $("#myModal-uredi-zapis").modal({
        backdrop: 'static',
        keyboard: false
    });

    var podaciOKnjizi = {
        idKnjige : $(this).attr("knjiga-id")
    };

    // slanje ajaxom na controller (na endpoint definiran parametrom "url")
    $.ajax({
        type: "POST",
        url: "getKnjigaById",
        data: podaciOKnjizi,

        // nakon što se metoda u controlleru uspješno izvrši onda se poziva ova sucess funkcija
        success: function(result) {
            // controller metoda mi vraća objekt result (u ovom slučaju mi vraća ProcitanaKnjiga)



            alert(result.knjiga.naslov);
        }
    });

});




// opacity search button
$("#btn-search").on('mouseover', function () {
    $(this).css("opacity", "60%");
});

$("#btn-search").on('mouseleave', function () {
    $(this).css("opacity", "100%");
});

$("#btn-search").on('mousedown', function () {
    $(this).css("opacity", "30%");
});

$("#btn-search").on('mouseup', function () {
    $(this).css("opacity", "60%");
});



