package org.nasdanika.demos.jira;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.nasdanika.capability.CapabilityLoader;
import org.nasdanika.capability.ServiceCapabilityFactory;
import org.nasdanika.capability.ServiceCapabilityFactory.Requirement;
import org.nasdanika.capability.emf.ResourceSetRequirement;
import org.nasdanika.common.PrintStreamProgressMonitor;
import org.nasdanika.common.ProgressMonitor;
import org.nasdanika.models.jira.JiraFactory;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import io.atlassian.util.concurrent.Promise;

public class JiraDemo {
	
	private static final String JIRA_MODEL_PATH = "target/jira-model.xmi";
	
	public void demoIssueClient() throws Exception {
		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		URI jirURI = new URI("https://nasdanika.atlassian.net");
		try (JiraRestClient client = factory.createWithBasicHttpAuthentication(
				jirURI, 
				System.getenv("JIRA_USER"), 
				System.getenv("JIRA_API_TOKEN"))) {
			
			IssueRestClient issueClient = client.getIssueClient();
			Promise<Issue> issuePromise = issueClient.getIssue("NSD-164");
			Issue issue = issuePromise.claim();
			System.out.println(issue);
		}
	}

	
	public void createResource() throws IOException {
		// Creating a model
		JiraFactory jiraFactory = JiraFactory.eINSTANCE;
		
		org.nasdanika.models.jira.Issue modelIssue = jiraFactory.createIssue();
		modelIssue.setKey("DEMO-1");
		modelIssue.setDescription("Some description");
		
		// Saving to a file
		ResourceSet resourceSet = createResourceSet();
		org.eclipse.emf.common.util.URI resourceURI = org.eclipse.emf.common.util.URI.createFileURI(new File(JIRA_MODEL_PATH).getCanonicalPath());
		Resource resource = resourceSet.createResource(resourceURI);
		resource.getContents().add(modelIssue);
		resource.save(null);		
	}
	
	public void readResource() throws IOException {
		ResourceSet resourceSet = createResourceSet();
		org.eclipse.emf.common.util.URI resourceURI = org.eclipse.emf.common.util.URI.createFileURI(new File(JIRA_MODEL_PATH).getCanonicalPath());

		Resource resource = resourceSet.getResource(resourceURI, true);
		org.nasdanika.models.jira.Issue modelIssue = (org.nasdanika.models.jira.Issue) resource.getContents().get(0);
		
		System.out.println(modelIssue.getKey() + ": " + modelIssue.getDescription());
	}

	private ResourceSet createResourceSet() {
		CapabilityLoader capabilityLoader = new CapabilityLoader();
		ProgressMonitor progressMonitor = new PrintStreamProgressMonitor();
		Requirement<ResourceSetRequirement, ResourceSet> requirement = ServiceCapabilityFactory.createRequirement(ResourceSet.class);		
		return capabilityLoader.loadOne(requirement, progressMonitor);
	}

}
