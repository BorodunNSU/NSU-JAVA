package ru.nsu.ccfit.borodin.workflowExecutor.blocks;

import ru.nsu.ccfit.borodin.workflowExecutor.BlockType;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.util.ArrayList;
import java.util.List;

public class GrepBlock implements Block {
    @Override
    public List<String> execute(List<String> text, String[] args) throws WorkflowException {
        if (args == null || args.length < 1) {
            throw new WorkflowException("Not enough args for the command");
        }
        if (text == null) {
            return null;
        }
        List<String> textWithKeyWord = new ArrayList<>();
        String keyWord = args[0];
        for (String line : text) {
            if(line.contains(" " + keyWord + " ")) {
                textWithKeyWord.add(line);
            }
        }
        return textWithKeyWord;
    }

    @Override
    public BlockType getType() {
        return BlockType.InputOutput;
    }
}