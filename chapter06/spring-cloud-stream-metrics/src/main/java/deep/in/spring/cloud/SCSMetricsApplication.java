/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package deep.in.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@EnableBinding({Source.class})
public class SCSMetricsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(SCSMetricsApplication.class)
            .web(WebApplicationType.SERVLET).run(args);
    }

    @Autowired
    private Source source;

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
            source.output().send(MessageBuilder.withPayload("msg1")
                .build());
            source.output().send(MessageBuilder.withPayload("msg2")
                .build());
            source.output().send(MessageBuilder.withPayload("msg3")
                .build());
        };
    }

}
