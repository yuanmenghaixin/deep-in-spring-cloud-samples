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

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class GrayRequestInterceptor implements RequestInterceptor {

    private TrafficRule rule;

    public GrayRequestInterceptor(TrafficRule rule) {
        this.rule = rule;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (rule.getType().equalsIgnoreCase("header")) {
            if (template.headers().containsKey(rule.getName())) {
                String value = template.headers().get(rule.getName()).iterator().next();
                if (value.equals(rule.getValue())) {
                    RibbonRequestContextHolder.getCurrentContext().put("Gray", Boolean.TRUE.toString());
                }
            }
        } else if (rule.getType().equalsIgnoreCase("param")) {
            if (template.queries().containsKey(rule.getName())) {
                String value = template.queries().get(rule.getName()).iterator().next();
                if (value.equals(rule.getValue())) {
                    RibbonRequestContextHolder.getCurrentContext().put("Gray", Boolean.TRUE.toString());
                }
            }
        }
    }
}
