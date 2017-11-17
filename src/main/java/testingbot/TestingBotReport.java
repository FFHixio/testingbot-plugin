package testingbot;

/**
 *
 * @author testingbot.com
 */
import com.testingbot.testingbotrest.TestingbotREST;
import hudson.model.AbstractBuild;
import hudson.tasks.junit.CaseResult;
import hudson.tasks.junit.TestAction;
import java.util.Collections;
import java.util.List;

/**
 * Show videos for the tests.
 *
 */
public class TestingBotReport extends TestAction {
    public final CaseResult parent;
    private final TestingbotREST apiClient;
    private final TestingBotCredential credentials;
    
    /**
     * Session IDs.
     */
    private final List<String> ids;

    public TestingBotReport(CaseResult parent, List<String> ids) {
        this.parent = parent;
        this.ids = ids;
        credentials = TestingBotCredentials.getCredentials();
        this.apiClient = new TestingbotREST(credentials.getKey(), credentials.getSecret());
    }

    public AbstractBuild<?, ?> getBuild() {
        return parent.getOwner();
    }

    public List<String> getIDs() {
        return Collections.unmodifiableList(ids);
    }

    public String getId() {
        return ids.get(0);
    }
    
    public String getClientKey() {
        return credentials.getKey();
    }
    
    public String getHash() {
        return apiClient.getAuthenticationHash(getId());
    }

    public String getIconFileName() {
        return (ids.size() > 0) ? "/plugin/testingbot/images/24x24/logo.jpg" : null;
    }

    public String getDisplayName() {
        return "TestingBot Report";
    }

    public String getUrlName() {
        return (ids.size() > 0) ? "testingbot" : null;
    }
}