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
![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/get_all.PNG)

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

![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/get_one.PNG)

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

![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/post.PNG)

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

![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/update.PNG)

### DELETE

#### *Delete one to-do*
```curl localhost:8000/api/v1/todos/1```

Responds with the following JSON:

```
{
	'message':'Todo deleted'
}
```

![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/delete_one.png)


#### *Delete all to-dos*
```curl localhost:8000/api/v1/todos/all```

Responds with the following JSON:

```
{
	'message':'All Todos deleted'
}
```

![alt text](https://raw.githubusercontent.com/enrdevp/ProgramacionDistribuida/main/rest-server/img/delete_all.PNG)
