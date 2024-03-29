# KauppaSimu

This application is a simulation of a grocery store. The aim is to simulate the flow of customers, products, service times and the overall operation of a grocery store.

## Instructions to start the application
- Start the simulation by running the `main.java` file.
- ![Application main view](./src/main/resources/images/Main.png)
  - "Syötä aika" - field is used to set the duration of the simulation.
  - "Syötä viive" - field is used to set the delay between the simulation steps.
  - "Saapumisväliaika" - field is used to set the delay between the arrival of new customers.
  - "Palveluaika" - field is used to set the duration of the service time.
  - "Poikkeama" - field is used to set the deviation of the service time.
- Select the "Kassojen määrä" - slider to set the number of cash registers. By default, the number of cash registers is set to 1.
- "Käynnistä" - button is used to start the simulation.
## During the simulation
- ![Application main view](./src/main/resources/images/DuringSimulation.png)
  - "Nopeuta" - button is used to speed up the simulation.
  - "Hidasta" - button is used to slow down the simulation.
## After the simulation
- ![Application main view](./src/main/resources/images/AfterSimulation.png)
  - ### Top Console
    - Top console displays live data of the simulation.
  - ### Bottom Console
    - Bottom console displays the final statistics of the simulation.
  - ### Statistics
    - Statistics of the simulation are able to be viewed in various charts by navigating to the "Avaa statistiikka" - button.
## Database connection
-![Application main view](./src/main/resources/images/DatabaseError.png)
  - As the Graphical User Interface opens and the application starts at the top right corner, the status of the database connection is displayed. The bottom console also displays a message if the database connection has not been established.
  - In the above image, the status of the database connection has not been established. The status of the database connection is displayed with red color and an information message.
-![Application main view](./src/main/resources/images/DatabaseSuccess.png)
  - In the above image, the status of the database connection has been established. The status of the database connection is displayed with green color and an information message.