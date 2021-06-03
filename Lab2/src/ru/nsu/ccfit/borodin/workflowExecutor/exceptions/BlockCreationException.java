package ru.nsu.ccfit.borodin.workflowExecutor.exceptions;

public class BlockCreationException extends WorkflowException {
    public BlockCreationException(String message) {
        super(message);
    }

    public BlockCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockCreationException(Throwable cause) {
        super(cause);
    }

    public BlockCreationException() {
    }
}