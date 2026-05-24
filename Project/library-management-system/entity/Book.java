package entity;

public class Book {

    private String id;
    private String title;
    private String author;
    private String category;
    private String status;

    // Constructor
    public Book(String id, String title, String author, String category, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }

    // Convert object → file line
    public String toLine() {
        return id + "," + title + "," + author + "," + category + "," + status;
    }

    // Convert file line → object
    public static Book fromLine(String line) {

        String[] data = line.split(",");

        if (data.length < 5) return null;

        return new Book(
            data[0],
            data[1],
            data[2],
            data[3],
            data[4]
        );
    }

    // For JTable (optional but useful)
    public Object[] toRow() {
        return new Object[] { id, title, author, category };
    }
}