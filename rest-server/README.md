# to-do API

This is a short description of the project. This was done by **José Enrique Ruvalcaba Segura**.

## Installation
In order to build the project, you will need to assemble the dependencies and then package the project into a jar:

```mvn assembly:assembly package```

Finally to execute the server, run:

```java -jar target/rest-server-1.0-SNAPSHOT-jar-with-dependencies.jar```

## Usage
The API can respond to the following endpoints:

### GET

#### *Get all to-dos*
```curl localhost:8000/api/v1/todos```
	
Responds with the following JSON:

```
[
  {
    "id": 1,
    "title": "Finish this API",
    "completed": false
  }
]
```
[IMAGE]

#### *Get one to-do*
```curl localhost:8000/api/v1/todos/1```
	
Responds with the following JSON:

```
{
  "id": 1,
  "title": "Finish this API",
  "completed": false
}
```

[IMAGE]

### POST

#### *Create to-do*
```curl localhost:8000/api/v1/todos```

Requires the following data:
```
{
	"title":"Advance the thesis"
}
```
	
Responds with the following JSON:

```
{
  "message": "Todo created"
}
```

[IMAGE]

### PUT

#### *Update to-do*
```curl localhost:8000/api/v1/todos/1```

Requires the following data:
```
{
    "completed": true
}
```
	
Responds with the following JSON:

```
{
  "message": "Todo updated"
}
```

[IMAGE]

### DELETE

#### *Delete one to-do*
```curl localhost:8000/api/v1/todos/1```

Responds with the following JSON:

```
{
	'message':'Todo deleted'
}
```

[IMAGE]


#### *Delete all to-dos*
```curl localhost:8000/api/v1/todos/all```

Responds with the following JSON:

```
{
	'message':'All Todos deleted'
}
```

[IMAGE]