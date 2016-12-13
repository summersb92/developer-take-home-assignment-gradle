// TODO: This file should include all functionality relevant to the College Search page.  Should make use of ExecuteSearchServlet.java

var numberOfCharacters = 0;
var inputData = '';

$('#college-input').keypress(function(event) {
	numberOfCharacters = $('#college-input').val().length;
    if(numberOfCharacters > 1) {
        $.get('execute-search?param=' + $('#college-input').val(), function(data) {
            if (data) {
                json = JSON.parse(data);

                if (json.length) {
                    var resultToDisplay = '';

					$("#college-results").html('');
                    $.each(json, function(key, value) {
                        console.log('adding a college result');
                        $("#college-results").append(json[key].name + ' (' + json[key].city + ', ' + json[key].state + ')<br />');
						console.log('after adding a college result');
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