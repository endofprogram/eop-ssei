package org.eop.ssei.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixinjie
 * @since 2018-10-30
 */
@RequestMapping("/example")
@Controller
public class ExampleController {

	@RequestMapping("/index")
	public String index() {
		return "example/index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "example/login";
	}
	
	@RequestMapping("/success")
	public String success() {
		return "example/success";
	}
	
	@RequestMapping("/failure")
	public String failure() {
		return "example/failure";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return "example/logout";
	}
	
	@RequestMapping("/permit")
	public String permit() {
		return "example/permit";
	}
	
	@RequestMapping("/nopermit")
	public String nopermit() {
		return "example/nopermit";
	}
	
	@RequestMapping("/deny")
	public String deny() {
		return "example/deny";
	}
	
	@RequestMapping("/rolea")
	public String rolea() {
		return "example/rolea";
	}
	
	@RequestMapping("/roleb")
	public String roleb() {
		return "example/roleb";
	}
	
	@RequestMapping("/rolec")
	public String rolec() {
		return "example/rolec";
	}
	
	@RequestMapping("/roled")
	public String roled() {
		return "example/roled";
	}
	
	@RequestMapping("/rolee")
	public String rolee() {
		return "example/rolee";
	}
}
