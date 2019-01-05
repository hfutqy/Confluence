SpringBoot 整合 邮件发送实现

### 一、 引入maven依赖
SpringBoot对email工具有原生的支持，引入spring-boot-starter-mail即可
```
		<!-- 支持发送邮件 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
```
### 二、 配置邮件基本属性
发现和redis的配置还是很像的，直接作用于MailProperties.java
```
# email (MailProperties)
spring.mail.default-encoding=UTF-8
spring.mail.host=mail.srv
spring.mail.port=25
spring.mail.username=
spring.mail.password=
```
### 三、 邮件发送工具类
由于是SpringBoot的项目，可以直接使用boot支持的JavaMailSender接口，会自动注入邮件配置属性，调用send方法实现邮件发送。
```java
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送 client
 *
 * @author qiyu
 * @date 2018-12-17 12:29
 */
@Service
public class BaseEmailClient {

    private static final Logger logger = LoggerFactory.getLogger(BaseEmailClient.class);

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 短信发送接口
     *
     * @param from    发件人
     * @param to      收件人list
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    public void send(String from, String[] to, String subject, String text) {
        // 发件人、收件人不能为空
        if (StringUtils.isEmpty(from) || to == null || to.length == 0) {
            return;
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        // 短信发送日志记录
        logger.info(simpleMailMessage.toString());
        javaMailSender.send(simpleMailMessage);
    }
}
```
### 四、 测试
``` java
@Autowired
private BaseEmailClient baseEmailClient;

/**
 * 测试短信发送client
 */
@RequestMapping("/sendEmail")
@ResponseBody
public void sendEmail() {
    try {
        baseEmailClient.send("robot@xxx.com", new String[]{"xxx@xxx.com"}, "测试邮件发送工具类", "测试消息内容");
    } catch (Exception e) {
        logger.error("error:", e);
    }
}
```
