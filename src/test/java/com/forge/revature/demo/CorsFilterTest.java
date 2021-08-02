package com.forge.revature.demo;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.forge.revature.CorsFilter;


@SpringBootTest
public class CorsFilterTest {
	private CorsFilter cf = new CorsFilter();

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private FilterChain filterChain;
	
    @Before
    public void setUp() throws ServletException {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testDoFilterInternal() throws ServletException, IOException {
		Mockito.when(request.getHeader("Access-Control-Request-Method")).thenReturn("GET");
		this.cf.doFilter(request, response, filterChain);
		Mockito.verify(response).setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
		Mockito.verify(filterChain).doFilter(request, response);
	}
}
