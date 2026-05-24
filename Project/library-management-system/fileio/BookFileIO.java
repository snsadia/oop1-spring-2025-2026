package fileio;

import entity.Book;
import java.io.*;

import javax.swing.JOptionPane;

public class BookFileIO {

    private static final String FILE_NAME = "fileio/database.txt";
    private static final String TEMP_FILE = "fileio/temp.txt";

    // ================= CREATE FILE =================
    public static void createFileIfNotExists() throws IOException {
        File file = new File(FILE_NAME);
        if(!file.exists())
            file.createNewFile();
    }

    // ================= CHECK DUPLICATE ID =================
    public static boolean idExists(String id) {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = br.readLine()) != null) {
                Book b = Book.fromLine(line);
                if(b != null && b.getId().equals(id))
                    return true;
            }
        } catch(IOException ignored) {}
        return false;
    }

    // ================= CHECK DUPLICATE TITLE =================
    public static boolean titleExists(String title) {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = br.readLine()) != null) {
                Book b = Book.fromLine(line);
                if(b != null && b.getTitle().equalsIgnoreCase(title))
                    return true;
            }
        } catch(IOException ignored) {}
        return false;
    }

    // ================= COUNT =================
    public static int countRecords() {
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = br.readLine()) != null) {
                if(Book.fromLine(line) != null)
                    count++;
            }
        } catch(IOException ignored) {}
        return count;
    }

    // ================= ADD =================
    public boolean addBook(Book b) {

        //  duplicate check (ID + TITLE)
        if(idExists(b.getId()) || titleExists(b.getTitle()))
            return false;

        try(PrintWriter pw = new PrintWriter(
                new BufferedWriter(new FileWriter(FILE_NAME, true)))) {

            pw.println(b.toLine());
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    // ================= UPDATE =================

   public boolean updateBook(Book b) {

    File inputFile = new File(FILE_NAME);
    File tempFile = new File(TEMP_FILE);

    boolean found = false;

    try (
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
    ) {

        // CHECK DUPLICATE TITLE (excluding same record)
        Book[] books = getAllBooks();

        for (int i = 0; i < books.length; i++) {
            if (!books[i].getId().equals(b.getId())) {
                if (books[i].getTitle().equalsIgnoreCase(b.getTitle())) {
                    JOptionPane.showMessageDialog(null, "Duplicate Title not allowed!");
                    return false;
                }
            }
        }

        // UPDATE FILE
        String line;

        while ((line = br.readLine()) != null) {
            Book existing = Book.fromLine(line);

            if (existing != null && existing.getId().equals(b.getId())) {
                bw.write(b.toLine());
                found = true;
            } else {
                bw.write(line);
            }
            bw.newLine();
        }

    } catch (IOException e) {
        return false;
    }

    if (found) {
        inputFile.delete();
        tempFile.renameTo(inputFile);
    } else {
        tempFile.delete();
    }

    return found;
}
    // ================= DELETE =================
    public boolean deleteBook(String id) {

        File inputFile = new File(FILE_NAME);
        File tempFile = new File(TEMP_FILE);

        boolean found = false;

        try(
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            while((line = br.readLine()) != null) {
                Book existing = Book.fromLine(line);

                if(existing != null && existing.getId().equals(id)) {
                    found = true;
                    continue;
                }

                bw.write(line);
                bw.newLine();
            }

        } catch(IOException e) {
            return false;
        }

        if(found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
        }

        return found;
    }

    // ================= GET ALL =================
    public Book[] getAllBooks() {

        int total = countRecords();
        Book[] books = new Book[total];

        int idx = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while((line = br.readLine()) != null && idx < total) {
                Book b = Book.fromLine(line);
                if(b != null) {
                    books[idx] = b;
                    idx++;
                }
            }

        } catch(IOException ignored) {}

        return books;
    }

    // ================= SEARCH =================
    public Object[][] searchBooks(String keyword) {

        String kw = keyword.toLowerCase();

        // PASS 1: count matches
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while((line = br.readLine()) != null) {
                Book b = Book.fromLine(line);

                if(b != null &&
                   (b.getId().toLowerCase().contains(kw) ||
                    b.getTitle().toLowerCase().contains(kw) ||
                    b.getAuthor().toLowerCase().contains(kw) ||
                    b.getCategory().toLowerCase().contains(kw))) {

                    count++;
                }
            }

        } catch(IOException ignored) {}

        // PASS 2: fill data
        Object[][] result = new Object[count][4];
        int idx = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while((line = br.readLine()) != null && idx < count) {
                Book b = Book.fromLine(line);

                if(b != null &&
                   (b.getId().toLowerCase().contains(kw) ||
                    b.getTitle().toLowerCase().contains(kw) ||
                    b.getAuthor().toLowerCase().contains(kw) ||
                    b.getCategory().toLowerCase().contains(kw))) {

                    result[idx][0] = b.getId();
                    result[idx][1] = b.getTitle();
                    result[idx][2] = b.getAuthor();
                    result[idx][3] = b.getCategory();
                    idx++;
                }
            }

        } catch(IOException ignored) {}

        return result;
    }
}