package com.leyou.httpdemo;

import com.leyou.httpdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpDemoApplication.class)
public class HttpDemoApplicationTests {
	/**
	 * Spring提供RestTemplate模板工具类
	 * 		对基于Http客户端进行封装，并实现对象与json的序列化和反序列化
	 *		没有限定Http的客户端类型，进行抽象，常用的三种都支持
	 *			HttpClient
	 *			OkHttp
	 *			JDK原生的URLConnection（默认）
	 */

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void httpGet() {//会自动发起请求，接收响应，并帮助对响应结果进行反序列化
		User user = this.restTemplate.getForObject("http://localhost/selectUser", User.class);
		System.out.println(user);
	}

}
