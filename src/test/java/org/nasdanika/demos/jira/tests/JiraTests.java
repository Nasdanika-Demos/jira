package org.nasdanika.demos.jira.tests;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.nasdanika.demos.jira.JiraDemo;

public class JiraTests {
	
	@Test
	public void testIssueClient() throws Exception {
		JiraDemo jiraDemo = new JiraDemo();
		jiraDemo.demoIssueClient();
	}
	
	@Test
	public void testIssueClientWithInterceptor() throws Exception {
		JiraDemo jiraDemo = new JiraDemo();
		jiraDemo.demoIssueClientWithInterceptor();
	}
	
	@Test
	public void testIssueClientWithRateLimit() throws Exception {
		JiraDemo jiraDemo = new JiraDemo();
		jiraDemo.demoIssueClientWithRateLimit();
	}
	
	@Test
	public void testModel() throws IOException {
		JiraDemo jiraDemo = new JiraDemo();
		jiraDemo.createResource();
		jiraDemo.readResource();
	}	
	
}
