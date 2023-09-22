package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.TestService1;
import edu.pnu.service.TestService2;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

	// 1번 
//	@Autowired
//	private TestService1 testService1;
//	
//	@Autowired
//	private TestService2 testService2;
//	
//	public TestController() {
//		System.out.println("TestController");
//	}
	
//	private TestService1 test1;
//	private TestService2 test2;
	
	// 2번
//	@Autowired  // controller실행하려면 service가 먼저 만들어져서 값이 들어가야 하는데 업으므로 auto로 먼저 만들고 controller를 실행한다. 
//	public TestController(TestService1 test1, TestService2 test2) {
//		this.test1 = test1;
//		this.test2 = test2;
//		
//		System.out.println("TestController");
//	}
	
	// 3번
	private final TestService1 test1;
	private final TestService2 test2;
	
	@GetMapping({"/", "home"})
	public String home() {
		return "Home";
	}
	
	@GetMapping("/test")
	public String test() {
		return test1.test();
	}
}
