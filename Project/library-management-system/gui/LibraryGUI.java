package gui;

import entity.Book;
import fileio.BookFileIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class LibraryGUI extends JFrame implements ActionListener {

    BookFileIO bf = new BookFileIO();

    JLabel titleLabel, searchLabel;
    JLabel idLabel, nameLabel, authorLabel, categoryLabel;

    JTextField searchField, idField, nameField, authorField, categoryField;

    JButton searchButton, addButton, updateButton, deleteButton, viewAllButton, clearButton, exitButton;
    String selectedId = "";

    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;

    public LibraryGUI() {

        setTitle("Library Management System");
        setSize(825, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("LIBRARY MANAGEMENT SYSTEM");

        titleLabel.setBounds(275, 2, 825, 25);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        searchLabel = new JLabel("");
        searchLabel.setBounds(30, 25, 180, 20);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(30, 50, 650, 25);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(688, 50, 100, 26);
        add(searchButton);
        JPanel searchPanel = new JPanel();
         searchPanel.setBounds(10, 30, 790, 55);
         searchPanel.setBorder(BorderFactory.createTitledBorder("Search by ID or Title"));
         searchPanel.setLayout(null);
         add(searchPanel);

        idLabel = new JLabel("Book ID (Exactly 8 Digits):");
        idLabel.setBounds(30, 320, 200, 25);

        add(idLabel);
        nameLabel = new JLabel("Book Title:");
        nameLabel.setBounds(30, 355, 100, 25);
        add(nameLabel);

        authorLabel = new JLabel("Author:");
        authorLabel.setBounds(30, 390, 100, 25);
        add(authorLabel);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(30, 425, 100, 25);
        add(categoryLabel);

        idField = new JTextField();
        idField.setBounds(240, 320, 550, 25);
        add(idField);

        nameField = new JTextField();
        nameField.setBounds(240, 355, 550, 25);
        add(nameField);

        authorField = new JTextField();
        authorField.setBounds(240, 390, 550, 25);
        add(authorField);

        categoryField = new JTextField();
        categoryField.setBounds(240, 425, 550, 25);
        add(categoryField);

        addButton = new JButton("Add");
        addButton.setBounds(40, 465, 90, 28);
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(170, 465, 90, 28);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(300, 465, 90, 28);
        add(deleteButton);

        viewAllButton = new JButton("View All");
        viewAllButton.setBounds(425, 465, 90, 28);
        add(viewAllButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(552, 465, 90, 28);
        add(clearButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(680, 465, 90, 28);
        add(exitButton);

        String[] columns = {"Book ID", "Book Title", "Author", "Category"};

        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();

        if(row != -1) {
             selectedId = model.getValueAt(row, 0).toString(); 
            idField.setText(model.getValueAt(row, 0).toString());
            nameField.setText(model.getValueAt(row, 1).toString());
            authorField.setText(model.getValueAt(row, 2).toString());
            categoryField.setText(model.getValueAt(row, 3).toString());
        }
    }
});
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 120, 752, 150);
        add(scrollPane);

        JPanel dataPanel = new JPanel();
                dataPanel.setBounds(10, 87, 790, 200);
                dataPanel.setBorder(BorderFactory.createTitledBorder("Book Records"));
                dataPanel.setLayout(null);
                add(dataPanel);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        viewAllButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);


        JPanel detailsPanel = new JPanel();
         detailsPanel.setBounds(10, 290, 790, 230);
         detailsPanel.setBorder(BorderFactory.createTitledBorder("Book Details"));
         detailsPanel.setLayout(null);
         add(detailsPanel);

        loadTable(); // load data initially

        setVisible(true);
    }

    // ================= LOAD TABLE =================
    public void loadTable() {
        model.setRowCount(0);

        Book[] books = bf.getAllBooks();

        for (int i = 0; i < books.length; i++) {
            String[] row = {
                    books[i].getId(),
                    books[i].getTitle(),
                    books[i].getAuthor(),
                    books[i].getCategory()
            };
            model.addRow(row);
        }
    }

    // ================= ACTION =================
    public void actionPerformed(ActionEvent e) {

       // ADD
     if (e.getSource() == addButton) {

    String id = idField.getText().trim();
    String title = nameField.getText().trim();
    String author = authorField.getText().trim();
    String category = categoryField.getText().trim();


    // Empty check
    if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required!");
        return;
    }

    // ID validation
    if (!id.matches("\\d{8}")) {
        JOptionPane.showMessageDialog(this, "ID must be exactly 8 digits (numbers only).\n" +"Minimum: 8 digits, Maximum: 8 digits.");
        return;
    }

    // Title validation
    if(!title.matches("[a-zA-Z0-9 &'\\-:+]+")) {
        JOptionPane.showMessageDialog(this, "Invalid title! Use letters, numbers, and basic symbols only.");
        return;
    }

    //  Author validation
    if(!author.matches("[a-zA-Z .]+")) {
        JOptionPane.showMessageDialog(this, "Author name should contain only letters.");
        return;
    }

    //  Category validation
    if(!category.matches("[a-zA-Z ]+")) {
        JOptionPane.showMessageDialog(this, "Category should contain only letters.");
        return;
    }

    // Add book ONLY after all validations pass
    Book b = new Book(id, title, author, category, "Available");

    if (bf.addBook(b)) {
        loadTable();
        JOptionPane.showMessageDialog(this, "Added!");
    } else {
        JOptionPane.showMessageDialog(this, "Cannot add duplicate ID or Title!");
    }
}

       // UPDATE
      
     if (e.getSource() == updateButton) {

    String id = idField.getText().trim();
    String title = nameField.getText().trim();
    String author = authorField.getText().trim();
    String category = categoryField.getText().trim();

    //  Select check
    if(selectedId == null || selectedId.equals("")) {
        JOptionPane.showMessageDialog(this, "Please select a record first!");
        return;
    }

    // ID validation
    if (!id.matches("\\d{8}")) {
        JOptionPane.showMessageDialog(this, "ID must be exactly 8 digits (numbers only).\n"
         +"Minimum: 8 digits, Maximum: 8 digits.");
        return;
    }

    //  Empty check
    if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required!");
        return;
    }

    //  Title validation (supports +)
    if(!title.matches("[a-zA-Z0-9 &'\\-:+]+")) {
        JOptionPane.showMessageDialog(this, "Invalid title! Use letters, numbers, and basic symbols only.");
        return;
    }

    //  Author validation
    if(!author.matches("[a-zA-Z .]+")) {
        JOptionPane.showMessageDialog(this, "Author name should contain only letters.");
        return;
    }

    //  Category validation
    if(!category.matches("[a-zA-Z ]+")) {
        JOptionPane.showMessageDialog(this, "Category should contain only letters.");
        return;
    }

    Book[] books = bf.getAllBooks();

    //  Duplicate ID check
    for(int i = 0; i < books.length; i++) {
        if(books[i].getId().equals(id) && !books[i].getId().equals(selectedId)) {
            JOptionPane.showMessageDialog(this, "Duplicate ID! Update failed.");
            return;
        }
    }

    //  Duplicate Title check
    for(int i = 0; i < books.length; i++) {
        if(books[i].getTitle().equalsIgnoreCase(title) && !books[i].getId().equals(selectedId)) {
            JOptionPane.showMessageDialog(this, "Duplicate Title! Update failed.");
            return;
        }
    }

    //  Update logic
    if(id.equals(selectedId)) {

        Book b = new Book(id, title, author, category, "Available");

        if (bf.updateBook(b)) {
            loadTable();
            JOptionPane.showMessageDialog(this, "Updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Update failed!");
        }

    } else {

        Book oldBook = null;

        for(int i = 0; i < books.length; i++) {
            if(books[i].getId().equals(selectedId)) {
                oldBook = books[i];
                break;
            }
        }

        bf.deleteBook(selectedId);

        Book newBook = new Book(id, title, author, category, "Available");

        if(bf.addBook(newBook)) {
            loadTable();
            JOptionPane.showMessageDialog(this, "Updated with new ID!");
        } else {
            if(oldBook != null) {
                bf.addBook(oldBook);
            }
            JOptionPane.showMessageDialog(this, "Update failed!");
        }
    }
}


