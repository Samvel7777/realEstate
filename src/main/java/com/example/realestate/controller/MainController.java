package com.example.realestate.controller;

import com.example.realestate.model.CurrentUser;
import com.example.realestate.model.Listing;
import com.example.realestate.model.ListingStatus;
import com.example.realestate.model.User;
import com.example.realestate.repository.ListingRepository;
import com.example.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ListingRepository listingRepository;

    @GetMapping("/")
    public String main(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            modelMap.addAttribute("currentUser", currentUser.getUser());
        }
        List<Listing> featuredList = listingRepository.findAllByListingStatus(ListingStatus.FEATURED);
        List<Listing> laTestThree = listingRepository.findTop3ByOrderByIdDesc();
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
        userRepository.save(user);
        return "redirect:/signIn";
    }

    @GetMapping("/search")
    public String search(ModelMap modelMap, @RequestParam("keyword") String keyword) {
        List<Listing> searchResult = listingRepository.findAllByTitleIgnoreCaseContaining(keyword);
        modelMap.addAttribute("listings", searchResult);
        return "search";


    }
}
