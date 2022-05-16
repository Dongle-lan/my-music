package com.it.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
public class SxzfDemoController {
    private final String APP_ID = "2021000118649115";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJAaCBX1Jvb/sw1lr/dBB4T5dmLlv2pynp7uCsQw4ebGnhCeDCvCbK+rpBqrFETqs1xKLmXHnjuyUBf3V0qWwAhZ01guBFQf4NuyskiGg1P+00yBk0qnx5ggxaFCho7qejVFuM25mJaH13kFExUgf/ZVK6m4VqhiwDKd+ScOwSGhsbT4cNoNNR8F6ILXx9hX415MUWbKqZqEMbg9bL2ipx3WXt/eL2aVgfpGwep/xpFTwrtqDKOQZMtspu0zCVh/UTdWL9UY9mzL7o5W1hUwQZkga+hu5/GwaMIXUrQkRBEepSjItNmqDe0BERv4KfWhK7bBRC+eo0SWwiC6lCgJY/AgMBAAECggEBAKd8g5FJLBdO56857XkN7OtS5oRt/JL5lyMluG/RL+KJ02+AG5gnzuszIB+3ax3PrzmOL25jf+0R8zPcULJ0uVl1/BiqhNUwt9AZNUmrn6k/Kxff46DOrglRI5mrUkCdG3IsyFwGX1jviBwoVH8UKzXT56s2C2VHh0Tbjz8meI5bWDvsMkfnRAA5vjO4+jrLPcUbO18LXCVDhy6DvkflE15fQibslQnsbps28albe3ClC+pkbg0lMQ7kcMnAPTzw9GxMN6WGNeCPv9/DBbvbfwgc0uD5yq8VjYzOqwAo9/PUbZUKQJ52/z9G6X7Q5A2OKuk15jP6DISs6far2HwcjwECgYEA579m4WHNheMYsdLpQXHih5veimi8h3w5RWlfqYRFTSFlHtOec+3cIQuc204Efpr49ag/rqHXVbD52E2HoeLHsZAMf2yO1duTFxFbi+awudamm5ODUl1L3+BGdL360aAPKjOLsBtBHS5x7hK2noh2S/osjtC78aU9m1NFo4oZd4cCgYEA3gqnswDWxaJ1RbwiL9oH9T/5LPqdLtAtc4XV9ZHSsVgIg64Rce2d+g+6ZdWaPjeRhywnjHC/m4McIbF0VCF4ZnrH55uSXdpoFUepNUR5D10d2NbL5lkDcmrqt3NC1ZQgXyrRWZ4/32S7QTKEHVPJCLoLKz28Wx/XUSDhGpwtKYkCgYAzDul0yJ6GXAVp0EUR5Sx6oi3Cota+pvDbSR8Jcxntp2jnBMrJUeOI6E0TyDmXRz+IqBv3trKBr68sJ9C1KwjCJzGJtXo2xdy/XVSlGxteplsotbReJi38UiZTvDORkckljBu/nhGXg08Ym8jeXsRpcuH0SSrPT4+mNA+Iu3lm6wKBgFwKEqWyrVRTGUWEzK8FM9NHZ2Rrpggnpw+B5MTcY3e7xBvBZd3R33AuYNehiURNCHf9p48hlWtDF8AnrM7K6Puh3Yd2B1/8DrTzdYJtFbl2oc6mivK65yDgjNbqYkPiGOEaAJmjbVqOiMhnxjfZ6TVC2SpddtxXAIvSkzzSjC3xAoGBAJzDCPDKEIDYFtdeQ7cjPaczbggoSDKucCeYgL2Nal+F04ku0p+9JrVNdJQcFO7+W0K1g7pspYnfshfDNmLTadaI/fs8IRxf8udXiQuBAiN05g8gBKgojt8L2pIKwYPH779vswteqTc3crEzxbehvqupbJArETrPJ6qqiXwcyjHk";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtov9XDWD3J/PYaBFwBt0TS4AmwkzjboSKDN6WjC7F9FozzJMJvwbGUI439RGkZom2g0oLKhlgB32PwjS7XkBZIQMwEHdjqRT4Z+ohg19YutpcPDZmpF8p/Y7zh6EEJ/xhtYMKMD7sG/sXDUh+7jR6CxPnnHOOQS0L6rbEpN2RP2I0ZJJRvRs90zlaXJL2A6B3jSQ5hByEdilYqAsoUhfTdnr3zy9osoeAJp+WnI2bpdpFre+mt98WYnf8hK09Zf3pgn4lJboU8b+VQsQiV5izVpICX4kzriLO6rsgfum6RzYrrXi16bb/NZ9TcRSPjsipwEle0vxcRZpH4ozpJQ3JwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    //    private final String GATEWAY_URL="https://openapi.alipay.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://localhost:8888/vuespringbootmusicsys/returnUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost:8888/vuespringbootmusicsys/returnUrl";
    /**
     *测试沙箱支付
     */
    @RequestMapping("/sxzfDemo")
    public void demoPay(HttpServletResponse httpResponse) throws IOException {
        Random r=new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Integer.toString(r.nextInt(200)+20);
        //订单名称，必填
        String subject ="lan先生音乐系统的音乐订单";
        //商品描述，可空
        String body = "尊敬的会员欢迎购买lan先生的音乐";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

        System.out.println("我已经请求到了支付宝");
    }

    /*
     * 付款完毕后后回调
     * */
    @RequestMapping(value = "returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + out_trade_no);
            System.out.println("支付宝交易号=" + trade_no);
            System.out.println("付款金额=" + total_amount);

            //支付成功，修复支付状态
//            payService.updateById(Integer.valueOf(out_trade_no));
            return "ok";//跳转付款成功页面
        } else {
            return "no";//跳转付款失败页面
        }
    }
}
