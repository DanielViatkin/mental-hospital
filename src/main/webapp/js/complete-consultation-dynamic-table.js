$(document).ready(function () {
    var i = 1;
    $("#add_row").click(function () {
        $('#diseaseSymptom' + i).html("<td><input name='symptoms-" + i + "' type='text' class='form-control input-md mx-auto' style='font-size: 1.5rem; width: 82%'/></td>" +
            "<td><input  name='disease-" + i + "' type='text' class='form-control input-md mx-auto'  style='font-size: 1.5rem; width: 82%'></td>");

        $('#tab_logic').append('<tr id="diseaseSymptom' + (i + 1) + '"></tr>');
        i++;
    });
    $("#delete_row").click(function () {
        if (i > 1) {
            $("#diseaseSymptom" + (i - 1)).html('');
            i--;
        }
    });
});

$(document).ready(function () {
    var j = 1;
    $("#add_row_1").click(function () {
        $('#drugRecipe' + j).html("<td><input name='drug-" + j + "' type='text' class='form-control input-md mx-auto' style='font-size: 1.5rem; width: 82%'/></td>" +
            "<td><input  name='dose-" + j + "' type='text' class='form-control input-md mx-auto'  style='font-size: 1.5rem; width: 82%'></td>" +
            "<td><input  name='description-" + j + "' type='text' class='form-control input-md mx-auto'  style='font-size: 1.5rem; width: 82%'></td>");

        $('#tab_logic_1').append('<tr id="drugRecipe' + (j + 1) + '"></tr>');
        j++;
    });
    $("#delete_row_1").click(function () {
        if (j > 1) {
            $("#drugRecipe" + (j - 1)).html('');
            j--;
        }
    });
});