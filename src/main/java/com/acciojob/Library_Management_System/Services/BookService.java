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

    //add book and also associate book with author
    public String addBook(Book book, Integer authorId, String authorName) {
        if (authorId != null) {
            Optional<Author>optionalAuthor=authorRepository.findById(authorId);
            Author author = optionalAuthor
                    .orElseThrow(() -> new RuntimeException("Invalid authorId: " + authorId));
            book.setAuthor(author);
        }
        else if (authorName != null && !authorName.isEmpty()) {
            List<Author> allAuthors = authorRepository.findAll();
            boolean found = false;
            for (Author author : allAuthors) {
                if (author.getAuthorName().equals(authorName)) {
                    book.setAuthor(author);
                    found = true;
                    break;
                }
            }
            if (!found) {
                book.setAuthor(null);
            }
        }
        else {
            book.setAuthor(null);
        }
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
        if(authorId!=null){
            boolean found= false;
            for (Book book:allBooks){
                if(book.getAuthor().getAuthorId().equals(authorId)){
                    ans.add(book.getTitle());
                    found =true;
                }
            }
            if(!found){
                if(authorName==null || authorName.isEmpty()) {
                    throw new InputValueNotFoundException("Please Provide the applicable authorId");
                }
                getByAuthorName(authorId,authorName);
            }
        }
        return ans;
    }
    public List<String> getByAuthorName(Integer authorId, String authorName) throws Exception{
        List<Book>allBooks = bookRepository.findAll();
        if(allBooks.isEmpty()){
            throw new notFoundInDbException("Database is Empty");
        }
        List<String>ans =new ArrayList<>();
        boolean found= false;
        for (Book book:allBooks){
            if(book.getAuthor().getAuthorName().equals(authorName)){
                ans.add(book.getTitle());
                found=true;
            }
        }
        if(!found){
            throw new InputValueNotFoundException("Please Provide the applicable authorName");
        }
        return ans;
    }
    public List<String> getBooksOfAuthor(Integer authorId, String authorName) throws Exception{

        if(authorId!=null){
            return getByAuthorId(authorId,authorName);
        }
        else if (authorName!=null&&!authorName.isEmpty()) {
           return getByAuthorName(null ,authorName);
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
        book.setAuthor(author);
        author.setNoOfBooks(author.getNoOfBooks()+1);
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
