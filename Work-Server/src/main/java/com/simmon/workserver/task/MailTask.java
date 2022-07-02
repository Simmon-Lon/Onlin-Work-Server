package com.simmon.workserver.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.simmon.workserver.domain.Employee;
import com.simmon.workserver.domain.MailConstants;
import com.simmon.workserver.domain.MailLog;
import com.simmon.workserver.service.IEmployeeService;
import com.simmon.workserver.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时服务
 * @author Simmon
 */

@Component
public class MailTask {

    @Resource
    private IMailLogService mailLogService;

    @Resource
    private IEmployeeService employeeService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时发送任务十秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            /*重试次数超过三次测不在重试*/
            if (3<=mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>()
                        .set("status",2)
                        .eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>()
                    .set("count",mailLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));
            Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
            /*重新发送消息*/
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,employee,new CorrelationData(mailLog.getMsgId()));
        });

    }
}
