package com.ewcms.monitor.web.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.PropertyGrid;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * JVM监控
 * 
 * @author wu_zhijun
 *
 */
@Controller
@RequestMapping(value = "/monitor/jvm")
@RequiresPermissions("monitor:jvm:*")
public class JvmMonitorController extends BaseController {

    @RequestMapping(value = "index")
    public String index() {
        return viewName("index");
    }
    
    @RequestMapping(value = "base/index")
    public String runtimeIndex(){
    	return viewName("base");
    }
    
    @RequestMapping(value = "base/query")
    @ResponseBody
    public Map<String, Object> runtimeQuery(){
    	List<PropertyGrid> propertyGrids = Lists.newArrayList();
    	
    	String group = "Java Runtime Info";
    	RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();

    	PropertyGrid propertyGrid = new PropertyGrid("Vm Name", rt.getVmName(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Vm Version", rt.getVmVersion(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Vm Vendor", rt.getVmVendor(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Up Time", ((float)rt.getUptime())/(1000*60*60), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Input Arguments", rt.getInputArguments(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Library Path", rt.getLibraryPath(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Class Path", rt.getClassPath(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "JVM OS Info";
    	OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    	
    	propertyGrid = new PropertyGrid("OS Name", os.getName(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("OS Version", os.getVersion(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("OS Available Processors", os.getAvailableProcessors(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("OS Architecture", os.getArch(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "JVM Memory Info";
    	ManagementFactory.getMemoryMXBean().gc();
    	
    	propertyGrid = new PropertyGrid("Init Memory", ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit()/1000000 + " MB", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("MAX Memory", ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()/1000000 + " MB", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Used Memory", ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/1000000 + " MB", group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "JVM Thread Info";
    	ThreadMXBean tm = ManagementFactory.getThreadMXBean();
    	tm.setThreadContentionMonitoringEnabled(true);
    	
    	propertyGrid = new PropertyGrid("Thread Count", tm.getThreadCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Started Thread Count", tm.getTotalStartedThreadCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("thread contention monitoring is enabled?", tm.isThreadContentionMonitoringEnabled(), group);
    	propertyGrids.add(propertyGrid);

    	propertyGrid = new PropertyGrid("if the Java virtual machine supports thread contention monitoring?", tm.isThreadContentionMonitoringSupported(), group);
    	propertyGrids.add(propertyGrid);

    	propertyGrid = new PropertyGrid("thread CPU time measurement is enabled?", tm.isThreadCpuTimeEnabled(), group);
    	propertyGrids.add(propertyGrid);

    	propertyGrid = new PropertyGrid("if the Java virtual machine implementation supports CPU time measurement for any thread?", tm.isThreadCpuTimeSupported(), group);
    	propertyGrids.add(propertyGrid);
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", propertyGrids.size());
        result.put("rows", propertyGrids);

        return result;
    }
    
    @RequestMapping(value = "thread/index")
    public String threadIndex(){
    	return viewName("thread");
    }

    @RequestMapping(value = "thread/query")
    @ResponseBody
    public Map<String, Object> threadList(){
    	ThreadMXBean tm = ManagementFactory.getThreadMXBean();
    	tm.setThreadContentionMonitoringEnabled(true);
    	
    	long [] tid = tm.getAllThreadIds();
    	ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);

    	long [][] threadArray = new long[tia.length][2];

    	for (int i = 0; i < tia.length; i++) {          
    	    long threadId = tia[i].getThreadId();

    	    long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
    	    threadArray[i][0] = threadId;
    	    threadArray[i][1] = cpuTime;
    	}

    	long [] temp = new long[2];
    	for (int j = 0; j < threadArray.length - 1; j ++){
    		for (int k = j + 1; k < threadArray.length; k++ )
    	    if (threadArray[j][1] < threadArray[k][1]){
    	        temp = threadArray[j];
    	        threadArray[j] = threadArray[k];
    	        threadArray[k] = temp;  
    	    }
    	}

    	List<Map<String, Object>> threads = Lists.newArrayList();
    	
    	for (int t = 0; t < threadArray.length; t ++) {
    		ThreadInfo ti = tm.getThreadInfo(threadArray[t][0],Integer.MAX_VALUE);
    		if (ti == null) continue;
    		
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("id", threadArray[t][0]);
    		map.put("name", ti.getThreadName());
    		map.put("state", ti.getThreadState());
    		map.put("lockName", ti.getLockName());
    		map.put("lockOwnerName", ti.getLockOwnerName());
    		map.put("cpuTime", threadArray[t][1]);
    		map.put("depth", ti.getStackTrace().length);
    		
    		List<Map<String, String>> elements = Lists.newArrayList();
    		StackTraceElement[] stes = ti.getStackTrace();
    		for(int j=0; j<stes.length; j++){
    			StackTraceElement ste = stes[j];
    			Map<String, String> elementMap = Maps.newHashMap();
    			elementMap.put("element", "+" + ste);
    			elements.add(elementMap);
    		}
    		map.put("elements", elements);

    		threads.add(map);
    	}
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", threads.size());
        result.put("rows", threads);

        return result;

    }
}
