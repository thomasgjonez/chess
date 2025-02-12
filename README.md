# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)


Full Sequence Diagram: To edit- https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco4Lo8zz6K8SwgX0ALnMCBQJig5T7oesLITyqLorE2IIQUrosmSFKxjOjboLS5aju++ifiaRLhgUlowGilBhMYMDnDABjcOAMCQHOMAAGbQDxqSGK+Yh4eaSaXmqiFsUGs7oI6zrJrBJZoWg2a5pg-4grK1AAQM5GjFBNbySR84TtB7a6YU2Q9jA-ZOIBFbjq2XzTgp86uYuZicKu3h+IEXgoOge4Hr4zDHukmSYHZF5FPpaYVNIACiu4pfUKXNC0D6qE+3QeeZ1lsjp5SFXO2mqb+CVOvKYX2BFqHhb6GEYthcqGC6YZugRVJGSgAZmXONFMm6Focox6JWOyAlCWJMBpJQhjlaFeCGH6hiqKOoa0WNUk1eCMBiUpiYEt1+EcCg3CZBQj6JKoZEjqMI1mhGhQMUx02sYJsC5U+rJ+nIMAgIk8g8kd-Vsbyl3XYY-HeMMwkQAYHEsv9O2jdK+0IeUZA+OGJ2dSpQJqc1YCaQgeZVZ2P5XN+V42V2ORgH2A49Acvkrp4AUbpCtq7tCMAAOKjqyUWnrF57MHpJTlBUQsZdl9ijgVQ2KT+JVVWVatoJVJPVTjMDkmACjKiLowPcrowC7ErVYYTGrnaSMBUMAyBaJkRGeYNdbmS94bjeUn0sToPEQKjhhIPxcm+3xMQu27SArhJEYy4drvuy0Fjog7XW7fhAkUiAqSmwg5tqLC-t7e9E3BzNP1scqRuiwtGSpMJKBILA4EQEQeq2lbKAAJIcLMADqLBD5lmRihDlCzAAQruChwAA0jPiM3LMg8AHLbSnNMdUh0IQPx5caDhBSlc31vQhTVP61JtOllMg+qOMlT9IPQ-SB-ACMvYADMAAWJ4J5MgGgrBML4OgECgAbJA4C0Cni71HNAvYLhMEwEaPTRKUk4oswcmzV+osP4VC-qOH+-8gGgKmOA-U-UVgwLgSABBjDkEkNGHvUY6DsFLj8tzdcgRsA+CgNgbg8BdSZGFqOFI0UzzMzZM-G8DQlYq2CDrIcqDRi4JKFfLWMAfbETnNMChXDgJ7GxDpJRR9gZSJQOXWEno9TlztliXOZ187Owzknb0OsjGeSrljGuQcpohzDhHGAUcY7GNCvHHxycnap2krVWSCSs5QBzghR2Xj3R2K9JkRx2iUBBMkiEmAABZYATY2IwDQCgZIN9DDJDbh3WayoIAtIcAJVACAOB5O-qPBa8jEGjAALyKmVLMWB8DRkoAmUqBA0xikY1eofGSZJRw1B0AAKxQOAXOxNUzlGcYU0c989apnWbLF+fRv6-3KAAkBMBdEdn2gQ1mTlOHDweTAJ5wCXkwE5v5IRARLBXQQJ0mAAApCA4Ny6BBmawyWiiZbXiqJSO8LRB6q1jugIc4jgDgqgHACAkKoDb0odIV51Vr4BPMqY54cDiWkvJSZO5VKoKWMuQWNOdUdlwrQI4gVPJXEoCYvbbJedMbO2Ngaelw1VkB0jLXMJM12LGznkBUYwyloxM8qyNabStrigPtjWxx0pWeJlXkkVQrRyPW1SgaYhKWVkugMsqlpTVD0QmgARTVd9ISG0YBQCQPYVIzBB4wAgLs-ZzBQAg3eDyRwkAmk-1mDyDozB5qJqWCmiJrrKCsqEjyNNg8lV7T5bJPGBMpVHOBOUO1YqcyUx5W8tFaZ2bFXeVLT53blwgsCgELwRKuxem7tgcRhB4iJDkRLAhNjEpy1SulTK2VjA9r-AYkA3A8CMj0AYSu7aDa2N3ROg9+gUBuOPVamQSSTl7qgJeo93rA7pmGBAGgjcEBatUJSi2sx2IrTQBoM11bcb4zdC0FKUBvBQEOdu-W5REAXu0Ie69agtLWPNcu0sNL8F9qIU5fhK4gA

