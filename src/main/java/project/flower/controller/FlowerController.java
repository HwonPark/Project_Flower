package project.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.flower.domain.admin.Business;
import project.flower.domain.flower.FlowerColor;
import project.flower.domain.flower.FlowerType;
import project.flower.domain.flower.bouquet.FlowerBouquet;
import project.flower.domain.flower.bouquet.FlowerBouquetForm;
import project.flower.domain.flower.selfmade.FlowerSingle;
import project.flower.domain.flower.selfmade.FlowerSingleForm;
import project.flower.domain.member.MemberDetails;
import project.flower.service.CartService;
import project.flower.service.FlowerService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class FlowerController {

    private final CartService cartService;
    private final FlowerService flowerService;

    @GetMapping("/admin/businesses/{businessId}/single/{singleId}/edit")
    public String editSingleForm(@PathVariable long singleId, Model model){
        FlowerSingle single = flowerService.findSingle(singleId);
        model.addAttribute("single", single);
        model.addAttribute("flowercolor", FlowerColor.values());

        return "admin/flower/editsingle";

    }

    @PostMapping("/admin/businesses/{businessId}/single/{singleId}/edit")
    public String editSingle(@PathVariable long singleId,@ModelAttribute("form") FlowerSingleForm form, @ModelAttribute("single") FlowerSingle single){

        flowerService.editSingle(form, singleId);

        return "redirect:/admin/businesses/{businessId}";
    }

    @GetMapping("/admin/businesses/{businessId}/bouquet/{bouquetId}/edit")
    public String editBouquetForm(@PathVariable long bouquetId, Model model){
        FlowerBouquet bouquet = flowerService.findBouquet(bouquetId);
        model.addAttribute("bouquet", bouquet);
        model.addAttribute("flowercolor", FlowerColor.values());

        return "admin/flower/editbouquet";

    }

    @PostMapping("/admin/businesses/{businessId}/bouquet/{bouquetId}/edit")
    public String editBouquet(@PathVariable long bouquetId,@ModelAttribute("form") FlowerBouquetForm form, @ModelAttribute("bouquet") FlowerBouquet bouquet){

        flowerService.editBouquet(form, bouquetId);

        return "redirect:/admin/businesses/{businessId}";
    }

    @GetMapping("/admin/businesses/{businessId}/single/{singleId}/delete")
    public String deleteSingle(@PathVariable Long singleId){

        flowerService.deleteSingle(singleId);

        return "redirect:/admin/businesses/{businessId}";
    }

    @GetMapping("/admin/businesses/{businessId}/bouquet/{bouquetId}/delete")
    public String deleteBouquet(@PathVariable Long bouquetId){

        flowerService.deleteBouquet(bouquetId);

        return "redirect:/admin/businesses/{businessId}";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/flower/{flowerType}/{itemId}")
    public String showDetailFlower(@PathVariable FlowerType flowerType, @PathVariable Long itemId,
                                   @AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        String type = flowerType.getType();

        if (type.equals(FlowerType.FLOWER_BOUQUET.getType())){
            FlowerBouquet b = flowerService.findBouquet(itemId);
            Business bouquetBusiness = b.getBusiness();
            List<FlowerBouquet> bouquetList = bouquetBusiness.getBouquetList();
            model.addAttribute("flower", b);
            model.addAttribute("related", bouquetList);
        }

        else if (type.equals(FlowerType.FLOWER_SINGLE.getType())){
            FlowerSingle single = flowerService.findSingle(itemId);
            Business singleBusiness = single.getBusiness();
            List<FlowerSingle> singleList = singleBusiness.getSingleList();
            model.addAttribute("flower", single);
            model.addAttribute("related", singleList);
        }

        if (memberDetails != null){
            model.addAttribute("cartItemCount", cartService.showItemCount(memberDetails.getMember()));
        } else {
            model.addAttribute("cartItemCount", 0);
        }

        return "item";
    }
}
