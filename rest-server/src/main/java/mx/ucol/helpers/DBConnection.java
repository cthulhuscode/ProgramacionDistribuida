package mx.ucol.helpers;

import mx.ucol.models.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static Connection connection = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    private DBConnection() {
        String url = "jdbc:sqlite:resources/todos.db";

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Error on DBConnection: " + e.getMessage());
        }
    }

    public static Connection getInstance() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }

    public static Todo insert(String title, boolean completed){
        Todo todo = null;
        try{
            int done = -1;

            if(completed) done = 1;
            else done = 0;

            String query = "INSERT INTO todos (title,completed) VALUES('" + title + "'," + done + ")";
            System.out.println(query);
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);

            // Get new id
            rs = stmt.getGeneratedKeys();
            int retrievedId = -1;
            if (rs.next()) retrievedId = rs.getInt(1);
            rs.close();

            todo = findOne("SELECT * FROM to_do WHERE id = " + retrievedId);

            //System.out.println(stmt.getUpdateCount());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return todo;
    }

    public static boolean insert(Todo todo){
        try{
            if(todo.getTitle() == "" || todo.getTitle() == null) return false;

            int done = -1;
            boolean completed = todo.getCompleted();
            String title = todo.getTitle();

            if(completed) done = 1;
            else done = 0;

            String query = "INSERT INTO todos (title,completed) VALUES('" + title + "'," + done + ")";
            stmt.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean update(Todo todo, int id){
        try{
            int done = -1;
            boolean completed = todo.getCompleted();
            String title = todo.getTitle();

            if(completed == true)
                done = 1;
            else
                done = 0;

            Todo currentTodo = findOne("SELECT * FROM todos WHERE id = " + id);

            if(title == "" || title == null) title = currentTodo.getTitle();

            String query = "UPDATE todos SET title = '"+ title +"', completed = " + done + " WHERE id = " + id;
            System.out.println(query);
            stmt.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(int id){
        try{
            String query = "DELETE FROM todos WHERE id = " + id;
            System.out.println(query);
            stmt.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteAll(){
        try{
            String query = "DELETE FROM todos";
            System.out.println(query);
            stmt.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Todo findOne(String query){
        Todo todo = null;
        try{
            System.out.println(query);
            rs = stmt.executeQuery(query);

            int id = -1;
            String title = "";
            int completed = -1;
            while (rs.next()) {
                id = rs.getInt("id");
                title = rs.getString("title");
                completed = rs.getInt("completed");
            }

            todo = new Todo(id, title, completed == 1 ? true : false);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return todo;
    }

    public static List<Todo> selectAll(){
        List<Todo> todos = new ArrayList<Todo>();
        try{
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT  * FROM todos");

            int id = -1;
            String title = "";
            int completed = -1;
            while (rs.next()) {
                id = rs.getInt("id");
                title = rs.getString("title");
                completed = rs.getInt("completed");

                todos.add(new Todo(id, title, completed == 1? true : false));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return todos;
    }
}
