package joydeep.poc.guist.persistor.consumers;

import joydeep.poc.guist.persistor.daos.PersistenceContract;
import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private static final String DEPT_TOPIC="dept-topic";
    private static final String FAC_TOPIC="fact-topic";
    private static final String GROUPID="guist-group";
    private final PersistenceContract<Department> departmentPersistenceContract;
    private final PersistenceContract<Faculty> facultyPersistenceContract;

    public KafkaConsumer(@Qualifier("department") PersistenceContract<Department> departmentPersistenceContract, @Qualifier("faculty") PersistenceContract<Faculty> facultyPersistenceContract) {
        this.departmentPersistenceContract = departmentPersistenceContract;
        this.facultyPersistenceContract = facultyPersistenceContract;
    }

    @KafkaListener(
            topics = DEPT_TOPIC,
            containerFactory = "departmentKafkaListenerContainerFactory", groupId = GROUPID)
    public void departmentsListener(Department department) {
        logger.info("Recieved department in topic {} in groupId {} . Department details: {}", DEPT_TOPIC, GROUPID, department);
        departmentPersistenceContract.persist(department);
    }

    @KafkaListener(
            topics = FAC_TOPIC,
            containerFactory = "facultyKafkaListenerContainerFactory", groupId = GROUPID)
    public void facultiesListener(Faculty faculty) {
        logger.info("Recieved faculty in topic {} in groupId {} . Faculty details: {}", FAC_TOPIC, GROUPID, faculty);
        facultyPersistenceContract.persist(faculty);
    }
}
