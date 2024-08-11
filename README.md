# backend-jpa-case-importer

## Overview

The Case Importer application is designed to parse and import case records from a flat file into a JPA-managed database. It updates existing records or inserts new ones as needed and provides an import report with the number of cases imported per organization.

## Getting Started

### Prerequisites

- **Java 17** or later
- **Maven** for building and managing dependencies
- **H2 Database** for local development

### Project Setup

1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd <repository-directory>

Build the Project

Ensure you have Maven installed, then build the project using:

    mvn clean install
    Run the Application

To start the application, use:


    mvn spring-boot:run

The application will start on port 8080. If port 8080 is already in use, you may need to stop any processes using that port (see instructions below).

##Configuration
###Database Configuration

The application uses an H2 in-memory database by default. Configuration is available in src/main/resources/application.properties.

Default Database Schema

The schema and initial data (for the Organization table) are set up using src/main/resources/data.sql.

##Usage
###Access the Application

Open your browser and navigate to http://localhost:8080.

Import Data

To import data from a CSV file, use a curl command. Ensure your CSV file is placed in the project root directory. A Sample file file.csv is available.

Example CSV file: file.csv

    mrn,name
    12345,John Doe
    67890,Jane Smith

To upload the file, use the following curl command:


    curl -X POST "http://localhost:8080/cases/import" \
    -F "file=@file.csv" \
    -F "orgShortname=org1" \
    -F "delimiter=,"

Uploads the file named filw.csv.
http://localhost:8080/import?orgShortname=org1: URL to which the file is posted, with org1 as the organization short name.

##Running Tests
To run unit tests, use:

    mvn test

###Troubleshooting
Port 8080 Already in Use

To stop processes using port 8080 on macOS, use:

    sudo lsof -i :8080

Find the PID of the process and kill it:

    sudo kill -9 <PID>

###Database Issues

If you encounter issues with the database schema or initialization, ensure data.sql is correctly configured and the application has been restarted.


###License
This project is licensed under the MIT License. See the LICENSE file for details.
