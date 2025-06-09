### Docker commands to set up database

#### Build Docker Image
```bash
docker build -t database-docker-image .
```
#### Run Container
```bash
docker run -d --name database-docker-container -v database-volume-data:/var/lib/mysql -p 3306:3306 database-docker-image
```