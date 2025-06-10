# Reviews API Showcase
## Summary
Hi, welcome!

This project was created in order to demonstrate my skill as a Java Back-End Engineer. Any suggestion or improvement is more than welcome.

You can reach me out at: https://www.linkedin.com/in/jc-sanchez-garcia/

## Features
- RESTful API for five entities (Category, Schedule, Commerce, Review, User).
- Secured endpoints with custom filter using JWT (JSON Web Token).
- Optimized data access with pagination and database indexation.
- Optimized massive data insert/update with batch processing and virtual threads (ie: CommerceStatsService).
- Scheduled background service.
- Containerisation of MySQL database + initialization script for local testing.
- Containerisation of Spring Boot application.
- Global exception handling.

## Working On Features
- Full automation of local deployment on docker (include DB at local_deploy_on_docker.sh script).
- Terraform script to deploy on AWS.
- Unit test coverage above 80%.
- Improved data access with In-Memory cache.

## Local Set Up Tutorial
### Requirements
- Docker (tested with v28.1.1).
### Database set up
1. Go to ./docker/database folder and execute commands found at file COMMANDS.md.
2. Execute init_db_script.sql on database to initialise tables schemas.
### Application set up
1. Execute bash script local_deploy_on_docker.sh.