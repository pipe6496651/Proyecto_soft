package com.ingesoft.carro.service.impl;

import com.ingesoft.carro.model.CartItem;
import com.ingesoft.carro.model.CartModel;
import com.ingesoft.carro.service.interfaces.CartService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit; 

@Service
public class CartServiceImpl implements CartService {

    private final RedisService redisService;

    @Autowired
    public CartServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public CartModel getCart(Long id) {
        return redisService.get("CART", id.toString());
    }

    @Override
    public void addToCart(Long id, CartItem item) {
        CartModel cart = getCart(id);
        if (cart == null) {
            cart = new CartModel(id, new ArrayList<>());
        }
        cart.getItems().add(item);
        redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);
    }

    @Override
    public void removeFromCart(Long id, Long itemId) {
        CartModel cart = getCart(id);
        if (cart != null) {
        List<CartItem> items = cart.getItems();
        items.removeIf(item -> item.getItemId().equals(itemId));
        redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);
    }
}
@Override
public void updateCartItemQuantity(Long id, Long itemId, Integer quantity) {
    CartModel cart = getCart(id);
    if (cart != null) {
        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            if (item.getItemId().equals(itemId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);
    }
}


}
