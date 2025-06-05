### Docker commands to set up database

#### Build Docker Image
```bash
docker build -t database-docker-image .
```
#### Run Container
```bash
docker run -d --name database-docker-container -p 3306:3306 database-docker-image
```