// SEARCH
if (e.getSource() == searchButton) {

    String keyword = searchField.getText().trim();

    Object[][] rows = bf.searchBooks(keyword);

    model.setRowCount(0);

    for (int i = 0; i < rows.length; i++) {
        model.addRow(rows[i]);
    }

    if (rows.length == 0) {
        JOptionPane.showMessageDialog(this, "No match found!");
    }
}

        // DELETE
        if(e.getSource() == deleteButton) {

        String id = idField.getText().trim();

        if(id.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please select a book first!");
         return;
    }

         int confirm = JOptionPane.showConfirmDialog(
          this,
         "Are you sure you want to delete this book?",
         "Confirm Delete",
         JOptionPane.YES_NO_OPTION
    );

        if(confirm == JOptionPane.YES_OPTION) {

        bf.deleteBook(id);

        loadTable(); // refresh table

        JOptionPane.showMessageDialog(this, "Deleted Successfully!");
    }
}

        // VIEW ALL
        if (e.getSource() == viewAllButton) {
            loadTable();
        }

        // CLEAR
        if(e.getSource() == clearButton) {
         idField.setText("");
         nameField.setText("");
         authorField.setText("");
         categoryField.setText("");
         searchField.setText("");
}

        // EXIT
               if(e.getSource() == exitButton) {
            System.exit(0);
        }
    }  // end of actionPerformed

}  // end of class