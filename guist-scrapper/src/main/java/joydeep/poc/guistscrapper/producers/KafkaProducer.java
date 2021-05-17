package joydeep.poc.guistscrapper.producers;

import joydeep.poc.guistscrapper.domains.Department;
import joydeep.poc.guistscrapper.domains.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {
    private static final String DEPT_TOPIC="dept-topic";
    private static final String FAC_TOPIC="fact-topic";
    @Autowired
    @Qualifier("department")
    private KafkaTemplate<String, Department> greetingKafkaTemplate;

    @Autowired
    @Qualifier("faculty")
    private KafkaTemplate<String, Faculty> facultyKafkaTemplate;



    public void produceDepartment(Department department) {
        ListenableFuture<SendResult<String, Department>> future = greetingKafkaTemplate.send(DEPT_TOPIC, department);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Department>>() {

            @Override
            public void onSuccess(SendResult<String, Department> result) {
                System.out.println("Sent department=[" + department + "] with offset=[" + result.getRecordMetadata()
                        .offset()
                        + "]");
            }

            @Override
            public void onFailure(Throwable exception) {
                System.out.println("Unable to send department=[" + department + "] due to : " + exception.getMessage());
            }
        });
    }

    public void produceFaculty(Faculty faculty) {
        ListenableFuture<SendResult<String, Faculty>> future = facultyKafkaTemplate.send(FAC_TOPIC, faculty);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Faculty>>() {

            @Override
            public void onSuccess(SendResult<String, Faculty> result) {
                System.out.println("Sent faculty=[" + faculty + "] with offset=[" + result.getRecordMetadata()
                        .offset()
                        + "]");
            }

            @Override
            public void onFailure(Throwable exception) {
                System.out.println("Unable to send faculty=[" + faculty + "] due to : " + exception.getMessage());
            }
        });
    }

}
