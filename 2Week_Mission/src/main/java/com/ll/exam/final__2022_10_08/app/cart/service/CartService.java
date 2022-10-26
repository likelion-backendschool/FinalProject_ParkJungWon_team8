package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getItemsByMemberId(Long memberId) {

        return cartItemRepository.findAllByMemberId(memberId);

    }

    public void deleteItem(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);

    }
}
