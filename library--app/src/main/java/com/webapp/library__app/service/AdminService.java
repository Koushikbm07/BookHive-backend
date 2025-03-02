package com.webapp.library__app.service;

import com.webapp.library__app.Repository.BookRepository;
import com.webapp.library__app.Repository.CheckoutRepository;
import com.webapp.library__app.Repository.ReviewRepository;
import com.webapp.library__app.entity.Book;
import com.webapp.library__app.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public AdminService(BookRepository bookRepository, ReviewRepository reviewRepository, CheckoutRepository checkoutRepository){
        this.bookRepository=bookRepository;
        this.checkoutRepository=checkoutRepository;
        this.reviewRepository=reviewRepository;
    }


    public void increaseBookQuantity(Long bookId) throws Exception{
        Optional<Book> book=bookRepository.findById(bookId);

        if(!book.isPresent()){
            throw new Exception("Book Not Found!");
        }
        book.get().setCopies(book.get().getCopies()+1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()+1);
        bookRepository.save(book.get());
    }

    public void decreaseBookQuantity(Long bookId) throws Exception{
        Optional<Book> book=bookRepository.findById(bookId);

        if(!book.isPresent()){
            throw new Exception("Book Not Found!");
        }
        book.get().setCopies(book.get().getCopies()-1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
        bookRepository.save(book.get());
    }
    public void postBook(AddBookRequest addBookRequest){
        Book book=new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());

        bookRepository.save(book);



    }

    public void deleteBook(Long bookId) throws Exception{
        Optional<Book> book=bookRepository.findById(bookId);

        if(!book.isPresent()){
            throw new Exception("Book Not Found!");
        }

        bookRepository.delete(book.get());
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }



}
