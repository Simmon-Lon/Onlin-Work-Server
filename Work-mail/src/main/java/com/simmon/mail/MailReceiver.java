package com.simmon.mail;

import com.rabbitmq.client.Channel;
import com.simmon.workserver.domain.Employee;
import com.simmon.workserver.domain.MailConstants;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author Simmon
 */
@Component
public class MailReceiver {

    private static final Logger LOGGER= LoggerFactory.getLogger(MailReceiver.class);

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private MailProperties mailProperties;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAX_QUEUE_NAME)
    public void handler(Message message, Channel channel){
        Employee employee=(Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();


        try {
            if (hashOperations.entries("mail_log").containsKey(msgId)){
                LOGGER.error("消息已经发送过了------>{}",msgId);
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple: 是否确认多条
                 */
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(employee.getEmail());
            helper.setSubject("入职欢迎邮件");
            helper.setSentDate(new Date());

            Context context=new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJoblevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(mimeMessage);
            LOGGER.info("邮件发送成功");
            //将msgid存入redis
            hashOperations.put("mail_log",msgId,"ok");
            channel.basicAck(tag,false);
        } catch (Exception e) {
            /**
             * 手动确认消息
             * tag: 消息序号
             * multiple: 是否确认多条
             * requeue: 是否退回到队列
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                LOGGER.error("邮件发送失败",e.getMessage());
            }
            LOGGER.error("邮件发送失败",e.getMessage());
        }
    }

}
