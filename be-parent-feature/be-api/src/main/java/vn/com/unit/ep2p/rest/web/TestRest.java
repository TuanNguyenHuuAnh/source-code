package vn.com.unit.ep2p.rest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.unit.ep2p.core.ers.service.UserService;

@RestController
@RequestMapping("/test")
public class TestRest {

	@Autowired
	private UserService userService;

	@GetMapping("/init")
	public void test() {
		userService.testUpdateUserByUsername("test");
	}
}
