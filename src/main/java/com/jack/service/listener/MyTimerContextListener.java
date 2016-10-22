package com.jack.service.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.jack.common.util.JackConstant;
import com.jack.task.FileMerge;
import com.jack.task.FileWalk;
import com.jack.task.JackAnalysis;

@WebListener
public class MyTimerContextListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(MyTimerContextListener.class);
	private static final long PERIOD = 5*60*1000;
	
	private Timer timer = new Timer();
	
    public MyTimerContextListener() {
    }

    public void contextInitialized(ServletContextEvent evt)  { 
    	logger.info("TimerConetxtListener initialized...");
    	
    	TimerTask jackAnalysis = new JackAnalysis();
    	this.timer.scheduleAtFixedRate(jackAnalysis, JackConstant.JACK_ANALYSIS, PERIOD);
    	
    	TimerTask fileWalk = new FileWalk();
    	this.timer.scheduleAtFixedRate(fileWalk, JackConstant.FILE_WORK, PERIOD);
    	
    	
    	TimerTask fileMerge = new FileMerge();
    	this.timer.scheduleAtFixedRate(fileMerge, JackConstant.FILE_MERGE, PERIOD);
    	
    }

    public void contextDestroyed(ServletContextEvent evt)  {
    	logger.info("TimerConetxtListener destroyed...");
    	this.timer.cancel();
    }
    
}
