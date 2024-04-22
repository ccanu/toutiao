package com.heima.app.gateway.filter;


import com.heima.app.gateway.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {@Override
	
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//1. リクエストとレスポオンスを取得
	    	ServerHttpRequest request = exchange.getRequest();
	    	ServerHttpResponse response = exchange.getResponse();
	    //2.ログインでしたらパス
	    	if(request.getURI().getPath().contains("/login")) {
	    		return chain.filter(exchange);
	    	}
	    //3.トークンを取得
	    	String token = request.getHeaders().getFirst("token");
	    	
	    //4.トークンあるか
	    	if(StringUtils.isBlank(token)) {
	    		response.setStatusCode(HttpStatus.UNAUTHORIZED);
	    		return response.setComplete();
	    	}
	    //5.トークンが有効か
	    	
	    	try {
	    		Claims claimsBody = AppJwtUtil.getClaimsBody(token);
		    	int result = AppJwtUtil.verifyToken(claimsBody);
		    	if(result == 1 || result == 2) {
		    		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		    		return response.setComplete();
		    	}
	    		
	    	} catch (Exception e) {
				e.printStackTrace();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
	    		return response.setComplete();
			}
	    	
	    	
	    	
	    //6.パス
		return chain.filter(exchange);
	}



   /**
    * 数値が小さければ小さいほど、優先度が高い
    * 
    */
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
