# url-shortener
Simple URL shorten-er REST service, think of it like Hello World of bitly.com API done in Java Spring-Boot project :)

## Setup and run the project

### Prerequisites

**These instructions are for `linux` only:**

1. Check your `JAVA` version you can do that by running `java --version`. You should have `JAVA 11`, project was build using `openjdk 11.0.6 2020-01-14`
2. Check your `MAVEN` instalation, you can do that by running `mvn -v`, project ueses Maven version `3.6.3`

### Setup instructions

1. Clone the project `git clone https://github.com/igor-susic/url-shortener.git`
2. `cd` into `/url-shortener` directory
3. Run `mvn clean install` Look for the output of this command, you should see directory where `maven` did build your `.jar` file
it will look something like this `/home/$USER/.m2/repository/com/demo/url-shortener/0.0.1-SNAPSHOT/url-shortener-0.0.1-SNAPSHOT.jar`
4. `cd` into `0.0.1-SNAPSHOT` directory and run `java -jar url-shortener-0.0.1-SNAPSHOT.jar` or run `java -jar /absolute/path/to/.jar`

### Restrictions and application arhitecture

This project is not for production use, it is only for demonstration purposes.

**  What is important  **

* Catching the `shortenUrl` is done here with use of `javax.servlet.Filer`, better solution would be to move evry API endpoint under `/v1/rest-of-the-path` and create `@RestController` that would catch `regex` expression for anything under `/` of depth `1`
* This implementation uses `H2` in-memory database. For this purposes it is enough, but you would want to use `NoSQL` database to save all those mappings of `longUrl: shortUrl`
* Currently generation of short `URL` is based on unique `int` in database, this could be removed from DB to save some space
* This implementation is creating short `URL`s using base62 technique [Url shortening Wiki](https://en.wikipedia.org/wiki/URL_shortening)
* Charachters `_` and `/` are removed to gain more friendly `URL`s

** This is my first project using Java and Spring Boot, so I'm positive that some things are not best practice from Spring/Java standpoint **

### What is left to do:

- [ ] Add unit tests for `UrlService`
- [ ] Refactor documentation for `IUrlService`


### API Endpoint (How to guide)

### POSSIBLE RESPONSES:
HTTP STATUS | Meaning
------------ | -------------
200 - OK | Everything worked as expected.
201 - Created | The request has succeeded and has led to the creation of a resource.
400 - Bad Request | The request was unacceptable, often due to missing a required parameter.
401 - Unauthorized | No valid Basic auth was provided.
404 - Not Found | The requested resource doesn't exist.
422 - Unprocessable Entity | server understands the content type of the request entity, and the syntax of the request entity is correct, but it was unable to process the contained instructions.
500 ... - Server Errors | Something went wrong with application. These are rare but you never know.


#### *[POST] /account*
Parameters:

```java
@Size(max = 255)
String accountId
``` 

Request example:
```bash
curl --header "Content-Type: application/json" --request POST --data '{"accountId":"xyz"}' http://localhost:8080/account
```

Response:

```javascript
 {
  "success": true,
  "description": "Your account is successfully opened",
  "password": "GNCaaavL" // pasword is encoded so if you don't write it down you will have to create new account
}
```

#### *[POST] /register*
Parameters:

```java
String url
int redirectType // optional parameter, possible values 301 | 302, and 302 is default value
``` 

Request example:
```bash
curl --header "Content-Type: application/json" --request POST --user name:password --data '{"url":"https://google.com"}' http://localhost:8080/register
```
```bash
curl --header "Content-Type: application/json" --request POST --user name:password --data '{"url":"https://google.com", "redirectType":301}' http://localhost:8080/register
```

Response:

```javascript
{
  "shortUrl": "http://localhost:8080/d"
}
```

#### *[GET] /statistic/{AccountId}*
Parameters:

```java
// No parameters in data, access endpoint only using GET and URL with basic autorization
``` 

Request example:
```bash
curl --request GET --user name:password http://localhost:8080/statistic/name
```

Response:

```javascript
{
  "https://www.kaggle.com/": 1,
  "https://www.pmfst.unist.hr/": 0,
  "https://news.ycombinator.com/": 2
}
```

#### *Testing short URL*

For example:

```bash
curl -L http://localhost:8080/c
```

`-L` argument is important as `cURL` won't follow redirect otherwise
