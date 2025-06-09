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
- Global exception handling.

## Working On Features
- Containerisation of API application.
- Terraform script to deploy on AWS.
- Unit test coverage above 80%.
- Improved data access with In-Memory cache.

## Local Set Up Tutorial
### Database set up
1. Install docker (v28.1.1 tested).
2. Go to ./docker/database folder and execute commands found at file COMMANDS.md.
### Application set up (containerisation coming soon)
1. Install IntelliJ IDEA 2025.1.1.1 (Community Edition).
2. Go to BackendApplication and right-click and Run Main().