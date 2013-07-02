package com.cwa.gamecore.action;

import com.cwa.gamecore.message.GameRequest;
import com.cwa.gamecore.message.MessageFactory;
import com.cwa.gamecore.util.ClassFinder;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import org.apache.log4j.Logger;

public class ActionFactory {
    private static final Logger logger = Logger.getLogger(MessageFactory.class);

    // 单例
    private static ActionFactory instance = new ActionFactory();
    public static ActionFactory getInstance() {
        return instance;
    }
    
    //@SuppressWarnings("rawtypes")
    private Map<Integer, IAction> actionMap = new HashMap<Integer, IAction>();

    //@SuppressWarnings("rawtypes")
    private ActionFactory() {
    }

    public void init(List<String> packageList) {
        init(packageList, "Action");
    }

    public void init(List<String> packageList, String classSuffix) {
        init(packageList, Arrays.asList(classSuffix));
    }
    
    public void init(List<String> packageList, List<String> classSuffix) {
        if (packageList == null) {
            packageList = new ArrayList<String>();
            packageList.add("com.cwa");
        }

        List<Class> actionList = ClassFinder.findClasses(packageList, classSuffix);
        for (Class clazz : actionList) {
            try {
                int mod = clazz.getModifiers();
                if (Modifier.isAbstract(mod) || Modifier.isInterface(mod)) {
                    continue;
                }

                IAction action = (IAction) clazz.newInstance();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!"execute".equals(method.getName())) {
                        continue;
                    }

                    Class[] paraTypes = method.getParameterTypes();
                    if (paraTypes.length == 1 || paraTypes.length == 2) {
                        Class paraType = paraTypes[paraTypes.length - 1];

                        int mod2 = paraType.getModifiers();
                        if (Modifier.isAbstract(mod2) || Modifier.isInterface(mod2)) {
                            continue;
                        }

                        Object obj = paraType.newInstance();
                        if (obj instanceof GameRequest) {
                            GameRequest message = (GameRequest) obj;
                            int commandId = message.getCommandId();
                            if (actionMap.containsKey(commandId)) {
                                IAction oldAction = actionMap.get(commandId);
                                logger.error("MessageId重复："
                                        + commandId
                                        + ",oldAction:"
                                        + oldAction.getClass().getSimpleName()
                                        + ",newAction:"
                                        + clazz.getSimpleName());
                            } else {
                                actionMap.put(commandId, action);
                                logger.debug("注册 Action: " + commandId + "->" + clazz);
                            }
                        }
                    }
                }
            } catch (InstantiationException e) {
                logger.error("加载Action时出错：class=" + clazz.getName(), e);
            } catch (IllegalAccessException e) {
                logger.error("加载Action时出错(无访问权限)：class=" + clazz.getName(), e);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public IAction getAction(int commandId) {
        return actionMap.get(commandId);
    }
    
}
