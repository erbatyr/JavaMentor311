package com.erbatyr.crud_app_springboot.controllers;

import com.erbatyr.crud_app_springboot.models.Role;
import com.erbatyr.crud_app_springboot.models.User;
import com.erbatyr.crud_app_springboot.repositories.RoleRepository;
import com.erbatyr.crud_app_springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/user")
    public String index(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users-index";
    }

    @GetMapping("/admin")
    @ModelAttribute
    public String adminPage(Model model, Principal principal) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("newUser", new User());
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(role -> roles.add(role));
        model.addAttribute("roleSet", roles);
        User currentUser = userRepository.loadUserByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser);
        return "admin";
    }

    @PostMapping("/admin/user/new")
    public String newUserPOST(@RequestParam String email, @RequestParam String password, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String[] roles, Model model) {
        Set<Role> rolesSet = new HashSet<>();
        for (String s: roles) {
            Long i = Long.parseLong(s);
            rolesSet.add(roleRepository.findById(i).get());
        }
        userRepository.save(new User(email, password, firstName, lastName, rolesSet));
        return "redirect: /admin";
    }

    @PostMapping("/admin/user/{id}/edit")
    public String userEditPOST(@PathVariable(value = "id") Long id,@RequestParam String email, @RequestParam String password, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String[] roles, Model model) {
        Set<Role> rolesSet = new HashSet<>();
        for (String s: roles) {
            Long i = Long.parseLong(s);
            rolesSet.add(roleRepository.findById(i).get());
        }
        User user = userRepository.findById(id).get();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(rolesSet);
        userRepository.save(user);
        return "redirect: /admin";
    }

    @PostMapping("/admin/user/{id}/delete")
    public String articleDeletePost(@PathVariable(value = "id")Long id, Model model) {
        userRepository.deleteById(id);
        return "redirect: /admin/";
    }
}
