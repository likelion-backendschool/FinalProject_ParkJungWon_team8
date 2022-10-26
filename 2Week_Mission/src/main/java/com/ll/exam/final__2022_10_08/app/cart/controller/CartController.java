package com.ll.exam.final__2022_10_08.app.cart.controller;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public String itemList(@AuthenticationPrincipal MemberContext memberContext, Model model) {

        List<CartItem> cartItems = cartService.getItemsByMemberId(memberContext.getId());

        model.addAttribute("cartItems", cartItems);

        return "cart/list";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {

        cartService.deleteItem(id);

        return "redirect:/cart/list";

    }

    @GetMapping("/add/{id}")
    public String addItem(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext) {

        CartItem cartItem = cartService.addItem(memberContext.getMember(), id);

        return "redirect:/cart/list";
    }

}
