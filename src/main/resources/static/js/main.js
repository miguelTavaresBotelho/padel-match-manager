$(document).ready(function () {
    // Array to store selected players
    const selectedPlayers = [];
    let data;

    // Function to fetch player suggestions based on input and update the suggestions list
    function fetchPlayerSuggestions(input) {
        if (input.length >= 3) {
            $.get('/autocomplete/players', {partialName: input}, function (data) {
                $('#playerSuggestions').empty();

                data.forEach(function (player) {
                    // Store the player object in the data attribute of the list item
                    const listItem = $('<li>' + players.username + '</li>');
                    listItem.data('players', players);
                    $('#playerSuggestions').append(listItem);
                });

                $('#playerSuggestions').css({
                    display: 'block',
                    position: 'absolute',
                    top: $('#playerSearch').offset().top + $('#playerSearch').outerHeight(),
                    left: $('#playerSearch').offset().left,
                });
            });
        } else {
            $('#playerSuggestions').css('display', 'none');
        }
    }

    // Function to select a player from the suggestions and add them to the list of selected players
    $(document).on('click', '#playerSuggestions li', function () {
        const selectedPlayer = $(this).data('players');

        // Check if the maximum number of players (3) is already reached
        if (selectedPlayers.length < 3) {
            // Add the selected player to the list of selected players
            selectedPlayer.push(selectedPlayer);
            // Append the selected player to the "Selected Players" list
            $('#selectedPlayers').append('<li>' + selectedPlayer.username + '</li>');
            // Clear the player search input
            $('#playerSearch').val('');
            // Clear the suggestions list
            $('#playerSuggestions').empty();

            // Update the hidden input field with selected player IDs
            updatePlayerIdsInput();
        }
    });

    // Function to handle player input changes in the playerSearch input field
    $('#playerSearch').on('input', function () {
        const input = $(this).val();
        // Fetch player suggestions based on the current input
        fetchPlayerSuggestions(input);
    });

    // Function to update the playerIds hidden input before form submission
    function updatePlayerIdsInput() {
        const selectedPlayerIds = selectedPlayers.map(player => player.id);
        $('#playerIds').val(selectedPlayerIds.join(','));
    }

    // Function to handle form submission
    $('form').submit(function (event) {
        // Check if the list of selected players is not empty
        if (selectedPlayers.length === 0) {
            event.preventDefault(); // Prevent form submission
            alert('Please select at least one player.');
        } else {
            // Update the playerIds hidden input before form submission
            updatePlayerIdsInput();

            // Attach selected player IDs to the form data before submission
            const form = $(this);
            const playerIdsInput = $('<input>')
                .attr('type', 'hidden')
                .attr('name', 'playerIds')
                .val(selectedPlayers.map(player => player.id).join(','));

            form.append(playerIdsInput);
        }
    });

    // Function to handle the "Join Challenge" button click
    $(document).on('click', '.join-button', function () {
        const challengeId = $(this).data('challenge-id');

        // Perform an AJAX request to join the challenge
        $.post(`/joinChallenge/${challengeId}`, function (response) {
            // Handle the response from the server (e.g., show a success message)
            alert('You have successfully joined the challenge!');
        }).fail(function (error) {
            // Handle any errors that occur during the AJAX request
            alert('An error occurred while trying to join the challenge. Please try again later.');
        });
    });




    // Function to format date and time
    function formatDateTime(dateTimeStr) {
        const dateTime = new Date(dateTimeStr);
        const day = dateTime.getDate();
        const month = dateTime.getMonth() + 1;
        const hours = dateTime.getHours();
        const minutes = dateTime.getMinutes();

        return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')} ${hours.toString().padStart(2, '0')}h${minutes.toString().padStart(2, '0')}`;
    }

});