/*
 * Copyright 2006-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.dsl.runner;

import com.consol.citrus.TestAction;
import com.consol.citrus.TestCase;
import com.consol.citrus.actions.EchoAction;
import com.consol.citrus.actions.TraceVariablesAction;
import com.consol.citrus.container.*;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.builder.BuilderSupport;
import com.consol.citrus.dsl.builder.TemplateBuilder;
import com.consol.citrus.report.TestActionListeners;
import com.consol.citrus.testng.AbstractTestNGUnitTest;
import org.easymock.EasyMock;
import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.easymock.EasyMock.*;

public class TemplateTestRunnerTest extends AbstractTestNGUnitTest {
    
    private ApplicationContext applicationContextMock = EasyMock.createMock(ApplicationContext.class);
    
    @Test
    public void testTemplateBuilder() {
        Template rootTemplate = new Template();
        rootTemplate.setName("fooTemplate");
        
        List<TestAction> actions = new ArrayList<TestAction>();
        actions.add(new EchoAction());
        actions.add(new TraceVariablesAction());
        rootTemplate.setActions(actions);
        
        reset(applicationContextMock);

        expect(applicationContextMock.getBean(TestContext.class)).andReturn(applicationContext.getBean(TestContext.class)).once();
        expect(applicationContextMock.getBean("fooTemplate", Template.class)).andReturn(rootTemplate).once();
        expect(applicationContextMock.getBean(TestActionListeners.class)).andReturn(new TestActionListeners()).once();
        expect(applicationContextMock.getBeansOfType(SequenceBeforeTest.class)).andReturn(new HashMap<String, SequenceBeforeTest>()).once();
        expect(applicationContextMock.getBeansOfType(SequenceAfterTest.class)).andReturn(new HashMap<String, SequenceAfterTest>()).once();

        replay(applicationContextMock);

        MockTestRunner builder = new MockTestRunner(getClass().getSimpleName(), applicationContextMock) {
            @Override
            public void execute() {
                applyTemplate(new BuilderSupport<TemplateBuilder>() {
                    @Override
                    public void configure(TemplateBuilder builder) {
                        builder.name("fooTemplate")
                                .parameter("param", "foo")
                                .parameter("text", "Citrus rocks!");
                    }
                });
            }
        };

        TestCase test = builder.getTestCase();
        Assert.assertEquals(test.getActions().size(), 1);
        Assert.assertEquals(test.getActions().get(0).getClass(), Template.class);
        Assert.assertEquals(test.getActions().get(0).getName(), "fooTemplate");
        
        Template container = (Template)test.getActions().get(0);
        Assert.assertEquals(container.isGlobalContext(), true);
        Assert.assertEquals(container.getParameter().toString(), "{param=foo, text=Citrus rocks!}");
        Assert.assertEquals(container.getActions().size(), 2);
        Assert.assertEquals(container.getActions().get(0).getClass(), EchoAction.class);
        Assert.assertEquals(container.getActions().get(1).getClass(), TraceVariablesAction.class);
        
        verify(applicationContextMock);
    }
    
    @Test
    public void testTemplateBuilderGlobalContext() {
        Template rootTemplate = new Template();
        rootTemplate.setName("fooTemplate");
        
        List<TestAction> actions = new ArrayList<TestAction>();
        actions.add(new EchoAction());
        rootTemplate.setActions(actions);
        
        reset(applicationContextMock);

        expect(applicationContextMock.getBean(TestContext.class)).andReturn(applicationContext.getBean(TestContext.class)).once();
        expect(applicationContextMock.getBean("fooTemplate", Template.class)).andReturn(rootTemplate).once();
        expect(applicationContextMock.getBean(TestActionListeners.class)).andReturn(new TestActionListeners()).once();
        expect(applicationContextMock.getBeansOfType(SequenceBeforeTest.class)).andReturn(new HashMap<String, SequenceBeforeTest>()).once();
        expect(applicationContextMock.getBeansOfType(SequenceAfterTest.class)).andReturn(new HashMap<String, SequenceAfterTest>()).once();

        replay(applicationContextMock);

        MockTestRunner builder = new MockTestRunner(getClass().getSimpleName(), applicationContextMock) {
            @Override
            public void execute() {
                applyTemplate(new BuilderSupport<TemplateBuilder>() {
                    @Override
                    public void configure(TemplateBuilder builder) {
                        builder.name("fooTemplate")
                                .globalContext(false);
                    }
                });
            }
        };

        TestCase test = builder.getTestCase();
        Assert.assertEquals(test.getActions().size(), 1);
        Assert.assertEquals(test.getActions().get(0).getClass(), Template.class);
        Assert.assertEquals(test.getActions().get(0).getName(), "fooTemplate");
        
        Template container = (Template)test.getActions().get(0);
        Assert.assertEquals(container.isGlobalContext(), false);
        Assert.assertEquals(container.getParameter().size(), 0L);
        Assert.assertEquals(container.getActions().size(), 1);
        Assert.assertEquals(container.getActions().get(0).getClass(), EchoAction.class);
        
        verify(applicationContextMock);
    }
}