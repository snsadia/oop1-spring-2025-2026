interface BookShopOperations {
    boolean insertBook(Book b);

    boolean removeBook(Book b);

    void showAllBooks();

    Book searchBook(String isbn);
}

interface BookOperations {
    void addQuantity(int amount);

    void sellQuantity(int amount);
}

class BookShop implements BookShopOperations {

    private String name;
    private Book[] listOfBooks;
    private int count;

    public BookShop() {
        this.name = "";
        this.listOfBooks = new Book[100];
        this.count = 0;
    }

    public BookShop(String name) {
        this.name = name;
        this.listOfBooks = new Book[100];
        this.count = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean insertBook(Book b) {
        if (count < 100) {
            listOfBooks[count] = b;
            count++;
            System.out.println("\"" + b.getBookTitle() + "\" inserted successfully.");
            return true;
        } else {
            System.out.println("BookShop is full! Cannot insert more books.");
            return false;
        }
    }

    @Override
    public boolean removeBook(Book b) {
        for (int i = 0; i < count; i++) {
            if (listOfBooks[i].getIsbn().equals(b.getIsbn())) {
                for (int j = i; j < count - 1; j++) {
                    listOfBooks[j] = listOfBooks[j + 1];
                }
                listOfBooks[count - 1] = null;
                count--;
                System.out.println("\"" + b.getBookTitle() + "\" removed successfully.");
                return true;
            }
        }
        System.out.println("Book not found to remove.");
        return false;
    }

    @Override
    public void showAllBooks() {
        System.out.println("\n " + name + " All Books;");
        if (count == 0) {
            System.out.println("No books ");
        } else {
            for (int i = 0; i < count; i++) {
                listOfBooks[i].showDetails();
            }
        }
        System.out.println("Total Books: " + count);
        System.out.println("\n");
    }

    @Override
    public Book searchBook(String isbn) {
        for (int i = 0; i < count; i++) {
            if (listOfBooks[i].getIsbn().equals(isbn)) {
                System.out.println("Book found!");
                return listOfBooks[i];
            }
        }
        System.out.println("No book found with ISBN: " + isbn);
        return null;
    }
}

abstract class Book implements BookOperations {

    private String isbn;
    private String bookTitle;
    private String authorName;
    private double price;
    private int availableQuantity;

    public Book() {
        this.isbn = "";
        this.bookTitle = "";
        this.authorName = "";
        this.price = 0.0;
        this.availableQuantity = 0;
    }

    public Book(String isbn, String bookTitle, String authorName, double price, int availableQuantity) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvaiableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public void addQuantity(int amount) {
        this.availableQuantity += amount;
        System.out.println(amount + " copies added. Total now: " + availableQuantity);
    }

    @Override
    public void sellQuantity(int amount) {
        if (amount <= availableQuantity) {
            this.availableQuantity -= amount;
            System.out.println(amount + " copies sold. Remaining: " + availableQuantity);
        } else {
            System.out.println("Not enough stock! Available: " + availableQuantity);
        }
    }

    public abstract void showDetails();
}

class StoryBook extends Book {

    private String category;

    public StoryBook() {
        super();
        this.category = "";
    }

    public StoryBook(String isbn, String bookTitle, String authorName, double price, int availableQuantity,
            String category) {
        super(isbn, bookTitle, authorName, price, availableQuantity);
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void showDetails() {
        System.out.println(" Story Book ");
        System.out.println("ISBN         : " + getIsbn());
        System.out.println("Title        : " + getBookTitle());
        System.out.println("Author       : " + getAuthorName());
        System.out.println("Price        : " + getPrice() + " BDT");
        System.out.println("Quantity     : " + getAvailableQuantity());
        System.out.println("Category     : " + category);
    }
}

class TextBook extends Book {
    private int standard;

    public TextBook() {
        super();
        this.standard = 0;
    }

