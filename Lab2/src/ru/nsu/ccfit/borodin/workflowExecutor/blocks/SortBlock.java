package ru.nsu.ccfit.borodin.workflowExecutor.blocks;

import ru.nsu.ccfit.borodin.workflowExecutor.BlockType;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.util.List;
import java.util.stream.Collectors;

public class SortBlock implements Block {
    @Override
    public List<String> execute(List<String> text, String[] args) throws WorkflowException {
        if (text == null) {
            return null;
        }

        return text.stream().sorted(String::compareTo).collect(Collectors.toList());
    }

    @Override
    public BlockType getType() {
        return BlockType.InputOutput;
    }
}