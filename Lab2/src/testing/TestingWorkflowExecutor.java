package testing;

import org.junit.Test;
import ru.nsu.ccfit.borodin.workflowExecutor.WorkflowExecutor;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.ParsingException;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.io.*;

public class TestingWorkflowExecutor {
    @Test
    public void commonTest() {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                csed
                1 -> 3 -> 4 -> 3 -> 2
                """).getBytes());
        try {
            WorkflowExecutor workflow = new WorkflowExecutor(testStream);
            workflow.execute();
        } catch (WorkflowException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = WorkflowException.class)
    public void sequenceTest() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                csed
                1 -> 3 -> 2 -> 4 -> 3 -> 1
                """).getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();

    }

    @Test(expected = ParsingException.class)
    public void parsingTest1() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("desc").getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();
    }

    @Test(expected = ParsingException.class)
    public void parsingTest2() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                1 = writefile out.txt
                1 = sort
                1 = replace JavaScript ASM
                csed
                1 -> 3 -> 4 -> 3 -> 2
                """).getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();
    }
}
