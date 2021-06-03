package ru.nsu.ccfit.borodin.workflowExecutor.blocks;

import ru.nsu.ccfit.borodin.workflowExecutor.BlockType;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.util.List;

public interface Block {
    List<String> execute(List<String> text, String[] args) throws WorkflowException;

    BlockType getType();
}