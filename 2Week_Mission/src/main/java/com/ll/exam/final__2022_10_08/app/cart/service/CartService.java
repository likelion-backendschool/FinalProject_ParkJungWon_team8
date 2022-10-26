package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartItemRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public List<CartItem> getItemsByMemberId(Long memberId) {

        return cartItemRepository.findAllByMemberId(memberId);

    }

    public void deleteItem(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);

    }

    public CartItem addItem(Member member, Long productId) {

        CartItem oldCartItem = cartItemRepository.findByMemberIdAndProductId(member.getId(), productId).orElse(null);

        if (oldCartItem == null) {
            Product product = productService.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

            CartItem cartItem = CartItem.builder()
                    .member(member)
                    .product(product)
                    .build();

            cartItemRepository.save(cartItem);

            return cartItem;
        }

        return oldCartItem;
    }
}
