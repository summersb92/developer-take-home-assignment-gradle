// TODO: This file should include all functionality relevant to the College Search page.  Should make use of ExecuteSearchServlet.java

$('#college-input').keyup(function(event) {
    if($('#college-input').val().length > 1) {
        $.get('execute-search?param=' + $.trim($('#college-input').val()), function(data) {
            if (data) {
                json = JSON.parse(data);

                if (json.length) {
                    var resultToDisplay = '';

                    $.each(json, function(key, value) {
                        resultToDisplay += json[key].name + ' (' + json[key].city + ', ' + json[key].state + ')<br />';
                        $("#college-results").html(resultToDisplay);
                    });
                } else {
                    $("#college-results").html('No search results. Please try again.');
                }
            }
        });
    } else {
        $("#college-results").html('');
    }
});