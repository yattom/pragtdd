package jp.yattom.webdb69.rule;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ContextOnly implements TestRule {

    private String name;

    public ContextOnly(String name) {
        this.name = name;
    }

    @Override
    public Statement apply(final Statement base, Description desc) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                Assume.assumeThat(name, is("local"));
                base.evaluate();
            }
        };
    }

}
