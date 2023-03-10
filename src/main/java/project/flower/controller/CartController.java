package project.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.flower.domain.cart.CartItem;
import project.flower.domain.member.MemberDetails;
import project.flower.service.CartService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/add/bouquet/{bouquetId}")
    public String addBouquetToCart(@PathVariable("bouquetId") Long bouquetId, @AuthenticationPrincipal MemberDetails memberDetails,
                                Model model){
        model.addAttribute("user", memberDetails.getMember());
        cartService.saveBouquet(bouquetId, memberDetails.getMember());
        return "redirect:/";
    }

    @GetMapping("/add/single/{singleId}")
    public String addSingleToCart(@PathVariable("singleId") Long singleId, @AuthenticationPrincipal MemberDetails memberDetails,
                                   Model model){
        model.addAttribute("user", memberDetails.getMember());
        cartService.saveSingle(singleId, memberDetails.getMember());
        return "redirect:/";
    }

    @GetMapping("/add/{businessId}/diy/{selfFlowerBouquetId}")
    public String addDiyBouquetToCart(@PathVariable Long selfFlowerBouquetId, @PathVariable Long businessId, @AuthenticationPrincipal MemberDetails memberDetails,
                                      Model model){

        model.addAttribute("user", memberDetails.getMember());
        model.addAttribute("memberId", memberDetails.getMember().getId());

        cartService.saveDiyBouquet(selfFlowerBouquetId, memberDetails.getMember());

        return "redirect:/diyshop/business/{businessId}";
    }



    @GetMapping("/member/cart")
    public String showCartItems(@AuthenticationPrincipal MemberDetails memberDetails,
                                Model model){
        List<CartItem> cartItems = cartService.findCartItems(memberDetails.getMember());
        model.addAttribute("cartItems", cartItems);
        return "member/cart";
    }

    @GetMapping("/member/cart/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return "redirect:/member/cart";
    }

    @GetMapping("/member/cart/edit/{cartItemId}")
    public String editCart(@AuthenticationPrincipal MemberDetails memberDetails, Model model){
        List<CartItem> cartItems = cartService.findCartItems(memberDetails.getMember());
        model.addAttribute("cartItems", cartItems);
        return "member/cartEdit";
    }
}
