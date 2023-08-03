# Padel Match Manager



Padel Match Manager is a web application designed to streamline the process of organizing padel matches, connecting players, and facilitating court reservations at local clubs . It allows players to launch challenges for padel matches and notify other users who are interested in playing. 

## Table of Contents

- [Features](#features)
- [Demo](#demo)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Challenges Creation**: Users can easily create challenges for padel matches, specifying details such as start time, play duration, and skill level.
- **Player Matching**: The app helps match players for challenges, notifying interested users and forming complete teams.
- **Court Reservations**: Future enhancement will enable automated court reservations at partnered clubs for the scheduled matches.

## Demo

Will do a demo later. For now the portal speaks for it self.

## Installation

### Prerequisites

- Java 8 or higher
- PostgreSQL database

### Getting Started

1. Clone the repository: git clone https://[github.com/miguelTavaresBotelho/padel-match-manager](https://github.com/miguelTavaresBotelho/padel-match-manager)
2. Navigate to the project directory: cd padel-match-manager 
3. Configure database connection in src/main/resources/application.properties 
4. Build the project: ./gradlew build 
5. Run the application: ./gradlew bootRun

## Usage

1. Access the application through your web browser at http://localhost:8080
2. Register a new account or log in if you already have one.
3. Create challenges for padel matches, specifying the start time, play duration, and other details.
4. Browse and accept challenges from other players.
5. As a future enhancement, the application will automate court reservations at partnered clubs for confirmed matches.

## Contributing

Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

1. Fork the repository
2. Create a new branch: `git checkout -b feature/my-feature`
3. Make your changes
4. Commit your changes: `git commit -m 'Add some feature'`
5. Push to the branch: `git push origin feature/my-feature`
6. Open a pull request

Please make sure to follow the project's coding conventions and style guide.

## License

This project is licensed under the MIT License. You can find the full license text in the [License](LICENSE) file.

