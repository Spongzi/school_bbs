package com.spongzi.utlis;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;

/**
 * Tencent Cloud Sms Sendsms
 */
public class SendSms {
    @Value("aliyun.secretId")
    private String accessKey;

    @Value("aliyun.secretKey")
    private String secretKey;

    @Value("aliyun.signName")
    private String signName;

    @Value("aliyun.templateCode")
    private String templateCode;

    public void sendSms(String mobile, String code) {
        try {
            //配置阿里云
            Config config = new Config()
                    // 您的AccessKey ID
                    .setAccessKeyId(accessKey)
                    // 您的AccessKey Secret
                    .setAccessKeySecret(secretKey);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            Client client = new Client(config);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(mobile)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSms(sendSmsRequest);

            SendSmsResponseBody body = response.getBody();

            System.out.println(body.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}