package tutorial;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.deployment.Deployment;
import com.atlassian.bamboo.specs.api.builders.deployment.Environment;
import com.atlassian.bamboo.specs.api.builders.deployment.ReleaseNaming;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.util.BambooServer;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;

/**
 * Plan configuration for Bamboo.
 *
 * @see <a href="https://confluence.atlassian.com/display/BAMBOO/Bamboo+Specs">Bamboo Specs</a>
 */
@BambooSpec
public class PlanSpec {

    /**
     * Run 'main' to publish your plan.
     */
    public static void main(String[] args) throws Exception {
        // by default credentials are read from the '.credentials' file
        BambooServer bambooServer = new BambooServer("https://my_host_of_bamboo/bamboo");

        Plan plan = new PlanSpec().createPlan();
        bambooServer.publish(plan);

        Deployment myDeployment = new PlanSpec().createDeployment();
        bambooServer.publish(myDeployment);
    }

    Project project() {
        return new Project()
                .name("CAP")
                .key("CAP");
    }

    private Deployment createDeployment() {
        return new Deployment(new PlanIdentifier("CAP", "NEWBETEST"),
                "Deployment project name")
                .releaseNaming(new ReleaseNaming("release-1")
                        .autoIncrement(true))
                .environments(new Environment("Test environment")
                        .tasks(new ScriptTask().inlineBody("echo Hello world!")));
    }

    Plan createPlan() {
        return new Plan(project(), "My Plan", "CAPTST")
                .description("Plan created from (enter repository url of your plan)")
                .stages(
                    new Stage("Stage 1")
                            .jobs(new Job("Run", "RUNTST")
                            .tasks(new ScriptTask().inlineBody("echo Hello world!"))));
    }
}
