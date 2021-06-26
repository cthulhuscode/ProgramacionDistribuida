package mx.ucol.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mx.ucol.models.Todo;
import mx.ucol.helpers.DBConnection;
import mx.ucol.helpers.JSON;

public class TodosHandler implements HttpHandler {
    Gson gson = null;
    Integer statusCode = null;
    String message = "";

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        gson = new GsonBuilder().setPrettyPrinting().create();

        switch (requestMethod) {
            case "GET":
                getHandler(exchange);
                break;
            case "POST":
                postHandler(exchange);
                break;
            case "PUT":
                putHandler(exchange);
                break;
            case "DELETE":
                deleteHandler(exchange);
                break;
            default:
                notSupportedHandler(exchange);
                break;
        }
    }

    private void getHandler(HttpExchange exchange) throws IOException {
        String json = "{}";
        /*
         * Supported endpoints for the GET handler: GET /todos Get all the ToDo entries
         * rom the DB instance
         *
         * GET /todos/:id Get the ToDo entry with the :id from the DB instance
         *
         * To get the connection to the DB use the following Connection connection =
         * DBConnection.getInstance();
         *
         * Then you can use the connection variable to create statements.
         */

        String uri = exchange.getRequestURI().toString();
        Integer id = uriToIdOrNull(uri);

        if(id != null){
            Todo todo = DBConnection.findOne("SELECT * FROM todos WHERE id = " + id);
            json = gson.toJson(todo);

        }else{
            List<Todo> todos = DBConnection.selectAll();
            json = gson.toJson(todos);
        }

        OutputStream output = exchange.getResponseBody();

        //Todo todo = new Todo(1, "Title example", false);

        byte[] response = json.getBytes();

        exchange.sendResponseHeaders(200, response.length);
        output.write(response);
        output.close();
    }

    private void postHandler(HttpExchange exchange) throws IOException {
        statusCode = 200;
        message = "{'message':'Todo created'}";
        /*
         * Supported endpoints for the POST handler: POST /todos Creates a new ToDo
         * entry
         *
         * You can use getBodyContent to read a JSON from the HTTP request: String
         * jsonBody = getBodyContent(exchange.getRequestBody())
         *
         * Try to convert the jsonBody variable to a Todo object to ensure the JSON is
         * well-formed.
         */

        String jsonBody = getBodyContent(exchange.getRequestBody());
        Todo todo = bodyToTodo(jsonBody);

        boolean success = DBConnection.insert(todo);

        if(!success) {
            statusCode = 500;
            message = "{'message':'All fields are required'}";
        }

        OutputStream output = exchange.getResponseBody();

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        String json = gson.toJson(jsonObject);
        byte[] response = json.getBytes();

        exchange.sendResponseHeaders(statusCode, response.length);
        output.write(response);
        output.close();
    }

    private void putHandler(HttpExchange exchange) throws IOException {
        statusCode = 200;
        message = "{'message':'Todo updated'}";

        /*
         * Supported endpoints for the PUT handler POST /todos/:id Update the details of
         * ToDo netry with :id if exists
         */

        Integer id = uriToId(exchange.getRequestURI().toString());

        String jsonBody = getBodyContent(exchange.getRequestBody());
        Todo todo = bodyToTodoUpdate(jsonBody);

        boolean success = DBConnection.update(todo, id);

        if(!success) {
            statusCode = 500;
            message = "{'message':'An error ocurred during the operation'}";
        }

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        OutputStream output = exchange.getResponseBody();

        String json = gson.toJson(jsonObject);
        byte[] response = json.getBytes();

        exchange.sendResponseHeaders(statusCode, response.length);
        output.write(response);
        output.close();
    }

    private void deleteHandler(HttpExchange exchange) throws IOException {
        statusCode = 200;
        message = "{'message':'Todo deleted'}";
        String all = "";
        boolean success = false;

        /*
         * Supported endpoints for the DELETE handler DELETE /todos/:id Remove ToDo
         * entry with :id if exists
         */

        String uri = exchange.getRequestURI().toString();
        Integer id = uriToIdOrNull(uri);
        all = uri.substring(uri.lastIndexOf("/")+1).trim();

        if(id != null){
            success = DBConnection.delete(id);
        }

        if(all.equals("all")){
            System.out.println(all);
            success = DBConnection.deleteAll();
            message = "{'message':'All Todos deleted'}";
        }

        if(!success) {
            statusCode = 500;
            message = "{'message':'An error ocurred during the operation'}";
        }

        OutputStream output = exchange.getResponseBody();
        byte[] response = message.getBytes();

        exchange.sendResponseHeaders(statusCode, response.length);
        output.write(response);
        output.close();
    }

    private void notSupportedHandler(HttpExchange exchange) throws IOException {
        OutputStream output = exchange.getResponseBody();
        byte[] response = "Not supported".getBytes();

        exchange.sendResponseHeaders(200, response.length);
        output.write(response);
        output.close();
    }

    private String getBodyContent(InputStream input) throws UnsupportedEncodingException, IOException {
        InputStreamReader streamReader = new InputStreamReader(input, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        int buffer;
        StringBuilder builder = new StringBuilder();

        while ((buffer = bufferedReader.read()) != -1) {
            builder.append((char) buffer);
        }

        bufferedReader.close();
        streamReader.close();

        return builder.toString();
    }

    private Todo bodyToTodo(String jsonBody){
        System.out.println(jsonBody);
        String title = "";
        String completed = "";

        if(jsonBody.contains("\",")){
            String[] body = jsonBody.split(",");
            title = body[0].substring(body[0].lastIndexOf(":\"") + 2, body[0].lastIndexOf("\""));
            completed = body[1].substring(body[1].indexOf(":") + 2, body[1].length() - 1);
        } else if(jsonBody.contains("title"))
            title = jsonBody.substring(jsonBody.indexOf(":") + 2, jsonBody.lastIndexOf("\""));

        title = title.trim();
        completed = completed.trim();

        return new Todo(1, title, completed == "true" ? true : false);
    }

    private Todo bodyToTodoUpdate(String jsonBody){
        String title = "";
        String completedStr = "";

       if(jsonBody.contains("title"))
           title = jsonBody.substring(jsonBody.indexOf(":") + 3, jsonBody.indexOf("\","));

       if(jsonBody.contains("completed"))
           completedStr = jsonBody.substring(jsonBody.lastIndexOf(":") + 2, jsonBody.lastIndexOf("}") - 1);

        title = title.trim();
        completedStr = completedStr.trim();

        return new Todo(1, title, Boolean.parseBoolean(completedStr));
    }

    private Integer uriToId(String uri){
        String id = uri.substring(uri.lastIndexOf("/")+1);
        return Integer.parseInt(id);
    }

    private Integer uriToIdOrNull(String uri) {
        try {
            String id = uri.substring(uri.lastIndexOf("/")+1);
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
