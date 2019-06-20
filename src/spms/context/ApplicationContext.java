package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ApplicationContext {
	
	// Properties파일을 토대로 객체 생성하면 담을 보관소
	Hashtable<String, Object> objTable = new Hashtable<>();
	
	//객체를 꺼냄
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	//ApplicationContext 생성자. Properties 파일 로딩
	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObjects(props);
		injectDependency();
	}
	
	//객체 준비
	private void prepareObjects(Properties props) throws Exception {
		//Tomcat 서버에 등록된 객체를 찾기 위함
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		for (Object item : props.keySet()) {
			key = (String) item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")) {
				objTable.put(key, ctx.lookup(value));
			}else {
				objTable.put(key, Class.forName(value).getDeclaredConstructor().newInstance());
			}
		}
	}
	// 객체 의존 할당
	private void injectDependency() throws Exception {
		for (String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		for (Method m : obj.getClass().getMethods()) {
			if(m.getName().startsWith("set")) {
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null) {
					m.invoke(obj, dependency);
				}
			}
		}
	}
	
	private Object findObjectByType(Class<?> type) {
		for (Object obj : objTable.values()) {
			if(type.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}
}
