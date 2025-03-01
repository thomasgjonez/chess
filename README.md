# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)


Full Sequence Diagram: To edit- https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco4Lo8zz6K8SwgX0ALnMCBQJig5T7oesLITyqLorE2IIQUrosmSFIGgAkmgABmEC0uWo7vvon4mkS4YFJaMBopQYTGDA5GwDcMA8uRoYMW6nYIeUNwkfxCEamGbrlJ63pBgGQYhnh5qRhyMDRhpSnaI6zrJrBJZoWg2a5pg-4grK1AAQMVGjFBNZBrOzYTtB7YWYU2Q9jA-ZOIBFbjq2XzTk584BYuZicKu3h+IEXgoOge4Hr4zDHukmSYJ5F5FFZaYVNIACiu75fU+XNC0D6qE+3TBY26BuWy5nlDVc5mQZv7ZU68qJfYyWoUlvoYRi2FyoYLrSfhMDkmAsb+s16D0UyQlqeUrFWOynHQDAc1oAJi3Skml5qohW3afIumJodnXHSAqQoCADYzSFil1rVaCzNOIaSWNgn4RwKDcJkM3PTOr0LWaEaFMxq3sToG2wBVT6sn6cgwCAiTyDyJ0vXOW28n9AOGNOnHeMMMB+hABgwAjiQaCpEaWVd5RkD44bnaN+lAoZ-VgCZCB5m1wlXmmPTfkLnaZWAfYDiLpjLlF66BJCtq7tCMAAOKjqyqWnhl57MAz15q8VZX2KO1WOa99Uc6mTUWy15lsiJk0Ugoyoa6Mqiwqbowq7Eg1YWzUk-aSzvTQp21g+GFrqdD61cVjIMtXTgtHbbcY6V9l3gqjt33Y9r3AyF72ncAw1HUHe0h6RFI3a7CDu2osKR0tkMx+ia0cfHwDKpNmswMkGSpGTt1ILA4EQEQeq2t7KBERwswAOosERJWZGKMA3LMABCu4KHAADSa+k5vvejAAcqOu3gynjN7tCECkQ3Gg4QUjWnygvs82opnmQdP5XFMGeqhxiVH6DPIi0gQEAEZewAGYAAsTwTyZANBWCYXwdAIFAA2VBwF0FPBnhfUY6C9guDITARooscoHQllLXygDNYgIqGA0cEDoFwMQVMZB+pbL3D6BgrB91cF2XwQw8+o4SEUKXJFTw0UNzYB8FAbA3B4C6kyOrUcKQ0pnhyPrS615agNBNmbYIdt0BDkIaOKhJRX5tXKIXV60wWHiLsnsbEDsGbZzkpkBusJvEoAbv7LEgdvqV3dKHIGEcr5R2WixduMM4YJxCtEpaniuofQziNR2I1ZK5wemYtADi5zF3TvIMuV0K7g1kmogJo4vajiISgZu+1W7lAALLACbFtGAaAUDJHfv3Qew9DDkWVBAAeDhOKoAQBwcJ4D57920cIlAABeRUypZiYOwcstZSoEDTEseKZOB0OrZxnjUHQAArO6WAvrW2BNUr0PjRy835pzdyHUAJiNnpA8oMCEEwGsR2E5tDvLS2+Wwv5HDAUwAiiuWRCsAiWH+ggcZMAABSEBMYN0CFs+6utdHZJyuUaolI7wtBnubbG5jejKOAMiqAcAICoqgLMcB0ggXtTfkU9ATjnhYIZUyll9k+jsqgm41q7yiW30uVitAvjZU8kCSgViAdM4yHGiHKakSCnNNUq0uJbE46bW2ik-aaTjoZLOuqp2N07r5OpYU7aJTgzaHKc6Akmq5kUl8eyvVEN2QrXica2AM8jDaC2soJYPJHCQHfhAs1qkLVklHPGLJyac72qIqRIV0AFBUF+LobgsI6WCuZdANlqaI0hM9cHcJir5V1NfCgaYpbKC5tZX6xNqgmLqQAIrBs7ptP0hgoBIHsKkZgYaIBXJuZGtG7wY1kwgPG6QsweQdGYM2+d0bJlxrbYy8tsAeRxpnt2m+2dmaszuX+OxMAG3KpzHzSVqYb4AU5eLPWdCZZywRTFAIXh6Vdi9GPbAyjCDxESFonWEtpXXgKkVEqZVjBW1ve82S3A8CMj0AYJuL6CwZpAJhqA2H9AoCCXh9VdMMMgYNKR3D-ro7lHxhSQmPceJaDOscjNV63Q1o1XWmjeAG70ZQHh45BqWPqO7ggAZnHgDnpOU7XjLJ+PUdRsRmaonxNeqY0Yf6rGto9xmuGrjumlM5PICzPjN6zjoeA1h7QOHyPf2fR4g2wtOU0K-WC3y0iVxAA

