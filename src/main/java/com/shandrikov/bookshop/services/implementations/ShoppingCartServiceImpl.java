package com.shandrikov.bookshop.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.OrderDetails;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.exceptions.BookNotFoundException;
import com.shandrikov.bookshop.exceptions.EmptyCartException;
import com.shandrikov.bookshop.exceptions.InvalidNumberBooksException;
import com.shandrikov.bookshop.repositories.BookRepository;
import com.shandrikov.bookshop.repositories.CartItemRepository;
import com.shandrikov.bookshop.repositories.OrderRepository;
import com.shandrikov.bookshop.services.ShoppingCartService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.shandrikov.bookshop.utils.StringPool.EMPTY_CART;
import static com.shandrikov.bookshop.utils.StringPool.ERROR_WHILE_MAPPING;
import static com.shandrikov.bookshop.utils.StringPool.INVALID_QUANTITY_MORE_10;
import static com.shandrikov.bookshop.utils.StringPool.INVALID_QUANTITY_NO_IN_CART;
import static com.shandrikov.bookshop.utils.StringPool.NO_BOOK_ID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Value("${main.topic}")
    private String orderTopic;
    @Value("${kafka.enable}")
    private boolean kafkaEnabled;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    @Override
    public List<CartItem> getAll(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public boolean notEmpty(User user) {
        return !cartItemRepository.findByUser(user).isEmpty();
    }

    @Override
    public int addBook(long bookId, int quantity, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(NO_BOOK_ID + bookId));
        int updatedQuantity = quantity;
        CartItem cartItem;
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserAndBook(user, book);
        if (cartItemOpt.isPresent()) {
            cartItem = cartItemOpt.get();
            updatedQuantity += cartItem.getQuantity();
        } else {
            if (quantity < 1) {
                throw new InvalidNumberBooksException(INVALID_QUANTITY_NO_IN_CART);
            }
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setBook(book);
            cartItemRepository.save(cartItem);
        }
        if (updatedQuantity > 10) throw new InvalidNumberBooksException(INVALID_QUANTITY_MORE_10);
        if (updatedQuantity < 1) {
            cartItemRepository.deleteByUserAndBook(user.getId(), bookId);
            return 0;
        }
        cartItemRepository.updateQuantity(updatedQuantity, user.getId(), bookId);

        return updatedQuantity;
    }

    @Override
    public void deleteItem(long bookId, User user) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(NO_BOOK_ID + bookId));

        //Example: how to get user info in any point of the app. To clean code delete line below and change the parameter -> user.getId()
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartItemRepository.deleteByUserAndBook(auth.getId(), bookId);
    }

    @Override
    public OrderDTO createOrder(User user) {
        Order order = new Order();
        List<CartItem> listCartItems = cartItemRepository.findByUser(user);
        if(listCartItems.isEmpty()) {
            throw new EmptyCartException(EMPTY_CART);
        }
        for (CartItem cartItem : listCartItems) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setBook(cartItem.getBook());
            orderDetails.setQuantity(cartItem.getQuantity());
            order.getOrderDetails().add(orderDetails);
        }
        order.setUser(user);
        cartItemRepository.deleteByUser(user);

        Order savedOrder = orderRepository.save(order);
        OrderDTO orderDTO = ObjectMapperUtils.map(savedOrder, OrderDTO.class);

        if (kafkaEnabled) {
            try {
                String orderString = mapper.writeValueAsString(orderDTO);
                kafkaTemplate.send(orderTopic, savedOrder.getId().toString(), orderString);
            } catch (JsonProcessingException e) {
                log.error(ERROR_WHILE_MAPPING);
                throw new RuntimeException(e.getMessage());
            }
        }

        return orderDTO;
    }
}
