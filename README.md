# SIBS Code Test

This is a code test project asked by SIBS.

## Configuration

### Database

**In `application.properties`:**
* Replace the `spring.datasource.url` with the path to the DB (with database name and port).
* Replace the `spring.datasource.username` with the database username.
* Replace the `spring.datasource.password` with the database password.

### Email Service

For this particular project it was used gmail as the email smtp service.

**In `application.properties`:**
* At `spring.mail.username` replace <username> with the desired e-mail (eg. test@gmail.com)
* At `spring.mail.password` replace <password> with the e-mail account password.

## Usage

### Swagger

* Go to `http://localhost:8080/api/docs/swagger.html` to access the swagger doc.
* Username is `user`
* Password will be automatically generated and will be in the console after `Using default security password: `