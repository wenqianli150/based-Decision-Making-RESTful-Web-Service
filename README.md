# TOPSIS-based Decision-Making RESTful Web Service

This project implements a RESTful API for a TOPSIS-based multi-criteria decision analysis (MCDA) system. The system employs two weight estimation techniques: **Shannon Entropy** and **CRITIC (Criteria Importance Through Intercriteria Correlation)**. The web service supports CRUD operations and processes decision matrices stored in a remote MySQL database hosted on a cloud platform.

## Features

- **CRUD Operations**:
  - Add, update, delete, and fetch records from the decision matrix stored in the cloud database.
- **Weight Estimation**:
  - Compute weights using the Shannon Entropy and CRITIC methods.
- **TOPSIS Computation**:
  - Rank alternatives using TOPSIS with entropy and CRITIC weights.
  - Compute correlation between TOPSIS results using both weight methods.
- **Comprehensive API Endpoints**:
  - Endpoints to manage decision matrices and compute results.
- **Remote Database Integration**:
  - The decision matrix is stored in a MySQL database hosted on a cloud service (e.g., GCP Cloud SQL).
- **JSON Input/Output**:
  - Input data is provided in JSON format, and all results are returned in JSON format.
- **Dynamic Web Form**:
  - A JSP form for uploading decision matrices and selecting weight estimation methods.

## Requirements

- **Environment**:
  - Java Development Kit (JDK) 8+
  - Apache Tomcat (or any compatible servlet container)
  - MySQL database (cloud-hosted, e.g., GCP Cloud SQL)
- **Libraries**:
  - JSON parsing library (e.g., Jackson or Gson)
  - JDBC for database connectivity

## How to Run

1. **Setup Database**:
   - Create a MySQL database on a cloud platform (e.g., GCP Cloud SQL).
   - Configure the database credentials in the application's configuration file.
   - Import the provided SQL schema to set up the necessary tables.

2. **Build and Deploy**:
   - Build the project using your preferred IDE or Maven.
   - Deploy the WAR file to an Apache Tomcat server.

3. **Access the API**:
   - Use Postman or any REST client to interact with the API.
   - Test endpoints with small, medium, and large decision matrices as described.
