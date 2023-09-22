package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	@GetMapping("/home")
	public String home() {
		// ==> [/WEB-INF/board/home.jsp]
		return "home";
	}
	
	// 아래 주소처럼 local뒤에 붙여서 갈라하면 안됨. 외부에서 접근할 수 없음 
	// ==> [/WEB-INF/board/home1.jsp]
	@GetMapping("/home1")
	public void home1() {
	}

	@GetMapping("/model")
	public String model(Model model) {
		model.addAttribute("data", "홍길동");
		return "model";
	}
}
