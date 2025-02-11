module org.nasdanika.demos.jira {
	
	exports org.nasdanika.demos.jira;
	
	requires jira.rest.java.client.core;
	requires atlassian.util.concurrent;
	requires jira.rest.java.client.api;
	
	requires org.nasdanika.models.jira;
	
}