    public TextBook(String isbn, String bookTitle, String authorName, double price, int availableQuantity,
            int standard) {
        super(isbn, bookTitle, authorName, price, availableQuantity);
        this.standard = standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getStandard() {
        return standard;
    }

    @Override
    public void showDetails() {
        System.out.println("Text Book ");
        System.out.println("ISBN         : " + getIsbn());
        System.out.println("Title        : " + getBookTitle());
        System.out.println("Author       : " + getAuthorName());
        System.out.println("Price        : " + getPrice() + " BDT");
        System.out.println("Quantity     : " + getAvailableQuantity());
        System.out.println("Standard     : " + standard);

    }
}

public class Start {
    public static void main(String[] args) {

        StoryBook sb1 = new StoryBook("SB101", "The Midnight Library", "Matt Haig", 450.0, 25, "Fantasy");
        StoryBook sb2 = new StoryBook("SB102", "A Game of Thrones", "George R.R. Martin", 850.0, 15, "Epic Fantasy");
        StoryBook sb3 = new StoryBook("SB103", "Project Hail Mary", "Andy Weir", 620.0, 30, "Sci-Fi");
        StoryBook sb4 = new StoryBook("SB104", "The Alchemist", "Paulo Coelho", 320.0, 40, "Adventure");
        StoryBook sb5 = new StoryBook("SB105", "Murder on the Orient Express", "Agatha Christie", 380.0, 18, "Mystery");

        StoryBook sbDefault = new StoryBook();
        sbDefault.setIsbn("SB106");
        sbDefault.setBookTitle("The Hobbit");
        sbDefault.setAuthorName("J.R.R. Tolkien");
        sbDefault.setPrice(480.0);
        sbDefault.setAvaiableQuantity(22);
        sbDefault.setCategory("Fantasy");

        TextBook tb1 = new TextBook("TB101", "University Physics", "Hugh D. Young", 1200.0, 12, 12);
        TextBook tb2 = new TextBook("TB102", "Introduction to Algorithms", "Thomas H. Cormen", 1500.0, 10, 11);
        TextBook tb3 = new TextBook("TB103", "Java: The Complete Reference", "Herbert Schildt", 950.0, 20, 12);
        TextBook tb4 = new TextBook("TB104", "Principles of Microeconomics", "N. Gregory Mankiw", 850.0, 15, 11);
        TextBook tb5 = new TextBook("TB105", "Organic Chemistry", "Jonathan Clayden", 1400.0, 8, 12);

        TextBook tbDefault = new TextBook();
        tbDefault.setIsbn("TB106");
        tbDefault.setBookTitle("Calculus: Early Transcendentals");
        tbDefault.setAuthorName("James Stewart");
        tbDefault.setPrice(1100.0);
        tbDefault.setAvaiableQuantity(14);
        tbDefault.setStandard(12);

        BookShop shop = new BookShop("Sadia's Book Sanctuary");

        System.out.println("\nBookShop Name: " + shop.getName());
        System.out.println("\n");

        shop.insertBook(sb1);
        shop.insertBook(sb2);
        shop.insertBook(sb3);
        shop.insertBook(sb4);
        shop.insertBook(sb5);

        shop.insertBook(tb1);
        shop.insertBook(tb2);
        shop.insertBook(tb3);
        shop.insertBook(tb4);
        shop.insertBook(tb5);

        shop.showAllBooks();

        System.out.println("--- Searching Book by ISBN: TB103 ---");

        Book found = shop.searchBook("TB103");

        if (found != null) {
            found.showDetails();
        }

        sb1.addQuantity(4);
        sb1.sellQuantity(2);

        tb2.addQuantity(8);
        tb2.sellQuantity(20);

        shop.removeBook(sb4);

        shop.showAllBooks();

        System.out.println("StoryBook Category: " + sb2.getCategory());
        System.out.println("TextBook Standard : " + tb1.getStandard());
        System.out.println("Book Price        : " + tb3.getPrice());
    }
}