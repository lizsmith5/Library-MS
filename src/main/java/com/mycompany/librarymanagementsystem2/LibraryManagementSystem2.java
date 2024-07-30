package com.mycompany.librarymanagementsystem2;

import java.util.Scanner;
import java.util.LinkedList;

// Class representing a User with ID and name
class User {
    int userId;
    String name;
    User next;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.next = null;
    }
}

// Class managing a User Linked List
class UserLinkedList {
    private User head;

    // Method to add a new user to the list
    public void addUser(int userId, String name) {
        User newUser = new User(userId, name);
        // If head is null, set the new user as the head
        if (head == null) {
            head = newUser;
        } else {
            // Traverse the list to find the end and add the new user
            User current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newUser;
        }
    }

    // Method to find a user by ID
    public User findUser(int userId) {
        User current = head;
        // Traverse the list and find user by ID
        while (current != null) {
            if (current.userId == userId) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}

// Class representing a Book in the system, with left and right references for BST
class Book {
    int isbn;
    String title;
    Book left, right;

    public Book(int isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        left = right = null;
    }
}

// Class managing a Book Binary Search Tree
class BookBinarySearchTree {
    private Book root;

    // Method to add a new book to the BST
    public void addBook(int isbn, String title) {
        root = addRecursively(root, isbn, title);
    }

    // Recursive function to add a book to the BST based on ISBN
    private Book addRecursively(Book current, int isbn, String title) {
        if (current == null) {
            return new Book(isbn, title);
        }

        if (isbn < current.isbn) {
            current.left = addRecursively(current.left, isbn, title);
        } else if (isbn > current.isbn) {
            current.right = addRecursively(current.right, isbn, title);
        }

        return current;
    }

    // Method to find a book by ISBN
    public Book findBook(int isbn) {
        return findRecursively(root, isbn);
    }

    // Recursive function to find a book in the BST by ISBN
    private Book findRecursively(Book current, int isbn) {
        if (current == null || current.isbn == isbn) {
            return current;
        }

        if (isbn < current.isbn) {
            return findRecursively(current.left, isbn);
        } else {
            return findRecursively(current.right, isbn);
        }
    }
}

// Class representing a Hash Table for key-value pairs
class HashTable {
    private class HashNode {
        int key;
        String value;
        HashNode next;

        public HashNode(int key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private LinkedList<HashNode>[] table;
    private int size;

    // Constructor to initialize the Hash Table with a given size
    public HashTable(int size) {
        this.size = size;
        table = new LinkedList[size];
        // Initialize each element of the table with a new LinkedList
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Hash function to calculate the index for a key
    private int hash(int key) {
        return key % size;
    }

    // Method to add an item to the Hash Table
    public void addItem(int key, String value) {
        int hashIndex = hash(key);
        HashNode newNode = new HashNode(key, value);
        table[hashIndex].add(newNode);
    }

    // Method to get an item from the Hash Table by key
    public String getItem(int key) {
        int hashIndex = hash(key);
        // Traverse the linked list at the calculated index and search for the key
        for (HashNode node : table[hashIndex]) {
            if (node.key == key) {
                return node.value;
            }
        }
        return null;
    }
}

// Main class to manage the library system
public class LibraryManagementSystem2 {
    private UserLinkedList users;
    private BookBinarySearchTree catalog;
    private BookBinarySearchTree retrievedBooks;
    private HashTable bookSearch;
    private HashTable userSearch;

    // Constructor to initialize the library system components
    public LibraryManagementSystem2() {
        users = new UserLinkedList();
        catalog = new BookBinarySearchTree();
        retrievedBooks = new BookBinarySearchTree();
        bookSearch = new HashTable(100);
        userSearch = new HashTable(100);
    }

    // Method to add a user to the system
    public void addUser(int userId, String name) {
        users.addUser(userId, name);
        userSearch.addItem(userId, name);
    }

    // Method to add a book to the system
    public void addBook(int isbn, String title) {
        catalog.addBook(isbn, title);
        bookSearch.addItem(isbn, title);
    }

    // Method to retrieve a book by ISBN
    public String retrieveBook(int isbn) {
        Book book = catalog.findBook(isbn);
        if (book != null) {
            retrievedBooks.addBook(book.isbn, book.title);
            return book.title;
        }
        return "Book not found";
    }

    // Method to search for a book by ISBN
    public String searchBook(int isbn) {
        return bookSearch.getItem(isbn);
    }

    // Method to search for a user by ID
    public String searchUser(int userId) {
        return userSearch.getItem(userId);
    }

    // Main method to run the library management system
    public static void main(String[] args) {
        LibraryManagementSystem2 library = new LibraryManagementSystem2();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu options to the user
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add User");
            System.out.println("2. Add Book");
            System.out.println("3. Retrieve Book");
            System.out.println("4. Search Book");
            System.out.println("5. Search User");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            // Handle user input and perform corresponding actions
            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter User Name: ");
                    String userName = scanner.nextLine();
                    library.addUser(userId, userName);
                    System.out.println("User added successfully.");
                    break;
                case 2:
                    System.out.print("Enter Book ISBN: ");
                    int isbn = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter Book Title: ");
                    String bookTitle = scanner.nextLine();
                    library.addBook(isbn, bookTitle);
                    System.out.println("Book added successfully.");
                    break;
                case 3:
                    System.out.print("Enter Book ISBN to retrieve: ");
                    int retrieveIsbn = scanner.nextInt();
                    String retrievedTitle = library.retrieveBook(retrieveIsbn);
                    System.out.println("Retrieved Book: " + retrievedTitle);
                    break;
                case 4:
                    System.out.print("Enter Book ISBN to search: ");
                    int searchIsbn = scanner.nextInt();
                    String searchTitle = library.searchBook(searchIsbn);
                    System.out.println("Searched Book: " + searchTitle);
                    break;
                case 5:
                    System.out.print("Enter User ID to search: ");
                    int searchUserId = scanner.nextInt();
                    String searchUserName = library.searchUser(searchUserId);
                    System.out.println("Searched User: " + searchUserName);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
