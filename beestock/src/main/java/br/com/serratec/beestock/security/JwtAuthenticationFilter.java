package br.com.serratec.beestock.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.serratec.beestock.model.UserModel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
			if(user.getCpf()!= null){
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getCpf(),
						user.getPassword(), new ArrayList<>());
				Authentication auth = authenticationManager.authenticate(authToken);
				return auth;
			}else{
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getCpf(),
				user.getPassword(), new ArrayList<>());
				Authentication auth = authenticationManager.authenticate(authToken);
				return auth;
			}
		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar usuário", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String name = ((UserDetailsSecurity)authResult.getPrincipal()).getUsername();
		System.out.println("SUCESSO");
        String token = jwtUtil.genereteToken(name);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");	}
}

