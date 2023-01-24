package project.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import project.flower.domain.member.MemberForm;
import project.flower.service.MemberService;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup/customer")
    public String signUpCustomerForm(@ModelAttribute("form") MemberForm form) {
        return "signup/customer";
    }

    @PostMapping("/signup/customer")
    public String signUpProc(@Valid @ModelAttribute("form") MemberForm form, BindingResult bindingResult) {
        log.info("name = {}, email = {}, password = {}, age = {}, sex = {}",
                form.getName(), form.getEmail(), form.getPassword(), form.getAge(), form.getSex());

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);

            // 회원가입 페이지로 다시 이동
            return "signup/customer";
        }

        // 중복 체크
        try {
            memberService.validateDuplicateMember(form.toEntity());
        } catch (Exception e) {
            bindingResult.addError(new FieldError("form", "email", e.getMessage()));
            return "signup/customer";
        }

        // 성공로직
        memberService.join(form);
        return "redirect:/login/customer";
    }

    @GetMapping("/login/customer")
    public String customerLogin() {
        return "login/customer";
    }
}
