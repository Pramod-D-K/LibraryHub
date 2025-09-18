package com.acciojob.Library_Management_System.Services;

import com.acciojob.Library_Management_System.Entities.Author;
import com.acciojob.Library_Management_System.Entities.Book;
import com.acciojob.Library_Management_System.Exceptions.InputValueNotFoundException;
import com.acciojob.Library_Management_System.Exceptions.InsufficientInputException;
import com.acciojob.Library_Management_System.Repositories.AuthorRepository;
import com.acciojob.Library_Management_System.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public String addBook(Book book,Integer authorId, String authorName) throws Exception {
        List<Author> allAuthors = authorRepository.findAll();
        if (authorId!=null){
            Author author= authorRepository.findById(authorId).get();
            book.setAuthor(author);

        }else if(!authorName.isEmpty()&&authorName!=null){
            for (Author author: allAuthors){
                if(author.getAuthorName().equals(authorName)){
                    book.setAuthor(author);
                }
            }
        }
        bookRepository.save(book);
        return "Book  "+book.getTitle()+"  has been saved to DB";
    }

    public List<String> getBooksOfAuthor(Integer authorId, String authorName) throws Exception{
        List<Book>allBooks = bookRepository.findAll();
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
                throw new InputValueNotFoundException("Please Provide the applicable authorId");
            }
            return ans;
        }
        else if (authorName!=null&& !authorName.isEmpty()) {
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
        else {
            throw new InsufficientInputException("You should give either AuthorId Or AuthorName");
        }
    }

    public String associateBookAndAuthor(Integer bookId, Integer authorId) throws Exception {
        Book book= bookRepository.findById(bookId).orElseThrow(()->new InputValueNotFoundException("Book not found with Id "+ bookId));
        Author author = authorRepository.findById(authorId).orElseThrow(()->new InputValueNotFoundException("Book not found with Id "+ authorId));
        book.setAuthor(author);
        author.setNoOfBooks(author.getNoOfBooks()+1);
        bookRepository.save(book);
        authorRepository.save(author);
        return "Book " + book.getTitle()+ " And Author " +author.getAuthorName()+ " associated  Successfully";
    }
}