To share- https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco4Lo8zz6K8SwgX0ALnMCBQJig5T7oesLITyqLorE2IIQUrosmSFKxjOjboLS5aju++ifiaRLhgUlowGilBhMYMDnDABjcOAMCQHOMAAGbQDxqSGK+Yh4eaSaXmqiFsUGs7oI6zrJrBJZoWg2a5pg-4grK1AAQM5GjFBNbySR84TtB7a6YU2Q9jA-ZOIBFbjq2XzTgp86uYuZicKu3h+IEXgoOge4Hr4zDHukmSYHZF5FPpaYVNIACiu4pfUKXNC0D6qE+3QeeZ1lsjp5SFXO2mqb+CVOvKYX2BFqHhb6GEYthcqGC6YZugRVJGSgAZmXONFMm6Focox6JWOyAlCWJMBpJQhjlaFeCGH6hiqKOoa0WNUk1eCMBiUpiYEt1+EcCg3CZBQj6JKoZEjqMI1mhGhQMUx02sYJsC5U+rJ+nIMAgIk8g8kd-Vsbyl3XYY-HeMMwkQAYHEsv9O2jdK+0IeUZA+OGJ2dSpQJqc1YCaQgeZVZ2P5XN+V42V2ORgH2A49Acvkrp4AUbpCtq7tCMAAOKjqyUWnrF57MHpJTlBUQsZdl9ijgVQ2KT+JVVWVatoJVJPVTjMDkmACjKiLowPcrowC7ErVYYTGrnaSMBUMAyBaJkRGeYNdbmS94bjeUn0sToPEQKjhhIPxcm+3xMQu27SArhJEYy4drvuy0Fjog7XW7fhAkUiAqSmwg5tqLC-t7e9E3BzNP1scqRuiwtGSpMJKBILA4EQEQeq2lbKAAJIcLMADqLBD5lmRihDlCzAAQruChwAA0jPiM3LMg8AHLbSnNMdUh0IQPx5caDhBSlc31vQhTVP61JtOllMg+qOMlT9IPQ-SB-ACMvYADMAAWJ4J5MgGgrBML4OgECgAbJA4C0Cni71HNAvYLhMEwEaPTRKUk4oswcmzV+osP4VC-qOH+-8gGgKmOA-U-UVgwLgSABBjDkEkNGHvUY6DsFLj8tzdcgRsA+CgNgbg8BdSZGFqOFI0UzzMzZM-G8DQlYq2CDrIcqDRi4JKFfLWMAfbETnNMChXDgJ7GxDpJRR9gZSJQOXWEno9TlztliXOZ187Owzknb0OsjGeSrljGuQcpohzDhHGAUcY7GNCvHHxycnap2krVWSCSs5QBzghR2Xj3R2K9JkRx2iUBBMkiEmAABZYATY2IwDQCgZIN9DDJDbh3WayoIAtIcAJVACAOB5O-qPBa8jEGjAALyKmVLMWB8DRkoAmUqBA0xikY1eofGSZJRw1B0AAKxQOAXOxNUzlGcYU0c989apnWbLF+fRv6-3KAAkBMBdEdn2gQ1mTlOHDweTAJ5wCXkwE5v5IRARLBXQQJ0mAAApCA4Ny6BBmawyWiiZbXiqJSO8LRB6q1jugIc4jgDgqgHACAkKoDb0odIV51Vr4BPMqY54cDiWkvJSZO5VKoKWMuQWNOdUdlwrQI4gVPJXEoCYvbbJedMbO2Ngaelw1VkB0jLXMJM12LGznkBUYwyloxM8qyNabStrigPtjWxx0pWeJlXkkVQrRyPW1SgaYhKWVkugMsqlpTVD0QmgARTVd9ISG0YBQCQPYVIzBB4wAgLs-ZzBQAg3eDyRwkAmk-1mDyDozB5qJqWCmiJrrKCsqEjyNNg8lV7T5bJPGBMpVHOBOUO1YqcyUx5W8tFaZ2bFXeVLT53blwgsCgELwRKuxem7tgcRhB4iJDkRLAhNjEpy1SulTK2VjA9r-AYkA3A8CMj0AYSu7aDa2N3ROg9+gUBuOPVamQSSTl7qgJeo93rA7pmGBAGgjcEBatUJSi2sx2IrTQBoM11bcb4zdC0FKUBvBQEOdu-W5REAXu0Ie69agtLWPNcu0sNL8F9qIU5fhK4gA

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
