package ru.nsu.ccfit.borodin.workflowExecutor;

import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class main {
    public static void main(String[] args) {
        InputStream stream = new ByteArrayInputStream(("""
                desc
                1 = readfile in.txt
                2 = writefile out.txt
                3 = sort
                4 = replace JavaScript ASM
                csed
                1 -> 3 -> 4 -> 3 -> 2""").getBytes());

        try {
            WorkflowExecutor executor = new WorkflowExecutor(stream);
            executor.execute();
        } catch (WorkflowException e) {
            e.printStackTrace();
        }

    }
}
