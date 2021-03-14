package es.codeurjc.mca.practica_1_cloud_ordinaria_2021;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("production")
@Configuration
@EnableRdsInstance(
        dbInstanceIdentifier = "${cloud.aws.rds.dbInstanceIdentifier}",
        password = "${cloud.aws.rds.p2rdsinstance.password}")
public class SpringCloudConfig {
}
