import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.shoestp.mains.service.transform.MetaToViewService;
import com.shoestp.mains.utils.xwt.HttpRequestBuilder;

/**
 * @description: 测试
 * @author: lingjian
 * @create: 2019/5/9 13:34
 */
public class Test111 {

  public static void main(String[] args) throws IOException {
    Integer id = 1;
    Map<String, String> map = new HashMap(16);
    map.put("id", id.toString());
    String sendUrl = "http://localhost:8080/web/goods/shoes/detail";
    String result =
        HttpRequestBuilder.create(sendUrl, HttpRequestBuilder.request_method_get)
            .addParam(map)
            .fetchStringContent();
    System.err.println("===================");
    System.err.println(result);
    System.err.println("===================");
    JSONObject resultObject = JSONObject.parseObject(result);
    JSONObject json = JSONObject.parseObject(resultObject.getString("result"));
    System.err.println("===================");
    System.err.println(json.getString("name"));
    System.err.println("===================");
      System.err.println("===================");
      System.err.println(JSONObject.parseObject(json.getString("enterprise")).getString("id"));
      System.err.println("===================");
  }
}