To share- https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco4Lo8zz6K8SwgX0ALnMCBQJig5T7oesLITyqLorE2IIQUrosmSFIGgAkmgABmEC0uWo7vvon4mkS4YFJaMBopQYTGDA5GwDcMA8uRoYMW6nYIeUNwkfxCEamGbrlJ63pBgGQYhnh5qRhyMDRhpSnaI6zrJrBJZoWg2a5pg-4grK1AAQMVGjFBNZBrOzYTtB7YWYU2Q9jA-ZOIBFbjq2XzTk584BYuZicKu3h+IEXgoOge4Hr4zDHukmSYJ5F5FFZaYVNIACiu75fU+XNC0D6qE+3TBY26BuWy5nlDVc5mQZv7ZU68qJfYyWoUlvoYRi2FyoYLrSfhMDkmAsb+s16D0UyQlqeUrFWOynHQDAc1oAJi3Skml5qohW3afIumJodnXHSAqQoCADYzSFil1rVaCzNOIaSWNgn4RwKDcJkM3PTOr0LWaEaFMxq3sToG2wBVT6sn6cgwCAiTyDyJ0vXOW28n9AOGNOnHeMMMB+hABgwAjiQaCpEaWVd5RkD44bnaN+lAoZ-VgCZCB5m1wlXmmPTfkLnaZWAfYDiLpjLlF66BJCtq7tCMAAOKjqyqWnhl57MAz15q8VZX2KO1WOa99Uc6mTUWy15lsiJk0Ugoyoa6Mqiwqbowq7Eg1YWzUk-aSzvTQp21g+GFrqdD61cVjIMtXTgtHbbcY6V9l3gqjt33Y9r3AyF72ncAw1HUHe0h6RFI3a7CDu2osKR0tkMx+ia0cfHwDKpNmswMkGSpGTt1ILA4EQEQeq2t7KBERwswAOosERJWZGKMA3LMABCu4KHAADSa+k5vvejAAcqOu3gynjN7tCECkQ3Gg4QUjWnygvs82opnmQdP5XFMGeqhxiVH6DPIi0gQEAEZewAGYAAsTwTyZANBWCYXwdAIFAA2VBwF0FPBnhfUY6C9guDITARooscoHQllLXygDNYgIqGA0cEDoFwMQVMZB+pbL3D6BgrB91cF2XwQw8+o4SEUKXJFTw0UNzYB8FAbA3B4C6kyOrUcKQ0pnhyPrS615agNBNmbYIdt0BDkIaOKhJRX5tXKIXV60wWHiLsnsbEDsGbZzkpkBusJvEoAbv7LEgdvqV3dKHIGEcr5R2WixduMM4YJxCtEpaniuofQziNR2I1ZK5wemYtADi5zF3TvIMuV0K7g1kmogJo4vajiISgZu+1W7lAALLACbFtGAaAUDJHfv3Qew9DDkWVBAAeDhOKoAQBwcJ4D57920cIlAABeRUypZiYOwcstZSoEDTEseKZOB0OrZxnjUHQAArO6WAvrW2BNUr0PjRy835pzdyHUAJiNnpA8oMCEEwGsR2E5tDvLS2+Wwv5HDAUwAiiuWRCsAiWH+ggcZMAABSEBMYN0CFs+6utdHZJyuUaolI7wtBnubbG5jejKOAMiqAcAICoqgLMcB0ggXtTfkU9ATjnhYIZUyll9k+jsqgm41q7yiW30uVitAvjZU8kCSgViAdM4yHGiHKakSCnNNUq0uJbE46bW2ik-aaTjoZLOuqp2N07r5OpYU7aJTgzaHKc6Akmq5kUl8eyvVEN2QrXica2AM8jDaC2soJYPJHCQHfhAs1qkLVklHPGLJyac72qIqRIV0AFBUF+LobgsI6WCuZdANlqaI0hM9cHcJir5V1NfCgaYpbKC5tZX6xNqgmLqQAIrBs7ptP0hgoBIHsKkZgYaIBXJuZGtG7wY1kwgPG6QsweQdGYM2+d0bJlxrbYy8tsAeRxpnt2m+2dmaszuX+OxMAG3KpzHzSVqYb4AU5eLPWdCZZywRTFAIXh6Vdi9GPbAyjCDxESFonWEtpXXgKkVEqZVjBW1ve82S3A8CMj0AYJuL6CwZpAJhqA2H9AoCCXh9VdMMMgYNKR3D-ro7lHxhSQmPceJaDOscjNV63Q1o1XWmjeAG70ZQHh45BqWPqO7ggAZnHgDnpOU7XjLJ+PUdRsRmaonxNeqY0Yf6rGto9xmuGrjumlM5PICzPjN6zjoeA1h7QOHyPf2fR4g2wtOU0K-WC3y0iVxAA

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
