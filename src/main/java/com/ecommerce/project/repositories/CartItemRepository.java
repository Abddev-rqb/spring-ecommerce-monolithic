package com.ecommerce.project.repositories;

import com.ecommerce.project.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.id = ?1 AND ci.product.productId = ?2")
    CartItems findCartItemByProductIdAndCartId(Long cartId, Long productId);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.id = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);
}
