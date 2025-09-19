package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Exceptions.InputValueNotFoundException;
import com.acciojob.Library_Management_System.Exceptions.InsufficientInputException;
import com.acciojob.Library_Management_System.Exceptions.notFoundInDbException;
import com.acciojob.Library_Management_System.Repositories.AuthorRepository;
import com.acciojob.Library_Management_System.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;


    public boolean updateNoOfBooks(Book addingBook){
        List<Book> allBooks= bookRepository.findAll();
        boolean found=false;
        for (Book book: allBooks){
            if(book.getTitle().equals(addingBook.getTitle())){
                book.setBooksQuantity(addingBook.getBooksQuantity()+ book.getBooksQuantity());
                book.setNoOfBooksLeft(addingBook.getBooksQuantity()+ book.getNoOfBooksLeft());

                if(!book.getAuthor().equals(null)){
                    Optional<Author>optionalAuthor=authorRepository.findById(book.getAuthor().getAuthorId());
                    Author author=optionalAuthor.get();
                    author.setNoOfBooks(author.getNoOfBooks()+addingBook.getBooksQuantity());
                    authorRepository.save(author);
                }
                found= true;
                break;
            }
            bookRepository.save(book);
        }
        return found;
    }

    public String addBookByAuthorID(Book book, Integer authorId, String authorName)throws Exception{
        Optional<Author>optionalAuthor=authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()){
            Author author= optionalAuthor.get();
            book.setAuthor(author);
            author.setNoOfBooks(author.getNoOfBooks()+book.getBooksQuantity());
            authorRepository.save(author);
            bookRepository.save(book);
            return "Book " + book.getTitle() + " has been saved to DB";
        }
        return addBookByAuthorName(book,authorName);

    }

    public String addBookByAuthorName(Book book, String authorName)throws Exception{
        List<Author> allAuthors = authorRepository.findAll();
        if(allAuthors.isEmpty()){
            throw new Exception("Author not present");
        }
        boolean found = false;
        for (Author author : allAuthors) {
            if (author.getAuthorName().equals(authorName)) {
                book.setAuthor(author);
                author.setNoOfBooks(author.getNoOfBooks()+ book.getBooksQuantity());
                authorRepository.save(author);
                found = true;
                break;
            }
        }
        if (!found) {
            book.setAuthor(null);
            bookRepository.save(book);
            return "Author "+authorName+" not found And Book " + book.getTitle() + " has been saved to DB";
        }
        bookRepository.save(book);
        return " Book " + book.getTitle() + " has been saved to DB";
    }

    //add book and also associate book with author
    public String addBook(Book book, Integer authorId, String authorName)throws Exception {
        if(book.getBooksQuantity() == null){
            book.setBooksQuantity(1);
        }
        if(updateNoOfBooks(book)){
            return "Book " + book.getTitle() + " has been saved to DB";
        }
        if(book.getNoOfBooksLeft()==0){
            book.setNoOfBooksLeft(book.getBooksQuantity());
        }

        if (authorId != null) {
            return addBookByAuthorID(book,authorId,authorName);
        }
        else if (authorName != null && !authorName.isEmpty()) {
            return addBookByAuthorName(book,authorName);
        }
        book.setAuthor(null);
        bookRepository.save(book);
        return "Book " + book.getTitle() + " has been saved to DB";
    }



    //get books by authorId OR authorName
    public List <String> getByAuthorId(Integer authorId,String authorName) throws Exception{
        List<Book>allBooks = bookRepository.findAll();
        if(allBooks.isEmpty()){
            throw new notFoundInDbException("Database is Empty");
        }
        List<String>ans =new ArrayList<>();
            boolean found= false;
            for (Book book:allBooks){
                if(book.getAuthor() != null && book.getAuthor().getAuthorId().equals(authorId)){
                    ans.add(book.getTitle());
                    found =true;
                }
            }
            if(!found){
                if(authorName==null || authorName.isEmpty()) {
                    throw new InputValueNotFoundException("Please Provide the applicable authorId");
                }
                return getByAuthorName(authorName);
            }
        return ans;
    }
    public List<String> getByAuthorName(String authorName) throws Exception{
        List<Book>allBooks = bookRepository.findAll();
        if(allBooks.isEmpty()){
            throw new notFoundInDbException("Database is Empty");
        }
        List<String>ans =new ArrayList<>();
        boolean found= false;
        for (Book book:allBooks){
            if(book.getAuthor() != null && book.getAuthor().getAuthorName().equals(authorName)){
                ans.add(book.getTitle());
                found=true;
            }
        }
        if(!found){
            throw new InputValueNotFoundException("No book present in authorName OR Please Provide the applicable authorName");
        }
        return ans;
    }
    public List<String> getBooksOfAuthor(Integer authorId, String authorName) throws Exception{

        if(authorId!=null){
            return getByAuthorId(authorId,authorName);
        }
        else if (authorName!=null&&!authorName.isEmpty()) {
           return getByAuthorName(authorName);
        }
        else {
            throw new InsufficientInputException("You should give either AuthorId Or AuthorName");
        }
    }

    //associate using bookId and authorId
    public String associateBookAndAuthor(Integer bookId, Integer authorId) throws Exception {
        Optional<Book>optionalBook=bookRepository.findById(bookId);
        Book book= optionalBook.orElseThrow(()->new InputValueNotFoundException("Book not found with Id "+ bookId));
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Author author = optionalAuthor.orElseThrow(() -> new RuntimeException("Author not found"));
        boolean isNull= false;
        if(book.getAuthor()==null){
            isNull=true;
        }
        if(isNull){
            author.setNoOfBooks(author.getNoOfBooks()+book.getBooksQuantity());
        }
        book.setAuthor(author);
        bookRepository.save(book);
        authorRepository.save(author);
        return "Book " + book.getTitle()+ " And Author " +author.getAuthorName()+ " associated  Successfully";
    }

    //delete by bookId
    public String deleteBookById(Integer bookId) throws Exception {
        Book book =bookRepository.findById(bookId).orElseThrow(()-> new InputValueNotFoundException("Book with Id "+bookId+" not found"));
        bookRepository.delete(book);
        return "Book with Id "+bookId+" has been deleted";
    }
}
