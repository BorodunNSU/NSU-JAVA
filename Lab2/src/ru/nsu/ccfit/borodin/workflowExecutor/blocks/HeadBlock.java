package ru.nsu.ccfit.borodin.workflowExecutor.blocks;

import ru.nsu.ccfit.borodin.workflowExecutor.BlockType;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.WorkflowException;

import java.util.ArrayList;
import java.util.List;

public class HeadBlock implements Block {
    @Override
    public List<String> execute(List<String> text, String[] args) throws WorkflowException {
        if (args == null || args.length < 1) {
            throw new WorkflowException("Not enough args for the command");
        }
        if (text == null) {
            return null;
        }

        int count = Integer.parseInt(args[0]);
        List<String> headText = new ArrayList<>();
        for (int i = 0; i < Math.min(count, text.size()); ++i) {
            headText.add(text.get(i));
        }

        return headText;
    }

    @Override
    public BlockType getType() {
        return BlockType.InputOutput;
    }
}