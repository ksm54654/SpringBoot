package edu.pnu;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
//@WebMvcTest
class Chapter03ApplicationTests {

//	@Autowired // 
//	MockMvc mockMvc; // test에서 제공해주는 객체
	
	@Test
	void contextLoads() {
		System.out.println("contextLoads");
		
//		mockMvc.perform(get("/hello").param("name", "둘리"))
//		.andExpect(status().isOk())
////		.andExpect(content().string("Hello : 둘리"))
//		.andDo(print());
	}

}
