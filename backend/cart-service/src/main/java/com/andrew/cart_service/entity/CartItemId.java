package com.andrew.cart_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemId implements Serializable {

    private Long cartId;
    private Long productId;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(productId, cartId);
    }

}
