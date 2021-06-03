package ru.nsu.ccfit.borodin.workflowExecutor;

import ru.nsu.ccfit.borodin.workflowExecutor.blocks.Block;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.BlockNotFoundException;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.FactoryException;
import ru.nsu.ccfit.borodin.workflowExecutor.exceptions.BlockCreationException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockFactory {

    private static final Logger log = Logger.getLogger(BlockFactory.class.getName());

    private final Properties config = new Properties();

    private static volatile BlockFactory factory;

    private BlockFactory() throws IOException, FactoryException {
        String cfgName = "config.cfg";

        var configStream = BlockFactory.class
                .getClassLoader()
                .getResourceAsStream(cfgName);
        if (configStream == null) {
            throw new FactoryException("Can not find config. " +
                    "It should be named " + cfgName + " and be located in src.");
        }
        config.load(configStream);
    }

    public static BlockFactory getInstance() throws IOException, FactoryException {
        if (factory == null) {
            synchronized (BlockFactory.class) {
                if (factory == null) {
                    factory = new BlockFactory();
                }
            }
        }
        return factory;
    }

    public Block getBlock(String blockName) throws BlockNotFoundException, BlockCreationException {
        if (!config.containsKey(blockName)) {
            log.severe("Block with name " + blockName + " not found in config");
            throw new BlockNotFoundException("Block with name " + blockName + " not found in config");
        }

        Block newBlock;
        try {
            var blockClass = Class.forName(config.getProperty(blockName));
            var blockObject = blockClass.getDeclaredConstructor().newInstance();
            newBlock = (Block) blockObject;
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "Factory can not find class by name", e);
            throw new BlockCreationException("Factory can not find class by name", e);
        } catch (NoSuchMethodException e) {
            log.log(Level.SEVERE, "Exception: Factory can not find constructor in block " + blockName, e);
            throw new BlockCreationException("Factory can not find constructor in block", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.log(Level.SEVERE, "Exception: Factory can not create new instance of " + blockName, e);
            throw new BlockCreationException("Factory can not create new instance of", e);
        }
        return newBlock;
    }
}