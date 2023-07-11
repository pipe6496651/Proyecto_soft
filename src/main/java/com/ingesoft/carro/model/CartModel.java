package com.ingesoft.carro.model;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartModel implements Serializable {
    private static final long serialVersionUID = -8783783030860258094L;

    private Long id;
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return items;
    }
}