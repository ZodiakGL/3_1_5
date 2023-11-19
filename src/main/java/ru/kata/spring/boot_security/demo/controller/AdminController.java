package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/api/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (users != null) {
			return new ResponseEntity<>(users, users.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser (@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		return user != null
				? new ResponseEntity<>(user, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create")
	public ResponseEntity<User> create(@RequestBody User user) {
		System.out.println("User added");
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/edit")
	public ResponseEntity<User> editUser(@RequestBody User user, @RequestParam("listRoles") ArrayList<Long> roles) {
		if(roles != null) {
			user.setRoles(roleService.findByIdRoles(roles));
		} else {
			user.setRoles(roleService.getRolesByUserId(user.getId()));
		}
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getRoles() {
		List<Role> roles = new ArrayList<>();
		roles.add(userService.findById((long)1));
		roles.add(userService.findById((long)2));
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

}
