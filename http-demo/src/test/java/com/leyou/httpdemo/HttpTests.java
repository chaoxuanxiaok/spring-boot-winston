package com.leyou.httpdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.httpdemo.pojo.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * HttpClient的使用 以及Json转换工具
 * @author: winston
 * @create: 2018-05-06 09:53
 **/
public class HttpTests {
    /**
     * HttpClient   是Http Components下的一个组件
     *      特点：
     *          基于标准、纯净的Java语言，实现 http1.0和http1.1
     *          以可扩展的面向对象的结构 实现http全部方法 （GET/POST/PUT/DELETE/HEAD/OPTIONS/TRACE）
     *          支持HTTPS协议
     *          通过http代理建立透明连接
     *          自动处理Set-Cookie中的Cookie
     */
    CloseableHttpClient httpClient;

    @Before
    public void init() {
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void testGet() throws IOException {//发起get请求
        HttpGet request = new HttpGet("http://www.baidu.com");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }

    @Test
    public void testPost() throws IOException {//发起post请求
        HttpGet request = new HttpGet("http://www.oschina.net/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }

    @Test
    public void testGetPojo() throws IOException {//向我们自定义的接口 发起请求
        HttpGet request = new HttpGet("http://localhost/hello");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }


    /**
     * Json转换工具
     *      JacksonJson是SpringMVC内置的json处理工具
     *          objectMapper类能方便实现处理json
     */
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * 对象转json、json转对象、json转集合
     */
    @Test
    public void testJson() throws IOException {
        User user = new User();
        user.setAge(22);
        user.setId(8L);
        user.setName("柳岩");
        User user2 = new User();
        user2.setAge(222);
        user2.setId(9L);
        user2.setName("柳岩er");
            // 1.   将对象序列化成json, 序列化过程中可能出现异常 得抛掉或抓取
        String json = mapper.writeValueAsString(user);
            // 2.   将json反序列化成对象，需要两个参数：json数据、目标类字节码文件
        User u = mapper.readValue(json,User.class);
            // 3.   json转集合   无法同时将集合的字节码与目标类字节码同时传递到一个参数，需要用到Jackson的类型工厂
        String json2 = mapper.writeValueAsString(Arrays.asList(user,user2));    //先将对象组装成集合，然后序列化字符串
        List<User> users = mapper.readValue(json2,mapper.getTypeFactory().constructCollectionType(List.class,User.class));
            // 4.   json转任意复杂对象     Jackson提供了TypeReference来接收类型泛型，然后底层通过反射来获取泛型上的具体类型
        List<User> uuuu = mapper.readValue(json2,new TypeReference<List<User>>(){});        //这个包不能引入错误，是jackson的
        System.out.println("json--------"+json);
        System.out.println("u--------"+u);
        for(User uu:users){
            System.out.println("uu--------"+uu);
        }
    }


}
