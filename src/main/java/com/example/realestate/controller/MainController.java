package com.example.realestate.controller;

import com.example.realestate.model.CurrentUser;
import com.example.realestate.model.Listing;
import com.example.realestate.model.ListingStatus;
import com.example.realestate.model.User;
import com.example.realestate.service.ListingService;
import com.example.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ListingService listingService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String main(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            modelMap.addAttribute("currentUser", currentUser.getUser());
        }
        List<Listing> featuredList = listingService.findAllByListingStatus(ListingStatus.FEATURED);
        List<Listing> laTestThree = listingService.findTop3ByOrderByIdDesc();
        modelMap.addAttribute("featuredList", featuredList);
        modelMap.addAttribute("laTestThree", laTestThree);
        return "index";
    }

    @GetMapping("/signIn")
    public String signIn(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null)
            modelMap.addAttribute("currentUser", currentUser.getUser());
        return "signin";
    }

    @GetMapping("/register")
    public String registerGet(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null)
            modelMap.addAttribute("currentUser", currentUser.getUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/signIn";
    }

    @GetMapping("/search")
    public String search(ModelMap modelMap, @RequestParam("keyword") String keyword) {
        List<Listing> searchResult = listingService.findAllByTitleIgnoreCaseContaining(keyword);
        modelMap.addAttribute("listings", searchResult);
        return "search";


    }
}
