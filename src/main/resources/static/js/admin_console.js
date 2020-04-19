/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function searchdate() {
    var searchvalue = $('#searchvalue').val();
    var searchfrom = $('#searchstart').val();
    var filter = $('#viewfilter').val();
    var filtervalue = $('#filtervalue').val();
    var searchto = $('#Todate').val();

//    alert(searchvalue);
//    alert('search from==>' + searchfrom);
//    alert(filter);
//    alert(filtervalue);



    if (filter !== 'Select' && (filtervalue === '' || filtervalue === undefined)) {
        bootbox.alert("Please supply filter value.");
        return;
    }

    if (!$('#searchstart').val()) {
        if ($('#searchend').val()) {
            bootbox.alert("Please supply start date.");
            return;
        }
    }

    if (!$('#searchend').val()) {
        if ($('#searchstart').val()) {
            bootbox.alert("Please supply end date.");
            return;
        }

    }
    if ($('#Fromdate').val()) {
        searchfrom = $('#Fromdate').val();
    } else {
        searchfrom = "n";
    }

    if ($('#Todate').val()) {
        searchto = $('#Todate').val();
    } else {

        if ($('#searchstart').val()) {
            searchfrom = $('#searchstart').val()
        } else {
            searchfrom = "n";
        }
        var searchto = $('#searchend').val();
        if ($('#searchend').val()) {
            searchto = $('#searchend').val();
        } else {
            searchto = "n";
        }

        if (searchvalue === '' && searchfrom === 'n' && searchto === 'n' && filter === 'Select' && filtervalue === '') {
            bootbox.alert("Please supply at least one search parameter.");
            return;
        }

        var url = "/search";

        var postInput = {
//            searchmodule: searchmodule,
            searchvalue: searchvalue,
            searchfrom: searchfrom,
            searchto: searchto,
            filter: filter,
            filtervalue: filtervalue

        };

        post(url, postInput);

    }
}

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}