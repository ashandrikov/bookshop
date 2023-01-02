package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.BookRepository;
import com.shandrikov.bookshop.repositories.CartItemRepository;
import com.shandrikov.bookshop.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.shandrikov.bookshop.utils.StringPool.BOOK_NOT_FOUND;
import static com.shandrikov.bookshop.utils.StringPool.INVALID_QUANTITY;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public List<CartItem> getAll(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    @Override
    public int addBook(long bookId, int quantity, User user){
        Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, BOOK_NOT_FOUND));
        int updatedQuantity = quantity;
        CartItem cartItem;
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserAndBook(user, book);
        if (cartItemOpt.isPresent()){
            cartItem = cartItemOpt.get();
            updatedQuantity += cartItem.getQuantity();
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setBook(book);
            cartItemRepository.save(cartItem);
        }
        if (updatedQuantity > 10) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_QUANTITY);
        if (updatedQuantity < 1) {
            cartItemRepository.deleteByUserAndBook(user.getId(), bookId);
            return 0;
        }
        cartItemRepository.updateQuantity(updatedQuantity, user.getId(), bookId);

        return updatedQuantity;
    }

    @Transactional
    @Override
    public void deleteItem(long bookId, User user){
        bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, BOOK_NOT_FOUND));
        cartItemRepository.deleteByUserAndBook(user.getId(), bookId);
    }

}
