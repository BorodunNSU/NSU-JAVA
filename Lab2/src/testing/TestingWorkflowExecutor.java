package testing;

import org.junit.Test;
import ru.nsu.ccfit.borodin.workflowExecutor.WorkflowExecutor;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.BlockNotFoundException;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.ParsingException;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.io.*;

public class TestingWorkflowExecutor {
    @Test
    public void commonTest() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                5 = head 2
                6 = tail 1
                csed
                1 -> 3 -> 4 -> 5 -> 6 -> 2""").getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();
    }

    @Test(expected = ParsingException.class)
    public void parsingTest1() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort""").getBytes());

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
                1 = head 2
                1 = tail 1
                csed
                1 -> 3 -> 4 -> 5 -> 6 -> 2""").getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();
    }

    @Test(expected = WorkflowException.class)
    public void sequenceTest() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                5 = head 2
                6 = tail 1
                csed
                1 -> 2 -> 5""").getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();

    }

    @Test(expected = BlockNotFoundException.class)
    public void sequenceTest2() throws WorkflowException {
        InputStream testStream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                5 = head 2
                6 = tail 1
                csed
                1 -> 3 -> 4 -> 5 -> 6 -> 7 -> 2""").getBytes());

        WorkflowExecutor workflow = new WorkflowExecutor(testStream);
        workflow.execute();
    }
}
