package taskmanagement;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        User user = new User("thiru", "1234"); // Default credentials

        System.out.println("==================================");
        System.out.println("      Welcome to Task Manager     ");
        System.out.println("==================================");

        boolean isAuthenticated = false;

        // Login
        for (int i = 0; i < 3; i++) {
            System.out.print("\nEnter Username: ");
            String username = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (user.authenticate(username, password)) {
                isAuthenticated = true;
                break;
            } else {
                System.out.println("❌ Incorrect username or password. Try again.");
            }
        }

        if (!isAuthenticated) {
            System.out.println("\n⛔ Too many failed attempts. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n======== Main Menu ========");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Complete Task");
            System.out.println("5. Delete Task");
            System.out.println("6. Logout");
            System.out.println("============================");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Task Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Task Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter Due Date (yyyy-MM-dd): ");
                    String dueDate = sc.nextLine();
                    taskManager.addTask(title, desc, dueDate);
                    break;

                case 2:
                    taskManager.viewTasks();
                    break;

                case 3:
                    System.out.print("Enter Task ID to Update: ");
                    int updateId = Integer.parseInt(sc.nextLine());
                    taskManager.updateTask(updateId);
                    break;

                case 4:
                    System.out.print("Enter Task ID to Mark as Complete: ");
                    int completeId = Integer.parseInt(sc.nextLine());
                    taskManager.completeTask(completeId);
                    break;

                case 5:
                    System.out.print("Enter Task ID to Delete: ");
                    int deleteId = Integer.parseInt(sc.nextLine());
                    taskManager.deleteTask(deleteId);
                    break;

                case 6:
                    System.out.println("\n👋 Thank you for using Task Manager. Goodbye!");
                    break;

                default:
                    System.out.println("\n⚠ Invalid choice. Please try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
package taskmanagement;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(int id, String title, String description, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Setters
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task ID: " + id +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nDue Date: " + dueDate +
                "\nCompleted: " + (isCompleted ? "Yes" : "No") + "\n";
    }
}
package taskmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskManager {
    private List<Task> tasks;
    private static int idCounter = 1;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String title, String description, String dueDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);
        Task task = new Task(idCounter++, title, description, dueDate);
        tasks.add(task);
        System.out.println("\n✅ Task added successfully!\n");
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n⚡ No tasks available.\n");
            return;
        }
        System.out.println("\n📋 Your Tasks:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void updateTask(int id) {
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("\n❌ Task not found.\n");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new title: ");
        String newTitle = sc.nextLine();
        System.out.print("Enter new description: ");
        String newDesc = sc.nextLine();
        System.out.print("Enter new due date (yyyy-MM-dd): ");
        String newDueDate = sc.nextLine();

        task.setTitle(newTitle);
        task.setDescription(newDesc);
        task.setDueDate(LocalDate.parse(newDueDate));
        System.out.println("\n✅ Task updated successfully!\n");
    }

    public void completeTask(int id) {
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("\n❌ Task not found.\n");
            return;
        }
        task.setCompleted(true);
        System.out.println("\n✅ Task marked as completed!\n");
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            System.out.println("\n✅ Task deleted successfully!\n");
        } else {
            System.out.println("\n❌ Task not found.\n");
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
}
package taskmanagement;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        return username.equals(inputUsername) && password.equals(inputPassword);
    }
}