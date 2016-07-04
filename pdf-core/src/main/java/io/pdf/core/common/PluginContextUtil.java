package io.pdf.core.common;

import org.eclipse.osgi.internal.loader.ModuleClassLoader;
import org.osgi.framework.Bundle;

import io.pdf.core.PluginContext;
import io.pdf.core.internal.PluginRepository;

public class PluginContextUtil {
	
	public static String getPluginName(Class<?> clazz){
		if(clazz.getClassLoader() instanceof ModuleClassLoader){
			return ((ModuleClassLoader)clazz.getClassLoader()).getBundle().getSymbolicName();
		}
		return null;
	} 
	
	public static PluginContext getPluginContext(Class<?> clazz){
		if(clazz.getClassLoader() instanceof ModuleClassLoader){
			Bundle bundle= ((ModuleClassLoader)clazz.getClassLoader()).getBundle();
			return PluginRepository.getInstance().getPluginContext(bundle);
		}
		return null;
	} 
}
