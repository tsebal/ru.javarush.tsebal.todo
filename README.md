# ToDo Tasks by Tsebal v.0.1b
The program is an online task list. You can add new tasks, edit and delete them, as well as set their status. Host it on a hosting site that supports Docker containers.

How to run the application:
1. Clone the project.
2. In the console, run the docker compose up command (Docker v.4.16.3 or higher).
3. Load the initialization schema from the ..\resources\todo-init.sql into the mysql database (use MySQL Workbench 8.0).
4. In your browser, go to http://localhost:8080/todo-tasks/