# Changelog

All notable changes to this project will be documented in this file.

## [0.2.0] - 2025-06-09
### Added
- RESTful API for five entities (Category, Schedule, Commerce, Review, User).
- Secured endpoints with custom filter using JWT (JSON Web Token).
- Optimized data access with pagination and database indexation.
- Optimized massive data insert/update with batch processing and virtual threads (ie: CommerceStatsService).
- Scheduled background service.
- Containerisation of MySQL database + initialization script for local testing.
- Global exception handling.

### Changed
- Include docker volume in the "run container" command to persist data locally.

## [0.1.0] - 2025-06-05
### Added
- README.md file.
- CHANGELOG.md file.
- GitHub Workflow to check PR to main branch (mandatory to update changelog.md and increase pom.xml version).
- Initial Spring Boot project setup.
- Database containerisation with docker for local testing.
- Health API to test database connection.