function initializePlayerSuggestions(playerSearchInputId, selectedPlayersContainerId) {
    // Array to store selected players
    const selectedPlayers = [];
    const selectedPlayerIds = new Set();

    // Function to fetch player suggestions based on input and update the suggestions list
    function fetchPlayerSuggestions(input) {
        if (input.length >= 3) {
            $.get('/autocomplete/players', { partialName: input }, function (responseData) {
                $('#playerSuggestions').empty();

                responseData.forEach(function (player) {
                    if (!selectedPlayerIds.has(player.id)) {
                        const listItem = $('<li>' + player.username + '</li>');
                        listItem.data('player', player);
                        $('#playerSuggestions').append(listItem);
                    }
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
        $.post(`/joinChallenge/${challengeId}`, function () {
            location.reload();
        }).fail(function () {
            alert('An error occurred while trying to join the challenge. Please try again later.');
        });
    });

    // Return the relevant functions and data
    return {
        fetchPlayerSuggestions,
        selectedPlayers,
        selectedPlayerIds,
        updatePlayerIdsInput
    };
}

$(document).ready(function () {
    // Initialize player suggestions and selection functionality for create challenge page
    const challengePlayerSuggestionsModule = initializePlayerSuggestions('playerSearch', 'selectedPlayers');

    // Attach player input event handler for create challenge page
    $('#playerSearch').on('input', function () {
        const input = $(this).val();
        challengePlayerSuggestionsModule.fetchPlayerSuggestions(input);
    });

    // Function to select a player from the suggestions and add them to the list of selected players
    $(document).on('click', '#playerSuggestions li', function () {
        const selectedPlayer = $(this).data('player');

        if (challengePlayerSuggestionsModule.selectedPlayers.length < 3) {
            challengePlayerSuggestionsModule.selectedPlayers.push(selectedPlayer);
            challengePlayerSuggestionsModule.selectedPlayerIds.add(selectedPlayer.id);
            $('#selectedPlayers').append('<li>' + selectedPlayer.username + '</li>');
            $('#playerSearch').val('');
            $('#playerSuggestions').css('display', 'none');

            challengePlayerSuggestionsModule.updatePlayerIdsInput();
        }
    });

});