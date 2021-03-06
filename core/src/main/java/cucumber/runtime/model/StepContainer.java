package cucumber.runtime.model;

import cucumber.runtime.Runtime;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.BasicStatement;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StepContainer {
    private final List<Step> steps = new ArrayList<Step>();
    final CucumberFeature cucumberFeature;
    private final BasicStatement statement;

    StepContainer(CucumberFeature cucumberFeature, BasicStatement statement) {
        this.cucumberFeature = cucumberFeature;
        this.statement = statement;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void step(Step step) {
        steps.add(step);
    }

    void format(Formatter formatter) {
        statement.replay(formatter);
    }
    
    void formatSteps(Formatter formatter) {
        for (Step step : getSteps()) {
            formatter.step(step);
        }
    }

    void runSteps(Formatter formatter, Reporter reporter, Runtime runtime) {
        for (Step step : getSteps()) {
            runStep(step, formatter, reporter, runtime);
        }
    }

    void runStep(Step step, Formatter formatter, Reporter reporter, Runtime runtime) {
        formatter.step(step);
        runtime.runStep(cucumberFeature.getUri(), step, reporter, cucumberFeature.getI18n());
    }
}
