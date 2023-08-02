$(document).ready(function () {
    // Array to store selected players
    const selectedPlayers = [];
    let data = [];

    // Function to fetch player suggestions based on input and update the suggestions list
    function fetchPlayerSuggestions(input) {
        if (input.length >= 3) {
            $.get('/autocomplete/players', { partialName: input }, function (responseData) {
                $('#playerSuggestions').empty();

                responseData.forEach(function (player) {
                    const listItem = $('<li>' + player.username + '</li>');
                    listItem.data('player', player); // Store the whole player object
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
        const selectedPlayer = $(this).data('player');

        if (selectedPlayers.length < 3) {
            selectedPlayers.push(selectedPlayer);
            $('#selectedPlayers').append('<li>' + selectedPlayer.username + '</li>');
            $('#playerSearch').val('');
            $('#playerSuggestions').css('display', 'none'); // Hide the suggestions list

            updatePlayerIdsInput();
        }
    });

    // Function to handle player input changes in the playerSearch input field
    $('#playerSearch').on('input', function () {
        const input = $(this).val();
        fetchPlayerSuggestions(input);
    });

    // Function to update the playerIds hidden input before form submission
    function updatePlayerIdsInput() {
        const selectedPlayerIds = selectedPlayers.map(player => player.id);
        $('#playerIds').val(selectedPlayerIds.join(','));
    }

    // Function to handle form submission of creating a challenge
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

            // Get the selected playTime value from the dropdown
            const selectedPlayTime = $('#playTime').val();
            const selectedPlayTimeInput = form.find('#selectedPlayTime');
            selectedPlayTimeInput.val(selectedPlayTime);
        }
    });

    // Function to handle the "Join Challenge" button click
    $(document).on('click', '.join-button', function () {
        const challengeId = $(this).data('challenge-id');

        // Perform an AJAX request to join the challenge
        $.post(`/joinChallenge/${challengeId}`, function (response) {
            location.reload();
        }).fail(function (error) {
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